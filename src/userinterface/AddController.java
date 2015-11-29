package userinterface;

import businesslayer.AppData;
import businesslayer.Person;
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
import java.util.ResourceBundle;

/**
 * Created by gunbo on 11/27/2015.
 */
public class AddController implements Initializable {

    private Stage primaryStage;

    @FXML
    private TextField gui_add_textField_first;

    @FXML
    private TextField gui_add_textField_last;

    @FXML
    private NumberTextField gui_add_textField_age;

    @FXML
    private NumberTextField gui_add_textField_ssn;

    @FXML
    private NumberTextField gui_add_textField_cc;

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
                Person person = new Person(
                        gui_add_textField_first.getText().toString(),
                        gui_add_textField_last.getText().toString(),
                        Integer.parseInt(gui_add_textField_age.getText().toString()),
                        Long.parseLong(gui_add_textField_ssn.getText().toString()),
                        Long.parseLong(gui_add_textField_cc.getText().toString()));
                appData.addPerson(person);
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
