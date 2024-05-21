Feature: Mechanic test
  Test mechanic's actions

  Scenario: Mechanic repairs a pipe
    Given a mechanic
    Given a pipe
    And the pipes is "damaged"
    And the mechanic is on the "pipe"
    When The mechanic repairs the pipe
    Then the pipe should be "fixed"

  Scenario: Mechanic picks up a pump
    Given a mechanic
    Given a cistern
    And the mechanic is on the "cistern"
    And the cistern has a pump
    When The mechanic picks up the pump
    Then the pump should be in the mechanic's hands

