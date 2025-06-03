FROM ubuntu:24.10
RUN apt-get update && apt-get install -y wget gzip
COPY download.sh /download.sh
COPY multi.sh /multi.sh
CMD ["./multi.sh"]

