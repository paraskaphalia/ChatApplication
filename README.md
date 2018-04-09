# ChatApplication
->This is a desktop based chat application written in JAVA.
->The application need Mysql to run,Install mysql
->According to the your username,password of the mysql account change code lines
 String USER = "root";
  String PASS = "1234";
-> according to the name of the database change lines
  String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	String DB_URL = "jdbc:mysql://localhost/sonoo";
  here sonoo is the name of the database.
->inside the database to tables are used 
    i) messages->to store all the messages transfered between users.
    ii)users-> this table is used to store the login details of the user.
->table users contains four fields which are fname(varchar(20)) lname(varchar(20)) hname(varchar(20)) pass(varchar(20))
->table messages contains 2 fields which are name(varchar(20)) message(varchar(500))

->create these two tables in the machine where server is running


RUNNING THE APPLICATION
-------------------------
->run the server code
->after the server starts run the client side .
->enter the ip address of the server and the port of the in which application is running.By default server is running in the port number-   5239
