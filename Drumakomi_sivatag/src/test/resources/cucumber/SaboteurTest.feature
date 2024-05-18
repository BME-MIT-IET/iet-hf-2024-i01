Feature: Saboteur test
  Test saboteur's actions

  Scenario: Step form a pump to a pipe
    Given A saboteur
    And The saboteur is on a "pump"
    And The pump and the pipe are connected
    Given A pipe
    When The saboteur steps to a pipe
    Then The saboteur should be on a "pipe"

  Scenario: Try to step away from a sticky pipe
    Given A pump
    Given A saboteur
    And The saboteur is on a "pipe"
    And The pipe is "sticky"
    And The pump and the pipe are connected
    When The saboteur steps to a pump
    Then The saboteur should be on a "pipe"

  Scenario: Damage an intact pipe
    Given A saboteur
    And The saboteur is on a "pipe"
    And The pipe is "intact"
    When The saboteur "damages" the element
    Then The pipe should be "damaged"

  Scenario: Damage a Pump
    Given A saboteur
    And The saboteur is on a "pump"
    And The pipe is "damaged"
    When The saboteur "damages" the element
    Then The pump should be "damaged"

  Scenario: Set a pipe to slippery
    Given A saboteur
    And The saboteur is on a "pipe"
    And The pipe is "not slippery"
    And The pipe is "not sticky"
    When The saboteur make the element to "slippery"
      Then The pipe should be "slippery"
    When The saboteur make the element to "sticky"
      Then The pipe should be "sticky"


