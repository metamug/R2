[//]: # (Request Parameters)
[//]: # (https://metamug.com/article/images/ajax-form-1.png)
[//]: # (2019-09-16T06:55:53+00:00)
[//]: # (URL Query String or POST request method body, console handles them well.)

This section describes processing of parameters received from the HTTP request. How to make request with parameters has been discussed <a href="https://metamug.com/docs/api-request#sending-request-parameters" target="_blank">here</a>.

Before proceeding to the following article, the reader must be familiar with the following

* XML (Extended Markup Language)
* RegEx Regular Expression for pattern matching


### Accessing request parameters
HTTP request parameters can be accessed anywhere inside `<Request>`. The name of the parameter must be prefixed with **$** symbol to access it.

- **GET** Request - Let us consider a GET request made to `/employee?department=marketing`. Here query parameter can be accessed using the variable *$department*.
- **POST/PUT** Request - POST/PUT requests can be made with [form parameters or JSON body](https://metamug.com/docs/api-request#making-request-with-parameters). In both cases, the key names of the parameters can be accessed using **$** symbol.

### Parameters inside Sql tag

In the following example, the request parameter `$department` (query parameter in GET request) is accessed inside `<Sql>`.

```xml
<Sql id="getDept">
	SELECT name,age,location FROM emp WHERE dept=$department
</Sql>
```

### Parameter Validation

Parameters can be validated using `<Param>` inside `<Request>`

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
	    <Desc>
	    	Request to display a single page.
	        User needs to calculate offset = limit + offset
	        for each subsequent request.
	        Offset is 900 i.e There are 1000 records.
	        Limit being 100, Page size cannot be more than 100.
	    </Desc>
	    <Param name="limit" type="number" min="0" max="100"/>
	    <Param name="offset" type="number" min="0" max="900"/>
	    <Sql id="getTasks" requires="limit,offset" limit="limit" offset="offset">
	        SELECT * FROM tbl_task_master
	    </Sql>
	</Request>
</Resource>
```


### Type Attribute

Here type is not only identifying the type of the variable but also cause a validation error if the value sent isn't the same type. For example, the type number can have floating point and integer numbers. The value for supported types are:

* url
* text
* number
* date
* datetime
* time
* email

### Validation Attributes

| Attribute | Description                                                  |
|-----------|--------------------------------------------------------------|
| min       | Minimum value of the parameter. Works only with type number. |
| max       | Maximum value of the parameter. Works only with type number. |
| minlength | Minimum length of the parameter string.                      |
| maxlength | Maximum length of the parameter string.                      |
| pattern   | Regular Expression to match the parameter string.            |
| value     | The default value of the parameter, in case it is not sent.  |
| required  | Makes sending the parameter mandatory.                       |

### Requires Attribute (Query/Update)

The requires attributes help identify the system that certain parameters are needed for the query to be executed. Typically, these parameters are used in the where clause of the query in "Query" tag. And as insert values, when performing an insert operation in "Update" tag.

The parameters required by the SQL statement are listed in this attribute, separated by commas. For e.g., `<Sql id="listTasks" requires="name,age,phone,salary" >`

### Pagination Parameters

The query executed can be paginated using limit and offset attribute on query tag, as seen in the example above (page.xml). These attributes take parameter names as value. When the query is executed on the server, the offset and limit are applied to the query defined by these attributes.

```xml
<Param name="limit" type="number" min="0" max="100"/>
<Param name="offset" type="number" min="0" max="900"/>
<Sql id="listTasks" requires="limit,offset" limit="limit" offset="offset">
    SELECT * FROM tbl_task_master
</Sql>
```

Suppose this query `SELECT * FROM tbl_task_master` gives you 100 records but you only want show 3 records. This can be done with the help of query parameters `limit` and `offset`.

Limit parameter reduces the records whereas the offset help's you to set from which record you would like to display data from the 100 records.

```cmd
http://localhost:7000/scheduler/v1.0/task?offset=11&limit=3
```

The above API call will give you 3 results starting from 11.

If you want to update the offset for the next page it will be `offset = offset + limit` i.e **offset = 11 + 3** so the offset would be 14 and limit will be 3.

### Item ID
The variable **$id** is reserved for id of the resource in case of an item request. Let us consider a GET request made to `/employees/2`. Here, the item id is *2* and it can be accessed in the resource as follows

```xml
<Sql id="listEmp" >
    SELECT * FROM employee WHERE id=$id
</Sql>
```

Note you need to your request tag with `item=true` attribute for $id to be available in the request. Check the difference between an Item request and collection request here.
https://metamug.com/docs/resource-file#collection-&amp;-item-request

### Test Data for Request Parameters

The SQL queries written in a resource file are [tested](https://metamug.com/docs/resource-file#sql-query-testing-on-resource-save) by the system before saving the file. Test data inputs can be specified for the SQL queries containing request parameters using the `<Param>` tags as follows

```xml
<Request method="POST">

    <Param name="desc" type="text" value="some desc" />
    <Param name="hits" type="text" value="100" />
    <Param name="url" type="text" value="https://example.com" />

  	<Sql id="add_link">
		INSERT INTO link (description,hits,url) VALUES ($desc,$hits,$url)
	</Sql>

</Request>
```

The *name* attribute should match the parameter variable name and the *value* attribute will be used as the test data input when testing the SQL query. 

