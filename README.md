# Project Overview
A console-based Railway Reservation System built using Core Java and file handling. The system supports role-based access (Admin/User), train management (CRUD), seat availability enforcement, ticket booking & cancellation with refund logic, revenue tracking, and persistent storage using TXT files.

## Features
# Role Based Login
  - Admin Login
  - User Login
  - Session Based Menu System

# Train Management(Admin)
  - Add new train
  - Prevent Duplicate Train Number
  - Update Train Details
  - Delete Train(Blocked if active booking exist)
  - Input Validation(no negative seat values)

# Ticket Booking(User)
  - Book Ticket by Train Number
  - Seat class selection(Sleeper/AC)
  - Seat Availability enforcement
  - Booking Blocked if seats = 0
  - Prevent past date booking
  - Auto ID Generation(Passenger & Ticket ID)

# Ticket Cancellation
  - Refund Logic
      - 2 days before - 80% refund
      - 1 day before - 50% refund
      - Same Day - no refund
   
# Revenue Report
  - Revenue is calculated from CONFIRMED tickets only.
  - Automatically reduced after cancellation.

# Persistent Storage(File Handling)
Data Stored using TXT Files
  - trains.txt
  - tickets.txt
  - passengers.txt
  - id_counter.txt

## Technologies Used
  - Java (Core Java)
  - OOP Concepts
  - File Handling(BufferedReader / BufferedWriter)
  - Console Based UI

## How to Run
1. Compile : javac *.java
2. Run : java Main

## Default Credentials
1. Admin
  - Username : admin
  - Password : admin123

2. User
  - Username : user
  - Password : user123

## Validatiion Implemented
  - Prevents negative seat values.
  - Prevents duplicate train numbers.
  - Prevents the booking of invalid train.
  - Prevents the booking of past date.
  - Prevents booking if seats are unavailable.
  - Prevents the deletion of that train which consists of active bookings.
  - Prevents double cancellation.
  - Input mismatch protection.

## Screenshots
1. Admin Login Screen
<img width="687" height="443" alt="image" src="https://github.com/user-attachments/assets/2e993b58-5485-440d-8bba-8f070844684d" />

2. Add Train
<img width="541" height="504" alt="image" src="https://github.com/user-attachments/assets/d4ab51dd-3273-4d42-af98-a8fc9c128bfb" />

3. Ticket Booking
<img width="615" height="502" alt="image" src="https://github.com/user-attachments/assets/ed53abdd-a973-4ece-a3ec-0b1cd2479f8b" />

4. Booking when seat = 0
<img width="622" height="450" alt="image" src="https://github.com/user-attachments/assets/1ad26b7d-bc2b-4e0b-b2f2-b15bc9d8fc79" />

5. Cancellation + Refund Logic
<img width="526" height="366" alt="image" src="https://github.com/user-attachments/assets/95fbc02f-5667-4e9d-b2a4-97587bd10b04" />

6. Revenue Report
<img width="551" height="316" alt="image" src="https://github.com/user-attachments/assets/64bf2146-2980-4ecc-87c4-9dc6798ec767" />

