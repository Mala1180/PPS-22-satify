Feature: Conversion to Conjunctive Normal Form

  Scenario: I want to convert a malformed expression to CNF
    Given the input "a ad b"
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException
    And no CNF has to be generated

  Scenario: I want to convert a literal to CNF
    Given the input "a"
    When it is reflected to scala compiler
    And converted to CNF Form using Tseitin transformation
    Then I should obtain the CNF "a"

  Scenario: I want to convert an expression to CNF
    Given the input "a and b"
    When it is reflected to scala compiler
    And converted to CNF Form using Tseitin transformation
    Then I should obtain the CNF "TSTN0 and not(a) or not(b) or TSTN0 and a or not(TSTN0) and b or not(TSTN0)"
