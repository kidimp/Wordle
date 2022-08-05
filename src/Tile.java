import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class Tile extends TextField {
    private static final int SIZE = 50;
    public Tile (int x, int y) {
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);
        setLayoutX(x);
        setLayoutY(y);
        setEditable(false);
        setAlignment(Pos.CENTER);
        setFont(Wordle.font);
    }
}
