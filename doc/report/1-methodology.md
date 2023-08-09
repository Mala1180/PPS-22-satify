# Methodology


## SCRUM

The methodology adopted during the application design has been AGILE, expecially using the SCRUM technique.

This technique involves the definition of time windows called Sprints, where each developer within the team carries out the tasks assigned to him during the Sprint Planning, to then discuss their implementation in the phase of Sprint Review, 

In order to organize the work in the best possible way, it has been decided that the period of a single Sprint had to be of two weeks, after which a meeting of variable duration would take place.

Initially we decided to dedicate the first Sprint to the project setup and for the definition of the used technologies and framerowks that we'd go to use later.

Only after the first setup Stream we decided how to divide the work at best, without overwhelm or privilege any team member and trying to divide the work type of each one. Moreover, for each Sprint Review, a potential artifact could be potentially delivered.

Furthermore, after a first phase of alignment at the Sprint Review, each member had to discuss how they approached to the modeling, implementation and successive tasks assigned to him.

## Trello

The tasks have been divided thanks to the using of Trello, which allowed us to define a first glance view of how the work is going across the Sprint, for each member of the team. This was possible dragging the card of each task. 


## Git Flow

An important choice for the team has been the use of Git Flow as development Workflow.
During each Sprint, each member made Pull Requests when a task was completed, in order to get supervised from the other team member in any moment they thought right. 

Each member had the possibility to get one or two code reviews, so that the merge was allowed in a more controlled way, drastically reducing bugs which could slow down other member's work.

Each Sprint Planning and Sprint Review had a variable length of (?)

## Testing

The testing phase has been carried out in parallel with the implementation phase, in order to guarantee the quality of the code and to avoid regressions.
We used the TDD technique for the core functionalities of the software, in this way we had to write the tests before the implementation of the code usefully to clarify the expected behavior of the code.
Not every part of the software has been developed following the TDD technique, in particular the GUI part has been developed in a more traditional way.

In particular, we used the ScalaTest framework, the ArchUnit library and the Cucumber framework.
Like said in the Continuous Integration section(TODO), following this approach we were certain that the code merged in the develop branch was always working.

First, we introduced tests for the architecture of the software, using the ArchUnit library, which allowed us to verify that the architecture of the software was respected during the implementation phase.

Subsequently, to let the tests more readable for an external user, we used FlatSpec and the relatives Matchers for ScalaTest in order to obtain tests in BDD-style.
In this case the tests are more simple to read but also to construct, because the test code is more similar to the natural language.

For an extra layer of comprehensibility, we used the Gherkin language, which is a business readable DSL for describing the behavior of the software, in particular, using the Cucumber framework.
Through the use of Cucumber we can test the specific BDD scenarios, which are written in Gherkin, and we can obtain a report of the test results in a more readable way for a potential client.

Furthermore, to obtain an indication of the coverage of the tests, we used the (Scoverage plugin for SBT), which allowed us to obtain a report of the coverage of the tests.
Our goal was not to obtain a 100% coverage, but to have a good coverage (percentuale decisa insieme) of the core functionalities of the software.





---
[Back to Index](README.md) | [Next](2-requirements.md)
