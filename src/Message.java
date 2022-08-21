import java.util.Arrays;

public class Message {
    private static final MyAlert alert = new MyAlert();

    public static void wordNotFound() {
        alert.show("Word is not found");
    }

    public static void wordTooShort() {
        alert.show("Word is too short");
    }

    public static void youLose(String word) {
        alert.show("You lost! The answer is \"" + word + "\"");
    }

    public static void youWin() {
        alert.show("You win!");
    }

    public static void showWord(String word) {
        alert.show("The answer is \"" + word + "\"");
    }
}
