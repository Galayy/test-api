# Testing Api Application

A simple employee API which is covered by integration tests and input validation is covered by unit tests.

## Getting Started

### Prerequisites

Project requires Java 11, Docker(optionally).

### Installing

Instructions for Java: [AdoptOpenJDK installation](https://adoptopenjdk.net/installation.html?variant=openjdk11)

Instructions for Docker: [Docker installation](https://docs.docker.com/v17.12/install/)

### Running

Project requires running PostgreSql database. You can connect it via `docker-compose up` or by adding it as datasource to
Intellij Idea. To configure datasource follow next steps:
 1. In the **Database** tool window **(View | Tool Windows | Database)**, click the Data Source Properties icon.
 2. In the **Data Sources and Drivers** dialog select PostgreSQL.
 3. Specify database connection details. You can find them in *application.yaml*.
 4. To ensure that the connection to the data source is successful, click **Test Connection**.

Then the project should be started, with the following command:

`./gradlew bootRun` for MacOS or Linux and `gradlew bootRun` for Windows.

### Running the tests

`./gradlew test` for MacOS or Linux and `gradlew test` for Windows.

## Contributing

Run `./gradlew bootRun` for MacOS or Linux and `gradlew bootRun` for Windows.
Minimal test coverage must be 90%. For more information visit 
[Jacoco Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
