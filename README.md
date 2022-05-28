### Mini Project 1 ###

## Authors: ##

	Godwin Tusime
	
## MySQL Configuration (After step 3 on the execution part) ##
	1.	Create a new Local Instance
	2.	Give it the name you want
	3.	Hostname: localhost
	4.	Port: 3307
	5.	Username: root
	6.	Password: 123456
	7.	Name of the database/schema: docker_batch_db
	
## Credentials (Postman) ##

	•	Username: godwin
	•	Password: 1234
	
## Execution ##

	1.	Extract the .zip file of the project wherever you want
	2.	Using your terminal go to the directory where you have extracted the project
	3.	Where the .md and .yml are execute docker-compose up
	4. 	Create the new instance in MySQL with the information given above. If you try to create it before this step it will not work.
		a.	Test the connection to make sure that the instance is running
		b.	If you have created your instance properly and you access it, you should see the database docker_batch_db already created.
	5.	Go to Postman and open the following endpoint:
		a.	POST -> localhost:6868/api/login?userName=godwin&password=1234 
	6.	You should receive an access token and a refresh token
	7.	Go to the following endpoint and use your token to read the data from the CSV file and persist it in the database.
		a.	GET -> localhost:6868/load
	8.	You should be able to read the word “COMPLETE” with status 200.
	9.	Go to MySQL and check docker_batch_db.
	10.	The table batch_file_writter should contain two rows with the following information:
		a.	id: 1, dob: 1993-01-01, first: Peter, gpa: 1, last: Godwin
		b.	id:  2, dob: 1992-01-01, first: Sam, gpa: 2, last: Bakulu
	11.	You can check the CSV file in the project to see that the age is converted from an integer value to a date value in the database.
	12.	If you have reached this point, then the project has run successfully.
	
## END ##

