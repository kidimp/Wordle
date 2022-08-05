import java.util.ArrayList;
import java.util.Collections;

public class Generator {

    public char[] getWordle() {
        char[] wordle = new char[Wordle.WORDLE_LENGTH];

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        for (int i = 0; i < Wordle.WORDLE_LENGTH; i++) {
                wordle[i] = Character.forDigit(list.get(i), 10);
        }

        return wordle;
    }
}
