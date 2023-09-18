Feature: Using a solver to decide the satisfiability of a propositional formula

  Scenario: If the expression is SAT, I want to get all the satisfiable assignments
    Given the input "a and a or b"
    When it is reflected to scala compiler
    And ran using a solver that returns all the assignments
    Then the result should be SAT
    And I should obtain the assignments "(a: true, b: true), (a: false, b: true), (a: true, b: false)"

  Scenario: If the expression is SAT, I want to get one assignment at a time
    Given the input "a and a or b"
    When it is reflected to scala compiler
    And ran using a solver that returns one assignment at a time
    Then the result should be SAT
    And I should obtain the assignments "(a: false, b: true)"
    And I should obtain another assignment "(a: true, b: true)"
    