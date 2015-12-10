package userinterface;

import businesslayer.AppData;
import businesslayer.Context;
import businesslayer.Person;

import javafx.application.Platform;
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

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage primaryStage;

    @FXML private ListView<Person> main_gui_listView;
    @FXML private Button main_controller_add;
    @FXML private Button main_controller_search;
    @FXML private TextField main_controller_textField;
    @FXML private ProgressIndicator main_controller_progressIndicator;
    @FXML private ChoiceBox main_controller_choiceBox;
    @FXML private Button main_gui_button_deleteSelectedItem;

    public final static Object lock = new Object();
    private final ObservableList<Person> currentList = FXCollections.observableArrayList();




    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Context context = Context.getInstance();
        context.getDatabaseProgress().setNumber(0);
        AppData appData = AppData.getAppData();
        appData.getAllPersonFromDatabase();
        setListViewItems();
        main_gui_listView.setItems(currentList);
        main_controller_choiceBox.getItems().addAll("ID","NICKNAME","COMMENT");
        main_controller_choiceBox.getSelectionModel().selectFirst();
        main_controller_progressIndicator.progressProperty().bind(context.getDatabaseProgress().numberProperty());





        main_controller_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToAddGUI();
                updateListView();
            }
        });

        main_controller_search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    updateListView();
                    if(main_controller_textField.getText().trim().isEmpty()){
                        throw new Exception("Empty Text");
                    }

                    appData.searchPerson(main_controller_choiceBox.getSelectionModel().getSelectedItem().toString(), main_controller_textField.getText().toString());
                    System.out.println(main_controller_choiceBox.getSelectionModel().getSelectedItem().toString() + " "  + main_controller_textField.getText().toString());

                }catch (Exception e){
                    System.out.println("Invalid Value");
                    appData.getAllPersonFromDatabase();
                }

            }
        });

        main_gui_button_deleteSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    appData.deletePerson(main_gui_listView.getSelectionModel().getSelectedItem().getId());
                }catch (NullPointerException e){
                    System.out.println("No selection");
                }catch (Exception e){
                    e.printStackTrace();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/layout/gui_add.fxml"));
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
