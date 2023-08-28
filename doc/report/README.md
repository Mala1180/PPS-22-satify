# Satify Report

## Introduction

Satify is a pure functional SAT Solver written in Scala.
It allows to solve SAT problem instances or simply convert them to the Conjunctive Normal Form (CNF) form.
It's possible to insert a custom SAT problem instance or use one of the predefined, in case of manual insertion the user can use an ad-hoc DSL.
Internally the solver make use of two main algorithms: Tseitin and DPLL.
The first one is used to convert the problem instance to CNF form and the second one is used to solve the problem instance.
Satify will print the solution of the problem (SAT or UNSAT), then if SAT it will provide a satisfiable assignment for all the variables.

## Boolean satisfiability problem (SAT)

The boolean satisfiability problem (SAT) is a decision problem that asks whether a given Boolean formula has a satisfying assignment or not.
It belongs to the class of decision problems checking if exists an assignment of truth values to a set of variables that satisfies a given formula.
A boolean formula is constructed using logical operators and variables that can assume only true or false values.
The goal of the tool is to determine if there exists an assignment of truth values to the variables that makes true the entire formula evaluation.
The usage of the CNF form is necessary because it allows to use the DPLL algorithm to solve the problem instance.

In the following, the report divided into sections is shown.

## Index:
1. [Methodology](1-methodology.md)
2. [Requirements](2-requirements.md)
3. [Architectural design](3-architectural-design.md)
4. [Detailed design](4-detailed-design.md)
5. [Implementation](5-implementation.md)
6. [Retrospective](6-retrospective.md)

