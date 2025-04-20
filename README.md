# 📝 Form Data Persistence with Java Servlets & Docker

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

A demonstration project showcasing form data persistence using Java Servlets, JPA/Hibernate, and Docker Compose with MySQL. This project highlights modern Java web development practices with containerization.

## 📌 Key Features

- **Modern Java Web Stack**: Jakarta EE Servlets with JPA/Hibernate ORM
- **Containerized Infrastructure**: Docker Compose for MySQL database
- **RESTful Communication**: JSON payloads with Fetch API
- **Production-ready Patterns**: Proper separation of concerns with DTOs and Entities
- **CI/CD Ready**: Maven build configuration

## 🛠️ Technology Stack

- **Backend**: Java 17+, Jakarta EE Servlets, JPA/Hibernate
- **Database**: MySQL 8.0
- **Frontend**: HTML5, CSS, JavaScript (Fetch API)
- **Infrastructure**: Docker, Docker Compose
- **Build Tool**: Maven

## 📂 Architecture Overview
```csharp
.
├── src/
│   └── main/
│       ├── java/
│       │   └── org/sergei/form
│       │       ├── entities/
│       │       │   └── SForm.java            # JPA entity class
│       │       ├── dto/
│       │       │   ├── RequestData.java      # Request DTO
│       │       │   └── ResponseData.java     # Response DTO
│       │       └── servlets/
│       │           └── MainServlet.java      # Main servlet
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml           # JPA configuration
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml                   # Servlet settings
│           ├── index.html                    # Form page
│           └── js/
│               └── script.js                 # Fetch API logic
│           └── css/
│               └── style.css                 # Styling
├── docker-compose.yml                        # Docker Compose for MySQL
├── init.sql                                  # SQL script to create table
├── pom.xml                                   # Maven build file
└── README.md                                 # This file
```


## 🚀Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker Engine 20.10+

### Installation
#### 1. Clone the repository:

```bash
git clone https://github.com/yourusername/form-data-persistence-with-java-servlets-docker.git
cd form-data-persistence-with-java-servlets-docker
```
#### 2.  Build the project:

```bash
mvn clean package
```
#### 3. Start the database with Docker Compose:

Make sure, that you have installed Docker!

```bash
docker-compose up -d
```
MySQL will be available on port ``3306``.

#### 4.   Deploy the WAR file to your Tomcat server

 - Copy the generated WAR from target/projectName.war into the webapps folder of your Tomcat.
 - Start (or restart) the Tomcat server.

#### 5. Open the app in your browser:

```bash
http://localhost:8080/yourapp/index.html
```