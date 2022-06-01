package co.greendeck.businesslogic;

/*
 * Class for handling temporary data and transfer to temporary file processes.
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

public class ForTemporaryWork {

	// Used to copy provided file to a temporary file and so that data can again be copied
	// and written with old data+new data in main CSV File
	public static void transferToTemporary() {

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(
						"C:\\Users\\user\\eclipse-workspace\\assignments\\java-sync-service\\Assignment Sheet.csv"),
				"UTF-8"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(
								"C:\\Users\\user\\eclipse-workspace\\assignments\\java-sync-service\\temporary.csv"),
						"UTF-8"))) {

			String k;
			while ((k = in.readLine()) != null) {
				out.append(k).append("\n");
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
