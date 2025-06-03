#!/usr/bin/bash
wget https://pages.cs.wisc.edu/~harter/cs544/data/wi2021.csv.gz 
gzip -d wi2021.csv.gz 
cat wi2021.csv > wi.txt

wget https://pages.cs.wisc.edu/~harter/cs544/data/wi2022.csv.gz
gzip -d wi2022.csv.gz 
cat wi2022.csv >> wi.txt

wget https://pages.cs.wisc.edu/~harter/cs544/data/wi2023.csv.gz
gzip -d wi2023.csv.gz 
cat wi2023.csv >> wi.txt
