# Product Backlog — Sprint 2

_No remaining tasks from the previous sprint._

<table>
    <thead>
        <tr>
            <th>Priority</th>
            <th>Product Backlog Item</th>
            <th>Sprint Task</th>
            <th>Volunteers</th>
            <th>Estimation</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>1</td>
            <td rowspan="4">Domain-Specific Language</td>
            <td>DSL Design</td>
            <td>Matteini</td>
            <td>2</td>
        </tr>
        <tr>
            <td>2</td>
            <td>DSL Human-like</td>
            <td>Matteini</td>
            <td>8</td>
        </tr>
        <tr>
            <td>3</td>
            <td>DSL Math-like</td>
            <td>Matteini</td>
            <td>5</td>
        </tr>
        <tr>
            <td>4</td>
            <td>Input Reflection</td>
            <td>Matteini</td>
            <td>5</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="2">Problem Solution</td>
            <td>Solution Implementation</td>
            <td>Matteini</td>
            <td>2</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Output the solution</td>
            <td>Matteini</td>
            <td>3</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="3">Conjunctive Normal Form</td>
            <td>CNF Analysis and design</td>
            <td>All</td>
            <td>2</td>
        </tr>
        <tr>
            <td>2</td>
            <td>CNF Implementation</td>
            <td>Paganelli</td>
            <td>5</td>
        </tr>
        <tr>
            <td>3</td>
            <td>CNF Output</td>
            <td>Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="3">Tseitin Transformation</td>
            <td>New variables introduction in sub-formulas</td>
            <td>Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Conjunction of all substituted sub-formulas</td>
            <td>Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Transformation of the conjunction in CNF</td>
            <td>Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="5">DPLL Algorithm</td>
            <td>Expression simplification</td>
            <td>Fabri</td>
            <td>10</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Conflict identification</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Backtrack and branch implementation</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>4</td>
            <td>Unit propagation</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>5</td>
            <td>Pure literals elimination</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
    </tbody>
</table>

## Sprint Goal

The goals of this sprint are:

- Terminate Tseitin transformation
- Provide a basic DSL
- Proceed with the implementation of the DPLL algorithm

There will be the first release of the project permitting to take in input an expression through DSL and to apply the
Tseitin transformation (CNF conversion).

## Deadline

The deadline for this sprint is 14/08/2023.

## Sprint Review

The sprint terminates with some incomplete tasks, but this was expected due to the holiday period.
The only missing part is the output of the CNF expression.

The release consists of a GUI that permits to take in input an expression through a basic DSL and to apply the Tseitin
transformation without having feedback.

## Sprint Retrospective

On DSL, the estimation of design was too optimistic while the implementation was relatively simple.

On CNF transformation, we have been quite precise with the estimation.

On DPLL, time estimations were too optimistic regarding the simplification of the expression in CNF.
On the other hand, the backtrack-and-branch task was unnecessary.
Tests with basic CNF expressions have been performed, but more complex ones have to be written to verify the correctness
of the algorithm.

---
[Previous](1-product-backlog.md) | [Next](3-product-backlog.md)
