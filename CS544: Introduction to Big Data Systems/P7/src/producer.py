from kafka import KafkaAdminClient, KafkaProducer
from kafka.admin import NewTopic
from kafka.errors import UnknownTopicOrPartitionError
import time
from sqlalchemy import create_engine,text
import os
import report_pb2
import pandas as pd
from datetime import datetime

broker = "localhost:9092"
admin = KafkaAdminClient(bootstrap_servers=[broker])


try:
    admin.delete_topics(["temperatures"])
except UnknownTopicOrPartitionError:
    time.sleep(3)
   
#create
admin.create_topics([
    NewTopic("temperatures", replication_factor=1, num_partitions=4)
    ])


source = os.environ["PROJECT"]
engine = create_engine("mysql+mysqlconnector://root:abc@mysql:3306/CS544")
conn = engine.connect()
prev_id = 0

producer = KafkaProducer(bootstrap_servers=[broker], retries=10, acks="all")

while True:
    query = text(""" SELECT * FROM temperatures WHERE id > :prev_id ORDER BY id ASC""") 
    rows = conn.execute(query, {"prev_id": prev_id })
    results = rows.mappings().all()
    
    #print(results)

    for row in results:
        #print(row)
        prev_id = row["id"]
        report = report_pb2.Report()
        report.date = row["date"].strftime("%Y-%m-%d")
        report.degrees = row["degrees"]
        report.station_id= row["station_id"]
        val= report.SerializeToString()
        #print("LOGS FROM PRODUCER", report)
        key= bytes(row["station_id"], "utf-8")
        result = producer.send("temperatures", key = key, value= val )
        #print(result.get())
    conn.commit()
        
