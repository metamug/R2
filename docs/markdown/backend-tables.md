[//]: # (Backend Tables)
[//]: # (https://lh3.googleusercontent.com/-2FaFa6zkPss/XZBhNNxVCLI/AAAAAAAAIfc/X99GTu9HeHYXhhgZstnk_Ib__hx4g3FlwCK8BGAsYHg/s0/2019-09-29.png)
[//]: # (2019-09-29T09:55:53+00:00)
[//]: # (Metadata Tables in your generated backend)

Metamug Console creates a set of tables in the backend database which are used by the console to store information about error logs, request statistics, query catalog data, user authentication etc. However, these tables are not required when the application is deployed in production. So, these tables can be dropped and we can provide you a script for the same.

The following tables are created by Metamug console in the database when backend is created.

### Log Tables

The tables described in points 1,2,3,4 are used by the Metamug console during development and have no useful purpose during production. So we can provide you a script to drop these tables from your production database.

1. **error_log** This table holds error logs occurring when API requests are made.
2. **query_log** This table contains a history of the SQL queries executed using the SQL-editor in the console.
3. **request_log** This table contains information of every request made to the API. This is used for showing the API request statistics in the console.

### SQL References

1. mtg_query_catalog, 
2. mtg_query_reference, 
3. mtg_query_tag, 
4. mtg_query_test_data

These four tables are used by [SQL Catalog Screen](https://metamug.com/docs/sql-catalog) and query test feature which enables the user to test a SQL query using optional test data before creating the API. You can find [sql reference](https://metamug.com/docs/sql-catalog#referring-queries-in-resource-files) documentation in sql catalog docs.

### User Entitlement

![Metamug Auth Properties](https://lh3.googleusercontent.com/-d2dJP4fL0ls/XMII4ibua1I/AAAAAAAAG4A/ALWcVCHPA3YqirlKpzyizl6t-LSllHDAACK8BGAs/s0/2019-04-25.png)

These tables are used for Authentication of resources as described [on the auth page](https://metamug.com/docs/auth). More importantly, the MTG_AUTH properties are only essential as it contains the SQL query for authentication. The two tables (`usr`, `usr_roles`) are only provided as default and can be replaced by any other tables containing user data. So, if we remove MTG_AUTH properties, resource authentication feature will no longer be available for use.

1. **usr** This is the default user table. 
2. **usr_role** This table defines the role and has a foreign key to the user table.

These two are default tables provided for user entitlement to resources. They can be used to store user and roles data or alternatively, any other tables can be used. 



### Production Use

In addition to the above, we recommend you to not use Metamug console with your production database. Instead, you can connect your development database with the console for API development and then [export your backend](https://metamug.com/docs/backend#exporting-backend-for-production) for use in production.

If you would like to drop these tables use the following script.

```sql
-- Console tables
DROP TABLE IF EXISTS error_log;
DROP TABLE IF EXISTS query_log;
DROP TABLE IF EXISTS request_log;

-- Query Reference Tables
DROP TABLE IF EXISTS mtg_query_reference;
DROP TABLE IF EXISTS mtg_query_tag;
DROP TABLE IF EXISTS mtg_query_test_data;
DROP TABLE IF EXISTS mtg_query_catalog;

-- Auth Tables
DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS usr_role; 

```