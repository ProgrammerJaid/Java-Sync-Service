package co.greendeck.businesslogic;

import java.sql.SQLException;

/*
 * This is the application class.Here batch can be set to 650 is the max data that can be transferred in one go
 * It takes 100 seconds to write 650 rows on MySQL.If batch was 1000 then it would take nearly 160 seconds 
 * more than 2 minutes. 
 */


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import co.greendeck.datastore.DatabaseWork;
import co.greendeck.entity.CSVData;
/*
 * This class initiates all the services.
 * Please note regarding tomcat you must have mysql connector jar in your tomcat lib or in src/main/webapp
 * I have not tested in this program with web app,I have tomcat in my program files/Apache tomcat/lib directory
 */

public class Application {

	private static ScheduledExecutorService newData = Executors.newSingleThreadScheduledExecutor();
	public static final int BATCH = 650;

	public static void syncData() {

		System.out.println("Checking for new data...");
		CheckForNewData data = new CheckForNewData();
		newData.scheduleAtFixedRate(data, 0, 2, TimeUnit.MINUTES);
	}

	public static CSVData getCSVData() {
		CSVData randomData = null;
		try {
			randomData = DatabaseWork.getLastData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return randomData;
	}
}
