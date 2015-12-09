package userinterface;

import businesslayer.AppData;
import businesslayer.DatabaseProgress;
import businesslayer.Person;

import datalayer.DatabaseConnection;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
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
import javafx.scene.control.*;

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
    @FXML private TextField main_controller_textField;
    @FXML private ProgressIndicator main_controller_progressIndicator;

    public final static Object lock = new Object();
    private final ObservableList<Person> currentList = FXCollections.observableArrayList();


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        DatabaseConnection.createTable();

        AppData appData = AppData.getAppData();

        appData.getAllPersonFromDatabase();
        setListViewItems();

        main_gui_listView.setItems(currentList);

        main_controller_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToAddGUI();
                main_controller_progressIndicator.setProgress(0);
                updateListView();
            }
        });

        main_controller_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    updateListView();
                    int id = Integer.parseInt(main_controller_textField.getText().toString());
                    appData.searchPerson(id);
                }catch (Exception e){
                    System.out.println("Invalid Value");
                    appData.getAllPersonFromDatabase();
                }

            }
        });

    }

    protected void updateListView(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        System.out.println("Waiting");
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setListViewItems();
                            main_controller_progressIndicator.setProgress(1);
                        }
                    });

                }
            }
        };

        new Thread(runnable).start();
    }

    private void setListViewItems(){
        AppData appData = AppData.getAppData();

        currentList.clear();
        currentList.addAll(appData.getPeople());
        System.out.println("ListView Size: " + main_gui_listView.getItems().size());
        //System.out.println(appData.getPeople());

    }

    private void goToAddGUI(){
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
