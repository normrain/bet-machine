## Setup
Before the application can be run in a docker container, a bootable jar file needs to be created
The application launches on `port 4000`, and the test client tries to connect there, so the Docker container would need to 
be bound to this port.

## Further Information
The application contains a REST endpoint which can be used to place bets, the documentation of which can also be found 
under `/swagger-ui/index.html` after the application has started.

In addition, there is also a WebSocket controller, however, as I haven't used WebSockets before, I wasn't sure how I would properly test
it with only a backend. I have therefore created a small client (modified example STOMP client) to test the WebSocket.

The client also tries to connect to `port 4000`.

In regard to WebSocket integration & unit testing, the same as above applies.

The optional test task can be found in the test folder, file named `OptionalTaskTest`