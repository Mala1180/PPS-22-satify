Feature: Conversion to Conjunctive Normal Form

  Scenario: I want to convert a malformed expression to CNF
    Given the input "a ad b"
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException
    And no CNF has to be generated

  Scenario: I want to convert a literal to CNF
    Given the input "a"
    When it is reflected to scala compiler
    And converted to CNF Form
    Then I should obtain the CNF "a"

  Scenario: I want to convert an expression to CNF
    Given the input "a and b or c"
    When it is reflected to scala compiler
    And converted to CNF Form
    Then I should obtain the CNF "TSTN0 and TSTN1 or c or not(TSTN0) and not(TSTN1) or TSTN0 and not(c) or TSTN0 and not(a) or not(b) or TSTN1 and a or not(TSTN1) and b or not(TSTN1)"
