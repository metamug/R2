
### What is Metamug API Console?

Metamug API Console is an API Lifecycle Management platform that runs as an HTTP web server which can be deployed on Premise or on the Cloud. API Console enables the user to define and manage REST API endpoints that provide a layer of abstraction for your backend services including databases, third party API services, business logic, scripts and more.

![Metamug API server workflow](https://metamug.com/img/api-server-workflow.png "Metamug API server workflow")

### Components of API Console

#### API console UI app
 A browser based UI to manage the REST API resources and other services like Auth, monitoring, API client management, backend properties. This is the single point of interaction between the developer and the platform. 

#### API management services
 API Management services are provided through the API Console UI app. These are as follows:

1. **Authentication**<br>
API endpoints can be protected with role based [authentication](https://metamug.com/docs/auth). User-role management is available on board. Alternatively third party oauth services can be integrated.

2. **Request analytics, monetisation and throttling**<br>
Incoming API request data is logged and is available on the analytics dashboard. The dashboard contains number of requests hitting the API per day, client device types, client operating systems, etc. Monetisation factor can be applied to the number of requests and rate limiting can be done by setting an upper limit to the number of requests.

3. **Client App management**<br>
Create multiple API keys for your API by creating client projects. The API keys allow you to track usage of your API by a particular client app on the analytics dashboard.

4. **Resource management**<br>
Every API endpoint is represented as a REST [resource](https://metamug.com/docs/resource-file). Resources can be created by entering a resource name (eg. pet). This name will appear in the API URL (/v1.0/pet). Versioning and authentication is done on the resource level.

5. **Workflow**<br>
The API workflow is designed using the [Mpath](https://metamug.com/docs/mpath) description language. The workflow involves calling different backend services, database queries, business logic invocation etc.

6. **Hot Deployment**<br>
Deployment is instant. On saving your workflow, the API is deployed in seconds.

7. **Documentation**<br>
API documentation and Open API spec for the API is generated live as soon as a resource is saved.

#### Business logic, scripting and plugins
On board code execution engine can be used for running Java maven projects as [plugins](https://metamug.com/docs/plugins). [Scripting](https://metamug.com/docs/scripting) is also supported using the Groovy scripting language.

Plugins and scripting are also useful for generating custom API responses, integrating third party Auth services or using SDKs to call external services like AWS S3 or Lambda.

####  Open source runtime
The runtime does the job of processing the incoming API requests. Source code is available on [GitHub](https://github.com/metamug/mason).

#### Backend services
The API workflow allows calling third party API services, querying of databases, execution of business logic or scripts. Query management is available for SQL databases allowing testing, saving and re using of database queries.