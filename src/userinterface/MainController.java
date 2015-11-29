package userinterface;

import businesslayer.Person;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage primaryStage;

    @FXML private ListView<Person> main_gui_listView;
    @FXML private Button main_controller_add;
    @FXML private Button main_controller_search;


    private final ObservableList currentList = FXCollections.observableArrayList();

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Person person1 = new Person("Mark", "Alano", 12, 123, 1344);
        Person person2 = new Person("Bark", "Alano", 12, 123, 1344);

        ArrayList<Person> test = new ArrayList<>();

        main_gui_listView.setItems(currentList);
        currentList.add(new CustomGridPane("First Name", "Last Name", "Age", "Credit Card", "SSN"));
        currentList.add(new CustomGridPane(person1));
        currentList.add(new CustomGridPane(person2));

        main_controller_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToAddGUI();
            }
        });

        main_controller_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

    }

    public void goToAddGUI(){
        Stage stageAdd = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui_add.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddController controller = loader.getController();
        controller.getPrimaryStage(stageAdd);
        stageAdd.setTitle("Add Person");
        stageAdd.setScene(new Scene(root));
        stageAdd.show();
    }

    public void getPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}