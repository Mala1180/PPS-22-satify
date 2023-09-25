# Implementation

In this section, every group member describes the personal work done for the realization of the project.

---

## Luca Fabri

## Mattia Matteini

### Setup

Initially I dedicated myself to the preparation of the repository and
to the DevOps tasks, so I set up Continuous Integration, SBT, ScalaTest and ScalaFMT.

### Architecture

After that, I was involved in the design and implementation of the software architecture (MVU).
Despite there aren't too many dependencies in MVU, I decided to use a simplified version of Cake Pattern to still give robustness and
flexibility.

In particular, I made use of the _Self-Type Annotation_ and _Mixin_ mechanisms offered by Scala in order to
manage dependencies between architecture components at compile-time and to create an application instance in a simple
and
readable way.

```scala
trait MVU extends ModelComponent with ViewComponent with UpdateComponent

object Main extends App with MVU
```

### Abstract Modelling

Initially, I implemented the components using _Abstract Modelling_,
because at the start of the project I didn't know yet how
`Model`, `View` and `Update` should have been concretely implemented.
This approach permitted me to focus on high-level design, architecture testing
and to postpone implementation details.

I adopted _Abstract Modelling_ approach also when I started to implement the `Model` entity.
In fact, I knew that the latter had to be an immutable data structure,
but it wasn't really clear how the entities it was supposed to contain should have been implemented.
So I started writing a _trait_ of concepts called `State` (referring to the state of the application),
which was containing the unimplemented main model entities.

### Domain-Specific Language

For the Domain-Specific Language (DSL) creation, I decided to exploit the Scala's _extension methods_ and _implicit
conversions_.

Before all, I defined a preprocessing part where through the use of a regular expression I wrapped all the non-keywords
with quotes to make them become strings.
During this phase, I also removed all the comments and the newlines escape characters.

Then, I used _reflection_ to execute the processed input string as a Scala code.
The words previously wrapped with quotes are interpreted as strings and implicitly converted to `Symbol` objects.
The use of the _extension methods_ allowed me to call methods and compose expressions using the infix notation.

Using an Internal DSL, I obtained free and correct use of parenthesis and automatic error checking.

## Alberto Paganelli

Initially, I was involved in the analysis of the Tseitin transformation algorithm and its phases.
In order to obtain a better understanding of the algorithm,
I started to analyze the algorithm's phases, keeping an eye on how to decompose them.
Obviously, the Expression data structure was necessary to represent formulas and work with them, so, with the team, we
defined the `Expression` data structure.

Looking to the algorithm's phases, I started writing some utils method for the `Expression` object that can be used from
all.

In particular, to zip the subexpressions with new Symbols, I made use of a generic type to make the code more reusable.

After the definition of these methods, where I have made great use of Pattern Matching, it was possible to start
implementing the algorithm's phases.

My idea was to exploit the functional programming, so I defined the `Tseitin` object as a singleton object that contains
the algorithm's phases and exposing only the `Tseitin` method as the entry point of the algorithm.
In order to increase the readability of the code, I named methods as the algorithm's phases obtaining a code that is
quite self-explanatory.

```scala
def tseitin(exp: Expression): CNF = concat(substitutions(exp).flatMap(transform))
```
Given that the algorithm is a fundamental part of the project, I decided to develop it following a TDD approach.
When the algorithm was completed, I deemed it necessary to rewrite the substitution phase,
in order to obtain better performances partly by giving up readability.
Given that this algorithm's phase is recursive, I rewrote it in a `@tailrec` way ensuring that the compiler
will optimize the code.

While for the Expression utils and the algorithm I followed a TDD approach, for the GUI I decided to use a
more traditional approach, testing the code manually to verify that it works as expected.

---
[Previous](4-detailed-design.md) | [Next](6-retrospective.md)
