# Database_assignment_2

# Overview

The Bookstore Management System is a Java application developed in IntelliJ IDEA that facilitates the management of books, authors, customers, and orders. It provides a set of functionalities to interact with a PostgreSQL database, including CRUD operations, transaction handling, and metadata access.

# Database Schema

The database consists of four tables: Author, Book, Customer, and OrderBook. The relationships are Many-to-One between Author and Book, Book and OrderBook, and Customer and OrderBook. For a visual representation of the database schema, refer to the ER Diagram.

F# unctions

# Connect Function

This function establishes a connection to a PostgreSQL database. It takes the database name, username, and password as parameters and returns a Connection object if the connection is successful.

# Insert_Into_Book Function

Inserts a new record into the Book table with the provided information. Uses SQL INSERT INTO statement to add a new row to the table.

Read_Book_Table Function

Reads and prints all records from the Book table. Retrieves data using an SQL SELECT * FROM Book query and prints details to the console.

# Update_Book_Table Function

Updates the quantity of a book in the Book table based on book ID. Uses SQL UPDATE statement to modify the quantity of a specified book.

# Delete_Row_From_Book Function

Deletes a row from the Book table based on the provided book ID. Uses SQL DELETE FROM statement to remove the specified book.

# INSERT_INTO_Book Function

Inserts a new record into the ORDERBOOK table, representing a customer order. Executes an SQL INSERT INTO statement to add a new order.

# Check_Number_Of_Books Function

Checks if there are enough books in stock based on the requested quantity. Returns the remaining quantity if there are enough books, otherwise returns -1.

# Order_Book Function

Initiates the process of ordering a book by checking availability and updating the tables. Calls Check_Number_Of_Books to verify stock, then updates ORDERBOOK and Book tables accordingly.

# Table_Detail Function

Prints details about tables in the connected database, including table names and column details.

# Column_Detail Function

Prints details about columns in each table, including column names and data types.

# Primary_Key, Foreign_Key Functions

Print details about primary keys and foreign keys in the connected database, respectively.

## Usage

Clone the repository.
Open the project in IntelliJ IDEA.
Update the database connection details in the main class.
Run the main class to execute various functions.
