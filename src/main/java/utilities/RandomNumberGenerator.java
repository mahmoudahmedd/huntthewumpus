package utilities;

import java.util.Random;

public class RandomNumberGenerator {

    public int generateNumber(int maximumNumber) {
        Random rand=new Random();
        return rand.nextInt(maximumNumber);
    }
}
