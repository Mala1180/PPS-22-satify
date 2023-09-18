Feature: Using DPLL algorithm to solve the Boolean Satisfiability Problem

  Scenario: If the expression is SAT, I want to get all the satisfiable assignments
    Given the input "a and a or b"
    When it is reflected to scala compiler
    And ran using the DPLL algorithm
    Then the result should be SAT
    And I should obtain the assignments "(a: true, b: true), (a: false, b: true), (a: true, b: false)"
