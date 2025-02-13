
### Item Request 

It is a request identifier as a path parameter.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://xml.metamug.net/resource/1.0 http://xml.metamug.net/schema/resource.xsd"
     v="1.1">
    <Desc>Contains information about movies.</Desc>

    <!-- Collection GET Request /movie -->
    <Request method="GET">
        <Desc>Get info of all movies with ratings greater than 3.</Desc>
        <Sql id="wellRatedMovies">
            SELECT name,rating FROM movie
            where rating gt 3.0
        </Sql>        
    </Request>

    <!-- Item GET Request /movie/{id} -->
    <Request method="GET" item="true">
        <Desc>Get info of movie with given id.</Desc>
        <Sql id="allMovies">
            SELECT * FROM movie where id=$id
        </Sql>
    </Request>

</Resource>
```

The above request executes the second request part when it receives a path with `resource/id` in it. 