# Self Sovereign Identity



## Create DID Document

### Generate Keys

Generate Private key
```shell
ssh-keygen -t ed25519 -f ./ssi.key
```


Generate ES256 Private and PublicKey

```shell
openssl ecparam -name prime256v1 -genkey -noout -out ssi-private-key.pem
openssl ec -in ssi-private-key.pem -pubout -out ssi-public-key.pem
```
