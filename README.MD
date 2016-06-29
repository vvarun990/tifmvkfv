##Tech Stack.
    1. Spring Boot.
    2. Rest Jersy Implementation.
    3. Lombok framework.
    4. Gradle.
    
## Running from IDE.
   Open class ```TicketServiceApplication.java``` and Run as main class.    
   
## Running from shell/command prompt.
  ### Prerequisites.    
     1. Gradle must be installed.    
     2. Project should be cloned to your machine.    
     
  Run the below gradle command.        
     ```$gradle bootRun``` It Runs the server.    
     
## API end point.
  checkAvailability: GET  http://localhost:8080/ticket/availability      
  BookTicket:        POST http://localhost:8080/ticket/book/?category=Balcony1      
                     RequestBody:   {      
                                        "name":"aaa",    
                                        "contact":"aaa",    
                                        "tickets":[    
                                                    {    
                                                     "seatNo":"5"    
                                                    }    
                                                  ]    
                                     }    
 CurrentBookingStatus: GET http://localhost:8080/ticket/status    
 
 For all above APIs Content-Typ: application/json.    
 
