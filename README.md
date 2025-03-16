# DEI Management System - DMS

## Author
- **Filipe Oliveira** (ist1110633)

## Overview
This project is a **PhD Management System** (PMS) developed using **Spring Boot** for the backend and **Vue.js** for the frontend. The system provides functionalities for managing DEI (Department de Engenharia Informática) personnel, including students, teachers, coordinators, and staff members.

## Features

### Core Functionalities
- **View all DEI personnel** in a quick-access table containing:
  - System ID
  - Name
  - IST ID
  - Type (Staff, Student, Teacher, Coordinator, SC)
  - Email
  - Phone number
- **Add new personnel** to the system.
- **Update and remove existing personnel**.
- **Assign roles in the frontend** (Staff, Student, Teacher, Coordinator, SC).
- **View all students** in a quick-access table containing:
  - Name
  - IST ID
  - Email
  - Thesis Workflow state
  - Defense Workflow state
- **Filter students** by thesis/defense workflow state.
- **View individual student pages**, including:
  - Personal information section (istID, email, phone number)
  - Thesis workflow
  - Defense workflow
- **Update workflow states** according to the described steps.
- **System Statistics Page**, including:
  - Number of students
  - Number of teachers
  - Number of students per thesis workflow state
  - Number of students per defense workflow state
- **Detailed Logs of System Actions:**, including:
  - Personnel creation
  - Editing personnel
  - Removing personnel
  - Update of Thesis Workflow states
  - Update of Defense Workflow states


## Setup & Installation
### Backend (Spring Boot)
1. Ensure you have Java and Maven installed.
2. Clone the repository.
3. Navigate to the backend folder.
4. Run:
   ```sh
   mvn clean spring-boot:run
   ```

### Frontend (Vue.js)
1. Ensure you have Node.js and npm installed.
2. Navigate to the frontend folder.
3. Install dependencies:
   ```sh
   npm install
   ```
4. Start the frontend server:
   ```sh
   npm run dev
   ```

## Database
- The system uses **PostgreSQL** as the database.
- Populate the database using `populate.sql` before starting the application.
