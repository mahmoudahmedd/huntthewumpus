Feature: Build game map
  In order to have fun,
  As a player,
  I want the map to be initialized.

  Scenario: Game map adds two bats
    Given location of game objects on map is initialized
    When game starts
    Then number of bats will be 2
    And cave 19 will contain the first bat and cave 13 will contain the second bat

