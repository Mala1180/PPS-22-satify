Feature: Inserting expression through DSL

  Scenario: I want to insert a basic expression through DSL
    Given the input "a and b or c"
    When it is reflected to scala compiler
    Then I should obtain the expression "((a and b) or c)"

  Scenario: I insert a empty expression
    Given an empty input
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException

  Scenario: I insert a malformed expression
    Given the input "a b c"
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException

  Scenario: I cannot use DSL keywords as variable names.
    Given the input "and or not"
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException

  Scenario: I cannot make code injection
    Given the input "a and b; println(\"hello\")"
    When it is reflected to scala compiler
    Then I should obtain an IllegalArgumentException
