# Self Sovereign Identity



## Create DID Document

### Generate Keys

Generate Private key
```shell
ssh-keygen -t ed25519 -f ./ssi.key
```


Generate a valid ES256 Private and PublicKey

```shell
openssl ecparam -name prime256v1 -genkey -noout -out ssi-private-key.pem
openssl ec -in ssi-private-key.pem -pubout -out ssi-public-key.pem
# Upgrade to newest version needed https://github.com/auth0/java-jwt/issues/270
openssl pkcs8 -topk8 -inform pem -in ssi-private-key.pem -outform pem -nocrypt -out file.pem
```
