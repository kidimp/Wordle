import javafx.scene.control.TextField;


public class Message {
    static TextField text = new TextField();

    public static void wordNotFound() {
        text = new TextField("Word is not found");
        text.setFont(Wordle.font);
        text.setPrefWidth(290);
        Wordle.messagePane.add(text, 16, 3);
    }


    public static void clean() {
        text = new TextField("clean");
        text.setFont(Wordle.font);
        text.setPrefWidth(290);
        Wordle.messagePane.add(text, 16, 3);
    }
}
