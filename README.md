# Database Application for Student Records Management


## YouTube Preview
[Watch the demo on YouTube](https://youtu.be/uPlaMhpub_o)

## Overview
The DatabaseApplication Java program facilitates the management of student records in a PostgreSQL database. It provides methods to perform basic CRUD (Create, Read, Update, Delete) operations on student records.

## Prerequisites
- Java Development Kit (JDK) installed
- PostgreSQL database server running
- PostgreSQL JDBC Driver (included in the project)

## Installation
1. Clone or download the project repository.
2. Ensure that PostgreSQL is installed and running.
3. Import the project into your Java IDE.
4. Configure the PostgreSQL database connection parameters in the `main` method of `DatabaseApplication` class (URL, username, password).

## Usage
1. Compile the Java program.
2. Run the compiled program.
3. Follow the prompts to interact with the database:
   - View all students' records.
   - Add a new student.
   - Update a student's email.
   - Delete a student record.

## Dependencies
- PostgreSQL JDBC Driver

## Configuration
The `DatabaseApplication` class accepts the following parameters in its constructor:
- `url`: URL of the PostgreSQL database.
- `user`: Username for database authentication.
- `password`: Password for database authentication.

## Example
```java
public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/Assignment3";
    String user = "postgres";
    String password = "test"; 

    DatabaseApplication app = new DatabaseApplication(url, user, password);

    try {
        // Example usage:
        app.getAllStudents();
        app.addStudent("Arun", "Karki", "arun.karki@example.com", "2024-03-18");
        app.updateStudentEmail(1, "arun.karki@cmail.carleton.ca");
        app.deleteStudent(11);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    app.disconnect();
}
```

## Contributors
- Arun Karki

## License
This project is licensed under the [MIT License](LICENSE).
