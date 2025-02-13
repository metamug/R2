[//]: # (REST API Documentation)
[//]: # (https://lh3.googleusercontent.com/-2JxUD32RkUA/XYG_7gXwczI/AAAAAAAAIQw/8Lg0RwmZjBkrLbDIB_FDpMLaCFLgX8HHQCK8BGAsYHg/s0/2019-09-17.png)
[//]: # (2019-09-18T06:55:53+00:00)
[//]: # (Let your REST resources auto-generate neat API Documentation for you.)

Console generates API documentation for the resources. Each resource information in the documentation contains the 

* version information.
* response status code
* url
* method information
* params
* response headers 

![Metamug API Docs](https://lh3.googleusercontent.com/-2JxUD32RkUA/XYG_7gXwczI/AAAAAAAAIQw/8Lg0RwmZjBkrLbDIB_FDpMLaCFLgX8HHQCK8BGAsYHg/s0/2019-09-17.png)


### Documenting the Resource

If the resource is well documented with the [Desc Tag](https://metamug.com/docs/resource-file#desc-tag). The docs users will find it easier to derive
meaningful information. This helps developers to make requests correctly.


The following resource is saved as `movie.xml`. It will automatically generate documentation when the console resource is saved.

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0"  v="1.1">
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
    
  	<Request method="POST">
      	<Desc>Insert a movie</Desc>
        <Sql id="insert">
            INSERT INTO movie (name,rating) values ($title,CAST($rate AS DECIMAL))
        </Sql>
    </Request>
    
  	<Request method="DELETE" item="true" status="410">
      	<Desc>Delete the selected movie record</Desc>
        <Sql id="delete">
            DELETE FROM movie WHERE id=CAST($id AS DECIMAL)
        </Sql>
    </Request>
</Resource>

```


### Request Parameters

[Request Parameters](https://metamug.com/docs/request-parameters) are automatically converted into parameters in the documentation. If param tag is added
to the request, it will additionally show the information about parameters to be sent during the request.

![Param Information in Generated API Docs](https://lh3.googleusercontent.com/-nnbEW53bZw0/XYHBnwoXqfI/AAAAAAAAIQ8/1QCm62EmQpYGm-maCIpPq_q0volkxfaCQCK8BGAsYHg/s0/2019-09-17.png)


