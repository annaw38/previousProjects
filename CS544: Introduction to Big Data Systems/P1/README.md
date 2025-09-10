# P1: Counting Loans with Dockerized Shell Script

## Prepare: Virtual Machine Connection

## Part 2: Multi Script

Write a `multi.sh` script first uses `download.sh` to generate wi.txt, then
counts the number of lines in wi.txt containing the text "Multifamily"
(any case).  Your script should output/print the correct number; it is
OK it generates additional output.

## Part 4: Docker Image

Create a `Dockerfile` that starts from a base image of your choosing
and includes your `multi.sh` file.  The Dockerfile should do any
installs needed for your script to run.

You should be able to create an image and container like this:

```
docker build . -t p1
docker run p1
```
