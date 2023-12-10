package org.example;

import java.sql.*;

public class Main {
    public static Connection establishDatabaseConnection(String database_name, String username, String password) {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + database_name, username, password);
            if(connection != null) {
                System.out.println("Successfully, connected");
            } else {
                System.out.println("It is not connected");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }


    public static void readBookTable(Connection connection) {
        Statement statement;
        ResultSet resultSet;
        try {
            String query = String.format("SELECT * FROM Book");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.print(resultSet.getString("book_id") + " " + resultSet.getString("title_of_book") + " ");
                System.out.print(resultSet.getString("author_id") + " " + resultSet.getString("price") + " ");
                System.out.println(resultSet.getString("year_published") + " " + resultSet.getString("quantity_of_book"));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void insertBookRecord(Connection connection, String title_of_book, String author_id, String price, String year_published, String quantity_of_book) {
        Statement statement;
        try {
            String query = String.format("INSERT INTO Book (title_of_book, author_id, price, year_published, quantity_of_book) VALUES('%s', %s, %s, %s, %s)", title_of_book, author_id, price, year_published, quantity_of_book);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("INSERT 0 1");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static void deleteRowFromBook(Connection connection, String book_id) {
        Statement statement;
        try {
            String query = String.format("DELETE FROM BOOK WHERE book_id = %s", book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row with book_id = " + book_id + " Deleted!");
        }  catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void updateBookTable(Connection connection, String book_id, String quantity_of_book) {
        Statement statement;
        try {
            String query = String.format("UPDATE BOOK SET quantity_of_book = %s WHERE book_id = %s", quantity_of_book,  book_id);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Book Table Updated");
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void insertIntoOrderBook(Connection connection, String book_id, String customer_id, String quantity_of_book) {
        Statement statement;
        try {
            String query = String.format("INSERT INTO ORDERBOOK (book_id, customer_id, quantity_of_book) VALUES(%s, %s, %s);", book_id, customer_id, quantity_of_book);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("INSERTED INTO ORDERBOOK");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void orderBook(Connection connection, String book_id, String customer_id, String quantity_of_book) {
        try {
            connection.setAutoCommit(false);
            if (checkNumberOfBooks(connection, book_id, quantity_of_book) >= 0) {
                insertIntoOrderBook(connection, book_id, customer_id, quantity_of_book);
                String quantity = String.valueOf(checkNumberOfBooks(connection, book_id, quantity_of_book));
                updateBookTable(connection, book_id, quantity);
                System.out.println("Customer Ordered Book");
                connection.commit();
            } else {
                connection.rollback();
                System.out.println("Can't order book because there's no enough book");
            }
        }  catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static int checkNumberOfBooks(Connection connection, String book_id, String quantity_of_book) {
        try {
            String query = String.format("SELECT quantity_of_book FROM BOOK WHERE book_id = %s", book_id);
            Statement statement;
            ResultSet rs;
            try {
                statement = connection.createStatement();
                rs = statement.executeQuery(query);

                if (rs.next()) {
                    if(rs.getInt("quantity_of_book") -  Integer.valueOf(quantity_of_book) >= 0) {
                        return (rs.getInt("quantity_of_book") -  Integer.valueOf(quantity_of_book));
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public static void tableDetail(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                System.out.println("Columns: ");
                while (columns.next()) {
                    String column_name = columns.getString("COLUMN_NAME");
                    String data_type = columns.getString("TYPE_NAME");
                    int size = columns.getInt("COLUMN_SIZE");
                    System.out.println("Name: " + column_name + ", Data Type: " + data_type + ", Size: " + size);
                }
                columns.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void columnDetail(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet table = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (table.next()) {
                String tableName = table.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
                while (resultSet.next()) {
                    System.out.println("Name: " + resultSet.getString("COLUMN_NAME") + ", Data Type: " + resultSet.getString("TYPE_NAME"));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void primaryKey(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
                ResultSet primary_keys = metaData.getPrimaryKeys(null, null, tableName);
                while (primary_keys.next()) {
                    String columnName = primary_keys.getString("COLUMN_NAME");
                    System.out.println("  Primary Key Column: " + columnName);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void foreignKey(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
           