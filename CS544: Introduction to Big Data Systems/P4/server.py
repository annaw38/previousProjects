import lender_pb2_grpc, lender_pb2
import grpc
from concurrent import futures
from sqlalchemy import create_engine,text
import pyarrow as pa
import pyarrow.parquet as parquet
import pyarrow.fs
import pandas as pd
import requests
import math
from io import BufferedReader

#hdfs = None
class Lenders(lender_pb2_grpc.LenderServicer):
    """Missing associated documentation comment in .proto file."""

    def DbToHdfs(self, request, context):
        """Load input.data from SQL server and upload it to HDFS
        """

        engine = create_engine("mysql+mysqlconnector://root:abc@mysql:3306/CS544")
        conn = engine.connect()
        #copied from https://www.red-gate.com/simple-talk/databases/sql-server/reading-and-writing-parquet-files-in-sql-server/
        query = """
            SELECT loans.*, loan_types.loan_type_name
            FROM loans
            INNER JOIN loan_types ON loans.loan_type_id = loan_types.id
            WHERE loans.loan_amount > 30000 AND loans.loan_amount < 800000
            """
        df = pd.read_sql_query(query, conn)
        table = pa.Table.from_pandas(df, preserve_index = False)

        #global hdfs
        hdfs = pa.fs.HadoopFileSystem(host="nn", port=9000, replication=2, default_block_size=(1024*1024))
        path = "/hdma-wi-2021.parquet"
        with hdfs.open_output_stream(path) as stream:
            parquet.write_table(table, stream)

        return lender_pb2.StatusString(status = "success")

    def BlockLocations(self, request, context):
        """Get the block locations of the Parquet file in HDFS
        """
        block_locations = {}
        r = requests.get(f"http://nn:9870/webhdfs/v1{request.path}?op=GETFILEBLOCKLOCATIONS")
        r.raise_for_status()

        for block in r.json()["BlockLocations"]["BlockLocation"]:
            for host in block["hosts"]:
                if host in block_locations.keys():
                    block_locations[host] += 1
                else:
                    block_locations[host] = 1

        return lender_pb2.BlockLocationsResp(block_entries = block_locations, error= None)

    def CalcAvgLoan(self, request, context):
        """Calculate the average loan amount for a given county_code
        """
        code = request.county_code
        code_path = f"/partitions/{code}.parquet"
        hdma_path = "/hdma-wi-2021.parquet"
        hdfs = pa.fs.HadoopFileSystem(host="nn", port=9000, replication=1, default_block_size=(1024*1024))
        try:
            with hdfs.open_input_file(code_path) as f:
                #reader = TextIOWrapper(BufferedReader(f))
                df = parquet.read_table(f).to_pandas()
            avg_loan = df["loan_amount"].mean()
            return lender_pb2.CalcAvgLoanResp(avg_loan = int(avg_loan), source = "reuse", error = None)

        except (OSError, FileNotFoundError) as  e :
            
            with hdfs.open_input_file(hdma_path) as f:
            #reader = TextIOWrapper(BufferedReader(f))
                table = parquet.read_table(f, filters =[[ ("county_code", "=", code)]])
            df = table.to_pandas()
            avg_loan = df["loan_amount"].mean()
            source = "create" if isinstance(e, FileNotFoundError) else "recreate"
            with hdfs.open_output_stream(code_path) as stream:
                parquet.write_table(table, stream)
            return lender_pb2.CalcAvgLoanResp(avg_loan = int(avg_loan), source = source, error = None)

print("start server")
server = grpc.server(futures.ThreadPoolExecutor(max_workers=1), options=[("grpc.so_reuseport", 0)])
#0.0.0.0 allias, want to listen to any single possible address
lender_pb2_grpc.add_LenderServicer_to_server(Lenders(), server)
server.add_insecure_port("0.0.0.0:5000")
server.start()
server.wait_for_termination()
