# render-farm
Client-server application - render farm.

The repository contains a separate client and server.

The connection between the client and the server takes place over a socket.

**Stack used on the server:**

-Java 11

-Spring Boot

-Spring DATA JPA

-H2 (in-memory)


The client allows you to send commands to the server and receive a response.

The server receives commands from the client, processes them and sends a response to the client.

List of available commands:

**help**   *:Show the list of commands;*

**reg \<login> \<password> \<confirm password>**	 *:Register an account;*

**login \<login> \<password>**	 *:Log in to your account;*

**logOut**	  *:Log out of your account;*

**exit**	    *:Exit the application;*

**createTask \<nameTask>**	 *:Create a task;*

**listTasks**	 *:Display the task list;*

**listTasks -n \<NameTask>**	 *:Display a list of tasks with filtering by task name;*

**listTasks -i \<IdTask>**	 *:Display a list of tasks with filtering by task ID;*

**listTasks -s \<StatusTask>**	 *:Display a list of tasks with filtering by task status;*

**statusHistory**	 *:Show the history of changing task statuses.*
