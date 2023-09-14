# Requirements

---

KEEP IN MIND: a requirement should be validatable, by definition.
Everything that pertains “how the system works and affects
output/environment” must become a requirement, even if it was not explicitly
elicited by a stakeholder (it is the developer’s duty to inform the stakeholder
later, and seek for acceptance)

## Business requirements

The goal of our project is to develop a SAT Solver that allows the user to check
the satisfiability of a logical expression.

Satify is a tool that can be used by anyone who has a basic knowledge of boolean logic,
it is not necessary to be familiar with DIMACS format like instead is required by other SAT Solvers.
In fact, Satify permits the user to insert the logical expression in a friendly and intuitive way.
That's important for any customer because it allows using the tool without any problems, considering non-expert,
letting it usable also for didactic purposes.

Developers' hopes are that Satify could help some people to understand better the boolean logic satisfiability,
and also obtain a successful open-source project supported by anyone who wants to contribute with new features.

## Domain model requirements

The domain model of the system is composed by the following main entities:

### Symbol

A Symbol is the simplest entity of the domain and can contain different types of values like a string
representing the name or a **Variable** representing the possible assignment to a boolean value.
Initially, a symbol only contains a string that represents the name of the variable.
Only in the resolution algorithm phase the symbol can assume a value,
depending on the satisfiability of the entire expression where it is contained.

### Variable

A Variable is a record that contains a name and an optional boolean value to represent the possible assignment.

### Literal

Literal is an atomic formula (in this model **Symbol**) or its negation (**Not(Symbol)**).

### Encodings

SAT Encodings are a way to transform particular constraints into a logical expression.
Generally, they encode cardinality constraints like "at least one" or "at most one" (among given symbols).

### Expression

An expression or logical formula is an entity that can be composed by symbols and operators.
It can be also generated using **Encodings**, simplifying the process of creating a logical formula.

### Conjunction Normal Form (CNF)

CNF is a particular type of **Expression**, in fact, has some constraints that make it different from a generic
expression.
CNF expressions are considered correct if it contains **only** conjunctions of clause (and)
composed by disjunctions (or) of **Literals**.
This type of expression is necessary to solve the SAT problem with the DPLL algorithm.

### Converter

A converter applies a set of predefined rules to generate an equivalent expression in CNF.
In this case, the term equivalent means that the same set of assignments found by the resolution
algorithm for the CNF satisfy also the input expression.
Multiple types of converter are admitted.

### Solver

The solver search for a solution for the instance inserted in input.
It makes use of two main algorithms: one for the conversion of the input expression to CNF through the **Converter**,
and one for the resolution and extraction of a **Solution**.
In particular, Tseitin transformation (conversion) and DPLL algorithm (resolution) are used.

### Solution

The solution is the result of the solving process, composed by:

- **Result**: represents the effective satisfiability (SAT or UNSAT).
- **Assignments**: collects all the assignments of the variables that make the expression satisfiable.

### Problem

This entity represents the main SAT problem examples.
A problem is represented by a set of constraints that can be mapped to **Encodings**
to easily compose the instance (which is an **Expression**).

## Functional requirements

1. ### User requirements
    1. The user can insert in input a logical expression through the use of a DSL.
    2. The user can see a help section that explains how to use the DSL.
    3. The user cannot use DSL keywords as variable names.
    4. The user can import a logical expression from a text file in DIMACS format.
    5. The user can visit a section where it is possible to select some main examples of SAT problem to solve.
    6. The user has to parameterize the problem selected.
    7. The user can convert the logical expression in CNF and see the transformed formula.
    8. The user can export the CNF to a text file in DIMACS format.
    9. The user can solve the instance inserted in input.
   10. The user can see the result of the algorithm, and all the assignments that make the expression satisfiable if they exist.
   11. The user must be able to see the assignments done by the DPLL of the variables that make the logical expression
        satisfiable.

2. ### System requirements
    1. The system preprocesses the expression given in input checking its correctness.
    2. The system transforms the input into the corresponding data structure through the Internal DSL.
    3. The system applies the Tseitin transformation to the expression obtaining the CNF.
    4. The system applies the DPLL algorithm with CNF as input. The DPLL algorithm works only with CNF expressions.
    5. The system collects all the assignments of the solution.
    6. The system can also only convert the expression in CNF simply applying the Tseitin transformation.
    7. A file imported must be a text file containing the input in DIMACS format.
    8. A file exported must be a text file containing the output in DIMACS format representing the CNF.
    9. When a file is imported, the system parses the file and converts the expression into DSL format, filling the input area.
    10. When a problem is selected, the system creates the corresponding expression based on the parameters inserted by the user, then follows the solving process.

3. ## Non-functional requirements
    1. The system must be executable on the three main operating systems: Windows, Linux and MacOS.
    2. The system has to be user-friendly, providing few and intuitive buttons, help section and flexible input.
    3. The system has to be easily extensible with other algorithms for conversion and solving.
    4. The system has to be secure, avoiding any kind of code injection through the input.
    5. // TODO: performance requirements and tests
    6. // TODO: maybe

4. ## Implementation requirements
    1. The system must be implemented in Scala 3.x.x, using SBT as automation build tool and JDK 17.
    2. Developers have to exploit the functional programming paradigm preferring immutable data structures.
    3. The core of the system must be implemented following a TDD approach and using the ScalaTest framework to obtain
       readable tests following FlatSpec style.
    4. The system must be correctly documented using Scaladoc to let potential new developers understand the code and
       the
       architecture of the system.
    5. The code must be constantly formatted following a predefined code style using ScalaFMT.
    6. The releases must follow the Semantic Versioning specification.

[Previous](1-methodology.md) | [Next](3-architectural-design.md)
