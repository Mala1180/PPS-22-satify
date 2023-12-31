# Retrospective

---

Each Sprint Retrospective is included in the corresponding Product Backlog
(see [Process](../process/README.md)).

## Personal comments

### Luca Fabri

During my work, I encountered some challenges in the testing phase due to the need to cover a wide range of possible scenarios. 
This diversity of cases required a careful and detailed approach to test design in order to ensure a comprehensive and accurate evaluation of the system.

I discarded the multi-threading approach due to the fact that the solving process implementation is not embarrassingly parallel.

### Mattia Matteini

The most difficult parts were the architecture understanding and modeling, and the DSL design.
In fact, both were totally new to me.

During the DSL design, I was undecided whether to use a custom monadic parser, or to simply use Scala mechanisms.
I chose the second one because it had the best trade-off between complexity and result.
Despite this, the DSL implementation took me more time than expected,
due to the fact I had no really clear idea how Internal DSLs work.

I had no other major difficulties during the development.

### Alberto Paganelli

Initially, not having sufficient knowledge, I spent some time understanding the algorithm and its intermediate phases so
that I could approach the development in an incremental way.

I had assumed that it would take me more time to understand the algorithm, but its parts are relatively
simple.
One particular difficulty was to understand some edge cases that were not well explained in papers like, for example,
the case of multiple equals sub-formulas in the same clause.

## Task review

We start requiring two code reviews for each Pull Request, but we noticed that this approach was not possible during all
the project development due to the fact that not all the team members were available to do the code review.
For this reason, we decided to momentarily reduce the number of code reviews to one but only when the PRs were not
so impacting.
We took this decision in order to avoid blocking the work of the other team members.

## Final comments

We are satisfied with the result of this project.
We think we have done summary a good job and a lot of practice with Scala and functional programming.
Another positive aspect is that we had the opportunity to experiment with new tools such as SBT, Cucumber
and a new AGILE methodology.
The strict rules on the code review and the tests have been very useful to improve the quality of the code and for
having always a working version of the software.

## Future developments

Many future developments are possible for this project.
Some of them are related to the improvement of the user interface and others to exploit the potential of the software.

To increase the user experience, it would be useful the completion while typing the formula or the possibility to
drag and drop logical operator components to generate it.
Another improvement could be the generation of an output logical circuit to better understand the solution.

For how the solver is designed, it is also possible to extend it with other conversion and resolution algorithms.
Another important feature could be the simulation of SAT attack on logical circuits.

Instead, in order to improve performance, a multi-threading approach could be evaluated.

---

[Previous](5-implementation.md) | [Back to Index](README.md)
