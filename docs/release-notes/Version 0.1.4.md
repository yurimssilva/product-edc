# Release Notes Version 0.1.4

10.02.2023

## 0. Summary

1. [Extensions](#1-extensions)
   - [1.1 Data Encryption Extension](#11-data-encryption-extension)
     - Fixed usage of a blocking algorithm

## 1. Extensions

### 1.1 Data Encryption Extension

The encryption of the `EndpointDataReference` took up to 3 minutes unter certain circumstances.
This was fixed by using a not blocking algorithm and setting the Java CMD flag `java.security.egd` correctly.
