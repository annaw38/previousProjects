import flask
from flask import Flask
import os
import PropertyLookup_pb2, PropertyLookup_pb2_grpc
import time
import grpc

app = Flask("p2")

source = os.environ["PROJECT"]

#"<PROJECT>-dataset-1:5000" and "<PROJECT>-dataset-2:5000"
#sources = [f"{PROJECT}-dataset-1:5000", f"{PROJECT}-dataset-2:5000"]
usedServer1 = False
cache_size = 3
cache = {} #key = zipcode, values = addresses
evict_order = [] # evict from the left, try to keep whatever is on the right
used = 'cache'

@app.route("/lookup/<zipcode>")

#usedServer1 = False
def lookup(zipcode):
    zipcode = int(zipcode)
    limit = flask.request.args.get("limit", default=4, type=int)
    #source-dataset-1:5000
    failures = 0
    #global usedServer1
    #usedServer1 = False #usedServer1 is True after using server 1 and false after using server2
    exception = None
    global used
    global cache
    global evict_order
    #check if in cache first
    if zipcode in cache:
        addrs = cache[zipcode][:limit]
        evict_order.remove(zipcode)
        evict_order.append(zipcode)
        return flask.jsonify({"addrs": [f"{addrs}"], "source": used, "error": None})
    #grpc call if not in cache
    else:
        response = grpcCall(zipcode, limit)
        if not response:
            return flask.jsonify({"addrs": [None], "source": used, "error": response})
        
        if usedServer1:
            used = '1'
        else:
            used = '2'
        cache[zipcode] = response
        evict_order.append(zipcode)
        if len(cache) > cache_size:
            victim = evict_order.pop(0)
            cache.pop(victim)

        return flask.jsonify({"addrs": [f"{cache[zipcode]}"], "source": used, "error": None})
def grpcCall(zipcode, limit):
    failures = 0
    global used
    global usedServer1
    while failures < 5:
        if usedServer1 == False:
            try:
                server = grpc.insecure_channel(f"{source}-dataset-1:5000")
                stub = PropertyLookup_pb2_grpc.PropertyLookupStub(server)
                response = stub.LookupByZip(PropertyLookup_pb2.zip_data(zip = zipcode, limit=limit))
                usedServer1 = True
                break
            except grpc.RpcError as e:
                exception = str(e)
                time.sleep(0.1)
                usedServer1 = True
                #count += 1
                failures += 1
                continue
        else:
            try:
                server = grpc.insecure_channel(f"{source}-dataset-2:5000")
                stub = PropertyLookup_pb2_grpc.PropertyLookupStub(server)
                response = stub.LookupByZip(PropertyLookup_pb2.zip_data(zip = zipcode, limit=limit))
                #global usedServer1
                usedServer1 = False
                #count += 1
                break
            except grpc.RpcError as e:
                exception = str(e)
                time.sleep(0.1)
                #global usedServer1
                usedServer1 = False
                #count += 1
                failures += 1
                continue
    if failures == 5:
        if usedServer1:
        #if count % 2 != 0:
            used = '1'
        else:
            used = '2'
        return exception
    
    return response
    
def main():
    app.run("0.0.0.0", port=8080, debug=False, threaded=False)

if __name__ == "__main__":
    main()
