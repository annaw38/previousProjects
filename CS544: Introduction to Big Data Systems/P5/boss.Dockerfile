FROM p5-base
CMD ["sh", "-c", "/spark-3.5.5-bin-hadoop3/sbin/start-master.sh && sleep infinity"]
