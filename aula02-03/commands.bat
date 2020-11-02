'DES
openssl enc -des-ecb -e -in short.txt -out short-des.bin -K 0011223344556677
openssl enc -des-ecb -d -in short-des.bin -out short-original.txt -K 0011223344556677

'AES with key length 128 bits
openssl enc -aes-128-ecb -e -in short.txt -out short-aes.txt -K 00112233445566778899aabbccddeeff
openssl enc -aes-128-ecb -d -in short-aes.txt -out short-original.txt -K 00112233445566778899aabbccddeeff 

' MAC (H-MAC)
openssl dgst -hmac mysecretkey rfc3565.txt