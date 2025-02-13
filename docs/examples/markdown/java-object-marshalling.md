[//]: # (Java Objects Marshalling)
[//]: # ( )
[//]: # (2019-10-21T06:55:53+00:00)
[//]: # (Add Java Objects to Response that generates JSON/XML.)


The `process()` method of the [Processable](https://metamug.com/docs/plugins#request-processable) classes returns Response objects containing Java objects as payload. The Java classes for these payload objects need to be created using JAXB annotations. This helps Metamug framework to convert the returned objects to JSON/XML.

### Rules for creating classes
- The parent class should use the `@XmlRootElement` JAXB annotation
- Instance Variables of the class must also be annotated with JAXB annotations
<!--- Inner child objects should annotated with `@XmlElement` along with a name (*contact* in this case). The name is used as key name in the JSON/XML response.-->
- All classes should have **empty constructor**.

Let us consider the following Java classes

**Customer.java**
```java
package com.metamug.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

    private String name;
    @XmlElement(name = "contact")
    private Contact contact;

    public void setName(String n) {
        name = n;
    }

    public void setContact(String phone, String email) {
        contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public Contact getContact() {
        return contact;
    }    
 }
```

**Contact.java**
```java
package com.metamug.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Contact {
    private String phone;
    private String email;

    public void setPhone(String ph) {
        phone = ph;
    }

    public void setEmail(String e) {
        email = e;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
```

Objects of the JAXB annotated classes can be returned in the following manner

```java
package com.metamug;

import com.metamug.entity.Request;
import com.metamug.entity.Response;
import com.metamug.exec.RequestProcessable;

public class CustomerHandler implments RequestProcessable{

    public Response process(Request request, DataSource ds, Map<String, Object> args) throws Exception {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setId(1);
        customer.setContact("8080808080", "john@example.com");

        Response resp = new Response(customer);
        return resp;
    }
}
```

The resource must include the classname

```xml
<Request method="GET">
    <Execute id='example' classname="com.metamug.CustomerHandler"/>
</Request>
```
By default the execute tag ouputs to the response. If you would like to suppress the object to api response, add `output=false` attribute on `Execute` tag.

### API Response

```java
MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create(mediaType, 
    "{\"customer\":{\"id\":22,\"name\":\"John Doe\",\"contact\":{\"email\":\"john.doe@metamug.com\",\"phone\":\"+1 674 2392 03\"}}}");

Request request = new Request.Builder()
  .url("http://localhost:7000/rest/v1.0/customer")
  .post(body)
  .addHeader("Content-Type", "application/json")
  .build();

Response response = client.newCall(request).execute();
```

The returned object will get converted to JSON/XML depending upon the value of the *Accept* header.

**JSON response (Accept: application/json)**
```json
{
    "customer": {
    	"contact": {
            "phone": "8080808080",
            "email": "john@example.com"
        },
        "name": "John"
    }
}
```

**XML response (Accept: application/xml)**
```xml
<response>
	<customer>
	    <contact>
	        <email>john@example.com</email>
	        <phone>8080808080</phone>
	    </contact>
	    <name>John</name>
	</customer>
</response>
```



### Returning List of Objects

List of Objects can be returned in the following manner
```java
//...
public Response process(Map<String, String> params, DataSource ds, Map<String, String> requestHeaders) {

    Customer customer = new Customer();
    customer.setName("John");
    customer.setContact("8080808080", "john@example.com");

    Customer customer2 = new Customer();
    customer2.setName("Jane");
    customer2.setContact("1212121212", "jane@example.com");

    List<Customer> customers = new ArrayList<>();
    customers.add(customer);
    customers.add(customer2);

    Response resp = new Response(customers);
    return resp;
}

//...

```

The corresponding HTTP response will be as follows

**JSON response (Accept: application/json)**
```json
[
    {
        "customer": {
            "contact": {
                "phone": "8080808080",
                "email": "john@example.com"
            },
            "name": "John"
        }
    },
    {
        "customer": {
            "contact": {
                "phone": "1212121212",
                "email": "jane@example.com"
            },
            "name": "Jane"
        }
    }
]
```

**XML response (Accept: application/xml)**
```xml
<response>
	<customer>
	    <contact>
            <email>john@example.com</email>
            <phone>8080808080</phone>
        </contact>
        <name>John</name>
    </customer>
    <customer>
        <contact>
            <email>jane@example.com</email>
            <phone>1212121212</phone>
        </contact>
        <name>Jane</name>
    </customer>
</response>
```


### Accessing returned objects 

The values of returned object can be accessed in subsequent tags using [MPath](https://metamug.com/docs/mpath). 

```xml
<Request method="GET">
    <Execute id='example' classname="com.metamug.CustomerHandler" output="false" />
    <Sql id="customer"> SELECT $[example].customer.name </Sql>
</Request>
```