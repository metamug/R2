Datatables.net is the most popular grid display library.

In this example we wil demonstrate how to integrate Metamug API call with datatable

```html

<link href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    
<table class="table table-striped" id="publisher-table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">Profie View</th>
      <th scope="col">Registered On</th>
      <th scope="col">Last Visited</th>
    </tr>
  </thead>
</table>
```

### Ajax Call

The ajax request needs to send Accept header as `application/json+dataset`.

```js
$('#publisher-table').DataTable({
    "ajax":{
    	'url': 'http://localhost:7000/bookmark/v1.0/publisher',
    	'type': 'GET',
		    'beforeSend': function (request) {
		        request.setRequestHeader("Accept", "application/json+dataset");
		    },
		    "dataSrc":function(json){
		    	return json.publisherList.dataset;
		    }
    } 
});
```

### Rest Resource

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
  <Request method="GET">
    <Desc> Example Resource </Desc>
    <Sql id="publisherList" > 
              select id, email, name, exp, registered_on, last_visited_on
        from publisher 
        </Sql>
  </Request>
</Resource>
```

If the accept header is set to application/json+dataset it will return the data in 2D array format.

![Metamug json dataset response](https://lh3.googleusercontent.com/-1i7sB22GDIs/XcJDCGqzD-I/AAAAAAAAI7Y/T6E5jd2tKbsA82D1iHYUL5AFD2DJeWvLwCK8BGAsYHg/s0/2019-11-05.png)

### Reference

https://datatables.net/examples/data_sources/ajax
