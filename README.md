# Remote Control with Gmail service

Welcome to our project from fit@hcmus! This project focuses on computer networking, specifically an application that allows users (clients) to utilize Gmail services to perform various requests on the server.

## Table of Contents

1. [Login](#login)
   - [Server](#server)
   - [Client](#client)

2. [Service](#service)
   - [System Control](#System-Control)
   - [Screen Shot](#Screen-Shot)
   - [Key Logger](#Key-Logger)
   - [Get File](#Get-File)
   - [List Process](#List-Process)
   - [List Application](#List-Application)
   
4. [Control by gmail](#Control-by-gmail)

5. [Contributor](#contributor)

# Login 
## Server
When the server starts, a verification code will be generated (highlighted in the red box below). This code is required for the client to establish a connection and log in.
![code](code.png)
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

# Control by gmail
Use Gmail to send directly
Send to the mail server
| Request         | Subject              | Content                          |
|-----------------|----------------------|----------------------------------|
| Shutdown        | 1                    | Code + sequence number           |
| Restart         | 2                    | Code + sequence number           |
| Log out         | 3                    | Code + sequence number           |
| Sleep           | 4                    | Code + sequence number           |
| Screenshot      | 5                    | Code + sequence number           |
| Start keylog    | 6                    | Code + sequence number           |
| End keylog      | 7                    | Code + sequence number           |
| Get file        | 8 + path (if any)    | Code + sequence number           |
| List process    | 9                    | Code + sequence number           |
| List app        | 10                   | Code + sequence number           |
| Kill process    | 11 + pid process     | Code + sequence number           |
| Start process   | 12 + name process    | Code + sequence number           |
- Code: taken from the server, consisting of 8 uppercase letters.
- Sequence number: starts from 1 and increases after each request (do not send two emails with consecutive duplicate sequence numbers).
- Path: The path to access to retrieve a file from the mail server.
- Example: You want to request "shutdown", you should send mail with subject = "WOIEUOIE 1", content = "1" to mail server (projectmangmaytinh2004@gmail.com). 
And you want to send the next request "screenshot", you should send mail with subject = "WOIEUOIE 2", content = "5" to mail server (projectmangmaytinh2004@gmail.com). 

# Contributor
- Dang Tan Phat: 22120261
- Phan Nguyen Minh Khoi: 22120166
- Dinh Tan Nhat: 22120251
