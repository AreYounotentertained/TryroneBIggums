package userinterface;

import businesslayer.Person;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Created by gunbo on 11/27/2015.
 */
public class CustomGridPane extends GridPane {
    private Person person;

    public CustomGridPane(Person person) {
        this.person = person;


        this.addColumn(5);
        this.add(new Label(person.getFirstName()), 0, 0);
        this.add(new Label(person.getLastName()),1,0);
        this.add(new Label(Integer.toString(person.getAge()) ),2,0);
        this.add(new Label(Long.toString(person.getCreditCard())),3,0);
        this.add(new Label(Long.toString(person.getSsn())),4,0);

        this.setPrefWidth(100);

        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));

    }

    public CustomGridPane(String c1, String c2, String c3, String c4, String c5) {
        this.addColumn(5);
        this.add(new Label(c1), 0,0);
        this.add(new Label(c2), 1,0);
        this.add(new Label(c3 ),2,0);
        this.add(new Label(c4),3,0);
        this.add(new Label(c5),4,0);

        this.setPrefWidth(100);

        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));
        this.getColumnConstraints().add(new ColumnConstraints(100));

    }

}
