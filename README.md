# Satify

<div style="text-align: center;">
<img src="src/main/resources/img/Satify_logo.png" style="width: 40%" alt="Satify logo"/>
</div>

Satify is a pure functional SAT solver written in Scala.

It wants to be **immutable**, **declarative** and **idiomatic** 
still using the OOP flexibility thanks to syntactic sugar power offered by Scala. 

Satify implements two principal algorithms:
- Tseitin transformation to convert a boolean formula in CNF (Conjunctive Normal Form).
- DPLL (Davis–Putnam–Logemann–Loveland) to solve the satisfiability problem.

Satify can simply take in input a set of clauses and say if it is satisfiable or not.
This can be done using both math-like and more verbose human-like DSLs.
Moreover, Satify provides a set of problems (N-Queens, Coloring Graph, Nurse Scheduling) to test the solver.
It will try also to give an instance of the problem satisfiable if it exists.

## Requirements
To run correctly Satify you need at least these versions of the following dependencies:
- Scala 3.3.0 (currently LTS)
- SBT 1.9.1
- Java 17 (currently LTS)

## Usage
To run Satify:
```bash
sbt run
```
To run tests:
```bash
sbt test
```

## Contributions
If you want to contribute to Satify, you can fork the repository and open a pull request.
The contributions must follow some constraints: 
- Attention to code style and quality.
- Don't use mutable data structures (if it's not strictly necessary).
- Introduce only tested code.

## Authors
- Luca Fabri ([w-disaster](https://github.com/w-disaster))
- Mattia Matteini ([Mala1180](https://github.com/Mala1180))
- Alberto Paganelli ([paga16-hash](https://github.com/paga16-hash))
