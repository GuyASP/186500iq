package GameArea;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FirstControl {
    @FXML
    private Button Play;

    @FXML
    protected void home_to_play() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("choosePage.fxml"));

        Stage window = (Stage)Play.getScene().getWindow();
        window.setScene(new Scene(root,800,700));
    }
}