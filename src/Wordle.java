import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    private static String word;
    static char[] wordle;

    private final HashMap<String, MyButton> buttons = new HashMap<>();
    private final ArrayList<Tile> arrayOfTiles = new ArrayList<>();
    private int index = 0;  // Index of each tile in tiles field.
    private int counter = 0;    // Counter for every letter in each attempt. Counter = from 0 to WORDLE_LENGTH
    private String attempt = "";
    private boolean isEndOfGame = false;

    static Connection connection;
    static Statement statement;
    static ResultSet resultSet;

    @Override
    public void start(Stage stage) {
        try {
            AnchorPane root = new AnchorPane();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("WORDLE");
            stage.setWidth(610);
            stage.setHeight(610);
            stage.setResizable(false);
            addComponents(root);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addComponents(AnchorPane root) {
        Pane tilesPane = new Pane(150, 5);
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

        Pane topLinePane = new Pane(0, 5);
        root.getChildren().add(topLinePane);

        Pane buttonsPaneFirstLine = new Pane(0, 340);
        root.getChildren().add(buttonsPaneFirstLine);

        Pane buttonsPaneSecondLine = new Pane(25, 405);
        root.getChildren().add(buttonsPaneSecondLine);

        Pane buttonsPaneThirdLine = new Pane(0, 470);
        root.getChildren().add(buttonsPaneThirdLine);

        // Creating letters listener
        LetterEvent letterEvent = new LetterEvent();

        // Adding buttons
        String[] lettersFirstLine = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
        for (int i = 0; i < 10; i++) {
            createButton(buttonsPaneFirstLine, letterEvent, lettersFirstLine[i], i + 1, 1);
        }

        String[] lettersSecondLine = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
        for (int i = 0; i < 9; i++) {
            createButton(buttonsPaneSecondLine, letterEvent, lettersSecondLine[i], i + 1, 2);
        }

        String[] lettersThirdLine = {"Z", "X", "C", "V", "B", "N", "M"};
        for (int i = 0; i < 7; i++) {
            createButton(buttonsPaneThirdLine, letterEvent, lettersThirdLine[i], i + 2, 3);
        }

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

        Button btn_giveUp = new MyButton("give up");
        btn_giveUp.setPrefWidth(110);
        btn_giveUp.setPrefHeight(10);
        btn_giveUp.setFont(Font.font("Arial", 18));
        btn_giveUp.setOnMouseClicked((EventHandler<Event>) event -> giveUp());
        topLinePane.add(btn_giveUp, 1, 1);

        Button btn_newGame = new MyButton("new game");
        btn_newGame.setPrefWidth(110);
        btn_newGame.setPrefHeight(10);
        btn_newGame.setFont(Font.font("Arial", 18));
        btn_newGame.setOnMouseClicked((EventHandler<Event>) event -> newGame());
        topLinePane.add(btn_newGame, 1, 2);
    }


    private void createButton(Pane pane, LetterEvent letterEvent, String text, int column, int row) {
        MyButton button = new MyButton(text);
        button.setOnMouseClicked(letterEvent);
        buttons.put(text, button);
        pane.add(button, column, row);
    }


    private void giveUp() {
        Message.showWord(word);
        isEndOfGame = true;
    }


    private void delete() {
        if (isEndOfGame) {
            return;
        }
        if (index == 0 || counter == 0) {
            return;
        }
        index--;
        counter--;
        attempt = attempt.substring(0, attempt.length() - 1);
        arrayOfTiles.get(index).setText("");
    }


    private void enter() {
        if (isEndOfGame) {
            return;
        }

        if (counter != 5) {
            Message.wordTooShort();
            return;
        }

        if (!isWordExist(attempt)) {
            Message.wordNotFound();
            return;
        }

        counter = 0;
        char[] attemptInChars = attempt.toCharArray();
        word = word.toUpperCase();

        for (int i = 0; i < WORDLE_LENGTH; i++) {
            String symbol = String.valueOf(attempt.charAt(i));
            if (word.contains(symbol)) {
                recolorElements(symbol, i, "yellow");
                if (String.valueOf(word.charAt(i)).equals(symbol)) {
                    recolorElements(symbol, i, "green");
                }
            } else {
                recolorElements(symbol, i, "gray");
            }
        }
        attempt = "";
        if (index >= arrayOfTiles.size()) {
            isEndOfGame = true;
            System.out.println("You lose");
            Message.youLose(word);
        } else {
            if (Arrays.equals(attemptInChars, wordle)) {
                System.out.println("You win");
                Message.youWin();
            }
        }
    }


    private void recolorElements(String symbol, int i, String color) {
        MyButton button = buttons.get(symbol);
        if (button != null) {
            button.setStyle("-fx-base: " + color);
        }
        arrayOfTiles.get(index - 5 + i).setStyle("-fx-control-inner-background: " + color + ";");
    }


    private boolean isWordExist(String attempt) {
        String query = "SELECT EXISTS(SELECT Word FROM wordle.Words WHERE word = '" + attempt + "')";
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(query);
            resultSet.absolute(1);
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void newGame() {
        index = 0;
        counter = 0;
        attempt = "";
        isEndOfGame = false;
        for (Tile tile : arrayOfTiles) {
            tile.setText("");
            tile.setStyle("-fx-control-inner-background: white;");
        }
        buttons.forEach((key, value) -> value.setStyle("-fx-base: "));
        getWordle();
    }


    private class LetterEvent implements EventHandler<Event> {
        public void handle(Event event) {
            if (isEndOfGame) {
                System.out.println("It is the end of game");
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


    public static void getConnectionToDB() {
        String url = "jdbc:mysql://localhost:3306/wordle";
        String user = "root";
        String password = "prasby";

        try {
            System.out.println("Establishing connection to database");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to database");
        } catch (SQLException e) {
            System.out.println("Error establishing a database connection");
            throw new RuntimeException(e);
        }
    }


    public static void closeConnectionToDB() {
        try {
            statement.close();
            resultSet.close();
            connection.close();
            System.out.println("Connection successfully closed");
        } catch (Exception e) {
            System.out.println("Error closing a database connection");
            throw new RuntimeException(e);
        }
    }


    public static void getWordle() {
        String query = "SELECT * FROM wordle.Words ORDER BY RAND() LIMIT 1";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                word = resultSet.getString(2);
                wordle = word.toUpperCase().toCharArray();
                System.out.println(word);
            }
        } catch (SQLException e) {
            System.out.println("Error getting Wordle");
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        getConnectionToDB();
        getWordle();
        launch(args);
        closeConnectionToDB();
    }
}
