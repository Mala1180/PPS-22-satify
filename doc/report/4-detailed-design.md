# Design di dettaglio

## Architettura
Come spiegato nella sezione precedente, il pattern architetturale utilizzato
è **Model-View-Update** (MVU), è stato inoltre introdotto il **Cake Pattern**
per una migliore modellazione delle dipendenze.

Sono stati progettati dei _trait_ che rappresentano le componenti del pattern MVU, 
i quali incapsulano al loro interno gli _abstract type member_ relativi a Model, View e Update.

<img src="../diagrams/mvu/mvu-detailed.png" alt="Diagramma Model-View-Update dettagliato">

## Model
Il Model viene concretizzato utilizzando un _trait_ **State** contenete tre tipi astratti: **Expression**, **Solution** e **Problem**.

### DPLL (Davis-Putnam-Loveland-Logemann)

#### Preliminaries

##### Definition of partial model
We will call elements of $Vars \rightarrow \mathcal{B}$ as partial model, e.g. not all variables are assigned at a given point of the algorithm.

##### State of the literal

Under partial model $m$,
- a literal $l$ is true if $m(l) = 1$;
- $l$ is false if $m(l) = 0$;
- otherwise $l$ is unassigned.

##### State of the clause

Under a partial model $m$,
- a clause (literals put in $\lor$) is true if there is $l \in C$ such that $l$ is true;
- $C$ is false if for each $l \in C$, $l$ is false;
- otherwise $C$ is unassigned.

##### State of a formula

Under a partial model $m$,
- CNF $F$ is true if for each $C \in F$, $C$ is true;
- CNF $F$ is false if there is $C \in F$, such that $C$ is false. 
- otherwise $F$ is unassigned


##### Definition of unit clause and unit literal

$C$ is a unit clause under $m$ if a literal $l \in C$ in unassigned and the rest are false, $l$ is called unit literal. 


#### DPLL

- maintains a partial model, initially $\emptyset$
- assigns unassignmed variables 0 or 1 randomly one after another
- sometimes forced to choose assignments due to unit literals

<img src="./img/dpll.png" height="300">

##### Unit propagation

Suppose we have the following formula in CNF:

$$(\lnot b \lor c) \land (\lnot c) \land (a \lor \lnot b \lor e) \land (d \lor b)$$

Since $\lnot c$ is the only literal in the clause, if it is false all the formula will be false, so set $c = false$ to delete the clause and all the others with $\lnot c$ inside. Delete also all the $c$ where the literal appears in positive form.
On the other hand, if $c$ is in positive form, do viceversa.


##### Pure literals

As a choice to simplify the formula, beyond the unit propagation, it's possible to choose an assignment to a variable (decision) to a literal which appears only in positive form or only in negative form.

For example:

$$(b \lor c) \land (\lnot c \lor d) \land (a \lor b \lor e) \land (d \lor b)$$

In this case $b$ appears only in positive form, then assigning $b = true$ no other clause will be "penalized", therefore delete all the other clauses where $b$ is included.

In other words: if $b$ doesn't appear in negative form inside the formula $F$, assigning $b = true$, the satisfability of $F$ is preserved.


##### Heuristic search

*Idea*: identify most constrained variable

**MOM**'s heuristic: choose variable based on most occurrence in clauses of minimum length.

---------------------------------------------------



## View

## Update

