# Product Backlog — Sprint 3

_Remaining tasks from the previous sprint:_

<table>
    <thead>
        <tr>
            <th>Priority</th>
            <th>Product Backlog Item</th>
            <th>Sprint Task</th>
            <th>Volunteers</th>
            <th>New Estimation</th>
        </tr>
    </thead>
<tbody>
    <tr>
        <td class="center">1</td>
        <td>Domain-Specific Language</td>
        <td>DSL Math-like</td>
        <td>Matteini</td>
        <td>1</td>
    </tr>
    <tr>
        <td>1</td>
        <td>Problem Solution</td>
        <td>Output the solution</td>
        <td>Matteini</td>
        <td>3</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Conjunctive Normal Form</td>
        <td>CNF Output</td>
        <td>Paganelli</td>
        <td>3</td>
    </tr>
</tbody>
</table>

_New tasks in this sprint:_

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
            <td rowspan="2">DSL Enrichment</td>
            <td>Derived operators</td>
            <td>Matteini</td>
            <td>2</td>
        </tr>
        <tr>
            <td>2</td>
            <td>SAT encodings</td>
            <td>Matteini, Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="2">SAT Problems</td>
            <td>Problem representation</td>
            <td>All</td>
            <td>3</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Problem instances</td>
            <td>All</td>
            <td>2</td>
        </tr>
        <tr>
            <td>2</td>
            <td rowspan="2">DPLL Completion</td>
            <td>DPLL enrichment using advanced FP</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Validation and testing</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>3</td>
            <td rowspan="2">Code Organization</td>
            <td>Test organization</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Packages organization</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>2</td>
            <td rowspan="4">Documentation</td>
            <td>Introduction</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Requirements update</td>
            <td>All</td>
            <td>3</td>
        </tr>
        <tr>
            <td>2</td>
            <td>View implementation</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Task revision</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="2">CI Update</td>
            <td>Set minimum coverage for tests</td>
            <td>Matteini, Paganelli</td>
            <td>2</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Automatic release</td>
            <td>Matteini</td>
            <td>2</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Redesign State/Model</td>
            <td></td>
            <td>Matteini</td>
            <td>3</td>
        </tr>
        <tr>
            <td>2</td>
            <td>BDD Tests</td>
            <td>Cucumber features</td>
            <td>All</td>
            <td>2</td>
        </tr>
    </tbody>
</table>

## Sprint Goal

The goals of this sprint are:

- Terminate DPLL algorithm
- Provide a richer DSL
- Provide some basic problems as examples

There will be the second pre-release of the project permitting to solve
SAT problem also using the examples provided.

## Deadline

The deadline for this sprint is 28/08/2023.

## Sprint Review

The sprint terminates with all tasks completed except for the problem instances.

A prototype is released, which permits inserting a more complex input using the DSL.
It is possible to solve the SAT problem using the DPLL, and also importing a problem in DIMACS format.

Some tasks on code and package organization are demanded to the next sprint because of the lack of time.

## Sprint Retrospective

We discovered a problem in the Tseitin Transformation that was corrected in this sprint.

We thought that the use of monads could be useful to handle all the tree data
structures, but the change would complicate the design, therefore, in the next Sprint we will
evaluate the pros and cons of an eventual implementation.

---

[Previous](2-product-backlog.md) | [Next](4-product-backlog.md)
