## Calendar Appointment Booking System

A Basic Calendar Slot Booking service which allows people to define their available slots on a day and other people to book them.

### Build with
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [JDk](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [MySQL](https://www.mysql.com/)
* [Git](https://git-scm.com/)

### External Tools Used
* [Postman](https://www.getpostman.com/)
* [Heroku](https://www.heroku.com/)

### Running the application

**To Run as a Spring Boot application**

Using Maven plugin: 

1. mvn clean spring-boot:run

Using Jar file: 

1. mvn clean package

2. java -jar ./target/CalendarSlotBookingservice-0.0.1-SNAPSHOT.jar

**To Run using Docker file**

1. mvn clean package

2. Build the Docker image : docker build -t appointment-booking-docker .

3. Run the Spring Boot application : docker run -p 8080:8080 appointment-booking-docker.
_ _ _

## REST Endpoints

Base Url = [https://calendar-appointment-booking.herokuapp.com/](https://calendar-appointment-booking.herokuapp.com/)

API Version = api/v1

Complete Url = [https://calendar-appointment-booking.herokuapp.com/api/v1](https://calendar-appointment-booking.herokuapp.com/api/v1)


### Registration

	Url - /user/register
	method - POST
	Body - JSON
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/user/register

| Fields        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| username      | string | valid Email |
| name      | string      |   Any Value |
| password | string      |    length between 8 to 32  |
|confirmPassword | string | length between 8 to 32 |

Sample Request Body
```javascript
{
	"username" : "sample@gmail.com",
	"name" : "Sample",
	"password" : "abcd1234",
	"confirmPassword" :"abcd1234"
}
```
Sample Response
```javascript
{
	"message": "User created please login with the credentials",
	"status": "CREATED",
		"response": {
		"id": 6,
		"username": "sample@gmail.com",
		"name": "Sample"
		}
}
```
_ _ _ 
### Login
For login please use Basic Auth and pass Registered email as username and Password along with any other Api request after that a Session Cookie will
issued for future requests
- - - 
### Logout
	Url - user/logout
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/user/logout

Sample response
```Javascript
{
	"message": "User logged out successfully",
	"status": "OK"
}
```
_ _ _ 
### Current User

Used to get current logged in user

	Url - user/currentUser
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/user/currentUser

Sample Response
```javascript
{
	"message": "Successful",
	"status": "OK",
		"response": {
		"id": 6,
		"username": "sample@gmail.com",
		"name": "Sample"
	}
}
```
_ _ _

### Find User

Used to find a user by using any user's Id

	Url - user/{userId}
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/user/6

Sample Response
```javascript
{
	"message": "Successful",
	"status": "OK",
		"response": {
		"id": 6,
		"username": "sample@gmail.com",
		"name": "Sample"
	}
}
```
_ _ _
### Create Appointment

	Url - /appointment/create
	method - POST
	Body - JSON
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/create

| Fields        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| appointmentDate      | Date | YYYY-MM-DD |
| appointmentStartTime      | Time      |   HH:MM:SS |
| appointmentEndTime | Time      |    HH:MM:SS  |

Sample Request Body
```javascript
{
	"appointmentDate": "2020-04-07",
	"appointmentStartTime": "17:00:00", 
	"appointmentEndTime": "18:00:00"
}
```
Sample Response Body
```javascript
{
	"message": "Appointment created",
	"status": "OK",
	"response": {
		"id": 7,
		"createdAt": "2020-04-07T08:25:02.647+0000",
		"appointmentDate": "2020-04-07",
		"appointmentStartTime": "17:00:00",
		"appointmentEndTime": "18:00:00",
		"appointmentStatus": "available",
		"creator": {
			"id": 6,
			"username": "sample@gmail.com",
			"name": "Sample"
		}
	}
}
```
_ _ _ 
### Create multiple Appointment on a Day
	Url - /appointment/createBatch
	method - POST
	Body - JSON
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/createBatch
	
| Fields        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| appointmentDate      | Date | YYYY-MM-DD |
| appointmentSlots | JsonArray | Array of start and end time|
| appointmentStartTime      | Time      |   HH:MM:SS |
| appointmentEndTime | Time      |    HH:MM:SS  |

Sample Request Body
```javascript
{
	"appointmentDate": "2020-04-08", 
	"appointmentSlots": [
		{
		"appointmentStartTime": "01:00:00", 
		"appointmentEndTime": "02:00:00"
		},
		{
		"appointmentStartTime": "03:00:00", 
		"appointmentEndTime": "05:00:00"
		},
		{
		"appointmentStartTime": "07:00:00", 
		"appointmentEndTime": "08:00:00"
		},
		{
		"appointmentStartTime": "10:00:00", 
		"appointmentEndTime": "11:00:00"
		}
	]
}
```
Sample Response Body
```javascript
	[
        {
            "message": "Appointment created",
            "status": "OK",
            "response": {
                "id": 12,
                "createdAt": "2020-04-07T11:16:15.560+0000",
                "appointmentDate": "2020-04-08",
                "appointmentStartTime": "01:00:00",
                "appointmentEndTime": "02:00:00",
                "appointmentStatus": "available",
                "creator": {
                    "id": 6,
                    "username": "sample@gmail.com",
                    "name": "Sample"
                }
            }
        },
        {
            "message": "Appointment created",
            "status": "OK",
            "response": {
                "id": 13,
                "createdAt": "2020-04-07T11:16:15.765+0000",
                "appointmentDate": "2020-04-08",
                "appointmentStartTime": "03:00:00",
                "appointmentEndTime": "05:00:00",
                "appointmentStatus": "available",
                "creator": {
                    "id": 6,
                    "username": "sample@gmail.com",
                    "name": "Sample"
                }
            }
        },
        {
            "message": "Appointment created",
            "status": "OK",
            "response": {
                "id": 14,
                "createdAt": "2020-04-07T11:16:15.801+0000",
                "appointmentDate": "2020-04-08",
                "appointmentStartTime": "07:00:00",
                "appointmentEndTime": "08:00:00",
                "appointmentStatus": "available",
                "creator": {
                    "id": 6,
                    "username": "sample@gmail.com",
                    "name": "Sample"
                }
            }
        },
        {
            "message": "Appointment created",
            "status": "OK",
            "response": {
                "id": 15,
                "createdAt": "2020-04-07T11:16:15.842+0000",
                "appointmentDate": "2020-04-08",
                "appointmentStartTime": "10:00:00",
                "appointmentEndTime": "11:00:00",
                "appointmentStatus": "available",
                "creator": {
                    "id": 6,
                    "username": "sample@gmail.com",
                    "name": "Sample"
                }
            }
        }
    ]
```
_ _ _
### All Appointment

	Url - /appointment/all
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all

Sample Response Body
```javascript
{
  "message": "Successful",
  "status": "OK",
  "response": [
    {
      "id": 7,
      "createdAt": "2020-04-07T08:25:03.000+0000",
      "appointmentDate": "2020-04-07",
      "appointmentStartTime": "17:00:00",
      "appointmentEndTime": "18:00:00",
      "appointmentStatus": "available",
      "creator": {
        "id": 6,
        "username": "sample@gmail.com",
        "name": "Sample"
      }
    },
    {
      "id": 8,
      "createdAt": "2020-04-07T08:45:28.000+0000",
      "appointmentDate": "2020-04-08",
      "appointmentStartTime": "01:00:00",
      "appointmentEndTime": "02:00:00",
      "appointmentStatus": "available",
      "creator": {
        "id": 6,
        "username": "sample@gmail.com",
        "name": "Sample"
      }
    },
    {
      "id": 9,
      "createdAt": "2020-04-07T08:45:28.000+0000",
      "appointmentDate": "2020-04-08",
      "appointmentStartTime": "03:00:00",
      "appointmentEndTime": "05:00:00",
      "appointmentStatus": "available",
      "creator": {
        "id": 6,
        "username": "sample@gmail.com",
        "name": "Sample"
      }
    },
    {
      "id": 10,
      "createdAt": "2020-04-07T08:45:28.000+0000",
      "appointmentDate": "2020-04-08",
      "appointmentStartTime": "07:00:00",
      "appointmentEndTime": "08:00:00",
      "appointmentStatus": "available",
      "creator": {
        "id": 6,
        "username": "sample@gmail.com",
        "name": "Sample"
      }
    },
    {
      "id": 11,
      "createdAt": "2020-04-07T08:45:28.000+0000",
      "appointmentDate": "2020-04-08",
      "appointmentStartTime": "10:00:00",
      "appointmentEndTime": "11:00:00",
      "appointmentStatus": "available",
      "creator": {
        "id": 6,
        "username": "sample@gmail.com",
        "name": "Sample"
      }
    }
  ]
}
```
_ _ _ 
### Search Appointment By Id
	Url - /appointment/{appointmentId}
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/11

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": {
        "id": 11,
        "createdAt": "2020-04-07T08:45:28.000+0000",
        "appointmentDate": "2020-04-08",
        "appointmentStartTime": "10:00:00",
        "appointmentEndTime": "11:00:00",
        "appointmentStatus": "available",
        "creator": {
            "id": 6,
            "username": "sample@gmail.com",
            "name": "Sample"
        }
    }
}
```
_ _ _
### Update Appointment using Appointment Id

	Url - appointment/update/{appointmentId}
	method - PUT
	BODY - JSON
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/update/11

| Fields        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| appointmentDate      | Date | YYYY-MM-DD |
| appointmentStartTime      | Time      |   HH:MM:SS |
| appointmentEndTime | Time      |    HH:MM:SS  |

Sample  Request Body
```javascript
{
	"appointmentDate": "2020-04-24", 
	"appointmentStartTime": "13:00:00", 
	"appointmentEndTime": "14:00:00"
}
```
Sample Response Body
```javascript
{
    "message": "Appointment updated",
    "status": "OK",
    "response": {
        "id": 11,
        "createdAt": "2020-04-07T08:45:28.000+0000",
        "appointmentDate": "2020-04-24",
        "appointmentStartTime": "13:00:00",
        "appointmentEndTime": "14:00:00",
        "appointmentStatus": "available",
        "creator": {
            "id": 6,
            "username": "sample@gmail.com",
            "name": "Sample"
        }
    }
}
```
_ _ _ 
### Book Appointment

	Url - appointment/book/{appointmentId}
	method - PATCH
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/book/11

Sample Response Body
```javascript
{
    "message": "Appointment booked",
    "status": "OK",
    "response": {
        "id": 11,
        "createdAt": "2020-04-07T08:45:28.000+0000",
        "appointmentDate": "2020-04-24",
        "appointmentStartTime": "13:00:00",
        "appointmentEndTime": "14:00:00",
        "appointmentStatus": "booked",
        "creator": {
            "id": 6,
            "username": "sample@gmail.com",
            "name": "Sample"
        },
        "bookedBy": "puneet",
        "bookerEmail": "puneet@samplemail.com"
    }
}
```
_ _ _
### Cancel Booked Appointment

	Url - appointment/cancel/{appointmentId}
	method - PATCH
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/cancel/11

Sample Response Body
```javascript
{
    "message": "Appointment canceled",
    "status": "OK",
    "response": {
        "id": 11,
        "createdAt": "2020-04-07T08:45:28.000+0000",
        "appointmentDate": "2020-04-24",
        "appointmentStartTime": "13:00:00",
        "appointmentEndTime": "14:00:00",
        "appointmentStatus": "available",
        "creator": {
            "id": 6,
            "username": "sample@gmail.com",
            "name": "Sample"
        }
    }
}
```
_ _ _
### Delete Appointment

	Url - appointment/{appointmentId}
	method - DELETE
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/11

Sample Response Body
```javascript
{
    "message": "Appointment successfully Deleted",
    "status": "OK"
}
```
_ _ _

### All Appointment between two dates

	Url - appointment/all/betweenDate
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/betweenDate?startDate=2020-04-05&endDate=2020-04-09&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| startDate      | Date | YYYY-MM-DD |
| endDate      | Date      |   YYYY-MM-DD |
| status | String      |    all / booked / available  |

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 7,
            "createdAt": "2020-04-07T08:25:03.000+0000",
            "appointmentDate": "2020-04-07",
            "appointmentStartTime": "17:00:00",
            "appointmentEndTime": "18:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 8,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "01:00:00",
            "appointmentEndTime": "02:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 9,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "03:00:00",
            "appointmentEndTime": "05:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 10,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "07:00:00",
            "appointmentEndTime": "08:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
_ _ _

### All Appointment before  a date

	Url - appointment/all/beforeDate
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/beforeDate?date=2020-04-08&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| date      | Date | YYYY-MM-DD |
| status | String      |    all / booked / available  |

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 7,
            "createdAt": "2020-04-07T08:25:03.000+0000",
            "appointmentDate": "2020-04-07",
            "appointmentStartTime": "17:00:00",
            "appointmentEndTime": "18:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
_ _ _ 
### All Appointment after  a date

	Url - appointment/all/afterDate
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/afterDate?date=2020-04-07&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| date      | Date | YYYY-MM-DD |
| status | String      |    all / booked / available  |

Sample Response Body
```Javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 8,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "01:00:00",
            "appointmentEndTime": "02:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 9,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "03:00:00",
            "appointmentEndTime": "05:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 10,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "07:00:00",
            "appointmentEndTime": "08:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
### All Appointment by a user between two dates

	Url - appointment/all/betweenDate/{userId}
	method - GET
	Sample Url -https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/betweenDate/6?startDate=2020-04-05&endDate=2020-04-07&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| startDate      | Date | YYYY-MM-DD |
| endDate      | Date      |   YYYY-MM-DD |
| status | String      |    all / booked / available  |

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 7,
            "createdAt": "2020-04-07T08:25:03.000+0000",
            "appointmentDate": "2020-04-07",
            "appointmentStartTime": "17:00:00",
            "appointmentEndTime": "18:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
_ _ _

### All Appointment by a user before a date

	Url - appointment/all/beforeDate/{userId}
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/beforeDate/6?date=2020-04-08&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| date      | Date | YYYY-MM-DD |
| status | String      |    all / booked / available  | 

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 7,
            "createdAt": "2020-04-07T08:25:03.000+0000",
            "appointmentDate": "2020-04-07",
            "appointmentStartTime": "17:00:00",
            "appointmentEndTime": "18:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
_ _ _
### All Appointment by a user after a date

	Url - appointment/all/afterDate/{userId}
	method - GET
	Sample Url - https://calendar-appointment-booking.herokuapp.com/api/v1/appointment/all/afterDate/6?date=2020-04-07&status=all

| Params        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| date      | Date | YYYY-MM-DD |
| status | String      |    all / booked / available  | 

Sample Response Body
```javascript
{
    "message": "Successful",
    "status": "OK",
    "response": [
        {
            "id": 8,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "01:00:00",
            "appointmentEndTime": "02:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 9,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "03:00:00",
            "appointmentEndTime": "05:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        },
        {
            "id": 10,
            "createdAt": "2020-04-07T08:45:28.000+0000",
            "appointmentDate": "2020-04-08",
            "appointmentStartTime": "07:00:00",
            "appointmentEndTime": "08:00:00",
            "appointmentStatus": "available",
            "creator": {
                "id": 6,
                "username": "sample@gmail.com",
                "name": "Sample"
            }
        }
    ]
}
```
