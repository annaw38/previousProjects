from kafka import KafkaAdminClient , KafkaConsumer
import report_pb2




broker = "localhost:9092"

consumer = KafkaConsumer(bootstrap_servers=[broker],  group_id="debug")
consumer.subscribe(["temperatures"])
assignment = consumer.assignment()

while True:
    batch = consumer.poll(1000)
    #print(batch)
    for topic_partition, messages in batch.items():
        for message in messages:
            #print(message.value)
            report = report_pb2.Report.FromString(message.value)
            data = {}
            data["station_id"] = report.station_id
            data["date"] = report.date
            data["degrees"] = round(report.degrees, 4)
            data["partition"] =message.partition
            print( data)

