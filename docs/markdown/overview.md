# Metamug Console

Metamug Console provides a framework for creating, managing and hosting REST APIs. REST APIs built with the [open-source metamug mason](https://github.com/metamug/mason) are data-driven, designed easily in few clicks through the user-friendly [web console interface](https://metamug.com/docs/console).

[API security and API permissions](https://metamug.com/docs/auth) are handled by the framework in order to guarantee security and privacy of data. The platform also provides automatic generation of API documentation. The framework provides <a href="/docs/metamug-features">many other features</a> to help developers deliver APIs faster.


![Metamug Block Diagram](https://lh3.googleusercontent.com/-d85Ygzat-H4/XFhzKJAFbFI/AAAAAAAAFsg/tTwambcbfwoZUh3sgO_Kd2LVQtyiemWLQCL0BGAYYCw/h422/2019-02-04.png "Metamug Block Diagram")


### Metamug Request - Response Flow

Metamug maintains a channel to allow components to communicate with the request and the response.
The database access and <a href="/docs/xrequest">external API calls</a> are available at every step to allow the components to communicate with the request and database. The request is available
to each process in the chain and each process can alter and relay the request information for
it's subsequent process.

<!-- ![Metamug Request-Response](https://lh3.googleusercontent.com/-W9PQ9vZfx4c/WYg-pT6baEI/AAAAAAAAAtc/Weiph-rWMugrDR_YEEsFJumLYM7hGPOgACL0BGAYYCw/h454/2017-08-07.png "Metamug Request-Response") -->

The response for the request is generated as soon as the request is handled and it is carried forward from process to process until the final process is reached. Here the response is serialized and send back to the client.


<a href="https://metamug.com/docs/console">Metamug Console</a> is a REST API development platform which makes use of XML to expose secure REST APIs over SQL databases. The objective of Metamug is to minimize the server-side code needed to be written by the developers so that they can focus on building outstanding UI/UX for their REST API driven Apps.

### Open-source Mason Runtime

The REST API backends created using Metamug Console make use of <a href="https://github.com/metamug/mason">Mason</a>, an open-source Java based REST API library.

### Marshalling

Single Page WebApps and Mobile Apps do not use entity objects instead they use data formats like JSON/XML. Parsing to entity objects is not required. Marshalling data is done on objects. Metamug converts SQL results straight to JSON/XML as desired.

### Request Mapping to SQL

Mapping request parameters to SQL query allows us to define mandatory fields implicitly. The resource file doesn't need to explicitly define the parameter or the NOT NULL property. The non-mandatory parameters sent can be used to use a switching between multiple SQL statements. Since roughly 80% of the requests are CRUD operations. Mapping HTTP requests straight to SQL cuts the long route.

### Plugins

<a href="https://metamug.com/docs/plugins">Plugins</a> can be developed using Java and Maven for mapping business logic functions to a request. I. Result Processing The default behavior of generating JSON/XML can be overridden to send the custom response using code. II. Request Processing The default behavior of generating JSON/XML can be overridden to send the custom response using code.

### Request Pre-processing

The [parameters](https://metamug.com/docs/request-parameters) sent to the server can be configured to be validated in same resource XML before the query is executed.

### Live Documentation

As soon as a resource file is updated, its documentation is generated. The generated API document contains parameters to be sent, HTTP methods status codes and validations performed on the server. This helps the app developers consume the API better.

![Live API Docs](https://metamug.com/img/feature_livedoc1.png "Live API Docs")

### Hot Deployment

Changes applied to resource XML are propagated immediately to the API. This enables quick fixes to SQL, validations and helps change the request handling as business requirement changes immediately.

### API Versioning

Versioning allows the API developer to maintain multiple copies of the same resource with changes. Every version of the resource can handle requests separately.

```
http://localhost:7000/shop/v1.0/product
http://localhost:7000/shop/v2.0/product
```

### Database Connection

The REST API backend can be connected to any SQL database and REST APIs can be developed on the database quickly.

### External API Requests

HTTP requests to third party APIs can be made using the <a href="https://metamug.com/docs/xrequest">XRequest</a> feature which allows communication between multiple APIs.

![External API Requests](/img/xrequest.png "XRequest Metamug")

### Request Analytics

Metamug Console provides a statistics panel which displays request count and other data related to requests hitting the REST API backend.

![API Request Analytics](https://metamug.com/img/feature_request_analytics1.png "API Request Analytics")

### Backend Export

The REST API backend developed using Metamug console can be exported for use in production environment. The [exported](https://metamug.com/docs/backend#exporting-backend-for-production) backend is in the form of a Java Web Archive (WAR).

![Metamug War Export](/img/war-export.png)

### Query Catalog and Testing
SQL queries can be [saved](https://metamug.com/docs/sql-catalog) along with test data values. On saving, the queries are tested on your connected database, allowing you to check their validity. This process ensures reusability of queries and testing before deployment of the API.

### Query Test On Resource Save
The SQL queries written inside a resource file are [tested](https://metamug.com/docs/resource-file#sql-query-testing-on-resource-save) on the connected database before the resource is saved. This ensures that the SQL query does not have any errors before the API is created.

### Resource Authentication
Resource XML contains an [auth](https://metamug.com/docs/auth) attribute that determines which role can access the resource. The API request made to an authenticated resource can be authorized using Basic-Auth or JWT Auth methods.


### RFC Implementation

https://tools.ietf.org/html/rfc7230

https://tools.ietf.org/html/rfc7231

https://tools.ietf.org/html/rfc2616  

https://tools.ietf.org/html/rfc7519  

https://tools.ietf.org/html/rfc6750  