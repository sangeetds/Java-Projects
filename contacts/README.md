# Contact Server

This is an API for maintaining a contact list for a given user.

# Registering an User
To register a user, send a POST request with JSON object with a body containing fields `user`(String) and `pass`(String) to the address <br/>

`http://localhost:8080/api/user` <br/>

which will send back a response object comprising your user, pass and ID which you will need for further operations

To delete the user, send a DELETE request, authorizing (basic) with your user and pass to the address <br/>

`http://localhost:8080/api/user/{:id}` <br/>
where `id` is the id assigned to you while registering.

#Maintaining your Contacts list

To get all of your contacts, send a GET request authorizing (basic) with your user and pass to the address <br/>

`http://localhost:8080/api/contacts` <br/>

This API endpoint can be coupled with query parameters to search for specific contacts. Use the query params `search`, with value either `number` or `name` to search by number or name respectively, together with the query param `value` to specify the value by which the search needs to be done.

You can delete a contact, by sending a DELETE request, authorizing (basic) with your user and pass to the address <br/>

`http://localhost:8080/api/contacts/{:number}` <br/>
where `number` is the number of the contact.

You can then add new contacts, by sending a POST request, comprising of JSON object with body containing fields, `name`(String), `phoneNumber`(long), `address`(String), `birthDate`(String), `email`(String) 
to the address <br/>

`http://localhost:8080/api/contacts/`

# Running the app

Please make sure you have the Java 8 installed and configured in your system. 
Compile and run the `ContactsTest` java file in the `contacts/src/main/java/com/cognitree/sangeet` folder to run the folder and database subsequently. <br/>
After that you can proceed with making API calls.
