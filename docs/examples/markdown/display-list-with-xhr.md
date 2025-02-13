# GET Request: Display list with Ajax

In this example, we will demonstrate how we can consume the JSON response returned by Metamug REST API.

### Create the Metamug backend

Let us start by <a href="https://metamug.com/docs/backend#backend-creation" target="_blank">creating a backend</a> with name "crm" and create a database table called "customer" using the following query

```sql
CREATE TABLE customer(
    customer_id INT NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(255),
    PRIMARY KEY (customer_id)
);
```  

We can insert data in the table using INSERT queries

```sql
INSERT INTO customer (customer_name) VALUES ("Adam Smith");
INSERT INTO customer (customer_name) VALUES ("John Doe");
```

### Create the REST API resource

Now we shall <a href="https://metamug.com/docs/resource-file" target="_blank">create a resource file</a> named "customer" and define a GET request inside it which will return JSON containing all records
inside the customer table as shown

`customer.xml`
```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
    <Desc> Customer Information </Desc>
    <Request method="GET">
        <Desc> Return all records from "customer" </Desc>
        <Sql> SELECT customer_id,customer_name FROM customer </Sql>
    </Request>

</Resource>
```

### Making API request

On saving the above resource file, a REST API will be generated having URL endpoint 

```cmd
http://localhost:7000/crm/v1.0/customer
```

To get customer data we shall make HTTP GET request to this URL and get JSON as shown

```javascript
[
	{"customer_id": 1, "customer_name": "Adam Smith"},
	{"customer_id": 2, "customer_name": "John Doe"},
]
```


### JS code with ES6

Metamug sends json array as result. In the below example we will using for each to loop over the result and appending each item in the html list.

The first step of attaching the values received via ajax is identifying the `ul` tag in the document. 

```javascript
var demo =  document.getElementById("demo"); //grab the element
```

We will use JQuery's popular AJAX method to make the request and ES6 for processing the response.

Here's the whole code.

```html
<ul id="demo"></ul>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> 

<script type="text/javascript">

$.ajax({
    url: "http://localhost:7000/crm/v1.0/customer", 
    success: names => {

        var demo =  document.getElementById("demo"); //grab the element

        names.forEach( name => {
            var li = document.createElement('li') //create li
            li.innerHTML = name.CUSTOMER_NAME; //write inner html 
            demo.appendChild(li) //append to ul 
        })
    }
});
</script>
```