# User Management & Messaging Tool

A client–server application where users can register, authenticate, exchange messages, and (as admins) manage other users. Built for the 1st Assignment with Java sockets and a PostgreSQL database.

## Features

- **Authentication** — every request to the server is authenticated via a per-connection session.
- **Roles** — regular users vs. admins. A default admin is seeded on first startup.
- **Messaging** — send messages, view Inbox and Outbox (paginated, 10 per page), read individual messages.
- **Admin CRUD** — admins can create, update, delete, search, and list users, and grant/revoke admin status.
- **Multi-threaded server** — each client is handled on its own thread.

## Architecture

```
Client (CLI)  ──socket──▶  Server ──▶ ClientHandler (per thread)
                                          │
                                          ▼
                                     Dispatcher
                                          │
                          ┌───────────────┼───────────────┐
                          ▼               ▼               ▼
                    UserService     AdminService    MessageService
                          └───────────────┼───────────────┘
                                          ▼
                                   Database (JDBC)
                                          ▼
                                     PostgreSQL
```

- **frontend** — CLI menus that only collect input and build protocol strings.
- **backend.network** — `Server`, `ClientHandler`, `Dispatcher`, `Client`.
- **backend.src.Service** — business logic (`UserService`, `AdminService`, `MessageService`).
- **backend.src.Data** — entity classes (`User`, `Message`, `Session`).
- **backend.src.Db** — `Database` (connection + schema setup).

## Protocol

A positional text protocol over TCP.

- Field separator: `|||`
- Record separator (multi-row responses): `###`
- Responses start with `OK` on success or `ERROR` on failure.

Actions: `LOGIN`, `REGISTER`, `LOGOUT`, `ADDUSER`, `UPDATEUSER`, `DELETEUSER`, `SEARCHUSER`, `VIEWUSERS`, `SETADMIN`, `SENDMSG`, `READMSG`, `INBOX`, `OUTBOX`.

Examples:

```
LOGIN|||admin|||Admin12345.
SENDMSG|||ali|||Hello|||How are you?
INBOX|||1
```

## Requirements

- Java (JDK 17+ recommended — uses text blocks)
- PostgreSQL, running locally
- PostgreSQL JDBC driver (`postgresql-*.jar`)

## Setup

1. Create the database:

   ```sql
   CREATE DATABASE ummt;
   ```

2. Update the connection settings in `backend/src/Db/Database.java` if needed:

   ```java
   URL  = jdbc:postgresql://localhost:5432/ummt
   USER = postgres
   PASS = <your password>
   ```

   Tables are created automatically on server startup.

## Running

Start the **server** first, then the **client** (two separate processes).

```bash
# 1. Server
java -cp "bin:lib/postgresql.jar" backend.network.Server

# 2. Client (new terminal)
java -cp "bin:lib/postgresql.jar" backend.network.Client
```

> On Windows, use `;` instead of `:` in the classpath.

## Default Admin

On first startup a default admin is inserted:

| Username | Password      |
|----------|---------------|
| `admin`  | `Admin12345.` |

Use this account to access the admin menu and promote other users.

## Notes

- Passwords are currently stored in plain text (hashing planned as a future improvement).
- The message text must not contain the `|||` or `###` delimiters.
