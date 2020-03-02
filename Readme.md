# siteminder
Fuel 50 interview application, a platform to receive feeling indicator and provide average feeling indicator for the day
The user is identified by using the IP Address. 

### Technologies used
The application is built using the following technologies:
* spring-boot
* PostgreSQL
* docker
* maven
* flyway

### Prerequisite before building

Please install docker locally

### Build project

Run command ```mvn clean install```

The above command builds the spring boot application after running 26 tests.

### Docker image build

Once the application is built, build the docker image using the command ```docker build ./ -t feeling-tracker-app```

After building the image, run the application using command ```docker-compose up```

### Test the application

The repository contains the postman collection, import the postman collection and that gives the sample request.

### Improvements:

* Build the UI.
* Add more information in the cookie to validate.
* Have better methods to identify the identify user to ensure there are no duplicate recordsings.
* The date is verified using LocalDate, zone identification is necessary.