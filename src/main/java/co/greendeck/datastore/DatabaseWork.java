package co.greendeck.datastore;

/*
 *This class deals with database handling.  
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import co.greendeck.entity.CSVData;

public class DatabaseWork {

	private static final String sqlInsert = "INSERT INTO csv_table (givenTime, product_id,country,check_failure,"
			+ "APIFailure, purchase_count, revenue) VALUES (?,?,?,?,?,?,?)";

	private static final String dbUrl = "jdbc:mysql://localhost:3306/csv_data";
	private static final String name = "root";
	private static final String pass = "passingword";

	
	//Retrieves the lastId stored in database. 
	public static int getLastId() throws SQLException {
		int id = 0;

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			myConn = DriverManager.getConnection(dbUrl, name, pass);
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select id from csv_table order by id desc LIMIT 1;");
			if (myRs.next())
				id = myRs.getInt("id");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		System.out.println(id);
		return id;
	}


	//Method for inserting data.
	public static void insertInSQL(List<String> time, List<Integer> productId, List<Integer> country,
			List<Double> checkFailure, List<Double> purchaseCount, List<Double> apiFailure, List<Double> revenue) {


		try (Connection myConn = DriverManager.getConnection(dbUrl, name, pass);
				PreparedStatement pState = myConn.prepareStatement(sqlInsert)) {

			for (int i = 0; i < productId.size(); i++) {
				pState.setString(1, time.get(i));
				pState.setInt(2, productId.get(i));
				pState.setInt(3, country.get(i));
				pState.setDouble(4, checkFailure.get(i));
				pState.setDouble(5, purchaseCount.get(i));
				pState.setDouble(6, apiFailure.get(i));
				pState.setDouble(7, revenue.get(i));
				pState.addBatch();
			}

			pState.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Retrieves last stored data on database and return an object to be passed as JSON object
	public static CSVData getLastData() throws SQLException {

		CSVData lastData = null;
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		int productId = 0;
		int country_data = 0;
		String time = null;
		double[] data = new double[4];

		try {

			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			myConn = DriverManager.getConnection(dbUrl, name, pass);
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from csv_table order by id desc LIMIT 1;");
			if (myRs.next()) {
				time = myRs.getString("givenTime");
				productId = myRs.getInt("product_id");
				country_data = myRs.getInt("country");
				data[0] = myRs.getDouble("check_failure");
				data[1] = myRs.getDouble("APIFailure");
				data[2] = myRs.getDouble("purchase_count");
				data[3] = myRs.getDouble("revenue");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(myConn,myStmt,myRs);
		}

		String product = productId == 1 ? "Clothes" : "shoes";
		String country = country_data >1 ? country_data==2 ?"Canada":"GreatBritain":"USA";
		
		lastData = new CSVData(time, "ECOM_200", product, country, "Mobile", "iOS", data[0], data[1], data[2], data[3]);
		
		return lastData;
	}

	//Used to retrieve random data
	public static CSVData retrieveElement(int id) throws SQLException{
		
		CSVData randomData = new CSVData();
		String sql = "select * from csv_table where id = ?;";
		
		Connection myConn = null;
		PreparedStatement pState = null;
		ResultSet myRs = null;
		
		int productId = 0;
		int country_data = 0;
		String time = null;
		double[] data = new double[4];
		
		try {
			
			myConn = DriverManager.getConnection(dbUrl,name,pass);
			pState = myConn.prepareStatement(sql);
			pState.setInt(1,id);
			myRs = pState.executeQuery();
			if(myRs.next()) {
				time = myRs.getString("givenTime");
				productId = myRs.getInt("product_id");
				country_data = myRs.getInt("country");
				data[0] = myRs.getDouble("check_failure");
				data[1] = myRs.getDouble("APIFailure");
				data[2] = myRs.getDouble("purchase_count");
				data[3] = myRs.getDouble("revenue");
			}			
		}finally {
			close(myConn,pState,myRs);
		}
		
		String product = productId == 1 ? "Clothes" : "shoes";
		String country = country_data >1 ? country_data==2 ?"Canada":"GreatBritain":"USA";
		
		randomData = new CSVData(time, "ECOM_200", product, country, "Mobile", "iOS", data[0], data[1], data[2], data[3]);
		return randomData;
	}
	
	//Used to close database connections.
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

		if (myRs != null)
			myConn.close();

		if (myStmt != null)
			myStmt.close();

		if (myConn != null)
			myConn.close();
	}

}
