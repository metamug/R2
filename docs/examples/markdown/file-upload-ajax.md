[//]: # (Uploaded File Operation)
[//]: # ( )
[//]: # (2019-10-02T06:55:53+00:00)
[//]: # (Using Plugin to process files.)

### File Upload

File must be uploaded using `Content-Type: multipart/form-data` header in a `POST` request.

### Processing Uploaded File

File Upload is automatically detected and UploadListener is called. Make sure you implement the UploadListener class as described [here](https://metamug.com/docs/plugins#event-handlers). The uploaded file can only be accessed 
inside a plugin by giving `$[_upload]` as one of the arguments.

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">

	<Request method="POST">
        <!-- File Upload is automatically detected and UploadListener is already called at this step -->
        <Execute classname="com.metamug.plugin.upload.FileUploadProcess" id="uploadProc" output="true" >
            <Arg name="uploadedFile" path="$[_upload]" />
            <Arg name="host" value="server.metamug.net" />
            <Arg name="user" value="johndoe" />
            <Arg name="password" value="pass_word" />
            <Arg name="dir" value="/var/www/uploads/myproject" />
        </Execute>
  	</Request>

</Resource>
```

### File Upload Plugin

The uploaded file can be accessed inside the processable class as follows.

```java
public class FileUploadProcess implements RequestProcessable {

    @Override
    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {
        File file = args.get("uploadedFile");

        //@TODO move the file to another location

        Response response = new Response("done");
        return response;
        
    }
}
```

### Uploading using ajax

A simple file upload can be done from the browser using HTML form and Ajax as follows:

```html
<form id="my_form">
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <input name="file" type="file" id = "upload">
            </div>
        </div>
        <div class="col-md-12">
            <div class="form-group">
                <input type="submit" class="btn btn-sm btn-primary">
            </div>
        </div>
    </div>
</form>
```

Ajax file upload request in jquery

```js

$("#my_form").submit(function(event){
    event.preventDefault(); //prevent default action
    var form_data = new FormData(this); //Creates new FormData object

    $.ajax({
        url : 'http://localhost:7000/bookmark/v1.0/code',
        type: 'POST',
        data : form_data,
        contentType: false,
        enctype: 'multipart/form-data',
        cache: false,
        processData:false
    }).done(function(response){ //
        console.log(response)
    });
});

```
