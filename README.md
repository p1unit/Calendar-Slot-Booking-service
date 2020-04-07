## Calendar Appointment Booking System

Base Url = [https://calendar-appointment-booking.herokuapp.com/](https://calendar-appointment-booking.herokuapp.com/)
API Version = api/v1

Complete Url = [https://calendar-appointment-booking.herokuapp.com/api/v1](https://calendar-appointment-booking.herokuapp.com/api/v1)

## REST Endpoints

### Registration

	Url - /user/register
	method - POST
	Body - JSON
	

| Fields        | Type          | Possible Values  |
| ------------- |:-------------:| -----:|
| username      | string | valid Email |
| name      | string      |   Any Value |
| password | string      |    length between 8 to 32  |

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
	Url - /logout
	method - GET
	https://calendar-appointment-booking.herokuapp.com/api/v1/user/logout

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

	Url - /currentUser
	method - GET
	https://calendar-appointment-booking.herokuapp.com/api/v1/user/currentUser

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
