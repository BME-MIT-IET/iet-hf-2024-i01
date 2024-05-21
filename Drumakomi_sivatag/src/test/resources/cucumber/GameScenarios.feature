Feature: Test Saboteur and Mechanic in complex game scenarios

  Scenario: A pump broke and a mechanic stop to it and repair it.
    Given A pump with input and output pipes
    When Some the time passes
    Then The pump is broken
    Given A mechanic on the output pipe
    And The "mechanic" steps to the "pump"
    When The mechanic repairs the pump
    Then The pump is working

  Scenario: The mechanic and the saboteur try to step to the same Pump
    Given A pump with input and output pipes
    Given A mechanic on the output pipe
    Given A saboteur on the input pipe
    When The "saboteur" steps to the "pump"
      Then The "saboteur" is on the "pump"
    When The "mechanic" steps to the "pump"
      Then The "mechanic" is on the "pump"

  Scenario: The mechanic and the saboteur try to step to the same Pipe
    Given A pump with input and output pipes
    Given A mechanic on the output pipe
    Given A saboteur on the input pipe
    When The "saboteur" steps to the "pump"
    Then The "saboteur" is on the "pump"
    When The "saboteur" steps to the "output"
    Then The "saboteur" is on the "output"

  Scenario: The mechanic detach the output and the saboteur tries to step to the output
    Given A pump with input and output pipes
    Given An addition pump connected to the output pipe
    Given A mechanic on the output pipe
    Given A saboteur on the input pipe
    And The "mechanic" steps to the "extra pump"
    Then The "mechanic" is on the "extra pump"
    When The "mechanic" detaches the output pipe from the pump
    When The "saboteur" steps to the "pump"
    Then The "saboteur" is on the "pump"
    When The "saboteur" steps to the "output"
    Then The "saboteur" is on the "pump"
