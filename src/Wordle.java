import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * HOW TO PLAY:
 * Guess the WORDLE in 6 tries.
 * Each guess must be a valid 5-digit number. There are no repeated digits in the number. Hit the enter button to submit.
 * After each guess, the color of the tiles will change to show how close your guess was to the number.
 * <p>
 * EXAMPLE:
 * Wordle - 12345.
 * Your guess - 98361.
 * The digit '3' is in the number and in the correct spot. So the color of it tile turned green.
 * The digit '1' is in the number but in the wrong spot. So the color of it tile turned yellow.
 * The digits '9', '8' and '6' not in the number i any spot. So the color of their tiles turned gray.
 * <p>
 * JAVAFX DESCRIPTION:
 * For the correct work of program it is necessary download and install JavaFX application.
 * File - Project Structure - Libraries - From Maven, in the searching line type fx,
 * Choose from the list org.openjfx:javafx-fxml:11.0.2
 * Install in the folder with program.
 * In Run - Edit Configurations - VM options write
 * --module-path "/Users/pras/IdeaProjects/Wordle/lib" --add-modules javafx.controls,javafx.fxml
 */

public class Wordle extends Application {
    static Font font = Font.font("Arial", 20);
    public static final int WORDLE_LENGTH = 5;
    static char[] wordle;
    private final ArrayList<Tile> arrayOfTiles = new ArrayList<>();
    private int index = 0;  // Index of each tile in tiles field.
    private int counter = 0;    // Counter for every digit in each attempt. Counter = from 0 to WORDLE_LENGTH
    private String attempt = "";
    private boolean isEndOfGame = false;

    @Override
    public void start(Stage stage) {
        try {
            AnchorPane root = new AnchorPane();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(310);
            stage.setHeight(600);
            stage.setResizable(false);
            addComp(root);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComp(AnchorPane root) {

        // adding a (WORDLE_LENGTH x WORDLE_LENGTH) tiles field
        for (int y = 10; y < 300; y += 60) {
            for (int x = 10; x < 300; x += 60) {
                Tile tile = new Tile(x, y);
                arrayOfTiles.add(tile);
                root.getChildren().add(tile);
            }
        }

        // Adding pane for digits components (buttons)
        Pane digitsPane = new Pane(325);
        root.getChildren().add(digitsPane);

        // Adding pane for logic components (buttons)
        Pane logicPane = new Pane(445);
        root.getChildren().add(logicPane);

        // Adding pane for new game button
        Pane newGamePane = new Pane(495);
        root.getChildren().add(newGamePane);

        // Creating digit listener
        DigitEvent digitEvent = new DigitEvent();

        // Adding buttons
        Button btn_1 = new MyButton("1");
        btn_1.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_1, 1, 1);

        Button btn_2 = new MyButton("2");
        btn_2.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_2, 2, 1);

        Button btn_3 = new MyButton("3");
        btn_3.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_3, 3, 1);

        Button btn_4 = new MyButton("4");
        btn_4.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_4, 4, 1);

        Button btn_5 = new MyButton("5");
        btn_5.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_5, 5, 1);

        Button btn_6 = new MyButton("6");
        btn_6.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_6, 1, 2);

        Button btn_7 = new MyButton("7");
        btn_7.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_7, 2, 2);

        Button btn_8 = new MyButton("8");
        btn_8.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_8, 3, 2);

        Button btn_9 = new MyButton("9");
        btn_9.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_9, 4, 2);

        Button btn_0 = new MyButton("0");
        btn_0.setOnMouseClicked(digitEvent);
        digitsPane.add(btn_0, 5, 2);

        Button btn_del = new MyButton("delete");
        btn_del.setMinSize(110, 50);
        btn_del.setOnMouseClicked((EventHandler<Event>) event -> delete());
        logicPane.add(btn_del, 2, 1);

        Button btn_enter = new MyButton("enter");
        btn_enter.setMinSize(170, 50);
        btn_enter.setOnMouseClicked((EventHandler<Event>) event -> enter());
        logicPane.add(btn_enter, 1, 1);

        Button btn_newGame = new MyButton("new game");
        btn_newGame.setMinSize(290, 50);
        btn_newGame.setOnMouseClicked((EventHandler<Event>) event -> newGame());
        newGamePane.add(btn_newGame, 1, 2);
    }


    private void delete() {
        if (index == 0 || counter == 0) {
            return;
        }
        index--;
        counter--;
        attempt = attempt.substring(0, attempt.length() - 1);
        arrayOfTiles.get(index).setText("");
    }


    private void enter() {
        if (counter != 5) {
            return;
        }
        counter = 0;
        char[] attemptInChars = attempt.toCharArray();
        attempt = "";
        for (int i = 0; i < WORDLE_LENGTH; i++) {
            boolean contains = false;
            for (char c : wordle) {
                if (c == attemptInChars[i]) {
                    contains = true;
                    break;
                }
            }
            if (contains) {
                arrayOfTiles.get(index - 5 + i).setStyle("-fx-control-inner-background: yellow;");
            } else {
                arrayOfTiles.get(index - 5 + i).setStyle("-fx-control-inner-background: gray;");
            }
            if (attemptInChars[i] == wordle[i]) {
                arrayOfTiles.get(index - 5 + i).setStyle("-fx-control-inner-background: green;");
            }
        }
        if (Arrays.equals(attemptInChars, wordle)) {
            isEndOfGame = true;
            if (index == arrayOfTiles.size()) {
                System.out.println("You lose");
            } else {
                System.out.println("Yoe win");
            }
        }
    }


    private void newGame() {
        index = 0;
        counter = 0;
        attempt = "";
        isEndOfGame = false;
        Generator generator = new Generator();
        wordle = generator.getWordle();
        System.out.println(wordle);

        // cleaning a WORDLE_LENGTH x WORDLE_LENGTH tiles field
        for (Tile tile : arrayOfTiles) {
            tile.setText("");
            tile.setStyle("-fx-control-inner-background: white;");
        }
    }


    public class DigitEvent implements EventHandler<Event> {
        public void handle(Event event) {
            if (isEndOfGame) {
                System.out.println("isEndOfGame");
                return;
            }
            if (counter == 5) {
                return;
            }

            // Getting source of event (getting the button that triggered the event)
            MyButton btn = (MyButton) event.getSource();
            // Getting content of current button
            String btnDigit = btn.getText();
            arrayOfTiles.get(index).setText(btnDigit);
            attempt += btnDigit;
            index++;
            counter++;
        }
    }


    public static void main(String[] args) {
        Generator generator = new Generator();
        wordle = generator.getWordle();
        System.out.println(wordle);
        launch(args);
    }
}
