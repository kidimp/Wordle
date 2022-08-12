import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class Pane extends GridPane {
    public Pane(int intendFromLeftEdge, int indentFromTopEdge) {
        // Setting horizontal and vertical intervals between components in a grid
        setHgap(10);
        setVgap(10);
        // Indent from the top edge
        setLayoutY(indentFromTopEdge);
        setLayoutX(intendFromLeftEdge);
        setAlignment(Pos.TOP_CENTER);
    }
}
