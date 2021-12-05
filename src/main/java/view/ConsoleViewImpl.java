package view;

import utilities.GameInitialConfigurations;
import presenter.WumpusPresenter;
import presenter.WumpusPresenterImpl;

import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ConsoleViewImpl implements WumpusView {
    WumpusPresenter wumpusPresenter;

    public ConsoleViewImpl() {
        wumpusPresenter = new WumpusPresenterImpl();
        wumpusPresenter.startNewGame();
    }

    @Override
    public void startTheGame() {
        this.paintComponent();
    }

    public void paintComponent() {
        while (!wumpusPresenter.isGameOver()) {
            this.render();
            this.drawPlayer();
            this.drawCaves();
            this.drawMessage();

            String keyBoardActionInput = getKeyBoardActionInput();
            handleKeyBoardActionInput(keyBoardActionInput);
        }
    }

    private String getKeyBoardActionInput() {
        Scanner keyboardScanner = new Scanner(System.in);
        System.out.print("SHOOT OR MOVE (S-M) ? ");
        String keyBoardActionInput = keyboardScanner.nextLine();
        return keyBoardActionInput;
    }

    private void handleKeyBoardActionInput(String keyBoardInput) {
        boolean isValidKeyBoardActionInput = validateTheKeyBoardActionInput(keyBoardInput);
        if (isValidKeyBoardActionInput) {
            executeActionBasedOnKeyBoardInput(keyBoardInput);
        }
    }

    void drawPlayer() {
        System.out.println("--------Player--------");
        int playerCave = wumpusPresenter.getPlayerCave();
        System.out.println("Player Cave: " + playerCave);
    }

    void drawCaves() {
        System.out.println("------Linked Cave------");
        int playerCave = wumpusPresenter.getPlayerCave();
        for (int link : GameInitialConfigurations.CAVE_LINKS[playerCave]) {
            System.out.print(link + " ");
        }
        System.out.println();
    }

    void drawMessage() {
        System.out.println("--------Messages--------");

        if (!wumpusPresenter.isGameOver()) {
            System.out.println("Arrows  " + wumpusPresenter.getNumberOfArrows());
        }

        if (wumpusPresenter.getMessages() != null) {
            List<String> messages = wumpusPresenter.getMessages();
            // collapse identical messages
            messages = messages.stream().distinct().collect(toList());

            // concat at most three
            String msg = messages.stream().limit(3).collect(joining(" & "));
            System.out.println(msg);

            // if there's more, print underneath
            if (messages.size() > 3) {
                System.out.println("& " + messages.get(3));
            }

            messages.clear();
        }
    }


    @Override
    public void render(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private boolean validateTheKeyBoardActionInput(String keyBoardInput) {
        if (!keyBoardInput.equals("S") &&
            !keyBoardInput.equals("M") &&
            !keyBoardInput.equals("s") &&
            !keyBoardInput.equals("m")) {
            System.out.println("Validation failed, SHOOT OR MOVE (S-M) please.");
            return false;
        }
        return true;
    }

    private void executeActionBasedOnKeyBoardInput(String keyBoardInput) {
        Scanner keyboardScanner = new Scanner(System.in);
        if (keyBoardInput.equals("M") || keyBoardInput.equals("m")) {
            executeMove(keyboardScanner);
        } else if (keyBoardInput.equals("S") || keyBoardInput.equals("s")) {
            executeShoot(keyboardScanner);
        }
    }

    private void executeShoot(Scanner keyboardScanner) {
        System.out.print("Cave to shoot ? ");
        Integer selectedCave = keyboardScanner.nextInt();
        while(!isKeyBoardInputWithinCorrectCave(selectedCave)) {
            System.out.println("Not Linked Cave, Try Again.");
            System.out.print("Cave to shoot to ? ");
            selectedCave = keyboardScanner.nextInt();
        }
        wumpusPresenter.shoot(selectedCave);
    }

    private void executeMove(Scanner keyboardScanner) {
        System.out.print("Cave to move to ? ");
        Integer selectedCave = keyboardScanner.nextInt();
        while(!isKeyBoardInputWithinCorrectCave(selectedCave)) {
            System.out.println("Not Linked Cave, Try Again.");
            System.out.print("Cave to move to ? ");
            selectedCave = keyboardScanner.nextInt();
        }
        wumpusPresenter.move(selectedCave);
    }

    private boolean isKeyBoardInputWithinCorrectCave(Integer selectedCave) {
        if(selectedCave < 0 || selectedCave > 19) {
            return false;
        }

        int playerCave = wumpusPresenter.getPlayerCave();
        for (Integer link : GameInitialConfigurations.CAVE_LINKS[playerCave]) {
            if(link == selectedCave) {
                return true;
            }
        }

        return false;
    }
}
