# Quarkus application with a datasource
This is a simple web application with a MySQL datasource created using Quarkus.

Use "docker run --name=testsql -e MYSQL_ROOT_PASSWORD=rashmini -d mysql:5" to run a MySQL container.
Include username, password and IP of the MySQL container as the datasource configurations in the application.properties file.

Use "docker run --name myadmin -d --link testsql:db -p 8081:80 phpmyadmin/phpmyadmin" to access MySQL database through a web interface.

Use "mvn clean package" command to generate the runner jar of the application. HotSpot build can be located in quarkus-servlet\target folder. Use Dockerfile.jvm to deploy this file.
