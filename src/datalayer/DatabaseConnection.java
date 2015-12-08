package datalayer;

import businesslayer.Person;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseConnection {
	
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		
		if (connection == null || connection.isClosed()) {
			connection = magicallyCreateNewConnection();
		}
		return connection;
	}

	private static Connection magicallyCreateNewConnection() {
		connection = null;
		try{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:MyDatabase.db");
			System.out.println("Opened database successfully");
		}catch (Exception e){
			System.out.println(e);
			connection = null;
		}

		return connection;
	}

	public static Connection deletePerson(int id){
		connection = null;
		Statement stmt = null;

		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			stmt = connection.createStatement();
			String sql = "DELETE from MARK where ID=" + id + ";";
			stmt.executeUpdate(sql);
			connection.commit();
			connection.close();

			System.out.println("(Deleted Person "+ id + ") done successfully");
		} catch ( Exception e ) {
			System.out.println(e);
			connection = null;
		}
		return connection;

	}

	public static ArrayList<Person> findAllPeople(){
		connection = null;
		Statement statement;
		ArrayList<Person> people = new ArrayList<>();

		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM MARK;");
			while ( rs.next() ) {
				people.add(new Person(rs.getInt("ID"),rs.getString("NICKNAME"), rs.getString("COMMENT")));
			}
			rs.close();
			statement.close();
			connection.close();

			System.out.println("(Find All People) done successfully");
		} catch ( Exception e ) {
			System.out.println(e);
			people = null;
		}
		return people;

	}

	public static Person selectPerson(int id){
		connection = null;
		Statement statement = null;
		Person person = new Person();
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM MARK where ID=" + id + ";");
			while ( rs.next() ) {
				person.setId(rs.getInt("ID"));
				person.setNickName(rs.getString("NICKNAME"));
				person.setComment(rs.getString("COMMENT"));
			}
			rs.close();
			statement.close();
			connection.close();

			System.out.println("(Select Person " + id + ") done successfully");
		} catch ( Exception e ) {
			System.out.println(e);
			person = null;
		}
		return person;

	}



	public static Connection insertPerson(Person person){
		connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);

			String sql = "INSERT INTO MARK (NICKNAME,COMMENT) VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, person.getNickName());
			preparedStatement.setString(2, person.getComment());
			preparedStatement.execute();

			System.out.println("added " + person.getNickName());

			connection.commit();
			connection.close();

			System.out.println("(Insert Person " + person.getNickName() + ") done successfully");
		} catch ( Exception e ) {
			System.out.println(e);
			connection = null;
		}

		return connection;
	}

	public static Connection insertPerson(ArrayList<Person> people){
		connection = null;
		Statement statement = null;

			try {
				connection = getConnection();
				connection.setAutoCommit(false);

				for (Person person: people){
					try {
						String sql = "INSERT INTO MARK (NICKNAME,COMMENT) VALUES (?, ?)";
						PreparedStatement preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, person.getNickName());
						preparedStatement.setString(2, person.getComment());
						preparedStatement.execute();

						System.out.println("added " + person.getNickName());
						connection.commit();

					}catch (SQLException e){
						System.out.println(e);
						System.out.println("Failed to add "+ person.getNickName());
						System.out.println(person.getComment());
						connection = getConnection();
					}
				}

				connection.close();
				System.out.println("Finished Adding all people");
			}catch (Exception e){
				System.out.println(e);
				connection = null;
			}
		return connection;
	}

	public static Connection createTable(){
		connection = null;
		Statement statement = null;
		try {
			connection = getConnection();

			statement = connection.createStatement();
			String sql = "CREATE TABLE MARK " +
					"(ID                INTEGER      PRIMARY KEY    AUTOINCREMENT, " +
					"NICKNAME           CHAR(30)     NOT NULL, " +
					"COMMENT           	TEXT            ) ";
			statement.executeUpdate(sql);
			statement.close();
			connection.close();

			System.out.println("Table created successfully");
		}catch (Exception e){
			System.out.println(e);
			connection = null;
		}

		return connection;
	}

}
