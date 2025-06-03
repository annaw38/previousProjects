# TODO: add servicer
import grpc, PropertyLookup_pb2_grpc, PropertyLookup_pb2

channel = grpc.insecure_channel("localhost:5000")
stub = PropertyLookup_pb2_grpc.PropertyLookupStub(channel)

resp = stub.LookupByZip(PropertyLookup_pb2.zip_data(zip = 53704, limit = 8))
print(resp.addr)



