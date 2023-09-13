# Product Backlog — Sprint 4

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
        <td>1</td>
        <td rowspan="2">SAT problems</td>
        <td>Problem representation</td>
        <td>All</td>
        <td>1</td>
    </tr>
    <tr>
        <td>2</td>
        <td>Problem instances</td>
        <td>All</td>
        <td>3</td>
    </tr>
    <tr>
        <td>2</td>
        <td rowspan="2">DPLL Completion</td>
        <td>DPLL enrichment using avanced FP</td>
        <td>Fabri</td>
        <td>2</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Validation and testing</td>
        <td>Fabri</td>
        <td>2</td>
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
        <td>1</td>
        <td>Documentation</td>
        <td>Requirements update</td>
        <td>All</td>
        <td>1</td>
    </tr>
    <tr>
        <td>2</td>
        <td>BDD Tests</td>
        <td>Cucumber features</td>
        <td>All</td>
        <td>1</td>
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
            <td rowspan="4">Performance improvements</td>
            <td>DPLL core functionalities tail recursive</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Tseitin transformation core functionalities tail recursive</td>
            <td>Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Caching mechanism for conversion</td>
            <td>Paganelli</td>
            <td>1</td>
        </tr>
        <tr>
            <td>1</td>
            <td>Caching mechanism for solving</td>
            <td>Matteini</td>
            <td>1</td>
        </tr>
        <tr>
            <td>2</td>
            <td>DPLL Parallelization</td>
            <td>Analysis for DPLL Multi-Thread</td>
            <td>Fabri</td>
            <td>5</td>
        </tr>
        <tr>
            <td>2</td>
            <td rowspan="2">DPLL improvement</td>
            <td>Next solution mechanism</td>
            <td>Fabri</td>
            <td>3</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Stop algorithm</td>
            <td>Fabri</td>
            <td>3</td>
        </tr>
        <tr>
            <td>1</td>
            <td rowspan="6">GUI improvement</td>
            <td>Problem selection</td>
            <td>Matteini/Paganelli</td>
            <td>4</td>
        </tr>
        <tr>
            <td>4</td>
            <td>DSL legenda</td>
            <td>Matteini/Paganelli</td>
            <td>1</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Input error handling</td>
            <td>Matteini/Paganelli</td>
            <td>2</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Loading during the solving</td>
            <td>Matteini/Paganelli</td>
            <td>1</td>
        </tr>
        <tr>
            <td>2</td>
            <td>Reaction management in View</td>
            <td>Matteini/Paganelli</td>
            <td>3</td>
        </tr>
        <tr>
            <td>3</td>
            <td>Multiple solution output</td>
            <td>All</td>
            <td>1</td>
        </tr>
        <tr>
            <td>2</td>
            <td rowspan="2">Behavioral testing</td>
            <td>Cucumber plugin</td>
            <td>Matteini</td>
            <td>2</td>
        </tr>
        <tr>
            <td>2</td>
            <td>BDD core testing</td>
            <td>All</td>
            <td>3</td>
        </tr>
    </tbody>
</table>

## Sprint Goal

The goals of this sprint are:

- Optimization algorithms to reduce memory usage.
- Providing the user a way to get all the solutions incrementally.
- Encoding the Nurse scheduling and Graph Coloring problems.
- Addition of some basic functionalities to the GUI.

There will be the third pre-release of the project containing all these functionalities.

## Deadline

The deadline for this sprint is 11/09/2023.

## Sprint Review

All optimization tasks took longer than expected as the entire functions were rewritten and tested.
Similarly, the next solution mechanism required a longer time because the algorithm was modified to return one solution
at a time.

The sprint terminates with a day of delay.
The main goals of the sprint are achieved, and all the tasks are completed except one which will be moved to the next
sprint.

Is released a new prototype permitting to solve NQueens, and Graph Coloring problems.

## Sprint Retrospective

The monad implementation for tree data structures was discarded because it was too complex and not worth the effort.

The introduced optimizations improve the performance of the program, but it is still possible to encounter memory
problems without resizing the default stack size.

---
[Previous](3-product-backlog.md)
