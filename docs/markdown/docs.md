[//]: # (Console Documentation)
[//]: # (https://metamug.com/img/doc-console-backend.png)
[//]: # (2019-09-20T06:55:53+00:00)
[//]: # (Metamug Console allows developers to create and manage their REST API.)

Metamug Console allows developers to create and manage their REST API [backends](https://metamug.com/docs/backend), resource files and to make queries to the SQL database.

<!-- <iframe width="560" height="315" src="https://www.youtube.com/embed/qLphDkJim9I" frameborder="0" allowfullscreen=""></iframe> -->

<div class="plyr" data-plyr-provider="youtube" data-plyr-embed-id="qLphDkJim9I"></div>

The console provides the developer with a set of tools and utilities to manage their REST APIs.

### Backends

The Backends screen is used for creating new App Backend and also displays the details for existing Backends in an account if any.

![Console Backends Screen](https://metamug.com/img/doc-console-backend.png "Console Backends Screen")

### REST Resources

The Resources screen enables uploading of [Resource files](/docs/resource-file) and displays the list of uploaded files.

![Console Resources Screen](https://metamug.com/img/doc-console-resources.png "Console Resources Screen")

### Adding Resources to Backend

A resource can be created in two ways

1. Uploading XML file
2. Create using text-editor

The resource creation buttons can be found on the top-right corner of the resources screen

![Resource Create Buttons](https://metamug.com/img/doc-console-resource-create-buttons.png "Resource Create Buttons")

The console provides a text-editor for creating/editing a resource file.

![Resource Editor](https://metamug.com/img/doc-console-resource-editor.png "Resource Editor")

### SQL Editor

The Metamug Console provides an [SQL Editor](/docs/sql-catalog) which allows the user to access and perform operations on the database connected to the backend.

![Metamug SQL Editor](https://metamug.com/img/sql-editor.png "Metamug SQL Editor")

The results of the query are displayed below the editor panel. After creating and setting up the database tables, the user can write the SQL queries to be executed on the database when a request is made to a particular resource, inside the resource file for the particular data resource. For more information please go through the [Resource File](https://metamug.com/docs/resource-file) section.

PL/SQL can be executed on the same editor by switching to PL/SQL Mode on the top right corner. Editor by default stores the last query executed. So data isn't lost when something goes wrong.

### Console Database

The Console related data like user-account, backend meta-data, resource meta-data is stored in an internal database. You can migrate this to your own database using this <a href="https://metamug.com/docs/migrating-console-database" target="_blank">doc</a>.

### Updating the Console

Update availability can be checked in the `About` section of the console.
![Updater](https://metamug.com/img/update-screen.png "Updater")
The console will get automatically updated to the latest version after clicking the Download Update button.


### Related Pages

[**Learn More about Backends >>**](/docs/backend)
