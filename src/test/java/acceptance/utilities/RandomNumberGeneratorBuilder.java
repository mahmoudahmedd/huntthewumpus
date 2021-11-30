package acceptance.utilities;

import model.GameInitialConfigurations;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class RandomNumberGeneratorBuilder {
    public RandomNumberGenerator randomNumberGenerator = mock(RandomNumberGenerator.class);

    List<Integer> defaultItemsForMocito = new ArrayList<>();
    List<Integer> appendItemsForMocito = new ArrayList<>();

    public int playerStartingCaveIndex = 9;
    public int wumpusStartingCaveIndex = 18;
    public int firstBatStartingCaveIndex = 19;
    public int secondBatStartingCaveIndex = 13;
    public int firstPitCave = 3;
    public int secondPitCave = 13;

    public RandomNumberGenerator build() {

        defaultItemsForMocito.addAll(Arrays.asList(
                playerStartingCaveIndex,
                wumpusStartingCaveIndex,
                firstBatStartingCaveIndex,
                secondBatStartingCaveIndex,
                firstPitCave,
                secondPitCave));

        defaultItemsForMocito.addAll(appendItemsForMocito);

        Mockito.when(randomNumberGenerator.generateNumber(GameInitialConfigurations.NUMBER_OF_CAVES))
                .thenAnswer(new Answer<Integer>() {
                    int counter = 0;

                    @Override
                    public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                        return defaultItemsForMocito.get(counter++);
                    }
                });


        return randomNumberGenerator;
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
}
