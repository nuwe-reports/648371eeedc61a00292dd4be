# Java Back End Developer challenge

This project is focused on renovating the appointment system of a hospital. My task is to complete the implementation and development of the backend's appointment management system. 

## Instructions for running the project with Docker

1. Install Docker on your system if you haven't already. 

2. Clone the project repository from the provided source.

3. Navigate to the project directory using the command line or terminal.

4. Build the Docker image for the MySQL database by running the following command:
                
        docker build -t mysql-image -f Dockerfile.mysql .

 5. Once the MySQL image is built, run a MySQL container using the following command:

        docker run -d --name mysql-container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql-image

6. Build the Docker image for the microservice, which includes the application and its dependences, and runs the test before compiling and executing the microservice. Run the following command:

       docker build -t microservice-image -f Dockerfile.maven .

7. Once the microservice image is built, run a container using the following command:

       docker run -d --name microservice-container -p 8080:8080 --link mysql-container microservice-image

8. Verify that the MySQL container and the microservice container are up and running:

       docker ps

9. Access the API by opening a web browser and navigating to "http://localhost:8080" or using any API testing tool such as Postman. 