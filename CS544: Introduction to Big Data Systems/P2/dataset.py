import PropertyLookup_pb2, PropertyLookup_pb2_grpc, grpc
from concurrent import futures
from zipfile import ZipFile
import csv
import pandas as pd

class PropertyLookup(PropertyLookup_pb2_grpc.PropertyLookupServicer):
	def LookupByZip(self, request, context):
		print("inside lookup by zip code")
		result = []
		#request.zip = zipcode
		#request.limit = limit
		count = request.limit
		reader = pd.read_csv("addresses.csv.gz")
		for (i, row) in reader.iterrows():
			if row["ZipCode"] == request.zip:
				addr = row["Address"]
				result.append(addr)
		result.sort()
		result = result[:count]
		addr =  PropertyLookup_pb2.addrs(addr=result)
		return addr
			



		
                	
			#copied from https://stackoverflow.com/questions/2100353/sort-csv-by-column
		#	sortedReader = sorted(reader, key = lambda row: row[0].get("Address"))
		#	for row in sortedReader:
			#	if row.get("ZipCode") == request.zip and count != 0:
				#	addr = row.get("Address")
				#	result.append(addr)
				#	count-= 1
				#if request.zip in row and count != 0:
					#addr = row[10]
					#result.append(addr)
					#count -= 1
					#addrs = [addr for addr in reader if request.zip in addr]
	#	zip.close()
	#	return result

server = grpc.server(futures.ThreadPoolExecutor(max_workers=1), options=[("grpc.so_reuseport", 0)])
PropertyLookup_pb2_grpc.add_PropertyLookupServicer_to_server(PropertyLookup(),server)
server.add_insecure_port("0.0.0.0:5000")
server.start()
server.wait_for_termination()

