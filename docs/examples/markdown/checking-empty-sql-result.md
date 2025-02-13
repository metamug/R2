
The following GET request is designed to retrieve customer information. 

```xml

<Request method="GET">
  	
    <Param name="name" type="text" required="true" />
  	
  	<!-- Show the customer information stored in the database  -->
  	<Sql id="customerList">
  		select * from customer where name = $name
  	</Sql>  
  
</Request>

```

SQL can give out zero to many results. We try to check for 0 results using first record attribute using [MPath](https://metamug.com/docs/mpath). 
We can take any attribute for this purpose. We can use empty keyword inside `when` condition 

```xml
<Sql when="empty $[customerList][0].id" id="addCustomer">
    insert into new_customer_query(name) values($name)
</Sql> 
```
OR we can use the standard `eq` comparison with `null`

```xml
<Sql when="$[customerList][0].id eq null" id="addCustomer">
    insert into new_customer_query(name) values($name)
</Sql> 
```

In our example we are saving the customer query into another table, in case our main customer table does not 
return any record.


```xml
<Request method="GET">
    
    <Param name="name" type="text" required="true" />
    
    <!-- Show the customer information stored in the database  -->
    <Sql id="customerList">
      select * from customer where name = $name
    </Sql>  

    <Sql when="empty $[customerList][0].id" id="addCustomer">
      insert into new_customer_query(name) values($name)
    </Sql>
  
</Request>
```  

Any other element like [XRequest](https://metamug.com/docs/xrequest) or a [Plugin](https://metamug.com/docs/plugins) can be executed based on empty Sql result. But for the sake of simplicity, we chose another Sql statement.

