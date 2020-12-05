# R2
R2 is an open source REST Resource server. It comes with a ready to use REST API. Developer can add/edit resources to the server
using R2 Console

![Metamug Resource Screen](https://metamug.com/img/res-screen.png)

## R2 Console
R2 console comes with following features.

* REST Resource Management 
* Hot deployment for REST Resources
* Resource editor with autocomplete and query testing (using open source project)
* Open API documentation generated.

## API Integration with XRequest

API Integration with third party services like AWS, Facebook, Twitter, Firebase, PayPal, Mailchimp and more.
Communication with multiple services in a single request using API Gateways.

![Metamug API Integration](https://metamug.com/img/api-integration1.svg)


## openapi-rest-model

![](https://travis-ci.org/metamug/openapi-rest-model.svg?branch=open-api) [![codecov](https://codecov.io/gh/metamug/openapi-rest-model/branch/open-api/graph/badge.svg)](https://codecov.io/gh/metamug/openapi-rest-model)

![Metamug Open API Integration](https://metamug.com/img/openapi-specification.svg)


Convert [OpenAPI](https://www.openapis.org/) Spec file into [Resource Resource XML](https://metamug.com/docs/resource-file) files.
This project aims to generate compatible resource xmls for a given spec file json/yml.

The sample petstore yml file will convert to a number of resource xmls.
https://editor.swagger.io/

### REST Resource Files

REST Resource files are designed to address a REST resources individually. Each Resource file
maps to its own URI. This makes resources easily identifiable and managable.

https://metamug.com/docs/resource-file

### Open API 

https://github.com/OAI/OpenAPI-Specification

## Dependencies

[Mason](https://github.com/metamug/mason) v3.4 available on Maven central
