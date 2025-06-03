import table_pb2_grpc, table_pb2
import grpc
from concurrent import futures
import pyarrow as pa
import pandas as pd
import pyarrow.csv
import pyarrow.parquet
import io
import threading

count = 0
paths = {}
lock = threading.Lock() # protects count and paths

class Tables(table_pb2_grpc.TableServicer):
    def Upload(self, request, context):
        with lock:
            global count
            count += 1
            csv_path = './uploaded_file' +str(count)+'.csv'
            parquet_path = './uploaded_file'+str(count)+'.parquet'
        #"uploaded_csv": csv_path, "uploaded_parquet": parquet_path
        #copied ai search result of "bytes to csv pyarrow"
        bytes_io = io.BytesIO(request.csv_data)
        try:
            new_csv = pyarrow.csv.read_csv(bytes_io)
            pyarrow.csv.write_csv(new_csv, csv_path)
            pa.parquet.write_table(new_csv, parquet_path) # Convert csv to parquet
            with lock:
                global paths
                paths[count] = {"csv": csv_path, "parquet": parquet_path}
        except Exception as e:
            return table_pb2.response(error = str(e))
        return table_pb2.response(error = None)


    def ColSum(self, request, context):
        column = request.column
        format = request.format
        total_sum = 0
        for file in paths.values():
            path = file[format]
            if format == "csv":
                try:
                    data = pa.csv.read_csv(path).to_pandas()
                    total_sum += data[column].sum()
                except KeyError:
                    total_sum += 0
            elif format == "parquet":
                #got inspiration from piazza question @301
                schema = pa.parquet.read_schema(path).names
                if column in schema:
                    data = pa.parquet.read_table(path, columns = [column]).to_pandas()
                    total_sum += data[column].sum()
                else:
                    total_sum += 0
            else:
                return table_pb2.response(error = "Invalid format")
        return table_pb2.response(error = None, total=total_sum)

# print("start server")
server = grpc.server(
        futures.ThreadPoolExecutor(max_workers=8),
        options=[("grpc.so_reuseport", 0)])

#0.0.0.0 allias, want to listen to any single possible address
table_pb2_grpc.add_TableServicer_to_server(Tables(), server)
server.add_insecure_port("0.0.0.0:5440")
server.start()
server.wait_for_termination()
