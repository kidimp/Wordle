import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class Tile extends TextField {
    private static final int SIZE = 50;
    public Tile() {
        setPrefWidth(SIZE);
        setPrefHeight(SIZE);
        setEditable(false);
        setAlignment(Pos.CENTER);
        setFont(Wordle.font);
    }
}
