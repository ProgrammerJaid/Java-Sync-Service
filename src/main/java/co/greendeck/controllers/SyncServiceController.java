package co.greendeck.controllers;

/*
 * Controller class
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.greendeck.businesslogic.Application;
import co.greendeck.businesslogic.CheckForNewData;
import co.greendeck.entity.CSVData;

@RestController
@RequestMapping("/api")
public class SyncServiceController {

	private CSVData data;
	
	@GetMapping("/startsyncing")
	public void syncing() {
		
		Application.syncData();
	}
	
	@GetMapping("/lastrecord")
	public CSVData getCSVData(){
		data = Application.getCSVData();
		return data;
	}
	
	@PostMapping("/populate")
	public void setDataInFile(@RequestBody CSVData data) {
		CheckForNewData.writeData(data);
	}
	
	@GetMapping("/random")
	public CSVData randomRecord() {
		return CheckForNewData.getRandomRecord();
	}
}
