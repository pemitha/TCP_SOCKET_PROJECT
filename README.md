# TCP_SOCKET_PROJECT
Simple TCP socket project created using Java Socket Programming using threads

Server.java:
Created two methods to load and get the array list:-getUsers(),loadUsers()
Used a logic to shutdown the server from being endless
Used try finally block around server socket object
Used if cases to check not null before closing Socket and Streams

-----------------------------------------------------------------

Client.java
Used try finally block to reformat the code lines

-----------------------------------------------------------------

ThreadPool.java
Changed variable types to private
Modified a method to shutdown the server for Server.java:-shutdownImmediately()

-----------------------------------------------------------------

ThreadHandler.java
Changed variable types to private
Created constant variables to repeating values:- logoutConst,loginConst
Used String buffers .append method to concat String texts and values
While using the switch cases created individual method according to it's process

------------------------------------------------------------------

FundTransaction.java
Changed variable types to private
Used if cases to check whether the variable values are null prevents from getting null point exceptions

-------------------------------------------------------------------

BankDetails.java
Changed variable types to private
Used String buffer to concat

-------------------------------------------------------------------

LoginProcess.java
Changed variable types to private
Used the method created by the Server.java to get static user accounts:- Server.getUsers()

--------------------------------------------------------------------
