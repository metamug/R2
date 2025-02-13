
### Execute Plugin

File download is peformed using a [file output plugin](https://metamug.com/docs/plugins#execute-output-processing)
The uploaded maven project should implement `RequestProcessable` interface.

### Invoking file download plugin

The plugin can be invoked using a resource file as follows.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource v="1.2" xmlns="http://xml.metamug.net/resource/1.0">
    <desc>File Download</desc>
    <Request method="GET">
    	<Param name="file" type="text" minlength="2" required="true" />
        <Execute classname="com.mycompany.store.FileDownload" output="true">
        	<Arg name="host" value="your.host.net" />
            <Arg name="user" value="server" />
            <Arg name="password" value="password" />
            <Arg name="dir" value="/home/user/projects/files" />
        </Execute>
    </Request>
</Resource>
```

### File Download Steps

1. The GET request handles the incoming request 
2. and executes the plugin. 
3. Plugin accesses the file and returns the file content.
4. Metamug detects the file download and streams the response.


### File Download Plugin

```java
public class FileDownload implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, String> args) throws Exception {

    		//.. retrieve the file from the server 
    	
            File file = new File(request.getParameter("file"));
            Response response = new Response(file);
            return response;
        
    }
}
```