# Alliance Jumpstart Program

## Prerequisites

The System must have the following installed:

* MySQL 5 -> MariaDB 10+
* JRE and JDK 8+

Before running review the `application.properties`:

    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.url=jdbc:mysql://localhost:3306/db_example
    spring.datasource.username=root
    spring.datasource.password=

`spring.datasource.url` needs to point to an existing database by replacing /db_example with your existing database's name.

An example with a Database with name of `jumpstart`:

    spring.datasource.url=jdbc:mysql://localhost:3306/jumpstart

For `username` and `password` set it to your machine's credentials.