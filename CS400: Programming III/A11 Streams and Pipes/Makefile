SHELL := /bin/env bash

p1:
	cat midwestConsumerPriceIndex.csv | cut -d, -f5 | sort -n | tail -n1 | xargs printf "grep %s midwestConsumerPriceIndex.csv" | bash

p2:
	cat midwestConsumerPriceIndex.csv | head | tail -n+2 | cut -d, -f3 | paste -sd+ | xargs printf "scale=2; ( %s )/9\n" | bc
