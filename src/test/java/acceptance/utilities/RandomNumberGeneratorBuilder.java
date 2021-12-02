package acceptance.utilities;

import model.GameInitialConfigurations;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import utilities.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class RandomNumberGeneratorBuilder {
    public RandomNumberGenerator randomNumberGenerator = mock(RandomNumberGenerator.class);

    List<Integer> defaultNumbersForDefaultLocationMocito = new ArrayList<>();
    List<Integer> appendedNumbersForDefaultLocationMocito = new ArrayList<>();
    List<Integer> defaultNumbersForWumpusWakeupProbabilityMocito = new ArrayList<>();
    List<Integer> defaultNumbersForWumpusLinkedCaveIndexMocito = new ArrayList<>();

    public int playerStartingCaveIndex = 9;
    public int wumpusStartingCaveIndex = 18;
    public int firstBatStartingCaveIndex = 19;
    public int secondBatStartingCaveIndex = 13;
    public int firstPitCave = 3;
    public int secondPitCave = 13;

    public int numberAtWhichWumpusWillRemainSleeping = 0;

    public int wumpusLinkedCaveIndex = 2;

    public RandomNumberGenerator build() {
        addDefaultNumbersForDefaultLocationMocito();
        addDefaultNumbersForWumpusWakeupProbabilityMocito();
        addDefaultNumbersForWumpusLinkedCaveIndexMocito();

        defaultNumbersForDefaultLocationMocito.addAll(appendedNumbersForDefaultLocationMocito);

        configureDefaultNumbersForDefaultLocationMocito();
        configureDefaultNumbersForWumpusWakeupProbabilityMocito();
        configureDefaultNumbersForWumpusLinkedCaveIndexMocito();

        return randomNumberGenerator;
    }

    private void configureDefaultNumbersForWumpusLinkedCaveIndexMocito() {
        int numberOfLinkedCaves = 3;
        Mockito.when(randomNumberGenerator.generateNumber(numberOfLinkedCaves))
                .thenAnswer(new Answer<Integer>() {
                    int counter = 0;

                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return defaultNumbersForWumpusLinkedCaveIndexMocito.get(counter++);
                    }
                });
    }

    private void addDefaultNumbersForWumpusLinkedCaveIndexMocito() {
        defaultNumbersForWumpusLinkedCaveIndexMocito.addAll(Arrays.asList(
                wumpusLinkedCaveIndex));
    }

    private void configureDefaultNumbersForWumpusWakeupProbabilityMocito() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.MAXIMUM_NUMBER_FOR_CALCULATING_WUMPUS_WAKEUP_PROBABILITY))
                .thenAnswer(new Answer<Integer>() {
                    int counter = 0;

                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return defaultNumbersForWumpusWakeupProbabilityMocito.get(counter++);
                    }
                });
    }

    private void configureDefaultNumbersForDefaultLocationMocito() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES))
                .thenAnswer(new Answer<Integer>() {
                    int counter = 0;

                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return defaultNumbersForDefaultLocationMocito.get(counter++);
                    }
                });
    }

    private void addDefaultNumbersForWumpusWakeupProbabilityMocito() {
        defaultNumbersForWumpusWakeupProbabilityMocito.addAll(Arrays.asList(
                numberAtWhichWumpusWillRemainSleeping));
    }

    private void addDefaultNumbersForDefaultLocationMocito() {
        defaultNumbersForDefaultLocationMocito.addAll(Arrays.asList(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                firstPitCave,
                secondPitCave));
    }

    public void setPlayerStartingCaveIndex(int playerStartingCaveIndex) {
        this.playerStartingCaveIndex = playerStartingCaveIndex;
    }

    public void setWumpusStartingCaveIndex(int wumpusStartingCaveIndex) {
        this.wumpusStartingCaveIndex = wumpusStartingCaveIndex;
    }

    public void setFirstBatStartingCaveIndex(int firstBatStartingCaveIndex) {
        this.firstBatStartingCaveIndex = firstBatStartingCaveIndex;
    }

    public void setSecondBatStartingCaveIndex(int secondBatStartingCaveIndex) {
        this.secondBatStartingCaveIndex = secondBatStartingCaveIndex;
    }

    public void setFirstPitCave(int firstPitCave) {
        this.firstPitCave = firstPitCave;
    }

    public void setSecondPitCave(int secondPitCave) {
        this.secondPitCave = secondPitCave;
    }

    public void setPlayerDropDownCave(Integer playerDropDownCave) {
        appendedNumbersForDefaultLocationMocito.add(playerDropDownCave);
    }

    public void setFirstBatFinalCave(Integer firstBatFinalCave) {
        appendedNumbersForDefaultLocationMocito.add(firstBatFinalCave);
    }

    public void setNumberAtWhichWumpusWillRemainSleeping(Integer numberAtWhichWumpusWillRemainSleeping) {
        this.numberAtWhichWumpusWillRemainSleeping = numberAtWhichWumpusWillRemainSleeping;
    }

    public void setWumpusLinkedCaveIndex(int wumpusLinkedCaveIndex) {
        this.wumpusLinkedCaveIndex = wumpusLinkedCaveIndex;
    }
}
