Feature: Tseitin Transformation

  Scenario: I want to convert an expression to CNF
    Given The expression "a or b"
    When I convert it to CNF Form
    Then I should obtain the CNF "a or b"