Feature: Conversion to Conjunctive Normal Form

  Scenario: I want to convert an expression to CNF using Tseitin Transformation
    Given the expression "a and b or c"
    When I convert it to CNF Form
    Then I should obtain the CNF "TSTN0 and TSTN1 or c or not(TSTN0) and not(TSTN1) or TSTN0 and not(c) or TSTN0 and not(a) or not(b) or TSTN1 and a or not(TSTN1) and b or not(TSTN1)"
