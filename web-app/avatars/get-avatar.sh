#!/bin/bash

for i in `seq 1 1000`; do
    wget http://lorempixel.com/100/100/ -O $i.jpg
done;