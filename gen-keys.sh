openssl genrsa -out src/main/resources/private.key 2048
openssl rsa -in src/main/resources/private.key -pubout -out src/main/resources/public.key
