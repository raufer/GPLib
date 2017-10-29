# GPLib
Genetic Programming Library in scala ( In progress ...)

[![Build Status](https://travis-ci.org/raufer/GPLib.svg?branch=master)](https://travis-ci.org/raufer/GPLib)

For an introduction of Genetic Programming, by John Koza:

https://www.amazon.com/Genetic-Programming-Computers-Selection-Adaptive/dp/0262111705

---

The purpose of this library is to allow the evolution of `Programs`, which are represented by tree data structures.

The user needs only to define:
- The `Terminal Set`
- The `Function Set

Note that each element in the Fucntion Set can be of arbitrary arity and should be a Scala function (not method)
