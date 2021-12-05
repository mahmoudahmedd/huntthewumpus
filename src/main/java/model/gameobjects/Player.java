package model.gameobjects;

import model.*;
import model.gameobjects.hazards.Bat;
import model.gameobjects.hazards.WarnableHazard;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject implements ShootableGameObject {
    private boolean dead;
    private Arrow arrow;
    private List<String> warnings;

    public Teleportation getTeleportation() {
        return teleportation;
    }

    public void setTeleportation(Teleportation teleportation) {
        this.teleportation = teleportation;
    }

    private Teleportation teleportation;

    @Override
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Player(int numberOfArrows) {
        warnings = new ArrayList<>();
        arrow = new Arrow();
        this.arrow.initializeNumberOfArrows(numberOfArrows);
    }

    public void move(Cave caveToMoveTo) {
        this.warnings.clear();

        if (this.isMoveValid(caveToMoveTo)) {
            changeTheCaveLocation(caveToMoveTo);
        }

        executePostMoveActions();
        senseWarning();
    }

    public void teleport(Cave caveToMoveTo) {
        this.warnings.clear();
        changeTheCaveLocation(caveToMoveTo);
        addWarning("a bat dropped you in a random cave");
        moveBat();
        executePostMoveActions();
        senseWarning();
    }

    private void moveBat() {
        Bat bat=teleportation.getBat();
        bat.move();
        teleportation=null;
    }

    private void changeTheCaveLocation(Cave caveToMoveTo) {
        this.getCave().removeGameObject(this);
        this.setCave(caveToMoveTo);
        caveToMoveTo.addGameObject(this);
    }

    public List<String> getWarnings() {
        return warnings;
    }

    private void senseWarning() {
        List<Cave> linkedCaves = this.getCave().getLinkedCaves();

        for(Cave linkedCave: linkedCaves) {
            List<GameObject> gameObjects = linkedCave.getGameObjects();
            for(GameObject gameObject : gameObjects){
                if(gameObject instanceof WarnableHazard){
                    warnings.add(((WarnableHazard) gameObject).getWarningInTheLinkedCave());
                }
            }
        }
    }

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    private void executePostMoveActions() {
        List<GameObject> gameObjects = this.getCave().getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof WarnableHazard){
                ((WarnableHazard)gameObject).executeActionOnPlayer(this);
                if(isDead()){
                    break;
                }
            }
        }
    }

    private boolean isMoveValid(Cave caveToMoveTo) {
        return this.getCave().getLinkedCaves().contains(caveToMoveTo);
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public void shoot(Cave caveToShoot) {
        this.warnings.clear();

        arrow.decrementByOne();

        List<GameObject> gameObjects = caveToShoot.getGameObjects();
        for(GameObject gameObject : gameObjects){
            if(gameObject instanceof ShootableGameObject){
                ((ShootableGameObject)gameObject).setDead(true);
                warnings.add("You killed " + gameObject.id);
            }
        }

        if(hasNoArrows()){
            warnings.add("You ran out of arrows");
        }
    }

    public boolean hasNoArrows(){
        return getArrows().getNumber()==0;
    }

    public Arrow getArrows() {
        return this.arrow;
    }
}
