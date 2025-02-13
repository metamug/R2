[//]: # (JAX-RS To Metamug Resource)
[//]: # (https://lh3.googleusercontent.com/-ue_YB6U9yFc/XYP7hBvhFiI/AAAAAAAAITo/iXj7MvJn1z0PcxEfAXfxTl2eI-KUjeRLQCK8BGAsYHg/s0/2019-09-19.png)
[//]: # (2019-09-20T06:55:53+00:00)
[//]: # (In this example we will see how a resource is written in JAX-RS and compare it with a Metamug Resource)




### Java API for RESTful Web Services (JAX-RS)

The current standard for writing REST APIs in java is JAX-RS. Though the popular spring framework [follows its own annotations](https://spring.io/guides/gs/rest-service/). 

You can find multiple implementation for the standard.

- [Jersey](https://jersey.github.io/)
- [Apache CXF](http://cxf.apache.org/)
- [JBoss RESTeasy](http://www.jboss.org/resteasy)

and many others.

The folllowing example demonstrates how a typical GET request 

	/user?name=banana&quant=12&desc=fruit

```java
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserService {

	@GET
	public Response getUsers(
		@QueryParam("name") String name,
		@QueryParam("quant") int quantity,
		@QueryParam("desc") String description) {

		return Response
		   .status(200)
		   .entity("{ itemName :" + name + ", itemQuantity : " + quantity
			+ ", itemDescription :" + description + " }").build();

	}

}
```

### Metamug Resource

The same resource can be written in metamug as follows.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	<Request method="GET">
	    <Query>
			select $name "itemName", $quant "itemQuantity", $desc "itemDescription"
		</Query>	 
	</Request>
</Resource>
```

Check the [resource file docs](https://metamug.com/docs/resource-file.php) for usage

### Service-DAO-POJO-DTO-Repository-JDBC-JPA-...

But JAX-RS REST resources are not so simple.

Every resource in JAX-RS is backed by 

- A Service Class
- A Data Access Object (DAO) class
- JPA Implementation (Hibernate, EclipseLink etc.) or JDBC
- POJOs 

[Here is an example for this in spring boot](https://medium.com/@josedrojasa/building-a-rest-api-using-spring-boot-2-0-69b5f6dd8da4)