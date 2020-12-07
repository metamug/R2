[//]: # (Server Setup)
[//]: # (https://metamug.com/img/doc-console-backend.png)
[//]: # (2019-09-20T06:55:53+00:00)
[//]: # (Run Metamug API Console on your linux server.)


#### Download from Website

The Metamug Console can be downloaded from https://metamug.com/dl as a zip or tar file in case of Linux. You can select the appropriate version depending on your operating system. No additional setup is required to run API Console.



#### Connect using SSH

If you are connecting to your remote server using `PuTTY` or `ssh` terminal, upload the the zip/tar file to your server. If you follow the guide for [AWS EC2 SSH access](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html) or [CPanel SSH access](https://documentation.cpanel.net/display/84Docs/SSH+Access#SSHAccess-ConnecttoyourserverviaSSH) depending on whether your server is hosted on AWS cloud or CPanel based hosting.

#### Download and Run

```bash
tar -xvf metamug-linux-x64-1.5.6.tar.gz

cd metamug-linux-x64-1.5.6/METAMUG_CONSOLE/bin

sudo sh catalina.sh start
```

After extracting, you will obtain a folder named `METAMUG_CONSOLE` inside which you will find the `bin` folder mentioned in the rest of this article and other required files and folders.

You can navigate to `METAMUG_CONSOLE/bin` folder for starting/stopping the server.


### Start/stop the server

The console runs as a server and can be started using the following commands:

#### For Windows

Navigate to the `\bin` folder and run the following

For starting the server.
```bash
startup.bat
```

For Stopping the server.
```bash
shutdown.bat
```


#### Mac/Linux

Navigate to the `/bin` folder and run the following

For starting the server
```bash
sudo sh catalina.sh start
```

For starting the server on the main thread of the terminal, this will show the logs on the terminal screen
```bash
sudo sh catalina.sh run
```

For stopping the server
```bash
sudo sh catalina.sh stop
```

It is recommended to run the server as root using `sudo` to prevent any file permission issues.

---

**Note**: The above scripts can be executed using `sh` instead of `bash` but we do **not** recommend doing so as the `sh` command may not completely run all the required scripts and may cause unpredictable behaviour.

Enable port 7000 to be accessed remotely.
```sh
sudo ufw allow 7000
sudo ufw enable
```


### Heap size for Java runtime

For Windows, Open `/bin/setenv.bat` and locate the following line in the file

```bash
set "JAVA_OPTS=%JAVA_OPTS% -Xms512m -Xmx2048m"
```

Xms and Xmx represents the minimum and maximum value in mb respectively. You can modify the values according to your requirements

For Max/Linux, open `/bin/setenv.sh` and locate the following lines in the file

```bash    
export CATALINA_OPTS="$CATALINA_OPTS -Xms1024m"
export CATALINA_OPTS="$CATALINA_OPTS -Xmx2048m"
```

### Launch

Point your browser at http://localhost:7000
   You should see the launch page of Metamug API Console.

1. Login into your account using the default credentials (username: admin, password: admin) given above and voila! You're ready to use the Metamug Console.

2. The password can be changed in the file `conf/config.properties`

```properties
password=admin
```

3. You'll need internet connection to download updates later. Otherwise you can use console without internet.

4. PDF Guide is available online at:

https://metamug.com/downloads/how-to-use-metamug-api.pdf

### Permission Issues on linux

If you get permission denied error on startup or shutdown.  `catalina.sh: 1: eval: permission denied`
Do the following on your linux box.

```
sudo chmod a+x metamug-linux-x64/METAMUG_CONSOLE/jdk/bin/java
sudo chown -R root:root metamug-linux-x64/
```


### Changing the default password

The default password can be changed in `conf/config.properties` file.

```properties
password=admin
```

### Changing default port 7000

A common startup problem is when another program has claimed port 7000, which
Metamug is configured to run on by default. To avoid this port conflict, Metamug's
port can be changed in `conf/server.xml`.

```xml
<Connector port="7000" protocol="HTTP/1.1" ...
```

If you encounter any problems, please create a support request at:
support@metamug.com

### SSL Reverse Proxy

If you have setup an apache server with ssl, you can use it to proxy the request to console and the generated apis.
Make changes to your `api.example.com.conf` file as follows
```cmd
ServerAdmin webmaster@localhost
DocumentRoot /var/www/html

ProxyPreserveHost On

ProxyPass / http://127.0.0.1:7000/
ProxyPassReverse / http://127.0.0.1:7000/
```
The above configuration assumes both apache and Metamug API Console are hosted on the same server.

Update the host name in `conf/server.xml` to match the domain name used in the virtual host of apache server. You need to change the `name="localhost"` to `name=api.example.com`

```xml
<Host name="api.example.com"  appBase="webapps"
            unpackWARs="true" autoDeploy="true" deployXML="true">
```
Also update your connector tag in `server.xml`

```xml
<Connector port="7000" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
               scheme="https" secure="true" proxyName="api.example.com" proxyPort="443" />
```

The following attributes inform API Console, it is being accessed via a reverse proxy with ssl.

```cmd
scheme="https" secure="true" proxyName="api.example.com" proxyPort="443"
```

If you do not perform the above step of adding proxy to Connector tag, every POST request will throw 403 error.
You can learn how to setup a [certbot ssl with apache reverse proxy](https://metamug.com/article/networking/lets-encrypt-ssl-on-tomcat.html)

### Start Building REST APIs

Once you get the [Metamug Console](https://metamug.com/docs/console) running, you can create a [backend](https://metamug.com/docs/backend) connected to your database, save a [resource](https://metamug.com/docs/resource-file) and your REST API will be deployed within seconds!

The API will be available at
```bash
http://localhost:7000/{backendName}/{resourceVersion}/{resourceName}
```

Read [this](http://metamug.com/docs/api-request) for more on making API requests.

### Questions?

Ask at Stackoverflow for Metamug:
https://stackoverflow.com/tags/metamug
