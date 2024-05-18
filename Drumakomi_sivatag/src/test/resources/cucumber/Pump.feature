Feature: Test Pump
  Test pump functions

  Scenario: Test pump with 2 connected pipes
    Given A pump with 2 connected pipes
    And There's 6 unit of water in the source
    And There's 0 unit of water in the destination
    When The pump "broken"
      Then The pump should not move the water from the source to the destination
    When The pump "working"
      Then The pump should move the water from the source to the destination




