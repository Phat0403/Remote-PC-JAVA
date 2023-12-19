# Remote Control with Gmail service

Welcome to our project from fit@hcmus! This project focuses on computer networking, specifically an application that allows users (clients) to utilize Gmail services to perform various requests on the server.

## Table of Contents

1. [Login](#login)
   - [Server](#server)
   - [Client](#client)

2. [Service](#service)

3. [Contributor](#contributor)

# Login 
## Server
When the server starts, a verification code will be generated (highlighted in the red box below). This code is required for the client to establish a connection and log in.

## Client
Use your Gmail and the code provided by the server to log in. Note: the password is the [app password](https://support.google.com/accounts/answer/185833?hl=en)

# Service
After logging in, on the client screen, you can request the server to perform the following functions:

## System Control
- **Shut Down**: Request the server to shut down the system gracefully.
- **Sleep**: Instruct the server to put the system into a low-power sleep mode.
- **Restart**: Ask the server to restart the system.
- **Log Out**: Trigger a logout action on the server, ending the current user session.

## Screen Shot
Request the server to send the current screen image (**Screen Shot**). You can also zoom in to full screen (**Zoom**) and save it (**Save as**).

## Key Logger
Request the server to perform a key logger.
- **Turn on**: start the key logger.
- **Turn off**: stop the key logger.
- **Save as**: save as a txt file.

## Get File
Allow you to access the server's files (**Res Get**, **Back**) and directories and get the necessary files (**Get**, **Save as**).

## List Process
View the active processes on the server (**Start**) and force the server to stop a process (**Kill**).

## List Application
View the currently opened applications on the server. (**Start**)

# Contributor
- Dang Tan Phat: 22120261
- Phan Nguyen Minh Khoi: 22120166
- Dinh Tan Nhat: 22120251
