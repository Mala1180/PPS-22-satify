# Requirements

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

A Symbol is the simplest entity of the domain and contains a value which could be of two different types: either a string that represents its name, or a bool value, which allows the Symbol to be recognized as a constant.

### Literal

Literal is an atomic formula (in this model **Symbol**) or its negation (**Not(Symbol)**).

### Expression

An Expression or propositional formula is an entity that can compose symbols by logical operators and operators by operators.

It describes the constraints to be satisfied in the solution.  

Thanks to the power of **Encodings**, its writing is simplified in case of particular constraints.

### Encodings

SAT Encodings are a way to transform particular constraints into a logical expression.
Generally, they encode cardinality constraints like "at least one" or "at most one" (among given symbols).

### Conjunction Normal Form (CNF)

CNF is a particular type of **Expression**, in fact, has some constraints that make it different from a generic
expression.
CNF expressions are considered correct if it contains **only** conjunctions of clause (and)
composed by disjunctions (or) of **Literals**.
This type of expression is necessary to solve the SAT problem with the DPLL algorithm.

### Converter

A converter applies a set of predefined rules to transform an expression into an equivalent one in CNF form.
Equivalent means that the same set of assignments found by the resolution
algorithm for the CNF satisfy also the input expression.
Multiple types of converter are admitted.

### Solver

The solver searches for a solution of the expression inserted in input.
It makes use of two main algorithms: one for the conversion of the input expression to CNF through the **Converter**,
and one for the resolution and extraction of a **Solution**.
In particular, Tseitin transformation (conversion) and DPLL algorithm (resolution) are used.

### Solution

The solution is the result of the solving process, composed by:

- **Result**: represents the effective satisfiability (SAT or UNSAT).
- **Assignments**: collects the assignments of the variables that make the expression satisfiable.
- **Status**: indicates if Assignments contains all the satisfiable assignments, or just some of them.


### Problem

This entity represents the main SAT problem examples.
A problem is represented by a set of constraints which are composed togheter to form an expression. They are connected in such a way that there's certainty that they are all satisfied, if the solution is SAT.

## Functional requirements

1. ### User requirements
    1. The user can insert in input a logical expression through the use of a DSL.
    2. The user can convert the logical expression in CNF and see the transformed formula.
    3. The user can solve an instance and get if it is SAT or UNSAT, given a logical expression in input.
    4. The user can get all the satisfiable variables assignments at once if the instance is SAT.
    5. The user can get one satisfiable variables assignment at a time if the instance is SAT, continuing to get them
        as long as there is some.
    6. The user can visit a section where it is possible to select some main examples of SAT problem to solve: NQueens, Graph Coloring, Nurse Scheduling problems.
    7. The user has to parameterize the problem selected and can solve it, by getting one satisfiable assignment at a time, as long as there is some.
    8. The user can use SAT encodings through the DSL (at most, at least, exactly).
    9. The user can see a help section that explains how to use the DSL.
    10. The user cannot use DSL keywords as variable names.
    11. The user can import a logical expression from a text file in DIMACS format.
    12. The user can export the CNF to a text file in DIMACS format.
    13. The user can see the time spent by the search algorithm to solve the instance.
    14. The user can see the time spent by the CNF transformation algorithm to convert the instance.

2. ### System requirements
    1. The system preprocesses the expression given in input checking its correctness.
    2. The system transforms the input into the corresponding data structure through the Internal DSL.
    3. The system applies the Tseitin transformation to the expression obtaining the CNF.
    4. The system applies the DPLL algorithm with CNF as input. It collects either all the assignments of the solution or one assignment at a time by request.
    5. The system measures the time spent by the Tseitin Transformation algorithm to convert and the DPLL algorithm to
       solve it (in nanoseconds).
    6. The system can also only convert the expression in CNF simply applying the Tseitin transformation.
    7. A file imported must be a text file containing the input in DIMACS format.
    8. When a file is imported, the system parses the file and converts the expression into DSL format, filling the
       input area.
    9. When a problem is selected, the system creates the corresponding expression based on the parameters inserted by
        the user, then follows the solving process.

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
       the architecture of the system.
    5. The code must be constantly formatted following a predefined code style using ScalaFMT.
    6. The releases must follow the Semantic Versioning specification.

## Requirements traceability

|          Requirement          |                                                                                                                   Feature / Scenarios                                                                                                                   |
|:-----------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
|   [1.i](#user-requirements)   |                                                                                              [DSL.feature](../../src/test/resources/features/DSL.feature)     
| [1.ii](#user-requirements)  |                                                                            [Conversion.feature](../../src/test/resources/features/Conversion.featureConversionfeature)                                                                             |                                                                                          |
[1.iii 1.iv 1.v](#user-requirements)  |                                                                            [Solver.feature](../../src/test/resources/features/Solver.feature)
[1.viii](#user-requirements)   |                                                                                     [SatEncodings.feature](../../src/test/resources/features/SatEncodings.feature)                                                                                      |                                                                              |
|  [1.x](#user-requirements)  |                                                                                              [DSL.feature](../../src/test/resources/features/DSL.feature)                                                                                               |
|  [2.i](#system-requirements)  |                                                                                    [ProcessInputTest.scala](../../src/test/scala/satify/dsl/ProcessInputTest.scala)                                                                                     |
| [2.ii](#system-requirements)  | [OperatorsTest.scala](../../src/test/scala/satify/dsl/OperatorsTest.scala) <br/> [MathOperatorsTest.scala](../../src/test/scala/satify/dsl/MathOperatorsTest.scala)<br/> [SatEncodingTest.scala](../../src/test/scala/satify/dsl/SatEncodingTest.scala) |
| [2.iii](#system-requirements) |                                                                              [TseitinTest.scala](../../src/test/scala/satify/update/converters/tseitin/TseitinTest.scala)                                                                               |
|  [2.iv](#system-requirements)  |                                                                                                [DpllEnumerator.scala](../../src/test/scala/satify/update/solver/dpll/impl/DpllEnumeratorTest.scala) <br/> [DpllFinder.scala](../../src/test/scala/satify/update/solver/dpll/impl/DpllFinderTest.scala) |                                                                                                 |                                                                                                                       |
|  [2.ix](#system-requirements)  |                                                                                                [problems package](../../src/test/scala/satify/problems)                                                                                                 |

[Previous](1-methodology.md) | [Next](3-architectural-design.md)
