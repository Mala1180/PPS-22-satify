# Methodology

## SCRUM

The methodology adopted during the application development has been **AGILE**, in particular we made use of **SCRUM**.

This methodology involves the definition of time windows called Sprints, where each developer within the team
carries out the tasks assigned to him during the Sprint Planning. Finally, the implementation is discussed in the phase
of Sprint Review.

We set the period of a single Sprint to two weeks.

The first Sprint is dedicated to the project setup, organization, and understanding of application domain.

During each Sprint Planning, each member of the team proposed himself to accomplish some task, assuring that the total
work was divided equally among the team members. At the end of the planning, it is obtained a Product Backlog collecting every task for the current Sprint. 

At the end of the Sprint, the team members make a wrap-up of the work done, refining the Product Backlog.
Moreover, it is produced an artifact ready to be delivered to the client, in order to show the progress of development.

## Trello

The tasks have been divided using Trello, which allowed us to have an accurate overview of the tasks and how much time they were taking.
This was possible dragging the card of each task between 3 main columns: TODO, WIP and DONE.

## Definition of done

### Task review

For each task implementation a Pull Request was created, in order to get a code review from the other team members.
This allowed us to have a more controlled merge of the code, reducing the possibility of introducing bugs.
Only after the Pull Request was reviewed and approved by all other team members, the author could merge it into the Develop branch

### Test
In addition to the code review from the other members, it was necessary that the addition to the software would not break the tests.

### Coverage
Finally, the last requirement to consider a task done was to keep a good percentage of coverage of the tests.


## Git Flow

An important choice for the team has been the use of Git Flow as development Workflow.
During each Sprint, each member made Pull Requests when a task was completed, in order to get supervised from the other
team member in any moment they thought right.

Each member had the possibility to get one or two code reviews, so that the merge was allowed in a more controlled way,
drastically reducing bugs which could slow down other member's work.

Each Sprint Planning and Sprint Review had a variable length of few hours.

## Testing

The testing phase has been carried out in parallel with the implementation phase, in order to guarantee the quality of
the code and to avoid regressions.
We used the TDD technique for the core functionalities of the software, in this way we had to write the tests before the
implementation of the code usefully to clarify the expected behavior of the code.
Not every part of the software has been developed following the TDD technique, in particular the GUI part has been
developed in a more traditional way.

In particular, we used the ScalaTest framework, the ArchUnit library and the Cucumber framework.
Like said in the Continuous Integration section(TODO), following this approach we were certain that the code merged in
the develop branch was always working.

First, we introduced tests for the architecture of the software, using the ArchUnit library, which allowed us to verify
that the architecture of the software was respected during the implementation phase and that the code was not violating
MVU dependency rules.

Subsequently, to let the tests more readable for an external user, we used FlatSpec and the relatives Matchers for
ScalaTest in order to obtain tests in BDD-style.
In this case the tests are more simple to read but also to construct, because the test code is more similar to the
natural language.

For an extra layer of comprehensibility, we used the Gherkin language, which is a business readable DSL for describing
the behavior of the software, in particular, using the Cucumber framework.
Through the use of Cucumber we can test the specific BDD scenarios, which are written in Gherkin, and we can obtain a
report of the test results in a more readable way for a potential client.

Furthermore, to obtain an indication of the coverage of the tests, we used the (Scoverage plugin for SBT), which allowed
us to obtain a report of the coverage of the tests.
Our goal was not to obtain a 100% coverage, but to have a good coverage (percentuale decisa insieme) of the core
functionalities of the software.

---
[Back to Index](README.md) | [Next](2-requirements.md)
