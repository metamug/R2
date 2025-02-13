[//]: # (SQL Catalog)
[//]: # (http://metamug.com/img/doc-sql-catalog1)
[//]: # (2019-09-16T06:55:53+00:00)
[//]: # (Keep your queries aside. So you can use them another day.)

The SQL Catalog displays the list of SQL queries in present in [resource files](http://metamug.com/docs/resource-file) as well as the queries saved using the [SQL Editor](https://metamug.com/docs/console#sql-editor).

![SQL Catalog](http://metamug.com/img/doc-sql-catalog1)

###Saving SQL Queries

For saving a query using the SQL Editor, select the query and click on the save icon as shown below.

![SQL Save Button](http://metamug.com/img/doc-sql-catalog-query-save-icon)

### Query Testing

The given SQL query is tested by the system before saving. The user is notified of errors in the query, if any.
This ensures that only valid SQL queries are saved in the catalog.

###Referring Queries in Resource Files

SQL Queries saved in the catalog can be refered in the resource files using the *ref* attribute of the [Sql tag](https://metamug.com/docs/resource-file#sql-tag).

For example, a saved SQL with reference ID *38* can be referred as shown below

![SQL Reference](https://metamug.com/img/doc-catalog-query-ref.png)

```xml
<Sql id="ref_example" ref="38" />
```
This allows the same SQL to be reused in multiple resource files.

### Test Data for Request Parameters
If SQL queries containing [request parameters](https://metamug.com/docs/request-parameters) are to be saved, the console will prompt the user to enter test data inputs for each request variable.

![Test Data Inputs](https://metamug.com/img/doc-catalog-testdata.png)

The SQL query is executed on the connected database with the test data inputs in place of the request parameters. On successful execution, the query is saved.
