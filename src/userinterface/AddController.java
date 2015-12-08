package userinterface;

import businesslayer.AppData;
import businesslayer.Person;
import datalayer.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by gunbo on 11/27/2015.
 */
public class AddController implements Initializable {

    private Stage primaryStage;

    @FXML
    private TextField gui_add_textField_url;

    @FXML
    private TextField gui_add_textField_max;

    @FXML
    private Button gui_add_button_add;

    @FXML
    private Button gui_add_button_cancel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AppData appData = AppData.getAppData();


        gui_add_button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String url = gui_add_textField_url.getText();
                int maxComments;
                try {
                    maxComments = Integer.parseInt(gui_add_textField_max.getText());
                }catch (Exception e){
                    maxComments = 0;
                }
                appData.runScraping(url, maxComments);
                primaryStage.close();
            }
        });

        gui_add_button_cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

    }




    public void getPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
