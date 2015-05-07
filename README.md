Type Suffering: Using shapeless' HList for the regular programmer
=======
Scala is a complex language, with features so obscure that some might not be encountered by people other than advanced library designers.
Sometimes though, library designers fail to insulate the library users from that internal complexity, and much confusion and frustration ensues.
This is never more true than with Shapeless, a library that appears to be quite easy to use in the simple tutorial, but whose use in real world scenarios
requires understanding of both complex language features and the way they interact with each other.

Fortunately for most of us, Shapeless solves problems that are rarely found outside of library design. Still, learning how to work with shapeless if you've not

This project is an attempt to explain just the features necessary to do basic operations with HLists once we are not just working in a single scope on a REPL.

##LUB Constraints: Welcome to Type Aliases.
##Filter: Higher kinded types.
##Prepend: Fun with implicits. Path Dependent Types
##Aux classes: Tricks