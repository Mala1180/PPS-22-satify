# Methodology

---

## SCRUM

The methodology adopted during the application development has been **AGILE**.
In particular, it was used **SCRUM**.

Regarding the scrum roles, Mattia Matteini was chosen as _Scrum Master_ and Luca Fabri as _Product Owner_.

This methodology involves the definition of time windows called Sprints, where each developer within the team
carries out the tasks assigned to him during the Sprint Planning.
Finally, the implementation is discussed in the phase of Sprint Review.

The period of a single Sprint was set to two weeks.

The first Sprint is dedicated to the project setup, organization, and understanding of application domain.

During each Sprint Planning, each member of the team proposed himself to accomplish some task,
assuring that the total work was divided equally among the team members.
Each Sprint Planning lasted approximately 2/3 hours of work.
At the end of the planning, it is obtained a Product Backlog collecting every task for the current Sprint.

At the end of the Sprint, the team members make a wrap-up of the work done, refining the Product Backlog.
Moreover, it is produced an artifact ready to be delivered to the client, in order to show the progress of development.

## Definition of Done

### Task review

For each task implementation, a Pull Request was created in order to get a code review from the other team members.
This allowed us to have a more controlled merge of the code, reducing the possibility of introducing bugs.
Only after the Pull Request was reviewed and approved by all other team members, the author could merge it into the
Develop branch.

### Build and Tests

In addition to the code review from the other members, it was necessary that the contribution to the software would not
break the tests.

### Coverage

Finally, the last requirement to consider a task done was to keep a good percentage (70%) of coverage of the tests.

## Git Flow

An important technical choice for the team has been Git Flow as a development workflow.
During each Sprint, each member opens a Pull Request when a task is completed.
In this way, he will get supervised from the other team members, and the code will be reviewed.
Finally, at every Sprint Review, every open Pull Request is merged into the Develop branch.
In order to prepare the release, a branch "Release/<version>" is created,
and then merged into Develop and Main branches.

## Testing

The testing phase is carried out in parallel with the implementation phase, in order to guarantee the quality of
the code and to avoid regressions.
It is used Test-Driven-Development (TDD) technique for the core functionalities of the software,
writing tests before implementation.
Not every part of the software has been developed following the TDD technique, in particular, the GUI part has been
developed in a more traditional way.
Moreover, to make a better communication with the client, it is used Behavior-Driven-Development (BDD) technique.

In particular, the libraries used for testing are:

- ScalaTest
- ArchUnit
- Cucumber

First, to test software architecture, it is used the ArchUnit library to verify that the software architecture
was respected during the implementation phase and that the code was not violating architecture dependency rules.

Then, to test the core functionalities of the software, it is used ScalaTest.
To make tests more readable for an external user, it is used FlatSpec style in order to obtain tests BDD-like.
In this case, the tests are more simple to read but also to realize, because the test code is more similar to the
natural language.

For an extra layer of comprehensibility, we used the Gherkin language, which is a business-readable DSL for describing
the behavior of the software, in particular, using the Cucumber framework.
Through the use of Cucumber, it is possible to test specific domain scenarios, which are written in Gherkin,
and to obtain a report of the results in a more client-friendly way.

### Microbenchmark
We used ScalaMeter to measure execution time and memory consumption of the software. 
In particular, using basic instances of common SAT problems to benchmark the performance.

## Continuous Integration

The use of Continuous Integration (CI) pipeline assures that the code within the
branch develop is always in a working state.
Build, tests and coverage of the software are executed automatically every time a Pull Request is opened or updated
on the three main operating systems (Windows, Linux, macOS) to guarantee the cross-platform compatibility.
Another mechanism introduced later during the development process is the automatic release of the software with the
relative changelog.

---
[Back to Index](README.md) | [Next](2-requirements.md)
