import javafx.scene.control.Alert;

public class MyAlert extends Alert {

    public MyAlert() {
        super(Alert.AlertType.INFORMATION);
    }


    public void show(String message) {
        setTitle(message);
        setHeaderText(null);
        setContentText(null);
        showAndWait();
    }
}
