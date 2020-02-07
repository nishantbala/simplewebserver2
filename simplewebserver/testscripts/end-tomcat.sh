echo = $(curl -X POST \
  http://localhost:8080/simplewebserver/ \
  -H 'cache-control: no-cache' \
  -H "Content-Type: text/plain" \
  -d end -s)