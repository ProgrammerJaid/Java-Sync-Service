![puzzle-4-xxl](https://user-images.githubusercontent.com/96864350/171295652-b3bed83d-1f8a-4b21-bcee-185ebc2f4d46.png)
# Java Sync Service.

An API that reads CSV File and updates data into databse if new rows are found in CSV File.Key features:

- Automatic Data gets uploaded on the database.
- Gets started with one command.

How to use?
--
This API uses your `localhost:8080`.
To run the sync service use this url:
    `http://localhost:8080/java-sync-service/api/startsync`
    
The code accepts JSON object as to populate the CSV file the link to add new rows in CSV is: `http://localhost:8080/java-sync-service/api/populate`
The JSON object can be provided in the following way:

```
{
    "time": "2018-08-23 04:25:29",
    "family": "ECOM_200",
    "product": "Clothes",
    "country": "Canada",
    "deviceType": "Mobile",
    "os": "iOS",
    "checkOutFailure": 2008.1457,
    "paymentAPIFailure": 2183.7807,
    "purchaseCount": 14984.9599,
    "revenue": 11564.4751
}

```
Its a lot of name-value pairs to write.So I have provided a link to ease the job just for testing purpose.
We can use the following `http://localhost:8080/java-sync-service/api/random` to get **random row** to be uploaded just for checking purposes.

Once the data in provided Assignment sheet.csv is fully loaded the _new data_ added can be seen database and we can retrieve it with using `http://localhost:8080/java-sync-service/api/lastrecord`.

The following are the screenshot of successful testing of endpoints in postman
---
To start the service.
![Screenshot (109)](https://user-images.githubusercontent.com/96864350/171312552-28d7efbb-1191-43ce-9cdd-1ce368bbff84.png)

To retrieve data and populate it, please **note**,use `http://localhost:8080/java-sync-service/api/lastrecord`.This can be used to checklast data in database.
![Screenshot (107)](https://user-images.githubusercontent.com/96864350/171312888-7ef5d27d-0d65-4e2e-b002-a4795d57a33a.png)

We can use `http://localhost:8080/java-sync-service/api/random` to get random data, that can be used to populated just for testing.
![Screenshot (112)](https://user-images.githubusercontent.com/96864350/171313586-d9cccd7e-cd64-4511-b5dd-51da20731385.png)


Send the data to populate through **POST** method body in JSON format `http://localhost:8080/java-sync-service/api/populate`.
![Screenshot (108)](https://user-images.githubusercontent.com/96864350/171313183-15b37be8-6325-40db-be93-977a1faa6ce7.png)

---

Once it starts syncing and all the data has been updated from csv file to database.When we try to populate with new data please wait 1 or half a minute to check lastRecord as the it will sync again in 2 minutes.The default batch value is set to 650 as it takes 100seconds to transfer from file to database.
![Screenshot (104)](https://user-images.githubusercontent.com/96864350/171314081-0051322d-f36c-4a26-8c45-a7ca630383ca.png)

MySQL script for creating database table is given below, helpful sql commands.
```
CREATE USER 'root'@'localhost' IDENTIFIED BY 'passingword';

GRANT ALL PRIVILEGES ON * . * TO 'root'@'localhost';
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'passingword';
create database csv_data;
use csv_data;

create table csv_table(id INT NOT NULL AUTO_INCREMENT,
givenTime VARCHAR(19) ,product_id INT,country INT,
check_failure Decimal(11,4),APIFailure Decimal(11,4),
purchase_count Decimal(11,4),revenue Decimal(11,4),
primary key(id));

select * from csv_table order by id desc limit 1;
drop table csv_table;

```

Thank you.
