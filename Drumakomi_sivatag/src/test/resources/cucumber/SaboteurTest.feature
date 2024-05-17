Feature: Saboteur test
  Test saboteur's actions

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

