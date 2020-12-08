[//]: # (MPath)
[//]: # (https://lh3.googleusercontent.com/-TNHOw4MAEaY/XWqexHNupqI/AAAAAAAAH8g/plWsQMIunqQjo95fwKS7-n2V_klbaTKogCK8BGAs/s0/2019-08-31.png)
[//]: # (2019-09-22T06:55:53+00:00)
[//]: # (Expressions that connect APIs)



<!-- <div style="font-size: 7em;background-color: #333;color:white;margin: 0.5em 0em;padding:0.5em">
	<span style="color:yellow">$</span><span style="color: green">[</span>element<span style="color: green">]</span><span style="color:skyblue">.</span><span style="color: #999">value</span>
</div> -->

![Metamug MPath Expression](https://lh3.googleusercontent.com/-TNHOw4MAEaY/XWqexHNupqI/AAAAAAAAH8g/plWsQMIunqQjo95fwKS7-n2V_klbaTKogCK8BGAs/s0/2019-08-31.png)

> MPath is an expression language for communication between processing elements like SQL, external api calls (XRequest) and code execution


* designed to retrieve information from processing elements.
* substitution of results into next processing element.

> Hence forming the bridge between two processing elements.


MPath uses the name of the previously declared elements according as per dollor($) and square brace([]) notation. Console will throw error if MPath expression uses an element that is not declared previously in the resource file.

### MPath in SQL

![Execute to SQL](https://lh3.googleusercontent.com/-DYPgEIij510/XVRg21BGaCI/AAAAAAAAHwA/dIYl_YEXVdwyjL8m-6W0Gub21vBlAnInQCK8BGAs/s0/2019-08-14.png)

>MPath uniquely identifies an element with the path provided. If the resulting location is a complex structure, MPath will return string representation of it

```xml
<Request method="GET">
      <XRequest persist="true" id="xreq" method="GET" verbose="true"
                  url="https://api.example.com/bookstore/v1.0/store/"></XRequest>
      <Sql id="select"> SELECT $[xreq].store.book[0].title </Sql>
</Request>
```

Execute tag can return an object that can be accessed using MPath. Here the processable class is expected to return an object with attribute `name` in it.

```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">
    <Request method="GET">

        <Execute classname="com.metamug.plugin.RequestExample" id="execRes" />
        <!-- Assuming Processable class returned a customer object with name attribute  -->
        <Sql id="result" output='true'>
              SELECT name, type, dept from customer
                  where customer_name = $[execRes].name
        </Sql>

    </Request>
</Resource>
```

XRequest response can also be accessed via MPath and added to the query

```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">

    <Request method="GET">

    	  <XRequest id="xreq" url="https://postman-echo.com/get?foo1=Hello&amp;foo2=World"
                          method="GET" >
            <Header name="Accept" value="application/json" />
        </XRequest>

        <Sql id="result" output="true">
            SELECT name
            from customer where customer_id = $[xreq].body.args.foo1
        </Sql>

    </Request>

</Resource>

```

### MPath Syntax

Lets assume the above `XRequest` tag returned the following json response.
```js
{
    "store": {
        "book": [
            {
                "category": "reference",
                "author": "Nigel Rees",
                "title": "Sayings of the Century",
                "price": 8.95
            },
            {
                "category": "fiction",
                "author": "Evelyn Waugh",
                "title": "Sword of Honour",
                "price": 12.99
            },
            {
                "category": "fiction",
                "author": "Herman Melville",
                "title": "Moby Dick",
                "isbn": "0-553-21311-3",
                "price": 8.99
            },
            {
                "category": "fiction",
                "author": "J. R. R. Tolkien",
                "title": "The Lord of the Rings",
                "isbn": "0-395-19395-8",
                "price": 22.99
            }
        ],
        "bicycle": {
            "color": "red",
            "price": 19.95
        }
    },
    "expensive": 10
}
```

The below table shows MPath and its output.

| MPath                  				| Output                                                        										|
| :------------------------ 			| :----------------------------------------------------------------- 									|
| `$[xreq].store.book[0].title`  		| Sayings of the Century										       									|
| `$[xreq].store.book[0]`  				| { "category": "reference", "author": "Nigel Rees", "title": "Sayings of the Century", "price": 8.95 }	|


SQL Result is a two dimensional data. In this case, record index is used in conjugation with element identifier (`$[element]`).
For example, to fetch the 3rd row `col1` `$[getQuery][1].name`


| name 	| type 	| dept 	|
| :-----|:------|:------|
| foo	| bar	| baz	|
| foo2	| bar2	| baz2	|


### MPath in Execute Tag

![SQL Result to Code](https://lh3.googleusercontent.com/-16cDXTugxm8/XVfRv_yZKhI/AAAAAAAAHwY/KGInhK73HTsLfhNm5ikjVBK_btO6EMNzgCK8BGAs/s0/2019-08-17.png)

```xml
	<Sql id="getCustomers" output="true">
		select name, type, dept
		from customer where customer_id = $id
	</Sql>
	<Execute classname="com.example.crm.CustomerProcess" verbose="true">
		<Arg name="customerName" path="$[getCustomers][1].name" />
		<Arg name="apiKey" value="bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq" />
	</Execute>
```

Another example. Above request used item request $id and following is a collection request.

```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">

    <Request method="GET">

        <Sql id="queryResult">
            SELECT * from movie
        </Sql>

        <Execute classname="com.metamug.plugin.ExtractExample" id="execRes" output="true">
    		    <Arg name="foo1" path="$[queryResult][2].name}" />
	  	</Execute>
    </Request>

</Resource>

```

Execute tag has `Arg` tags as children to take arguments for the executing class. Arg tags are suitable for taking
arguments for key attributes like tokens, api key etc. Additionally it has a `path` attribute for extracting values from previous output.

In the below example, Execute tag accepts [XRequest](/docs/xrequest) response via MPath.
```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">

    <Request method="GET">
    	  <XRequest id="xreq" url="https://postman-echo.com/get?foo1=Hello&amp;foo2=World"
                          method="GET" >
            <Header name="Accept" value="application/json" />
        </XRequest>

        <Execute classname="com.metamug.plugin.ExtractExample" id="execRes" output="true">
    		    <Arg name="foo1" path="$[xreq].body.args.foo1" />
    	  </Execute>

    </Request>

</Resource>
```

### MPath in XRequest

```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">

    <Request method="POST">
        <Sql id="res" output="false">
            SELECT * from movie
        </Sql>
        <XRequest output="true" id="xreq" url="https://postman-echo.com/post" method="POST" >
            <Header name="Content-Type" value="application/json"/>
            <Body>
                {
                    "foo1": "$[res][0].name",
                    "foo2": "$[res][0].rating"
                }
            </Body>
        </XRequest>
    </Request>

</Resource>
```

```xml
<Resource v="1.0" xmlns="http://xml.metamug.net/resource/1.0">

    <Request method="GET">
        <Execute classname="com.metamug.plugin.RequestExample" id="execRes"/>

        <XRequest output="true" id="xreq" url="https://postman-echo.com/post" method="POST" >
           <Header name="Content-Type" value="application/json"/>
           <Body>
               {
                   "foo1": "Hello",
                   "foo2": $[execRes].name
               }
           </Body>
        </XRequest>

    </Request>

</Resource>
```

### MPath in Text Tag

Text tag is added to the resource xml to add output elements to response.

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
    <Desc> Text Tag Example </Desc>
    <Request method="GET">
      <XRequest id="jsonResponse" method="GET"
                url="https://jsonplaceholder.typicode.com/todos/1">
      </XRequest>
      <Text id="textoutput"> $[jsonResponse].title </Text>
    </Request>
</Resource>
```

The text element extracts the output and displays the response as follows

```json
{"textoutput":"delectus aut autem"}
```

### MPath in When Condition

There are cases our further processing is dependent on external API response. In such cases, MPath can be used in when condition further
to evaluate and execute. The below example demonstrates MPath condition being used to  integrate API response.

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0"  v="1.0">
      <Request method="GET">
            <XRequest id="testXReq"
                method="GET"
                output="true"
                url="https://jsonplaceholder.typicode.com/todos/1">
            </XRequest>
            <Sql id="somesql" when="$[testXReq].completed eq false">
                select * from movie
            </Sql>
      </Request>
</Resource>
```
