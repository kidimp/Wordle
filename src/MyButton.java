import javafx.scene.control.Button;

public class MyButton extends Button {

    public MyButton(String text) {
        super(text);
        setFont(Wordle.font);
        setMinSize(50, 50);
    }
}
