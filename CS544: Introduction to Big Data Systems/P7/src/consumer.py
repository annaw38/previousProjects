# consumer.py
import os, sys
from kafka import KafkaConsumer, TopicPartition
from subprocess import check_output
import pandas as pd
import pyarrow as pa
import pyarrow.parquet as parquet
import report_pb2
import json


os.environ["CLASSPATH"] = str(check_output([os.environ["HADOOP_HOME"]+"/bin/hdfs", "classpath", "--glob"]), "utf-8")

broker     = 'localhost:9092'
topic_name = 'temperatures'

def main():
    if len(sys.argv) != 2:
        print("Usage: python consumer.py <partition_number>")
        sys.exit(1)
    partition_id = int(sys.argv[1])

    # TODO: Create KafkaConsumer using manual partition assignment
    consumer = KafkaConsumer(bootstrap_servers=[broker])
    consumer.assign([TopicPartition("temperatures", partition_id)]) 
    #consumer.seek_to_beginning()
    consumer.assignment()
    #consumer.seek() 
    count = 0
    file_path_json = f"/partition-{partition_id}.json"

    
    if os.path.exists(file_path_json):
        with open(file_path_json, "r") as file:
            check_point = json.load(file)
        consumer.seek( TopicPartition("temperatures", partition_id) , check_point["offset"])
    else:
        consumer.seek(TopicPartition("temperatures", partition_id), 0)



    while True:
        batch = consumer.poll(1000)
        data = {"station_id": [], "date": [], "degrees": []}
        #consumer.assign([TopicPartition("temperatures", partition_id)]) 
        offset_data = {}
        offset_data["batch_id"] = count
        offset = consumer.position(TopicPartition("temperatures", partition_id))
        offset_data["offset"] = offset
        with open(file_path_json, "w") as f:
            json.dump(offset_data, f, indent=4)
        
        for topic_partition, messages in batch.items(): 
            for message in messages:
                report = report_pb2.Report.FromString(message.value)
                data["station_id"].append(report.station_id)
                data["date"].append(report.date)
                data["degrees"].append(report.degrees)
                #data["partition"] =message.partition
        write_hdfs(data, partition_id, count)
        count += 1
 

def write_hdfs(data, partition_id, count):
    df = pd.DataFrame.from_dict(data)
    table = pa.Table.from_pandas(df, preserve_index = False)
    #hdfs://boss:9000/data/partition-0-batch-0.parquet
    hdfs = pa.fs.HadoopFileSystem(host="boss", port=9000, replication=1, default_block_size=(1024*1024))
    file_path = f"/data/partition-{partition_id}-batch-{count}.parquet"
    # Atomic writes to HDFS
    path_tmp = file_path + ".tmp"
    with hdfs.open_output_stream(path_tmp) as f:
        # TODO: write the data
        parquet.write_table(table, f)
        hdfs.move(path_tmp, file_path)
   
    





if __name__ == '__main__':
    main()
