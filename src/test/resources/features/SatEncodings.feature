Feature: Using SAT encoding to create expressions

  Scenario: I want to insert at least one encoding
    Given the input "(a, b, c) atLeast one"
    When it is reflected to scala compiler
    Then I should obtain the expression "((a or b) or c)"

  Scenario: I want to insert at least one encoding
    Given the input "(a, b, c) atLeast one"
    When it is reflected to scala compiler
    Then I should obtain the expression "((a or b) or c)"

#  Scenario: I want to insert at most one encoding
#    Given the input "(a, b, c) atMost one"
#    When it is reflected to scala compiler
#    And it is used as instance of SAT problem
#    Then I should obtain 4 possible assignments
