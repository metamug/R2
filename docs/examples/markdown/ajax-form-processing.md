# POST Request: Process HTML5 form with Ajax

The following example demonstrates an HTML5 form saving data via an Ajax API call using the `POST` method. The ajax call will be processed by an REST resource written in xml. 

### Prerequisite
Basic knowledge of the following technologies.
- HTML5
- XML
- SQL

### On Client Side

When the end-user inputs the data in the form and clicks the submit button the form data is sent with the HTTP POST method, this data onsubmit function catches the data and with the help of the xhr.send request sends the data to the respective URL.

```html
<form id="x-login-form">
    <div class="form-group">
        <label for="xuser">Username</label>
        <input type="text" name="iuser" class="form-control" />
    </div>
    <div class="form-group">     
        <label for="xpass">Password</label>
        <input type="password" name="xpass" class="form-control" />
    </div>  
    <input type="submit" class="btn btn-danger" value="Submit" class="form-control" />
</form>
```

------------------------------------------------------------------------------------

Javascript ajax to resource `form.xml`

```javascript
//get the form from DOM (Document object model) 
    var form = document.getElementById('x-login-form');
    form.onsubmit = function(event){
        var xhr = new XMLHttpRequest();
        var data = new FormData(form);
        //open the request
        xhr.open('POST','http://localhost:7000/tests/v1.0/form')
        //send the form data
        xhr.send(data);
        //Dont submit the form.
        return false; 
    }
```

### On the Network

The form-data format for sending data over the network to the server.

![Metamug Server Side](https://metamug.com/article/images/ajax-form-1.png "Metamug Server Side")

### On Server Side

The parameters sent in the POST request are received and replaced with their corresponding $ symbols. These $ variables must match exact names of the POST params that are sent in the request. Here's the resource `form.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
    <Request method="POST">
        <Desc> Create a User </Desc>
        <Sql> 
            INSERT INTO USER (user, pass, comment) 
            values ($iuser, $xpass, $comment)
        </Sql>
    </Request>
</Resource>
```
### In the Database

If you query the  `user` table, you can find the data just saved from the form.

```sql
select * from user;
```

![Metamug Database](https://metamug.com/article/images/ajax-form-2.png "Metamug Database")

### What Next?

This example demonstrated `POST` method. The next example shows how to use the `GET` ajax call results.

[Display List with Ajax and Metamug Resource](https://metamug.com/docs/examples/display-list-with-xhr)