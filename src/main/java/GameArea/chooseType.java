package GameArea;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class chooseType {
    @FXML
    private Button NormalMode, TimeMode;
    private Areacontrol ac;

    public void ChooseMode(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playView.fxml"));
        Parent root = loader.load();
        Areacontrol ac = loader.getController();
        String mode = ((Button)event.getSource()).getText();
        ac.setMode(mode);

        Stage window = (Stage)NormalMode.getScene().getWindow();
        window.setScene(new Scene(root,800,700));

    }
}
