# BookMyMovie


API's 
1. http://localhost:8082/api/v1.0/getAllMovies
here, the method gives teh list of movies available as well as sold out

2. http://localhost:8082/call/consumer/authenticate/
this is the initial login api, where user can login by giving following details

{
"userName": "vishalsb1",
"userPassword" : "vish@123"
}

3. http://localhost:8082/api/v1.0/admin/addMovie
   here, admin can add movie using this api

{
    "movieName": "Asur 2",
    "theatreName": "Bharat Cinemas"
}

4. http://localhost:8082/api/v1.0/admin/delete/Pathan/Inox
here, admin can delete the movie which is not booked yet!, delete by providing moviename and theatrename

5. http://localhost:8082/api/v1.0/ticket/user/getAllTickets/vishalsb1
here user can view his booked tickets


6. http://localhost:8082/api/v1.0/admin/updateMovie/3
here admin can update his current added movie by giving movieId and updated movie name

{
    "movieName": "RRR"
}

7. http://localhost:8082/api/v1.0/ticket/admin/getAllTickets
here admin can see all the tickets 

8. http://localhost:8082/api/v1.0/ticket/add/6/35/vishalsb1
here user can book his ticket by providng movie and seats

9. http://localhost:9091/registerNewUser

{
"userName": "test12",
"fullName": "test",
"email": "test@gmail.com",
"userPassword": "tes@123",
"secretQuestion": "What is your favourite athelete name",
"secretAnswer": "Virat Kohli",
"role": [
{
"roleName":"user",
"roleDesc":"For users"
}
]
}

10. http://localhost:8082/call/consumer/logout
here current user can logout

11. http://localhost:9091/user/forgetpassword
here if user forgot his password, they can update it by providing username and secret question, answer.

{
    "secretQuestion": "What is your nickname",
    "secretAnswer": "Vishu",
    "newPassword": "vishal@123",
    "userName": "vishalsb1"
}

12. http://localhost:9091/username/forget/{email}/{password}
here if user forgets his username, they can get it by providing email and password
