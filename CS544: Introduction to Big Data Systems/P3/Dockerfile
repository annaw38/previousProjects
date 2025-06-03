FROM ubuntu:24.10

RUN apt-get update && apt-get install -y python3 pip 
RUN python3 -m pip install grpcio-tools==1.70.0 grpcio==1.70.0 protobuf==5.29.3 pyarrow pandas --break-system-packages 
COPY server.py ./server.py
COPY table.proto ./table.proto
COPY csvsum.py ./csvsum.py
COPY parquetsum.py ./parquetsum.py
COPY bigdata.py ./bigdata.py
COPY upload.py ./upload.py
RUN python3 -m grpc_tools.protoc -I=. --python_out=. --grpc_python_out=. table.proto

CMD ["python3",  "server.py"]
