# Output Format

This section describes various formats in which the API response is received using queries inside the resource file. The response type (JSON/XML) can be determined using the *Accept* as described <a target="_blank" href="https://metamug.com/docs/api-request#response-types">here</a>.

---

Each of the `<Sql>`, `<XRequest>` or `<Execute>` tags, produces its own output and since all these tags can be used inside a single `<Request>` tag their output is clubbed together and sent as a JSON array containing different JSON objects. So typical response to our API call will have output format as follows

```js
{
	"sqlResult":{
		"jsObject": "value1"
	},
	"xrequetResult":{
		"jsObject": "value2"
	},
	"executeResult":{
		"jsObject": "value3"
	}
}
```

### Single query response
Consider a follow resource file

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
		<Sql id="getMovies">
			SELECT name, rating FROM movie
		</Sql>
	</Request>
<Resource>
```

Gives output in this format

```js
{
	"getMovies":[
		{
			"name": "Captain America",
			"rating": 2.5
		},
		{
			"name": "Passengers",
			"rating": 5
		},
		{
			"name": "Revolver",
			"rating": 2.5
		},
		{
			"name": "The Godfather",
			"rating": 3.4
		}
	]
}
```

Here you can see data of each row is being represented as single JSONObject and all such JSONObjects are clubbed inside a JSONArray.

### Multiple query response
Now consider this resource file which produces more than one response.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
		<Sql id="getMovies" >
			SELECT name, rating FROM movie
		</Sql>
		<Sql id="userQuery" >
			SELECT user_id, user_name FROM user
		</Sql>
	</Request>
<Resource>
```

Gives output

```js
{
	"getMovies":[
		{
			"name": "Captain America",
			"rating": 2.5
		},
		{
			"name": "Passengers",
			"rating": 5
		},
		{
			"name": "Revolver",
			"rating": 2.5
		},
		{
			"name": "The Godfather",
			"rating": 3.4
		}
	],
	"userQuery":[
		{
			"user_id": 1,
			"user_name": "Foo"
		}
	]
}
```

Since we’ve two queries data from each query is enclosed within a *JSONArray* and both the *JSONArrays* is enclosed inside the main Array. The response output is in the same order as that of the `<Sql>` tag order in the resource file.

### Output

The Update query generally returns how many records have been updated or DDL queries returns a generic message which we need not show as a response to the developer for all such queries the response output can be suppressed by using a output attribute in `<Sql> or <Execute>` tag.

Consider the following resource file

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
		<Query id="getMovies" output="false">
			SELECT name, rating FROM movie
		</Sql>
		<Sql id="userQuery" >
			SELECT user_id, user_name FROM user
		</Sql>
	</Request>
<Resource>
```

Here we’ve suppressed the output of 1st `<Sql>` tag due to which it is considered as a resource file with single `<Sql>` tag as per Figure 1. The above resource file give generates the following output

```js
[
	"userQuery":{
		"user_id": 1,
		"user_name": "Foo"
	}
]
```

### Error Output

Errors are an integral part of any code development they help us identify our flaws and shortcomings, so even though errors seem to be a bad guy it is necessary that they are presented succinctly so that developers can identify the root cause of the error. So whenever there is an error we give a simple error response in the following format.

```js
{
	"errorID": 3802718736064892400,
	"message": "API Error. Please contact your API administrator.",
	"status": 512
}
```

So instead of giving a dirty error message in API response, we give the developer an errorId which he can use to see the detailed error trace from the console but sometimes errors are necessary and we want things to fail for certain condition. For which we’ve two attributes onerror and onblank. With these attributes one can give a custom error message when an error occurs, this is typically used if the data breaks any unique-key constraint or foreign-key constraints.

Consider the following resource file.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
	<!-- I've purposely written incorrect query to show you the use of onerror attribute -->
		<Sql onerror="Incorrect query">
			SELECT name,rating movie
		</Sql>
	</Request>
<Resource>
```

The given `<Sql>` will generate following output

```js
{
	"errorID": 7298420097052428000,
	"error": "Incorrect query",
	"message": "API Error. Please contact your API administrator.",
	"status": 512
}
```

### Response Notation

If you want a nested structure in your json data, the dot notation helps you to do this.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
		<Sql>
            SELECT `name`,`rating`, releaseDate as 'info.releaseDate' from movie
        </Sql>
	</Request>
<Resource>
```

The dot notation in the resource file changes to nested json in the output.

```js
{  
	"name":"Passengers",
	"rating":5,
	"info": {  
	    "releaseDate":null
	}
}
```

If we change our query to

```sql
SELECT `name`,`rating` as 'info.rating', releaseDate as 'info.releaseDate' from movie
```
`rating` and `releaseDate` both will appear under the same attribute info.

```js
{  
	"name":"Passengers",
	"info": {  
	  	"rating":5,
	    "releaseDate":null
	}
}
```
