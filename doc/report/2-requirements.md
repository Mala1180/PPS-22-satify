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
The mission is to implement it in a pure functional way using the Scala programming language, and its flexibility.
Satify is a tool that can be used by anyone who needs to check the satisfiability
of a logical expression, but also to convert the logical expression in CNF form.

The user can use the tool in two ways:

- by using the text field to insert the input formula.
- selecting an example from the list provided by the tool.

The tool will give in output the result of the satisfiability check and the result of the conversion of the logical
expression in CNF form.

## Domain model requirements

The domain model of the system is composed by the following entities:

- **Expression**: represents a formula in form of a tree.
- **Variable**: represents a variable of the formula.
- **Operator**: represents an operator of the formula.
- **Assignment**: represents an assignment of a variable.
- **CNF**: represents a formula in CNF form.
- **Solution** represents the solution of the satisfiability check of the formula.

## Functional requirements

### User requirements

- The user must be able to insert in input a logical expression using a friendly and intuitive syntax.
- The user must be able to select an example from the list provided by the tool.
- The user must be able to see the result of the satisfiability check.
- The user must be able to see the result of the conversion of the logical expression in CNF form.
- The user must be able to see the result of the satisfiability check of the CNF form.
- The user must be able to see the assignments done by the DPLL of the variables that make the logical expression
  satisfiable.

### System requirements

- The system must be able to operate on 3 main operating systems: Windows, Linux and macOS.
- The system must be efficient in terms of memory usage and performances without blocking the system.
- The system has to be user-friendly and intuitive to let the user use it without any problems, considering non-expert
  ones.

## Non-functional requirements

- The system must be able to operate on 3 main operating systems: Windows, Linux and macOS.
- The system must be efficient in terms of memory usage and performances without blocking the system.
- The system has to be user-friendly and intuitive to let the user use it without any problems, considering non-expert
  ones.

## Implementation requirements

- The system must be implemented in Scala exploiting the functional programming paradigm and the Scala language features
  to let the code be more readable and maintainable.
- The system must be implemented using a scalable architecture to make easier the evolution of the system. For example
  adding other resolution algorithms.
- The core of the system must be implemented following a TDD approach and using the ScalaTest framework to guarantee the
  correctness of the code.
- The system must be correctly documented using Scaladoc to let potential new developers understand the code and the
  architecture of the system.
- The system must be correctly documented using Markdown to let potential users understand how to use the system. ??????
-

[Previous](1-methodology.md) | [Next](3-architectural-design.md)
