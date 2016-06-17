# CST8277 Assignment #3 - RMI and Hibernate
This assignment's purpose was to get familiar with RMI and Hibernate. This assignment is basically an "RMI-based 3-tier networked multi-user version" of <a href="https://github.com/richard1990/CST8277-Assignment1">Assignment #1 (Bouncing Sprites)</a>. The server handles the business rules (Sprite creation, movement, etc.), accesses the database, and tells the presentation layer (the client) to display the Sprites. Multiple clients can connect to the server at the same time and see the same number of Sprites and their movement, and each client has a different coloured Sprite associated with it (a randomly generated Color object basically). The Sprites are persistent objects in the database. The program functions the same as Assignment #1 

SpriteClient.java has all the client code, which just connects to the server and retrieves a list of Sprites. On the server side, the bulk of the code is in SpriteImpl.java which implements an interface (SpriteInterface.java) to allow the creation of Sprites, the JPanel window size, the Sprite movement, and the Sprite colour. Each Sprite created is also stored in a database using Hibernate. The program also makes use of <a href="https://dev.mysql.com/downloads/connector/j/5.1.html">Oracle's MySQL Connector/J<a/>, and the server makes use of a database called "assignment03".

I also included a basic <a href="https://github.com/richard1990/CST8277-Assignment3/blob/master/Class_Diagram.png">Class Diagram</a> and <a href="https://github.com/richard1990/CST8277-Assignment3/blob/master/Sequence_Diagram.png">Sequence Diagram</a>.

Here's some instructions on getting the program running:

1. Before launching program, run MySQL Server.

2. Create user 'scott' identified by 'tiger' (or edit the "hibernate.cfg.xml" file with your own user) and
   grant the user the necessary permissions. Create a database called "assignment03".

3. Run "SpriteWorld" to start the server. By default, it will use "localhost" as the hostname, however 
   you can specify a specific host by adding an argument from the command line (e.g. "java server.SpriteWorld 196.168.0.2",
   which will consider the hostname to be 192.168.0.2).

4. After having the server running, launch "SpriteClient" to launch the client. By default, it will use
   "localhost" as the hostname, however you can specify a specific host by adding an argument from the 
   command line (e.g. "java client.SpriteClient 196.168.0.2", which will consider the hostname to be 192.168.0.2).

5. After SpriteClient launches, you can then click anywhere within the window that pops up to create a Sprite at that location.

6. To end the program, simply stop the server (SpriteWorld).
