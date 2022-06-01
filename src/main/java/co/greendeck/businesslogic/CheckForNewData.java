package co.greendeck.businesslogic;

/*
 * This class deals with all main CSV files handling.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.greendeck.datastore.DatabaseWork;
import co.greendeck.entity.CSVData;

public class CheckForNewData implements Runnable {

	public void run() {
		checkForNewData();
		//System.out.println("------Done------");  Uncomment me to check in console
	}

	//This method initiates new data checking if new data exists then giving command to store it on MySQL. 
	private void checkForNewData() {

		List<String> time = new ArrayList<>();
		List<Integer> country = new ArrayList<>();
		List<Integer> productId = new ArrayList<>();
		List<List<Double>> res = new ArrayList<>();
		try {

			res = getCSVData(time, productId, country);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!res.isEmpty()) {
			DatabaseWork.insertInSQL(time, productId, country, res.get(0), res.get(1), res.get(2), res.get(3));
		}
	}

	// It checks for new data by getting lastId stored in MySQL database and counts
	// the current file length if its more than lastId the extra data is stored and sent toDatabaseWork class.
	private List<List<Double>> getCSVData(List<String> time, List<Integer> productId, List<Integer> country)
			throws SQLException {

		int lastPos = -1;
		try {
			lastPos = DatabaseWork.getLastId();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int count = 0, currCount = 0;

		List<Double> checkFailure;
		List<Double> apiFailure;
		List<Double> purchaseCount;
		List<Double> revenue;
		List<List<Double>> res = new ArrayList<>();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"C:\\Users\\user\\eclipse-workspace\\assignments\\java-sync-service\\Assignment Sheet.csv"),
				"UTF-8"))) {

			checkFailure = new ArrayList<>();
			apiFailure = new ArrayList<>();
			purchaseCount = new ArrayList<>();
			revenue = new ArrayList<Double>();

			// To skip firstLine
			String line = in.readLine();

			while (count < lastPos) {
				in.readLine();
				count++;
			}
			while ((line = in.readLine()) != null) {
				currCount++;
				String[] mp = line.split(",");

				time.add(mp[0]);

				productId.add(mp[2].equals("Clothes") ? 1 : 2);
				country.add(mp[3].equals("USA")?1:mp[3].equals("Canada")?2:3);
				checkFailure.add((Double.parseDouble(mp[6])));
				apiFailure.add((Double.parseDouble(mp[7])));
				purchaseCount.add((Double.parseDouble(mp[8])));
				revenue.add((Double.parseDouble(mp[9])));

				// Setting the max data to 650 for batch processing
				if (currCount == Application.BATCH)
					break;
			}
			res.add(checkFailure);
			res.add(apiFailure);
			res.add(purchaseCount);
			res.add(revenue);
		}

		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	//This is used to write the JSON data to file.
	public static void writeData(CSVData newData) {

		ForTemporaryWork.transferToTemporary();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"C:\\Users\\user\\eclipse-workspace\\assignments\\" + "java-sync-service\\temporary.csv"),
				"UTF-8"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						"C:\\Users\\user\\eclipse-workspace\\assignments\\java-sync-service\\Assignment Sheet.csv"),
						"UTF-8"))) {

			String k;
			while ((k = in.readLine()) != null)
				out.append(k).append("\n");

			out.append(newData.getTime()).append(",").append(newData.getFamily()).append(",").append(newData.getProduct())
			.append(",").append(newData.getCountry()).append(",").append(newData.getDeviceType()).append(",").append(newData.getOs());
			k = Double.toString(newData.getCheckOutFailure());
			out.append(",").append(k);
			k = Double.toString(newData.getPaymentAPIFailure());
			out.append(",").append(k);
			k = Double.toString(newData.getPurchaseCount());
			out.append(",").append(k);
			k = Double.toString(newData.getRevenue());
			out.append(",").append(k).append("\n");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static CSVData getRandomRecord() {
		
		CSVData randomData = null;
		int id = 0;
		try {
			id = DatabaseWork.getLastId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Random random = new Random();
		id=random.nextInt(id);
		try {
			randomData = DatabaseWork.retrieveElement(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return randomData;
	}

}
