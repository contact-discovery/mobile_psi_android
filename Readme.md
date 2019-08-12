# Android Benchmarking Application for Mobile Private Contact Discovery

A small Android benchmarking application using the [C++ library for Mobile Private Contact Discovery](https://github.com/contact-discovery/mobile_psi_cpp).

## Requirements

* Phone with arm64-v8a ABI
* C++ compiler supporting C++14
* Android NDK 19.0+
* Android SDK 28+


## Build instructions

```bash
git submodule update --init --recursive
```

Open gradle project in Android Studio, build and deploy.

## Test programs

The application implements a client for the three different OPRF based PSI protocols.
The interface allows you to input an ip address, port and number of items.

The corresponding server applications can be found in the droidCrypto/test folder of the C++ library.

e.g., `droidCrypto/tests/test_psi_oprf_lowmc` runs the Garbled-Circuit based PSI protocol based on LowMC

In terminal 1:
```bash
droidCrypto/tests/test_psi_oprf_lowmc 0 20
```

In app:

* Enter IP address of server
* Enter port of application (default is 8000)
* Enter number of client items as 1024

This performs a set intersection using 2^{20} elements on the server (0) side and 2^{10} elements on the client (1) side. Only the item with index 0 is common for both sets, so the client program should only print "Intersection C0" (errors may occur based on the parameters of the cuckoo filter, but the default parameters should have an error probablity of 2^{-30}).

## References

 * **_Mobile Private Contact Discovery at Scale_** by Daniel Kales ([TU Graz](https://www.iaik.tugraz.at/content/about_iaik/people/kales_daniel/)), Christian Rechberger ([TU Graz](https://www.iaik.tugraz.at/content/about_iaik/people/rechberger_christian/)), Thomas Schneider ([TU Darmstadt](https://www.encrypto.de/tschneider)), Matthias Senker ([TU Darmstadt](https://www.encrypto.de/)), and Christian Weinert ([TU Darmstadt](https://www.encrypto.de/cweinert)) in [28. USENIX Security Symposium (USENIX Security'19)](https://www.usenix.org/conference/usenixsecurity19). Paper available on **[ePrint](https://eprint.iacr.org/2019/517)**.
