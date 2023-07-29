# Implementation

In this section every group member describes the work done for the realization of the project.

## Luca Fabri

My first task was deeply understand the DPLL algorithm. Initially I spent some time understending how it works in an high level view: 

- The DPLL algorithm builds a tree of decisions, where each node is a boolean assignment to a variable;
- For each decision, a partial model (e.g. a partial assignment of the variables) is memorized, togheter with the updated expression;
- If after an assignment a clause of the expression gives a conflict (e.g. is evaluated false) the entire expression is unsat. In this case the tree is backtracked to the previous decision, in order to let the algorithm to branch on another assignment/variable.

The variable to be assigned at a decision branch is choosed:
- randomly, if every clause of the expression has more than one literal
- as a unit literal inside a clause of the expression, e.g. a literal which is the only one inside the clause. This technique is called unit propagation
- pure literal. If a literal appears only in positive form inside the expression, or only in negative form. This techniques is called pure literal elimination.

It is obvious to observe that the complexity of the algorithm is strictly related on choosing the right variable to branch. In the literature exist a bunch of more heuristics and optimizations.


## Mattia Matteini

Inizialmente mi sono dedicato alla preparazione del repository e
alle tasks riguardanti le operazioni di DevOps, quindi ho impostato la Continuous Integration,
SBT, ScalaTest e ScalaFMT.

Dopodiché mi sono occupato della progettazione e implementazione dell'architettura di base del software (MVU).
Ho deciso di utilizzare il Cake Pattern, nonostante in MVU non ci sono troppe dipendenze, per dare comunque robustezza, sostituibilità e flessibilità.

In particolare quindi ho sfruttato i meccanismi di _Self-Type Annotation_ e _Mixin_ offerti da Scala per poter 
gestire le dipendenze tra le componenti del pattern a compile-time e per creare un istanza dell'applicazione in maniera semplice e leggibile.

```scala
trait MVU extends ModelComponent with ViewComponent with UpdateComponent

object Main extends App with MVU
```

## Alberto Paganelli

My first task was to analyze in depth the Tseitin transformation algorithm. 

Not having sufficient knowledge I spent some time understanding the algorithm and its intermediate phases so that I could approach the development in an incremental way.

I had assumed that it would take me more time to understand the algorithm, but in reality its sub-parts are relatively simple. The transformation consists in the division of the formula into sub-clauses, a part of introduction of new variables and a rewriting of the initial formula through these last ones in CNF.

## Tseitin Algorithm
The Tseitin algorithm is a method for converting a formula in propositional logic into a CNF formula.

CNF is a specific form of representing logical formulas as a conjunction of clauses, where each clause is a disjunction of literals (variables or their negations).

The idea behind the Tseitin transformation is to introduce new auxiliary variables for subformulas in the original formula. These auxiliary variables are used to represent the truth values of the subformulas. 

By doing this, the original formula can be broken down into smaller parts, each represented in CNF, and then combined using the introduced auxiliary variables to maintain the overall semantics of the original formula.

The Tseitin transformation follows these steps:

1. Assign a unique identifier to each subformula in the original formula.
2. Replace each subformula with an auxiliary variable representing its truth value.
3. Express the truth conditions of the subformulas in CNF using the auxiliary variables and standard logical connectives (AND, OR, NOT).
4. Combine the CNF representations of the subformulas to obtain the CNF representation of the entire formula.

    The resulting CNF formula is equi-satisfiable with the original formula, meaning they have the same set of satisfying assignments. This transformation enables the use of various CNF-based algorithms and tools to analyze and reason about the original logical formula efficiently


---
[Previous](4-detailed-design.md) | [Next](6-retrospective.md)
