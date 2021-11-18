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
  
