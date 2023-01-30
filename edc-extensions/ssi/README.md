# Self Sovereign Identity

!! In Progess !!


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


## SSI-Settings Variables


| Name                                               | Description                                        |
|----------------------------------------------------|----------------------------------------------------|
| edc.ssi.wallet                                     | Settings for Wallet                                |
| edc.ssi.verifiable.presentation.signing.key.alias  | SETTING_VERIFIABLE_PRESENTATION_SIGNING_KEY_ALIAS  |
| edc.ssi.did.operator                               | SETTING_DID_OPERATOR                               |
| edc.ssi.verifiable.presentation.signing.method     | SETTING_VERIFIABLE_PRESENTATION_SIGNING_METHOD     |
| edc.ssi.did.connector                              | SETTING_DID_CONNECTOR                              |
| edc.ssi.wallet.storage.membership.credential.alias | SETTING_WALLET_STORAGE_MEMBERSHIP_CREDENTIAL_ALIAS |
| edc.ssi.wallet.storage.credential.alias.list       | SETTING_WALLET_STORAGE_CREDENTIAL_ALIAS_LIST       |
