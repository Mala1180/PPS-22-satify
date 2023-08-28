Feature: Tseitin Transformation

  Scenario: I want to convert an expression to CNF
    Given The expression "a and b or c"
    When I convert it to CNF Form
    Then I should obtain the CNF "X0 and X1 or c or notX0 and notX1 or X0 and notc or X0 and nota or notb or X1 and a or notX1 and b or notX1"