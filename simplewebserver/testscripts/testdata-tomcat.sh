#!/bin/bash
url="http://localhost:8080/simplewebserver/"
#data="1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19"
#data="10000000 2 3 4 5 6 7 8 9 9789000393 11 12 13 14 15 16 17 18 19 20"
#data="1 2 3 4 5 6 7 8 9 3.2 11 12 13 14 15 16 17 18 19 20"
data="1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000 1000000000"
for d in $data; do
   echo "fetching $url for $d"
   echo $(curl -X POST \
          $url \
          -H 'cache-control: no-cache' \
          -H "Content-Type: text/plain" \
          -d $d -s) && echo "done-$d" &
done
wait