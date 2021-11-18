# identity-service

Identity service aims to create a general user registration and user login flow for users. 

Current Scope of Flow : 
1. New User Registration 
2. User Login to get Access Token 
3. Access Token Refresh whenever it expires. 

Flow of the User Registration : 
  1. Client makes a POST call to regsister. 
  2. Check in mongoDb whether the user exits or not.
  3. If new user, then create a registration event in the kafka topic.
  4. Consume the event by a consumer to persist in the mongoDb.
  
  Attached is the High level flow with components :
 
 ![UserRegistrationAndLoginFlow](https://user-images.githubusercontent.com/13188958/142438867-c3b23ef5-4f97-4a28-9bea-acc54dc08446.png)


Future Scopes : 
  1. Email Trigger to verify user registration via a cron job that monitors mongodb inserts
  2. API Exposure for Super Admins to change role of any user.
  3. Add Permissions to roles and drive accesss control to entity objects based on permissions.
