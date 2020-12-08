## Adding datasources

Datasource connections can be added inside the file `server/webapps/api/META-INF/context.xml`. An example for a datasource using PostgeSQL database connection is as follows:

```xml
<Resource auth="Container"
    closeMethod="close"
    connectionTimeout="13000"
    dataSource.implicitCachingEnabled="true"
    driverClassName="org.postgresql.Driver"
    factory="com.zaxxer.hikari.HikariJNDIFactory"
    idleTimeout="120000"
    jdbcUrl="jdbc:postgresql://{dbhost}:5432/{dbname}"
    maxLifetime="1800000"
    maximumPoolSize="2"
    minimumIdle="1"
    name="postgresql"
    password="{password}"
    poolName="pgtestPostgresqlPool"
    type="javax.sql.DataSource"
    username="{username}"/>
```

`jdbcUrl` represents the database connection string

`name` datasource name to be used in resource files

`username` username for the database

`password` password for the database

`minimumIdle` minimum number of connections to be kept in the connection pool

`maximumPoolSize` maximum connections allowed in the connection pool