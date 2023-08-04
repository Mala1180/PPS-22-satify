Feature: CNF Conversion

  Scenario: I want to convert an expression to CNF
    Given The expression (exp scritta in DSL)
    When I convert it to CNF
    Then I should obtain the CNF expression