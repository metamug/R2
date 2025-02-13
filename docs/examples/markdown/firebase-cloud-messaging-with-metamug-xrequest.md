# External API Call: FCM

[Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/) (FCM) is a service which is useful for sending push notifications to Android, iOS and few other platforms.

In this article, we shall build a simple push messaging API using Metamug console and use [XRequest](https://metamug.com/docs/xrequest) feature to make HTTP request to FCM servers which shall send push notifications to an Android device.

### Prerequisites

- [Metamug Console](https://metamug.com/download/)
- Android Studio
- Android Test device



### Create Backend

[Create a backend](https://metamug.com/docs/backend#backend-creation) in the Metamug Console with name `pushnotifier`. Use the [SQL Editor](https://metamug.com/docs/console#sql-editor) to create a new table called `messages` which contains columns *title* and *body*. 

You can refer the following query 

```sql
CREATE TABLE messages (id INT NOT NULL AUTO_INCREMENT, title VARCHAR(20) NOT NULL, body VARCHAR(50) NOT NULL, PRIMARY KEY(id))
```

### Create Resource

Create a [resource file](https://metamug.com/docs/resource-file) called `message.xml` which contains a GET request to fetch all records in the *messages* table and a POST request for inserting a new message record.

The `message.xml` should look as follows

```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	  <Request method="GET">
        <Sql> SELECT * FROM messages </Sql> 
  	</Request>
  
  	<Request method="POST">
        <Sql> INSERT INTO messages (title, body) VALUES ($title, $body) </Sql>
  	</Request>
</Resource>
```

After saving the above resource file, the REST API for `message` will get generated. The endpoint for the API will be <em><span>http://</span>localhost:7000/pushnotifier/v1.0/message</em> and its functions would be as follows

1. GET request made to the API shall return the list of records from the messages table
2. POST request along with form params *title* and *body* shall insert a new record into the table

### Setting up FCM client on Android

In order to receive FCM push notifications in your Android app, we need to extend FirebaseMessagingService from the Firebase SDK. The detailed instructions to implement FCM in your Android project are given [here](https://firebase.google.com/docs/cloud-messaging/android/client). 

We shall make use of [topic messaging](https://firebase.google.com/docs/cloud-messaging/android/topic-messaging) in FCM. Subscribe to a topic `messages` by adding the following line in your Android code
```java
FirebaseMessaging.getInstance().subscribeToTopic("messages");
```

### Sending Push notifications

Sending push notifications to the devices subscribed to a particular topic requires you to make an HTTP POST request to the FCM servers. The format of the request is as follows

```cmd
https://fcm.googleapis.com/fcm/send
Content-Type:application/json
Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA
```
```javascript
{
	"to": "/topics/{topicName}",
	"notification": {
    	"title": {titleValue},
        "body": {bodyValue}
    }
}
```

The *Authorization* header requires the FCM server key which can be found in `Project Settings > Cloud Messaging` section of your app in the firebase console. You can test whether you can send push notifications to your Android device using any REST client.

### Using XRequest

Let us update `message.xml` so that everytime a message is added to the table using POST request, a push notification is sent to the Android devices that have subscribed to the topic `messages`.
```xml
<Resource xmlns="http://xml.metamug.net/resource/1.0" v="1.0">
	  <Desc> Store FCM Messages </Desc>
    <Request method="GET">
        <Sql> SELECT * FROM messages </Sql> 
  	</Request>
  
  	<Request method="POST">
        <Sql> 
        	INSERT INTO messages (title, body) VALUES ($title, $body) 
        </Sql>
        <XRequest id="fcmApiRequest" url="https://fcm.googleapis.com/fcm/send"
                  method="POST" verbose="true" persist="true" >
      		<Header name="Content-Type" value="application/json"/> 
          	<Header name="Authorization" value="key=AAAAf.....3e5lijsrdP6"/>
            <Body>
                {
                  	"to": "/topics/messages",
                  	"notification": {
                    	"title": "$title",
                    	"body": "$body"
                   	}
                }
            </Body> 
      	</XRequest>
  	</Request>

</Resource>
```