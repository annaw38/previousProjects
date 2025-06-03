import flask
from flask import Flask
import os
import PropertyLookup_pb2, PropertyLookup_pb2_grpc
import time
import grpc

app = Flask("p2")

source = os.environ["PROJECT"]

usedServer1 = False #usedServer1 is True after using server 1 and false after using server2
cacheAddrs = []
cache_size = 3
cache = {} #key = zipcode, values = addresses
evict_order = [] # evict from the left, try to keep whatever is on the right
sourceUsed = 'cache'

@app.route("/lookup/<zipcode>")
def lookup(zipcode):
    zipcode = int(zipcode)
    limit = flask.request.args.get("limit", default=4, type=int)

    # check if the addrs are in the cache 
    global cacheAddrs
    cacheAddrs = LRUcache(zipcode, limit)
    if cacheAddrs != "Zipcode not in the cache":
        return flask.jsonify({"addrs": cacheAddrs, "source": "cache", "error": None})
    
    #else: used servers
    else:
        failures = 0
        global usedServer1
        exception = None
        used = 0
        while failures < 5:
            if not usedServer1:
                try:
                    print(f"Attempting to connect to {source}-dataset-1:5000", flush=True) # debugging
                    server = grpc.insecure_channel(f"{source}-dataset-1:5000")
                    stub = PropertyLookup_pb2_grpc.PropertyLookupStub(server)
                    response = stub.LookupByZip(PropertyLookup_pb2.zip_data(zip = zipcode, limit=limit)) # type message contains adddrs
                    usedServer1 = True
                    break
                except grpc.RpcError as e:
                    exception = str(e)
                    time.sleep(0.1)
                    usedServer1 = True
                    failures += 1
                    continue
            else:
                try:
                    server = grpc.insecure_channel(f"{source}-dataset-2:5000")
                    stub = PropertyLookup_pb2_grpc.PropertyLookupStub(server)
                    response = stub.LookupByZip(PropertyLookup_pb2.zip_data(zip = zipcode, limit=limit))
                    usedServer1 = False
                    break
                except grpc.RpcError as e:
                    exception = str(e)
                    time.sleep(0.1)
                    usedServer1 = False
                    failures += 1
                    continue
        if failures == 5:          
            used = '1' if usedServer1 else '2'
            return flask.jsonify({"addrs": [], "source": used, "error": f"{exception}"})   
        used = '1' if usedServer1 else '2'

    # Update the cache to store addresses
        global cache
        if response.addr is not None:
            cache[zipcode] = list(response.addr)[:8]
            global evict_order
            evict_order.append(zipcode)
            #print("cache content else statement:", cache, flush=True)
            if len(cache) > cache_size:
                victim = evict_order.pop(0)
                cache.pop(victim)
        
        return flask.jsonify({"addrs": list(response.addr), "source": used, "error": None}) # return list of addrs inside the message addr


def LRUcache(zipcode, limit):
    global cache
    global evict_order
    if zipcode in cache and limit <= 8:
        addrs = cache[zipcode][:limit]
        evict_order.remove(zipcode)
        evict_order.append(zipcode)
        return addrs
        #print("cache content if statement:", cache, flush=True)
    else:
        return "Zipcode not in the cache"
        
    #print("cache content before return value:", cache, flush=True)
    
def main():
    app.run("0.0.0.0", port=8080, debug=False, threaded=False)



if __name__ == "__main__":
    main()

