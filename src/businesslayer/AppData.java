package businesslayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import datalayer.DatabaseConnection;


// This is an example of using the Singleton pattern to make the application's data available throughout the 
// application while guaranteeing that there is only one copy of it.

public class AppData {
		
	private List<Person> people = new ArrayList<Person>();
	
	// this is the reference to the single instance of the AppData class
	private static AppData appData = null;
	
	
	// A private constructor that is only called one time
	private AppData() {
		
	}
	
	// A public method to make the app Data available throughout the application.
	// The first time the method is called, the Single instance of AppData is created,
	//   each subsequent time, the one data object created is returned.
	
	public static AppData getAppData(){
		if(appData == null){
			appData = new AppData();
		} 
		
		return appData;
		
	}
	
	// example of a method to change the appData from throughout the project
	// there might be lots of there to add / remove data.
	
	public void addPerson(Person person){
		
		people.add(person); // this adds the object to the datastructures in RAM
		
		try {
			Connection con = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt;
		
		// make the insert into the database.
		
	}
	
}
