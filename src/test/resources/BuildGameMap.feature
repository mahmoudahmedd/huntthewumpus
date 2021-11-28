Feature: Build game map
  In order to have fun,
  As a player,
  I want the map to be initialized.

  Scenario: Game map adds bats
    Given location of game objects on map is initialized
    When game starts
    Then cave 19 will contain the first bat and cave 13 will contain the second bat

  Scenario: Game map adds player
    Given location of game objects on map is initialized
    When game starts
    Then cave 9 will contain the player object
    And player cave index will be 9

  Scenario: Game map adds wumpus
    Given location of game objects on map is initialized
    When game starts
    Then cave 18 will contain the wumpus object
    And wumpus cave index will be 18

  Scenario: Game map adds pits
    Given location of game objects on map is initialized
    When game starts
    Then cave 3 will contain the first pit and cave 13 will contain the second pit

  Scenario Outline: Game map adds the correct number of game objects
    Given location of game objects on map is initialized
    When game starts
    Then number of <GameObject> will be <NumberOfTheGameObjects>
    Examples:
      | GameObject | NumberOfTheGameObjects |
      | "arrows"   | 5                      |
      | "bats"     | 2                      |
      | "pits"     | 2                      |
      | "wumpus"   | 1                      |
      | "players"  | 1                      |