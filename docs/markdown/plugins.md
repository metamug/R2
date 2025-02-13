[//]: # (Plugins)
[//]: # (https://lh3.googleusercontent.com/-euFMo-qtzbg/XFhzD7ozidI/AAAAAAAAFsg/3hmPP3rn5vAmsemcXmTe29paCN2Mo1LpACL0BGAYYCw/h352/2019-02-04.png)
[//]: # (2019-09-16T06:55:53+00:00)
[//]: # (Plugins allow the developer to develop independent API components.)

The following article assumes the developer knows Java* & Maven. Download the seed project to get started with plugins.

Here is the example plugin. You can download it from github.

<div>
    <!-- Place this tag where you want the button to render. -->
    <a class="github-button" href="https://github.com/metamug/plugin-example/archive/master.zip" data-icon="octicon-cloud-download" data-size="large" aria-label="Download Metamug Plugin Example on GitHub">Download</a>

    <!-- Place this tag where you want the button to render. -->
    <a class="github-button" href="https://github.com/metamug/plugin-example/fork" data-icon="octicon-repo-forked" data-size="large" aria-label="Fork Metamug Plugin Example on GitHub">Fork</a>

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async="" defer="" src="https://buttons.github.io/buttons.js"></script>
</div>
-----------------------------------------------------------------------

Plugins allow the developer to write custom business logic for their API using Java.
Code Execution capabilities are provided via [SDK](https://github.com/metamug/mtg-api) to pre-process request, post-process sql results or run a business logic.

> Because there will be cases where direct sql result to JSON would not be sufficient.


![Metamug Plugin](https://lh3.googleusercontent.com/-euFMo-qtzbg/XFhzD7ozidI/AAAAAAAAFsg/3hmPP3rn5vAmsemcXmTe29paCN2Mo1LpACL0BGAYYCw/h352/2019-02-04.png "Metamug Plugin")

### Creating Plugins

The user is required to upload a compressed .zip of a [Maven project](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) folder. The zip can be uploaded by click on **New Project** button in the Plugins section of the console.

![Metamug Plugins Section](https://metamug.com/img/plugins1.png "Metamug Plugins Section")

<!--![Metamug Upload Project](https://s3.eu-central-1.amazonaws.com/cdn.metamug.net/images/metamug.com/metamug-upload.PNG "Metamug Upload Project")
-->
**No other format except zip is supported for the project upload. Please do not upload .tar.gz, .rar or any other format.**

![Project Upload](https://metamug.com/img/plugins2.png "Project Upload")

The project should contain the following
1. One or more classes implementing one of the [Processable](https://metamug.com/docs/plugins#request-processable) interfaces explained further in this article.
2. One or more classes whose object is to be returned in the HTTP response. If custom objects are being returned.

The details of uploaded projects and information of processable classes is shown in the console

![Metamug Plugins Section 2](https://metamug.com/img/plugins3.png "Metamug Plugins Section 2")

### Implementation

The following dependency must be provided in the *pom.xml* of the Maven project

```xml
<dependency>
    <groupId>com.metamug</groupId>
    <artifactId>mtg-api</artifactId>
    <version>1.5</version>
</dependency>
```

Metamug provides following two Java interfaces
1. RequestProcessable
2. ResultProcessable

The classes implementing either of these interfaces must be **public** and will have to Override the `process()` method. The code inside the process method gets executed and an `Object` is returned. The returned object is converted to XML or JSON as specified by the HTTP request header and is returned in the HTTP response.

<div>

    <!-- Place this tag where you want the button to render. -->
    <a class="github-button" href="https://github.com/metamug" data-size="large" aria-label="Follow @metamug on GitHub">Follow @metamug</a>
    <!-- Place this tag where you want the button to render. -->
    <a class="github-button" href="https://github.com/metamug/mtg-api" data-size="large" data-show-count="true" aria-label="Star metamug/mtg-api on GitHub">Star</a>

    <!-- Place this tag in your head or just before your close body tag. -->
    <script async="" defer="" src="https://buttons.github.io/buttons.js"></script>
</div>


-------------------

Two types of plugins can be created for Metamug
1. Request processors - Incoming HTTP requests can be directly processed upon.
2. SQL result processors - The result set received by making SQL query to the database can be processed upon.


### Request Processable

The `RequestProcessable` interface can be implemented to process HTTP request. The `process()` method provides access to the request parameters, the DataSource object and the request headers. The class implementing the interface should be **public**.

```java
public interface RequestProcessable {
    public Response process(Request request, DataSource ds, Map<String, Object> args);
}
```

RequestHandler.java


```java
public class RequestHandler implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args){

    	//retrieve request parameters
    	String name = request.getParameter("name");
        String email = request.getParameter("email");

        //get database connection
        try(Connection conn = ds.getConnection()){

            String insertSQL = "INSERT INTO users(name, email) VALUES (?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			// execute insert SQL stetement
			preparedStatement .executeUpdate();
        }

        String message = "Insert completed";
        Response response = new Response();
        response.setPayload(message);
        return response;
    }
}
```


The `DataSource` object is used to obtain the database connection. The connection is given to the database of the backend in which the Maven project is uploaded. The connection **must be closed** after the database operation is done.

-----------------------------------------------------------------------------


### Execute Tag

The `Execute` tag is used for invoking the RequestProcessable classes. The value of classname attribute has to be the full name of a RequestProcessable class (including package name) inside an uploaded maven project. When the HTTP request is made to the resource, the `process()` method inside the corresponding class will be executed. The HTTP request parameters will automatically be mapped inside the `Map<String,String>` of the `process()` method.

```xml
<Request method="GET">    
   <Execute classname="com.mycompany.plugin.RequestHandler" output="true" />
</Request>
```

> Here `output` attribute hints the api to print the response of the `Execute` tag.

### Result Processable

This interface must be implemented to process SQL results. The `process()` method provides `resultMap` and `columnNames` to iterate through records and look up column names. The `rowCount` gives the number of records in the resultMap. This method is provided to override the XML/JSON output for SQL queries. This helps the user to customize the HTTP response body. The Content-Type header in this case will be text/plain and Content-Length will be the length of the output string.

```java
public interface ResultProcessable {
    public Response process(Result queryResult) throws Exception;
}
```

ResultHandler.java

```java
import com.metamug.entity.Response;
import com.metamug.entity.Result;

public class ResultHandler implements ResultProcessable {

    @Override
    public Response process(Result queryResult) {
        Map[] records = queryResult.getRecordMap();
        String id1 = records[0].get("id").toString();
        String name1 = records[0].get("name").toString();
        String id2 = records[1].get("id").toString();
        String name2 = records[1].get("name").toString();

        Response response = new Response();
        //do something with the result data
        return response;
    }
}
```
The result map obtained inside the `process()` method of the ResultProcessable class can be used to obtain values from the database which can be used for further processing.

#### SQL Post-Processing

The result received by executing the SQL query can be processed upon by the developer using the ResultProcessable interface. The attribute "classname" is available for the `<Sql>` element which is used for pointing to the ResultProcessable class. Let us consider the following resource file

```xml
...
<Request method="GET">
    <Sql id="shortner" classname="com.mycompany.plugin.ResultHandler" >
        SELECT id FROM urls WHERE url=$q
    </Sql>
</Request>
...
```
In the above case, the given SQL will be executed when a GET request is made on the urls resource. The result obtained from the database on the execution of the SQL query will be handled by the com.mycompany.plugin.ResultHandler class (included in an uploaded maven project) which implements the ResultProcessable interface.

###### * Oracle and Java are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.


### Execute Output Processing

The `process()` method has return type as `Response`. Response payload is designed to handle following 3 types.

* String
* Pojo with Annotations
* Inputstream or ByteBuffer

#### String

A simple Java string returned by the `process()` method can be obtained directly as it is in the HTTP response.

```java
public Response process(Result sqlResult) {
    String message = "Hello World";
	Response response = new Response();
	response.setPayload(message);
	return response;
}
```

#### POJO with annotation

The class of the object returned by the `process()` method should be annotated with JAXB annotations. At a minimum, the class has to be annotated with `@XmlRootElement` to automatically produce XML/JSON response depending on the Accept Header.

```java
@XmlRootElement
public class Customer {

    String name;
    //...
}
```
This is the minimal configuration required to configure serialization of the object to json/xml. You can find the whole list of annotations on [oracle's doc page](http://docs.oracle.com/javaee/6/api/javax/xml/bind/annotation/package-summary.html).

Take a look at this [example](https://metamug.com/docs/examples/java-object-marshalling) for more on returning objects.

#### InputStream

[InputStream](https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html) object is set as the payload of the `Response`. This is useful for downloading a file. Inside the `process` method, file can be accessed from the desired location and converted to InputStream object.

Alternatively, in certain cases we may prefer to peform some operations on the file input stream before we send it for download. In such a case, [ByteBuffer](https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html) can be returned after operating on the byte array obtained from the inputstream.

![InputStrema to ByteBuffer Java](https://lh3.googleusercontent.com/-WMdC9K1BiEI/XV5E1Atia2I/AAAAAAAAH0Q/J8C4D6K0U70_oDJv20Sf2vYFgwvsx3qVwCK8BGAs/s0/2019-08-22.png)

### Event Handlers

Event Handlers are invoked implicitly. They need not be declared from resource xml. However, plugins must include implementation of these handlers.


#### Upload Event

A request can be directed to the POST method of a request. As Upload event handler should be executed before the request is forwarded to the resource. So the handler can pass the `Response` to the resource xml.

```cmd
POST / HTTP/1.1
[[ Less interesting headers ... ]]
Content-Type: multipart/form-data; boundary=---------------------------735323031399963166993862150
Content-Length: 834

-----------------------------735323031399963166993862150
Content-Disposition: form-data; name="jsonObj"

Some json here in the request
-----------------------------735323031399963166993862150
Content-Disposition: form-data; name="file"; filename="E-com site requirement.PNG"
Content-Type: text/plain

Content of E-com site requirement.PNG

-----------------------------735323031399963166993862150--
```


When Upload is performed, the class implemented with the following interface is invoked. The uploaded file can be accessed from the event object also you will need to keep the param of the file object as `file`.

![Metamug Upload File](https://lh3.googleusercontent.com/-3_0a6mUUQ6o/WmmG06XH1BI/AAAAAAAACLc/CfTN4LPyRhgLHQ8welh-lhjBE8AhTjHowCL0BGAYYCw/h276/2018-01-24.png "Metamug Upload File")



```java
public class FileUpload implements UploadListener {

    @Override
    public Response uploadPerformed(UploadEvent event, DataSource dataSource) {
        File uploadedFile = event.getUploadedFile();
        Response response = new Response(uploadedFile);
        return response;
    }
}
```

Here a user must return a `Response` Object which can be made available to the resource. <!-- If a file is sent in that request then file’s name and size will be made available as a parameter to the resource in the form of `filename` and `filesize` respectively. --> If the resource is authenticated then it is mandatory to pass the authorization token else the upload will not work.

The upload Listener is saved with implicit id `_upload`. It can be accessed in the resource using the MPath `$[_upload]`

To access the uploaded file, we will write another execute tag as follows in the same POST request.

```xml
<Request method="POST">
   <Param name="file" type="text" minlength="2" required="true" />
   <Execute classname="com.mycompany.plugin.FileHandler" id="uploadProc" output="true" >
     <Arg name="uploadedFile" value="$[_upload]" />     
   </Execute>
</Request>
```

>

```java

import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;
import java.io.File;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author pc
 */
public class FileHandler implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {
        File file =  args.get("uploadedFile");
        //@TODO do some operations on the file
        Response response = new Response("Successfully registered.");
        return response;
    }
}
```

[UploadEvent](https://github.com/metamug/mtg-api/blob/master/src/main/java/com/metamug/event/UploadEvent.java) contains [Request](https://github.com/metamug/mtg-api/blob/master/src/main/java/com/metamug/entity/Request.java), and the uploaded file.

> By default, file upload is disabled in console. To enable file upload, the [default file uploader plugin](/downloads/FileUploader.zip) must
be uploaded in plugins.

![Default File Upload Plugin Metamug Console](https://lh3.googleusercontent.com/-XoVJyhWmr6o/XUM-DHi89iI/AAAAAAAAHoo/hpdCOJ0lrtQ0GNpxvcDtv-G3eftZDk0NQCK8BGAs/s0/2019-08-01.png)


Update the plugin to the latest dependency. You can download the default File Uploader plugin from [here](/downloads/FileUploader.zip).

### Examples

[API with POJO Response](https://metamug.com/docs/examples/java-object-marshalling)

[Upload File Via Ajax](https://metamug.com/docs/examples/file-upload-ajax)

[Download File with Execute Tag](https://metamug.com/docs/examples/file-download)
