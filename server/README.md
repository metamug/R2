Metamug API Server 1.0.1 README
----------------------------------------------

Thank you for downloading Metamug AS 1.0.1 This distribution comes with a
built-in Tomcat 9.0.16 web server, so it runs (almost) out of the box.


Metamug Console
---------------------------------------

1. Login into your account using the default credentials **(admin/admin)** given above and voila!! you're ready to use the Metamug Console.

2. You'll need internet connection to work with the Console Web. 

3. Point your browser at http://localhost:7000/
   You should see Metamug's Launch Page.


PDF Guide is available online at:

https://metamug.com/downloads/how-to-use-metamug-api.pdf

PROBLEMS?
----------------------------------

A common startup problem is when another program has claimed port 7000, which
Metamug is configured to run on by default. To avoid this port conflict, Metamug's
port can be changed in conf/server.xml.

If you encounter any problems, please create a support request at:
support@metamug.com


JAVA_HOME setup for Code Execution
--------------------------------------------

1. Install Oracle's (formerly Sun's) Java Development Kit (JDK) 1.8 or above:

   http://www.oracle.com/technetwork/java/javase/downloads/index.html

2. Set the JAVA_HOME variable to where you installed Java. See the following instructions
   for details:

   http://metamug.com/download/set-java-home.php

MySQL Installation
------------------------------------

This API Server comes default configured with HSQLDB. But it can be configured to work with MySQL server 5.6+ (You can use XAMPP or LAMP for easier control of database).
Setup instructions below assume that you have MySQL installed on your local machine.

1. Run the SETUP.sql script in your MySQL server.
   It'll create 'mtg_console' database and tables which are required for the console to run and one database user;DO NOT remove this user from your database.
   It'll also create one user with credentials as follows
   username: admin
   password: admin
2. Remember this credentials since you'll be using this to login to the console, you can later on change your password but not the username.In case you FORGOT your changed password, run the reset_user.sql script on MySQL server which will again set your credentials to default ones.

3. Open the 'db.properties' file located at "API Server/conf/" and enter your database username and password. If your database doesn't have any user then create one with  ALL Privileges and then fill out the db.properties file. A sample file is already provided.

4. Keep your MySQL server running. Run API Server using starup.bat or startup.sh on Linux/Unix from "API Server/bin/" folder.


QUESTIONS?
----------

Questions? Try the docs at:

https://metamug.com/docs/

or ask at Stackoverflow for Metamug:

https://stackoverflow.com/tags/metamug

> Happy API Development and thank you for using Metamug API Server!
 " The Metamug Team