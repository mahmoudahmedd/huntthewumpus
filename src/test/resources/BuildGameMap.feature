Feature: Build game map
  In order to have fun,
  As a player,
  I want the map to be initialized.

  Scenario: Two bats are added to cave game map
    Given first bat in cave 19 and second bat in cave 13
    When game starts
    Then Then number of bats will be 2