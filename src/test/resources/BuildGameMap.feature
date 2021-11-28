Feature: Build game map
  In order to have fun,
  As a player,
  I want the map to be initialized.

  Scenario: Game map adds two bats
    Given location of game objects on map is initialized
    When game starts
    Then number of bats will be 2
    And first bat will be at cave 19 and second bat will be at cave 13

