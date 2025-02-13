[//]: # (User Action in REST URI)
[//]: # ( )
[//]: # (2019-09-24T06:55:53+00:00)
[//]: # (Using REST API Item Request to handle user actions.)

One of the common resources in a REST API is `user`. As per REST conventions,
the user CRUD operations can be performed on the user resource uri directly.
with the help of HTTP Methods.


But, there are other operations on the user resource. To perform these operations, typically the following URI's are used.

For user login,

```cmd
/user/login
```

For logging out users
```cmd
/user/logout
```

> Metamug provides a feature to add item request identifiers in the resource url

The actions like login and logout need to be treated as an item request identifier

```cmd
/user/{action}
```

Here the `$id` parameter will resolve to action sent in the request URI. 

These actions can be handled in resource xml as follows.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">

    <!-- Other CRUD operations for the user -->

    <Request method="POST" item="true">    
        <Desc>Handle User Requests</Desc>
        <Sql id="login" when="$id eq login">
            SELECT token FROM user_table 
            where username = $username and password = $password
        </Sql>
        <Sql id="logout" when="$id eq logout">
            SELECT username 
            FROM user_table where username = $username
        </Sql>
    </Request>

</Resource>
```

### Item Request Identifier as action

In the above resource, you can see that Request tag is marked as an item request.
A request tag marked with item identifier `item=true`, provides `$id` populated with `/user/{id}` value.

This value can be used in `when` condition to allow conditional execution of the Sql query. Item identifier
is used as an action keyword within the request to distinguish between the operations to be performed.

Multiple sql tags can contain the when condition which work for the same action.