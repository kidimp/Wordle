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
 * Each guess must be a valid 5-letter word. Hit the enter button to submit.
 * After each guess, the color of the tiles will change to show how close your guess was to the word.
 * <p>
 * EXAMPLE:
 * WEARY
 * The letter W is in the word and in the correct spot. So the color of it tile turned green.
 * PILLS
 * The letter I is in the word but in the wrong spot. So the color of it tile turned yellow.
 * VAGUE
 * The letter U is not in the word in any spot. So the color of their tiles turned gray.
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
            stage.setTitle("WORDLE");
            stage.setWidth(610);
            stage.setHeight(600);
            stage.setResizable(false);
            addComponents(root);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addComponents(AnchorPane root) {
        Pane tilesPane = new Pane(150, 10);
        root.getChildren().add(tilesPane);

        for (int i = 0; i < WORDLE_LENGTH * WORDLE_LENGTH; i++) {
            Tile tile = new Tile();
            arrayOfTiles.add(tile);
        }

        int item = 0;
        for (int i = 1; i <= WORDLE_LENGTH; i++) {
            for (int j = 1; j <= WORDLE_LENGTH; j++) {
                tilesPane.add(arrayOfTiles.get(item++), j, i);
            }
        }

        Pane buttonsPaneFirstLine = new Pane(0, 325);
        root.getChildren().add(buttonsPaneFirstLine);
        Pane buttonsPaneSecondLine = new Pane(25, 390);
        root.getChildren().add(buttonsPaneSecondLine);
        Pane buttonsPaneThirdLine = new Pane(0, 455);
        root.getChildren().add(buttonsPaneThirdLine);

        // Creating letters listener
        LetterEvent letterEvent = new LetterEvent();

        // Adding buttons
        Button btn_q = new MyButton("Q");
        btn_q.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_q, 1, 1);

        Button btn_w = new MyButton("W");
        btn_w.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_w, 2, 1);

        Button btn_e = new MyButton("E");
        btn_e.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_e, 3, 1);

        Button btn_r = new MyButton("R");
        btn_r.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_r, 4, 1);

        Button btn_t = new MyButton("T");
        btn_t.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_t, 5, 1);

        Button btn_y = new MyButton("Y");
        btn_y.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_y, 6, 1);

        Button btn_u = new MyButton("U");
        btn_u.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_u, 7, 1);

        Button btn_i = new MyButton("I");
        btn_i.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_i, 8, 1);

        Button btn_o = new MyButton("O");
        btn_o.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_o, 9, 1);

        Button btn_p = new MyButton("P");
        btn_p.setOnMouseClicked(letterEvent);
        buttonsPaneFirstLine.add(btn_p, 10, 1);

        Button btn_a = new MyButton("A");
        btn_a.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_a, 1, 2);

        Button btn_s = new MyButton("S");
        btn_s.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_s, 2, 2);

        Button btn_d = new MyButton("D");
        btn_d.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_d, 3, 2);

        Button btn_f = new MyButton("F");
        btn_f.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_f, 4, 2);

        Button btn_g = new MyButton("G");
        btn_g.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_g, 5, 2);

        Button btn_h = new MyButton("H");
        btn_h.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_h, 6, 2);

        Button btn_j = new MyButton("J");
        btn_j.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_j, 7, 2);

        Button btn_k = new MyButton("K");
        btn_k.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_k, 8, 2);

        Button btn_l = new MyButton("L");
        btn_l.setOnMouseClicked(letterEvent);
        buttonsPaneSecondLine.add(btn_l, 9, 2);

        Button btn_z = new MyButton("Z");
        btn_z.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_z, 2, 3);

        Button btn_x = new MyButton("X");
        btn_x.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_x, 3, 3);

        Button btn_c = new MyButton("C");
        btn_c.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_c, 4, 3);

        Button btn_v = new MyButton("V");
        btn_v.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_v, 5, 3);

        Button btn_b = new MyButton("B");
        btn_b.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_b, 6, 3);

        Button btn_n = new MyButton("N");
        btn_n.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_n, 7, 3);

        Button btn_m = new MyButton("M");
        btn_m.setOnMouseClicked(letterEvent);
        buttonsPaneThirdLine.add(btn_m, 8, 3);

        Button btn_enter = new MyButton("↵");
        btn_enter.setMinWidth(75);
        btn_enter.setFont(Font.font("Arial", 35));
        btn_enter.setOnMouseClicked((EventHandler<Event>) event -> enter());
        buttonsPaneThirdLine.add(btn_enter, 1, 3);

        Button btn_del = new MyButton("⌫");
        btn_del.setMinWidth(85);
        btn_del.setFont(Font.font("Arial", 30));
        btn_del.setOnMouseClicked((EventHandler<Event>) event -> delete());
        buttonsPaneThirdLine.add(btn_del, 9, 3);

//        Button btn_newGame = new MyButton("new game");
//        btn_newGame.setMinSize(290, 50);
//        btn_newGame.setOnMouseClicked((EventHandler<Event>) event -> newGame());
//        newGamePane.add(btn_newGame, 1, 2);
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
            if (index > arrayOfTiles.size()) {
                System.out.println("You lose");
            } else {
                System.out.println("You win");
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


    public class LetterEvent implements EventHandler<Event> {
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
            String btnLetter = btn.getText();
            arrayOfTiles.get(index).setText(btnLetter);
            attempt += btnLetter;
            index++;
            counter++;
        }
    }


    public static void main(String[] args) {
//        Generator generator = new Generator();
//        wordle = generator.getWordle();
//        System.out.println(wordle);
        launch(args);
    }
}
