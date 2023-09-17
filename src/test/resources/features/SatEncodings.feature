Feature: Using SAT encoding to create expressions

  Scenario: I want to insert "at least one" constraint
    Given the input "(a, b, c) atLeast one"
    When it is reflected to scala compiler
    Then I should obtain the expression "((a or b) or c)"

#  Scenario: I want to insert "at least three" constraint
#    Given the input "(a, b, c, d) atLeast three"
#    When it is reflected to scala compiler
#    Then I should obtain 5 possible assignments

#  Scenario: I want to insert "at most one" constraint
#    Given the input "(a, b, c) atMost one"
#    When it is reflected to scala compiler
#    And it is used as instance of SAT problem
#    Then I should obtain 4 possible assignments

#  Scenario: I want to insert "at most three" constraint
#    Given the input "(a, b, c, d) atMost three"
#    When it is reflected to scala compiler
#    Then I should obtain 15 possible assignments

#  Scenario: I want to insert "exactly one" constraint
#    Given the input "(a, b, c) exactly one"
#    When it is reflected to scala compiler
#    And it is used as instance of SAT problem
#    Then I should obtain 3 possible assignments

#  Scenario: I want to insert "exactly two" constraint
#    Given the input "(a, b, c) exactly two"
#    When it is reflected to scala compiler
#    And it is used as instance of SAT problem
#    Then I should obtain 2 possible assignments
