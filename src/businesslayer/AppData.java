package businesslayer;

import java.util.ArrayList;
import java.util.List;

import datalayer.DatabaseConnection;
import userinterface.MainController;


// This is an example of using the Singleton pattern to make the application's data available throughout the 
// application while guaranteeing that there is only one copy of it.

public class AppData {
		
	private ArrayList<Person> people = new ArrayList<Person>();
	
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

	public void runScraping(String url, int maxComments) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				ScrapeYahooNewsComments scrapeYahooNewsComments = new ScrapeYahooNewsComments(url, maxComments);
				DatabaseConnection.insertPerson(scrapeYahooNewsComments.getPersons());
				getAllPersonFromDatabase();

			}
		};

		new Thread(runnable).start();

	}

	public void getAllPersonFromDatabase(){
		setPeople(DatabaseConnection.findAllPeople());
	}

	public void searchPerson(int id){
		ArrayList<Person> person = new ArrayList();
		person.add(DatabaseConnection.selectPerson(id));
		setPeople(person);
	}

	public ArrayList<Person> getPeople() {
		return people;
	}

	private void setPeople(ArrayList<Person> people) {

		synchronized (MainController.lock){
			this.people = people;
			try {
				System.out.println("Notifying lock");
				MainController.lock.notifyAll();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

	}

	// example of a method to change the appData from throughout the project
	// there might be lots of there to add / remove data.
//
//	public void addPerson(Person person){
//
//		people.add(person); // this adds the object to the datastructures in RAM
//
//		try {
//			Connection con = DatabaseConnection.getConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Statement stmt;
//
//		// make the insert into the database.
//
//	}
	
}
