# P2: gRPC and Containers

Web requests to the caching layer specify a zipcode, and the number of
addresses that should be returned (the "limit").  To find the answer,
cache containers will ask a dataset container via gRPC.  Requests will
alternate between the two dataset containers to balance the load.  If
one dataset server is down, temporarily or long run, the cache server
should attempt to use the other dataset server to obtain the result.

## Part 1: gRPC Server (Dataset Layer)

Define an RPC service called "PropertyLookup" in a .proto file.  It
should have a single RPC call named "LookupByZip".  This method should
accept a `zip` and `limit` (both int32 values) and return addresses in
a "repeated string" field.

The server should read Madison addresses from "addresses.csv.gz" (downloaded from https://data-cityofmadison.opendata.arcgis.com/datasets/a72d02a4fda34327ae68dd0c2fd07455_20/explore) prior to the first request so it is ready to return addresses.  Given a zipcode, it should return "limit" number of addresses (return the first ones according to an alphanumeric sort).

Create a Dockerfile.dataset that lets you build a Docker image with your code
and any necessary resources.  Note that we won't install any Python
packages (such as the gRPC tools) on our test VM, so it is important
that compiling your .proto file is one of the steps that happens
during Docker build.  Your Dockerfile should also directly copy in the
dataset at build time.

## Part 2: HTTP Server (Cache Layer)

Create an HTTP server in a "cache.py" file.  You can do this with the
help of the Flask framework: https://flask.palletsprojects.com/en/stable/.

Your cache.py program should alternate between sending requests to
dataset server 1 or 2 in order to balance load (the first request
should go to server 1).  In the "source" field of the returned JSON
value, return "1" or "2" to indicate to a client which dataset server
cache.py relied on to obtain the answer.

## Part 3: Retry

When a dataset server is down, your code in cache.py using the stub
will throw a `grpc.RpcError` exception.  When this happens, sleep
100ms, then try the other server.  If there are more failures, just
keep alternating, up to 5 times total.  At that point, specify an
informative string in the "error" field of the JSON being returned
(you can decide what it is, but one approach would be to convert the
exception to a string).

## Part 4: Caching

Imlement a cache in "cache.py" so that your caching server can
sometimes respond to HTTP requests without making a gRPC call to a
dataset server.

Specifications:
* implement an LRU cache of size 3
* a cache entry should consist of a zipcode and 8 corresponding addresses
* if an HTTP request specifies a limit <8 and there IS a corresponding cache entry, just slice the cache entry to get the desired number of addresses
* if an HTTP request specifies a limit <8 and there IS NOT a corresponding cache entry, request 8 addresses from the dataset server anyway so we can create a cache entry useful for subsequent requests
* if an HTTP request specifies a limit >8, we will not be able to use the cache to respond to the request, but you should still add the first 8 addresses to the cache (if not already present)
* caching should allow the HTTP servers to continue to function in a limited capacity even if all the dataset servers are down
* the "source" entry should be "cache" (no gRPC call necessary), or "1" or "2" (got the data from a dataset server)


