[//]: # (Server Side Scripting with Node js)
[//]: # (/img/server-side-scripting-with-nodejs.svg)
[//]: # (2020-02-02T06:55:53+00:00)
[//]: # (Execute Node Js scripts with Metamug API Console using XRequest)


Start Node Server
-----------------

Download node, create a new nodejs project and install [express](https://www.npmjs.com/package/express), [bodyparser](https://www.npmjs.com/package/body-parser).

#### Express JS server

```js
const express = require('express')
const bodyParser = require('body-parser')

const app = express()

app.use(bodyParser.json())

app.get('/addfive/:int', (req,res)=> {
    const integer = req.params.int
    res.send(\`Sum:::${parseInt(integer) + 5}\`)
})

app.listen(5000, () => console.log(\`App Running on port 5000 ::::\`))

```

### Create A Express Application

Import Express and create a new express application:

```js
//Import Express
const express = require('express');

//Create Express App
const app = express();
```

### Body Parser

Body parser is a package that parses incoming request stream and makes in available under the _req.body property._

```js
//Import body-parser
const bodyParser = require('body-parser');

//Use body-parser middleware
app.use(bodyParser.json());  // parse application/json
```

It is important to note that body parser middleware should be used before the actual route handlers.

### Route Handler


```js
app.get('/addfive/:int', (req,res)=> {
    const integer = req.params.int
    res.send(\`Sum:::${parseInt(integer) + 5}\`)
});
```

I have defined a simple route handler which will handle GET request at _**“{base\_url}/addfive/{integer\_user\_input}”**_ .

Every time a request is made, integer 5 is added to the incoming user input (integer that will come after /addfive/ , example: /addfive/5 will return 10 as sum) and output is set as response in **JSON** format.

### Running The Application

```js
app.listen(5000, () => console.log(\`App Running on port 5000 ::::\`))
```

This is how we run an express application on a specific port.

### XRequest

XRequest will allow user to make requests to the express application from the console.

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
  <Request method="GET">
     <XRequest id="1" url="http://localhost:5000/addfive/14"
                       method="GET" output="true">              
     </XRequest>
  </Request>
</Resource>
```

The above XML resource represents a sample XRequest from console to the node application running on port 5000. We specify **output=”true”** to see the output in **JSON** format.

### **Output**

```json
{
  "1": {
    "sum": 19
  }
}
```

Refer [XRequest Documentatation](https://metamug.com/docs/xrequest) for more information.
