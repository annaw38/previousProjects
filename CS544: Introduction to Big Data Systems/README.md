# Projects for CS544: Introduction to Big Data Systems

## P1: Counting Loans with Dockerized Shell Script
- Deploy a **virtual machine** in the cloud.
- Install **Docker** within the virtual machine.
- Write a **shell script** to automate bash comands.
- Bundle shell script as a **Docker image and container**.

## P2: gRPC and Containers
- Communicate between containers via **gRPC**, where one container set hosts the data and the other container set contains an HTTP interface to the data and a least recently used cache.
- Tolerate failures, where a container dies or is killed, with replication and retries.
- Implement a **least recently used cache**.

## P3: Large, Thread-Safe Tables
- Implement logic for uploading and processing CSV and Parquet files.
- Perform computations like summing values from specific columns.
- Manage concurrency with locking in a **multi-threaded server**.

## P4: SQL and HDFS
-  Communicate with the **SQL Server** using SQL queries.
-  Use the **WebHDFS API**.
-  Utilize **PyArrow** to interact with HDFS and process Parquet files.
-  Handle data loss scenarios.

## P5: Spark And Hive
- Use **Spark's RDD, DataFrame, and SQL interfaces** to answer questions about data the load the data into Hive to query using Spark.
- Group and optimize queries.
- Use **PySpark's machine learning API** to train a decision tree.

## P6: Cassandra, Weather Data
- Create **Cassandra** schemas involving partition and cluster keys.
- Use **Spark** to preprocess data before inserting into Cassandra schemas.
- Configure queries to achieve a tradeoff between read and write availability.
- Use prepared statements.

## P7: Kafka, Weather Data
- Set up **Kafka** producers and consumers.
- Apply streaming techniques to achieve "exactly once" semantics.
- Use manual and automatic assignment of Kafka topics and partitions.

## P8: Google Cloud Services
- Create a Virtual Machine on a public cloud and connect to it via SSH.
- Create a **GCS bucket** and upload data to it.
- Write a **DataForm** pipeline to bring data from GCS to BigQuery storage.
- Write **BigQuery** queries to manipulate geographic data.

