# Satify Report

## Introduction

Satify is a pure functional SAT Solver written in Scala.
It allows to solve SAT problem instances or simply convert them in Conjunctive Normal Form (CNF).
It's possible to insert a custom SAT problem instance or use one of the predefined.
In case of manual insertion, the user can use an adhoc DSL.

Internally, the solver makes use of two main algorithms: Tseitin transformation and DPLL (Davis-Putnam-Logemann-Loveland).
The first one is used to convert the problem instance to CNF form, and the second one is used to solve the problem instance.

Satify will print the solution of the problem (SAT or UNSAT) and, if SAT, provide one or more satisfiable assignment to the variables.

## Boolean satisfiability problem (SAT)

The boolean satisfiability problem (SAT) is a decision problem that asks whether a given propositional expression has a satisfiable assignment.
In other words, it is the problem of determining if a set of boolean constraints applied to the variables of an expression exist, in such a way that it is overally evaluated to true.

If no such assignment exists, the propositional expression is said to be unsatisfiable (UNSAT). 

SAT is proven to be NP-complete, nevertheless, many heuristic SAT algorithms exist in the literature which work well for many practical problems.
SAT applications include artificial intelligence, model checking, circuit design and automatic exploit generation.

## Index:

1. [Methodology](1-methodology.md)
2. [Requirements](2-requirements.md)
3. [Architectural design](3-architectural-design.md)
4. [Detailed design](4-detailed-design.md)
5. [Implementation](5-implementation.md)
6. [Retrospective](6-retrospective.md)

