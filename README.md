# Satify

<p align="center">
<img src="src/main/resources/img/Satify_logo.png" style="width: 40%" alt="Satify logo"/>
</p>

Satify is a pure functional SAT solver written in Scala.

It wants to be **immutable**, **declarative** and **idiomatic**
still using the OOP flexibility thanks to syntactic sugar power offered by Scala 3.

Satify implements two principal algorithms:

- Tseitin transformation to convert a boolean formula in CNF (Conjunctive Normal Form).
- DPLL (Davis–Putnam–Logemann–Loveland) to solve the satisfiability problem.

Satify can simply take in input a set of clauses and say if it is satisfiable or not.
This can be done using a versatile DSL providing both math-like and human-like styles.

Moreover, Satify provides a set of problems (N-Queens, Coloring Graph, Nurse Scheduling) to test the solver.
It will try also to give an instance of the problem satisfiable if it exists.

## Requirements

To correctly run Satify, you need at least these versions of the following dependencies:

- Scala 3.3.0 (currently LTS)
- SBT 1.9.1
- Java 17 (currently LTS)

## Usage

### Downloading the jar

You can download the `jar` of the application in the
[release](https://github.com/Mala1180/PPS-22-satify/releases) page.

To execute it, run:

```bash
java -jar path/to/downloaded/jar
```

### Using sbt

If you have the project locally, you can use `sbt` to run the application:

```bash
sbt run
```

Or to run tests:

```bash
sbt test
```

---
You can also generate a new jar (it will be located in `target/scala-3.3.0/` directory):

```bash
sbt assembly
```

## Authors

- Luca Fabri ([w-disaster](https://github.com/w-disaster))
- Mattia Matteini ([Mala1180](https://github.com/Mala1180))
- Alberto Paganelli ([paga16-hash](https://github.com/paga16-hash))
