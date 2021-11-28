Feature: Build game map
  In order to have fun,
  As a player,
  I want the map to be initialized.

  Scenario: Game map adds two bats
    Given location of game objects on map is initialized
    When game starts
    Then number of bats will be 2
    And cave 19 will contain the first bat and cave 13 will contain the second bat

  Scenario: Game map adds one player
    Given location of game objects on map is initialized
    When game starts
    Then cave 9 will contain the player object
    And player cave index will be 9

  Scenario: Game map adds one wumpus
    Given location of game objects on map is initialized
    When game starts
    Then cave 18 will contain the wumpus object
    And wumpus cave index will be 18

  Scenario: Game map adds two pits
    Given location of game objects on map is initialized
    When game starts
    Then number of pits will be 2
    And cave 3 will contain the first pit and cave 13 will contain the second pit