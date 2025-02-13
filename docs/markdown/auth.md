[//]: # (Authentication and Authorization)
[//]: # (https://metamug.com/img/doc_auth_roles_screen.png)
[//]: # (2020-07-08T06:55:53+00:00)
[//]: # (Authenticate REST API Resources with OAuth 2.)

<div id="auth-video">
<div class="plyr" data-plyr-provider="youtube" data-plyr-embed-id="lFyx73B59XE"></div>
</div>
<br><br>

Before proceeding to the following section, the reader must be familiar with the following:

- [*HTTP Basic Authorization*](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication)
- [JSON Web Token (JWT)](https://tools.ietf.org/html/rfc7519)
<br>

### Resource XML Configuration

Metamug provides role-based authentication and authorization of resources. If the requested resource has *auth* attribute with a role value for e.g. auth="baz", then the request must send authorization header. If the user fails to send or sends incorrect credentials the server will respond with 401 message.

If the authenticated user does not have access to the resource i.e. not provided the role of the resource, the server will respond back with 403 Forbidden error.

After authentication and authorization, the [resource file](https://metamug.com/docs/resource-file.php) is given access. The accessed resource file is provided with `$uid` as the authorized user id. `$uid` represents the logged-in user. `$uid` can be used to access information related to the logged-in user. The following entry needs to be made in the XML file to give access to the logged in user in the backend schema as follows.


```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0" auth="baz">
```

Here is an example.

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0" auth="movie_reviewer">
    <Desc>Get Information about movie reviews.</Desc>

    <!-- Collection GET Request /movie -->
    <Request method="GET">
        <Desc>Get info of all movies with ratings greater than 3 rated by the user</Desc>
        <Sql id="wellRatedMovies">
            select review_comment, rating, review_date
            from movie_rating
            where movie_user = $uid and rating > 3
        </Sql>        
    </Request>
</Resource>
```

### User Role Management

The console provides a user-role management screen which enables creation of roles and assignment of roles to existing users as well as setting them as ```auth``` attribute in a resource.

![Roles Screen](https://metamug.com/img/doc_auth_roles_screen.png)


After auth is configured successfully. You can check the resource listing in the console it will show an auth badge next to the name of the resource.

Assigning roles to users using the roles screen internally creates entries in the `usr_role` table mentioned above.

> Under the Hood, tables are used to store the roles and users in the connected database

 `usr` table is used to store information about the user

1. user_id
2. user_name
3. pass_word
4. created_on

![Metamug User Table Structure](https://lh3.googleusercontent.com/-4o7T4DInZbo/WZQzmJrv9TI/AAAAAAAACoc/EAfIIAAVQEc-aFL0Jlhqb86CCsA3zgcGwCL0BGAYYCw/h546/2017-08-16.png)

<div>
<svg xmlns="http://www.w3.org/2000/svg" height="300px" width="480px"><circle cx="102" cy="150" r="100" stroke="black" stroke-width="1" fill="#e8b2c9" fill-opacity="0.5"/><circle cy="150" r="100" stroke="black" stroke-width="1" fill="#a8f2c9" fill-opacity="0.4" cx="200"/><text y="150" x="220" fill="#333">baz</text><text y="150" fill="#333" x="20">foo</text></svg>
</div>

`usr_role` table is used to authorize the authenticated user to a resource using a role. The role_name is used in the resource file to map roles to an auth attribute.
1. id
2. role_name
3. user_id
4. created_on

![Metamug User Role Table Structure](https://lh3.googleusercontent.com/-j3kzTt3YbgI/WZQ0_NbHy8I/AAAAAAAACoo/soVvKQbQH980TX9l1vaAjgfxR-bu9pkcgCL0BGAYYCw/h540/2017-08-16.png)

The configuration table relies on the querying the `usr` and `user_role` table as per this example to check for authentication and authorization requests.

For a resource file with auth="baz", following entry needs to be made in the user and user_role table in the backend schema as follows.

Considering you have a user (`foo`,`bar`) entitled to a role `baz`, you need to make the following entry.

```sql
insert into usr(user_name, pass_word) values("foo", "bar");
insert into usr_role (role_name, user_id) values("baz", 1);
```

### Backend Properties

Queries required for `BASIC` and `BEARER` authentication are part of backend properties. They use the default `usr` and `user_role` tables.  You can access these queries and modify them to use custom tables.


![Metamug Config table](https://lh3.googleusercontent.com/-CDE3YHy6Qhs/XLlNdjexQRI/AAAAAAAAGyM/ImbuPWFXDBcVaMjj2uXURz-jIKzKceR7gCK8BGAs/s0/2019-04-18.png)


> Note: These two queries are already configured as per `usr` and `usr_role` tables. If you use these two tables for storing users and their roles, auth properties need not be changed.

The query can be edited change the table used to returning the authenticated user identifier and the role. These two attributes are needed by the resource for authentication.

![](https://lh3.googleusercontent.com/-jm2hZGWfFz0/Xl9OsXPwDfI/AAAAAAAAKkY/N1Yi9GErpBw3Lla8BK2kDbmqy__3glr_QCK8BGAsYHg/s0/2020-03-03.png)



In the auth_scheme you can write **Basic** or **Bearer** and also the auth_query will differ depending upon this scheme.

```sql
-- Basic
select r.user_id,r.role_name from usr_role r inner join usr u on r.user_id=u.user_id WHERE u.user_name=$user AND u.pass_word=$pass
```
The above insert record is for `Basic`. The variables `$user` and `$pass` will automatically be replaced with the values sent in the Authorization header.

```sql
-- Bearer
select r.user_id as sub,r.role_name as aud from usr_role r inner join usr u on r.user_id=u.user_id WHERE u.user_name=$user AND u.pass_word=$pass
```

The above insert record is for `Bearer`. The following three values in the payload  `iat`, `iss` and `jti` are system generated and you cannot set them using config query.

------------------------------------------------------------------------------

> These requests are to be sent over SSL to ensure TLS end-to-end encryption. We don't encourage using APIs without HTTPS under suggested auth scheme.

#### Authorization Header

> Metamug generated apis do not accept auth tokens via url parameters or request body.

Authorization header is used to send authentication information. We proceed with sending requests with HTTP Basic or Token Based under OAuth 2.0. If you try to access a resource without `Authorization` header, **when auth is enabled**, you will get the following error.

```javascript
{
	"message": "Forbidden access to resource",
	"status":403
}
```

### HTTP Basic Auth


Username and password must be sent in form of Base64 encoded under the authorization header as follows.

```cmd
Authorization: Basic BASE64(username:password)
```

The basic keyword in the header value tells the server the token followed by it uses Basic Auth scheme.
Metamug matches the username and password after decoding the values and provides access to the requested resource accordingly.

The implementation conforms to <a target="_blank" href="https://www.ietf.org/rfc/rfc2617.txt">RFC 2617 for Basic Auth</a>.

Javascript has `btoa` function that does Base64 encoding.

```javascript
req.setRequestHeader("Authorization", "Basic " + btoa('foo' + ":" + 'bar'));
//Authorization: Basic Zm9vOmJhcg==
```

As per RFC 2617, the equal signs should be omitted.

```cmd
curl -H "Authorization: Basic Zm9vOmJhcg==" http://localhost:7000/movie/v1.0/resource
```

### Token Based Authentication (JWT)

In this scheme, the app requests a token from the backend as follows using the user credentials (`foo`,`bar`).

```cmd
curl -d "auth=bearer&userid=foo&password=bar" -X POST http://localhost:7000/{backend_name}/
```

The POST request made to the backend will generate the token in the response.

```javascript
{
    "token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiYmF6IiwiaXNzIjoibWFzb24ubWV0YW11Zy5uZXQiLCJleHAiOjE1NjE0NDk4NjYsImlhdCI6MTU1MzY1NDA2NjA5NCwianRpIjoiODFjZDVhMzYtZmVmYi00NDA3LTk5NmUtYjc0OTM5Y2Q5ZmFmIn0=.8SrZr998LYJ0v/MOh75tHtyISp+UipfRbammjFTlZ6I=",
    "type":"Bearer"
}
```

The app must store this token to make subsequent requests. This token shall be invalidated
when expiry timestamp is reached.

The below is the example of Token Auth header.

```cmd
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiYmF6IiwiaXNzIjoibWFzb24ubWV0YW11Zy5uZXQiLCJleHAiOjE1NjE0NDk4NjYsImlhdCI6MTU1MzY1NDA2NjA5NCwianRpIjoiODFjZDVhMzYtZmVmYi00NDA3LTk5NmUtYjc0OTM5Y2Q5ZmFmIn0=.8SrZr998LYJ0v/MOh75tHtyISp+UipfRbammjFTlZ6I=
```

The token is a [RFC 7519](https://tools.ietf.org/html/rfc7519) JWT compliant token. You can decode and use the information in the payload. Paste the token on [JWT.io](https://jwt.io) to see its contents.

The curl request looks as follows

```cmd
curl -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXVkIjoiYmF6IiwiaXNzIjoibWFzb24ubWV0YW11Zy5uZXQiLCJleHAiOjE1NjE0NDk4NjYsImlhdCI6MTU1MzY1NDA2NjA5NCwianRpIjoiODFjZDVhMzYtZmVmYi00NDA3LTk5NmUtYjc0OTM5Y2Q5ZmFmIn0=.8SrZr998LYJ0v/MOh75tHtyISp+UipfRbammjFTlZ6I=" http://localhost:7000/movie/v1.0/resource

```

Base URL decoding will produce the following information from the token. This information can be used by client apps.

```js
{
  "sub": "1",
  "aud": "baz",
  "iss": "mason.metamug.net",
  "exp": 1561449866,
  "iat": 1553654066094,
  "jti": "81cd5a36-fefb-4407-996e-b74939cd9faf"
}
```



### RFCs

Metamug Console generated apis follow the following RFCs recommended for authentication.

- [*HTTP Basic Authentication*](https://tools.ietf.org/html/rfc7617)
- [OAuth 2.0](https://tools.ietf.org/html/rfc6750)
- [*JSON Web Token (JWT)*](https://tools.ietf.org/html/rfc7519)

<script type="text/javascript" src="assets/js/auth-video-event.js"></script>
