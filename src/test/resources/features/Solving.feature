Feature: Using a solver to decide the satisfiability of a propositional formula

  Scenario: I want to get all the assignments of a satisfiable expression
    Given the input "a and a or b"
    When it is reflected to scala compiler
    And it is passed in input to a solver that returns all the assignments
    Then the result should be SAT
    And I should obtain the assignments:
      | (a: true, b: true)  |
      | (a: false, b: true) |
      | (a: true, b: false) |

  Scenario: I want to get one assignment at a time of a satisfiable expression
    Given the input "a and a or b"
    When it is reflected to scala compiler
    And it is passed in input to a solver that returns one assignment at a time
    Then the result should be SAT
    And I should obtain an assignment in:
      | (a: true, b: true)  |
      | (a: false, b: true) |
      | (a: true, b: false) |
    And I should obtain another assignment different from the previous one

  Scenario: I insert an unsatisfiable expression
    Given the input "a and !a"
    When it is reflected to scala compiler
    And it is passed in input to a solver that returns all the assignments
    Then the result should be UNSAT
    And I should obtain no assignments
