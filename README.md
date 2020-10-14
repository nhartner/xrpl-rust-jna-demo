# XRPL RUST JNA DEMO

If you've coming looking for an example of calling Rust from Java to do some XRPL signing
stuffs, then sir, you are in for a treat.

## Requirements

Must have JDK 1.8+ and Maven installed.

## Running

Run the demo via maven:
 
```
mvn clean compile exec:java -Dexec.mainClass=nhartner.Signing
```

## XRPL Rust Signing Library

The XRPL Signing Library is stored in `src/main/resources` and is compiled from https://github.com/xpring-eng/signing-lib-rs-trashfire

To update the signing library, checkout that project and run:

```
cargo build
cargo package
``` 

Then copy `./target/debug/libsigning_lib_rs.dylib` to `src/main/resources` in this project.