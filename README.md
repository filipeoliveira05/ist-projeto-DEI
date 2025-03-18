# 🎓 DEI Management System (DMS)

## 👨‍💻 Author
- **Filipe Oliveira** (ist1110633)

## 📌 Overview
The **PhD Management System (PMS)** is a web-based application designed for managing DEI (Departamento de Engenharia Informática) personnel.  
Built with **Spring Boot** (backend) and **Vue.js** (frontend), the system allows efficient management of students, teachers, coordinators, and staff members.

---

## 🚀 Features

### 🏢 Personnel Management
✅ View all DEI personnel in a structured table with:  
   - System ID  
   - Name  
   - IST ID  
   - Role (Staff, Student, Teacher, Coordinator, SC)  
   - Email  
   - Phone number  

✅ Add new personnel to the system.  
✅ Update and remove existing personnel.  
✅ Assign roles directly from the frontend.  

### 🎓 Student Management
✅ View all students in a table including:  
   - Name  
   - IST ID  
   - Email  
   - Thesis Workflow state  
   - Defense Workflow state  

✅ Filter students based on workflow states.  
✅ View individual student details, including:  
   - Personal information (IST ID, email, phone)  
   - Thesis and defense workflows  

✅ Update workflow states dynamically.  

### 📊 System Statistics
📌 Overview of key system metrics:  
   - Total number of students and teachers  
   - Students per **Thesis Workflow** state  
   - Students per **Defense Workflow** state  

### 📝 Activity Logs
🔍 Track all critical system actions:  
   - Personnel creation, editing, and removal  
   - Updates in **Thesis Workflow** states  
   - Updates in **Defense Workflow** states  

---

## ⚙️ Setup & Installation

### 🔧 Backend (Spring Boot)
1. Ensure **Java** and **Maven** are installed.
2. Clone the repository:
   ```sh
   git clone https://github.com/your-repo-url.git
   ```
3. Navigate to the backend folder:
   ```sh
   cd backend
   ```
4. Run the backend:
   ```sh
   mvn clean spring-boot:run
   ```

### 🖥️ Frontend (Vue.js)
1. Ensure **Node.js** and **npm** are installed.
2. Navigate to the frontend folder:
   ```sh
   cd frontend
   ```
3. Install dependencies:
   ```sh
   npm install
   ```
4. Start the frontend server:
   ```sh
   npm run dev
   ```

---

## 🗄️ Database Configuration
- The system uses **PostgreSQL** as the database.  
- Before starting the application, **populate the database** using the provided `populate.sql` file.
