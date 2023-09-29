# Authentication LDAP Service

## Project Diagram
![project_diagram.drawio.png](docs%2Fproject_diagram.drawio.png)

## Application
Endpoints:

![1.png](docs%2F1.png)

## Requirements

- [`Java 8+`]( https://www.oracle.com/java/technologies/downloads/)
- [`Docker`](https://www.docker.com/)

## Start Environment
This command start phpldapadmin service and OpenLDAP.
```
docker compose up -d
```

### Group and roles in ldif

2 types of records:
- users
- services

User's groups:
- user
    - read (role)
- editor
    - write (role)
- admin
    - read
    - write

Service's groups:
- viewer
    - read
- writer
    - write
- admin
    - read
    - write


Attribute 'description' has mean:
- cto
- manager
- developer
- user

## Import OpenLDAP Users

The `LDIF` file we will use, `src/main/resources/ldap-dromakin-com.ldif`, contains a pre-defined structure for `example.com`.

```
Mark Cuban > username: mcuban, password: 123
Other > username: username_here, password: 123
```

There are two ways to import those users: by running a script; or by using `phpldapadmin`

### Import users running a script

- In a terminal, make use you are in `springboot-ldap-testcontainers` root folder

- Run the following script
```
./import-openldap-users.sh
```

- Check users imported using [`ldapsearch`](https://linux.die.net/man/1/ldapsearch)
```
ldapsearch -x -D "cn=admin,dc=mycompany,dc=com" \
  -w admin -H ldap://localhost:389 \
  -b "ou=users,dc=mycompany,dc=com" \
  -s sub "(uid=*)"
```

### Import users using phpldapadmin

- Access https://localhost:8080

- Login with the following credentials
  ```
  Login DN: cn=admin,dc=mycompany,dc=com
  Password: admin
  ```

- Import the file `src/main/resources/ldap-dromakin-com.ldif`

## Run application with Maven

- In a terminal, make use you are in `spring-boot-ldap` root folder

- Run the following command to start `spring-boot-ldap`
  ```
  ./mvnw clean spring-boot:run --projects spring-boot-ldap
  ```

- Environment Variables

  | Environment Variable | Description                                             |
    |----------------------|---------------------------------------------------------|
  | `LDAP_HOST`          | Specify host of the `LDAP` to use (default `localhost`) |
  | `LDAP_PORT`          | Specify port of the `LDAP` to use (default `389`)       |

- Run Docker Container
  ```
  docker run --rm --name spring-boot-ldap -p 8088:8088 \
    -e LDAP_HOST=openldap \
    --network spring-boot-ldap_default \
    dromakin/spring-boot-ldap:1.0.0
  ```

## Testing using Postman

Use this file to import requests:
[spring-boot-ldap.postman_collection.json](src%2Ftest%2Fjava%2Fspring-boot-ldap.postman_collection.json)

## Testing using Swagger
Access http://localhost:8088/swagger-ui.html

![2.png](docs%2F2.png)
