package acceptance.utilities;

import utilities.GameInitialConfigurations;
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
    List<Integer> defaultNumbersForLinkedCaveIndicesMocito = new ArrayList<>();

    public int playerStartingCaveIndex = 9;
    public int enemyPlayerStartingCaveIndex = 6;
    public int wumpusStartingCaveIndex = 18;
    public int firstBatStartingCaveIndex = 19;
    public int secondBatStartingCaveIndex = 13;
    public int firstPitCave = 3;
    public int secondPitCave = 13;

    public RandomNumberGenerator build() {
        addDefaultNumbersForDefaultLocationMocito();

        defaultNumbersForDefaultLocationMocito.addAll(appendedNumbersForDefaultLocationMocito);

        configureDefaultNumbersForDefaultLocationMocito();
        configureDefaultNumbersForWumpusWakeupProbabilityMocito();
        configureDefaultNumbersForLinkedCaveIndicesMocito();

        return randomNumberGenerator;
    }

    private void configureDefaultNumbersForLinkedCaveIndicesMocito() {
        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_LINKED_CAVES))
                .thenAnswer(new Answer<Integer>() {
                    int counter = 0;

                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return defaultNumbersForLinkedCaveIndicesMocito.get(counter++);
                    }
                });
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

    private void addDefaultNumbersForDefaultLocationMocito() {
        defaultNumbersForDefaultLocationMocito.addAll(Arrays.asList(
                playerStartingCaveIndex,
                enemyPlayerStartingCaveIndex,
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
        defaultNumbersForWumpusWakeupProbabilityMocito.add(numberAtWhichWumpusWillRemainSleeping);
    }

    public void addLinkedCaveIndex(int linkedCaveIndex) {
        defaultNumbersForLinkedCaveIndicesMocito.add(linkedCaveIndex);
    }

    public void setEnemyPlayerStartingCaveIndex(Integer enemyPlayerStartingCaveIndex) {
        this.enemyPlayerStartingCaveIndex = enemyPlayerStartingCaveIndex;
    }
}
