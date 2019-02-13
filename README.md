# Alliance Jumpstart Program


## System Requirements

Your System should have the following installed:

* [Docker CE][docker]: refer for [Linux][linux] and [Windows][windows] installation
* [JDK 8][jdk8] 

*Additional Note: Contributors should create a Docker account especially Windows Users so that you can use Docker*

## Starting The Application

Once done cloning get inside the directory:

    git clone https://github.com/Pofay/alliance-jumpstart-updated.git
    cd alliance-jumpstart-updated 

Run the Following Commands in your Terminal/Command Line as per your O.S:

For Linux & Mac

    ./mvnw install dockerfile:build

For Windows (Standard cmd)

    mvnw.cmd install dockerfile:build

For Windows (Powershell > PS)

    .\mvnw.cmd install dockerfile:build

**This will build the appropriate Docker Image for the Application.**

To Run the image:

    docker-compose up

*Note: Incase you get errors the first time running docker-compose don't panic, run it again and it'll probably work*


## For Rebuilds and Restarts

**To allow code changes to take effect you need to rebuild and restart the docker image.**

The commands for rebuilding and restarting are as follows:


For Linux & Mac:

    ./mvnw clean package dockerfile:build
    docker-compose up

For Windows (Standard cmd):

    mvnw.cmd clean package dockerfile:build
    docker-compose up

For Windows (Powershell > PS)
 
    .\mvnw.cmd clean package dockerfile:build
    docker-compose up
   

## Common Errors and Fixes

### Error For alliance-app: Cannot start service alliance-app: driver failed programming external connectivity on endpoint alliance-jumpstart-updated_alliance-app_1: Error starting userland proxy: mkdir /port/tcp:0.0.0.0:8010:tcp:172.18.0.3:8080

** This usually happens when**:

* The Database image was not run during the first docker-compose up
* The port 8080 is already taken in your system.

For the first symptom run the following Commands:

    docker stop $(docker ps -a -q)
    # Windows (Powershell): restart-service *docker* or through the GUI in settings
    # Linux: sudo systemctl restart docker

    Then just run `docker-compose up` again

For the second symptom: In the `docker-compose.yml` file

    ports:
        8080:8080 

Replace with:

    ports:
        <YOUR-PREFERRED-PORT>:8080

### (For Windows Users) Not Enough memory to start Docker

*Play around with the [settings](https://docs.docker.com/docker-for-windows/#advanced). Turn the Memory from 2048 -> 1280 -> 1024*

[linux]: https://docs.docker.com/install/linux/docker-ce/ubuntu/
[windows]: https://hub.docker.com/editions/community/docker-ce-desktop-windows
[jdk8]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[docker]: https://www.docker.com/

### Standard Setup for those that can't run Docker

Hvae a Standard Application Properties


    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.url=jdbc:mysql://alliance-db-server:3306/jumpstart_db
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
    spring.jpa.show-sql=false

In order for this to work You must have atleast [XAMPP v 7.+][xampp] 

For Database Initialization either use **PhpMyAdmin** and create a database named `jumpstart_db` or through command line:

    mysql -u <user> -p
    MariaDB> create database jumpstart_db
    (quit via Ctrl + c)
   
Hopefully, you should be able to run the Application without any issues.
    

[xampp]: https://www.apachefriends.org/index.html
