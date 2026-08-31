# JDBC — Java Database Connectivity

A comprehensive guide to **JDBC (Java Database Connectivity)** in Java, from the fundamentals to advanced concepts such as `ResultSet`, `RowSet`, transactions, batch processing, metadata, and connection management.

---

## Table of Contents

1. [What is JDBC?](#1-what-is-jdbc)
2. [Why JDBC?](#2-why-jdbc)
3. [JDBC Architecture](#3-jdbc-architecture)
4. [JDBC Components](#4-jdbc-components)
5. [JDBC Driver](#5-jdbc-driver)
6. [JDBC Driver Types](#6-jdbc-driver-types)
7. [JDBC Workflow](#7-jdbc-workflow)
8. [Project Setup](#8-project-setup)
9. [Database Connection](#9-database-connection)
10. [Connection Interface](#10-connection-interface)
11. [Statement](#11-statement)
12. [PreparedStatement](#12-preparedstatement)
13. [Statement vs PreparedStatement](#13-statement-vs-preparedstatement)
14. [CallableStatement](#14-callablestatement)
15. [ResultSet](#15-resultset)
16. [ResultSet Types](#16-resultset-types)
17. [ResultSet Concurrency](#17-resultset-concurrency)
18. [ResultSet Holdability](#18-resultset-holdability)
19. [ResultSet Cursor](#19-resultset-cursor)
20. [ResultSet Navigation](#20-resultset-navigation)
21. [Reading Data from ResultSet](#21-reading-data-from-resultset)
22. [Updating Data Through ResultSet](#22-updating-data-through-resultset)
23. [RowSet](#23-rowset)
24. [Why RowSet?](#24-why-rowset)
25. [RowSet Types](#25-rowset-types)
26. [Connected RowSet](#26-connected-rowset)
27. [Disconnected RowSet](#27-disconnected-rowset)
28. [JdbcRowSet](#28-jdbcrowset)
29. [CachedRowSet](#29-cachedrowset)
30. [WebRowSet](#30-webrowset)
31. [FilteredRowSet](#31-filteredrowset)
32. [JoinRowSet](#32-joinrowset)
33. [ResultSet vs RowSet](#33-resultset-vs-rowset)
34. [Transactions](#34-transactions)
35. [Commit and Rollback](#35-commit-and-rollback)
36. [Savepoints](#36-savepoints)
37. [Batch Processing](#37-batch-processing)
38. [Generated Keys](#38-generated-keys)
39. [Database Metadata](#39-database-metadata)
40. [ResultSet Metadata](#40-resultset-metadata)
41. [JDBC Exceptions](#41-jdbc-exceptions)
42. [Try-With-Resources](#42-try-with-resources)
43. [SQL Injection](#43-sql-injection)
44. [Connection Pooling](#44-connection-pooling)
45. [JDBC Best Practices](#45-jdbc-best-practices)
46. [Complete JDBC Example](#46-complete-jdbc-example)
47. [JDBC Mental Model](#47-jdbc-mental-model)
48. [Learning Checklist](#48-learning-checklist)

---

# 1. What is JDBC?

**JDBC** stands for:

> Java Database Connectivity

It is the standard Java API used to communicate with relational databases.

JDBC allows a Java application to:

* Connect to a database
* Execute SQL queries
* Insert records
* Update records
* Delete records
* Retrieve records
* Execute stored procedures
* Manage transactions
* Retrieve database metadata
* Process query results
* Perform batch operations

Examples of databases that can be accessed through JDBC:

* MySQL
* PostgreSQL
* Oracle
* SQL Server
* MariaDB
* H2
* SQLite

---

# 2. Why JDBC?

Suppose we have a Java application:

```text
Java Application
       |
       |
       v
     JDBC
       |
       |
       v
   Database
```

Instead of Java having database-specific APIs, JDBC provides a common API.

For example:

```java
Connection connection = DriverManager.getConnection(
    url,
    username,
    password
);
```

The Java code can use the same JDBC concepts regardless of whether the database is MySQL or PostgreSQL.

The database-specific implementation is provided by the JDBC driver.

---

# 3. JDBC Architecture

A simplified JDBC architecture looks like this:

```text
+-------------------------+
|     Java Application    |
+------------+------------+
             |
             v
+-------------------------+
|      JDBC API           |
|                         |
| Connection              |
| Statement               |
| PreparedStatement       |
| CallableStatement       |
| ResultSet               |
+------------+------------+
             |
             v
+-------------------------+
|     JDBC Driver         |
+------------+------------+
             |
             v
+-------------------------+
|      Database           |
|                         |
| MySQL / PostgreSQL etc. |
+-------------------------+
```

The important idea is:

```text
Application
     ↓
JDBC API
     ↓
JDBC Driver
     ↓
Database
```

---

# 4. JDBC Components

The most important JDBC interfaces/classes are:

| Component           | Purpose                                              |
| ------------------- | ---------------------------------------------------- |
| `DriverManager`     | Manages JDBC drivers and creates connections         |
| `Connection`        | Represents a connection to the database              |
| `Statement`         | Executes simple SQL statements                       |
| `PreparedStatement` | Executes parameterized SQL                           |
| `CallableStatement` | Executes stored procedures                           |
| `ResultSet`         | Represents query results                             |
| `RowSet`            | Enhanced/disconnected representation of tabular data |
| `SQLException`      | Represents JDBC errors                               |
| `DatabaseMetaData`  | Information about the database                       |
| `ResultSetMetaData` | Information about query result columns               |
| `Savepoint`         | Marks a point inside a transaction                   |

---

# 5. JDBC Driver

A JDBC driver is a library that allows Java to communicate with a specific database.

For example:

```text
Java Application
       |
       v
JDBC API
       |
       v
MySQL JDBC Driver
       |
       v
MySQL Server
```

For PostgreSQL:

```text
Java Application
       |
       v
JDBC API
       |
       v
PostgreSQL JDBC Driver
       |
       v
PostgreSQL Server
```

The JDBC API defines the interfaces.

The driver implements those interfaces.

---

# 6. JDBC Driver Types

Historically, JDBC drivers were classified into four types.

## Type 1 — JDBC-ODBC Bridge

```text
Java
 ↓
JDBC
 ↓
JDBC-ODBC Bridge
 ↓
ODBC
 ↓
Database
```

This approach is obsolete.

It was removed from modern Java.

---

## Type 2 — Native API Driver

```text
Java
 ↓
JDBC
 ↓
Native Database API
 ↓
Database
```

Requires native database libraries installed on the machine.

Less portable.

---

## Type 3 — Network Protocol Driver

```text
Java
 ↓
JDBC
 ↓
Middleware Server
 ↓
Database
```

The JDBC driver communicates with middleware, which communicates with the database.

Rare today.

---

## Type 4 — Thin Driver

Modern JDBC drivers generally use Type 4.

```text
Java
 ↓
JDBC
 ↓
Pure Java JDBC Driver
 ↓
Database
```

Examples:

```text
MySQL Connector/J
PostgreSQL JDBC Driver
Oracle JDBC Driver
```

Advantages:

* Pure Java
* Platform independent
* No native libraries required
* Direct database communication
* Common modern approach

---

# 7. JDBC Workflow

A typical JDBC operation looks like this:

```text
1. Load / discover driver
        ↓
2. Create Connection
        ↓
3. Create Statement
        ↓
4. Execute SQL
        ↓
5. Process ResultSet
        ↓
6. Commit / rollback if necessary
        ↓
7. Close resources
```

Example:

```java
Connection connection = DriverManager.getConnection(
    url,
    username,
    password
);

PreparedStatement statement =
    connection.prepareStatement(
        "SELECT id, name FROM users"
    );

ResultSet resultSet = statement.executeQuery();

while (resultSet.next()) {
    System.out.println(resultSet.getString("name"));
}

resultSet.close();
statement.close();
connection.close();
```

---

# 8. Project Setup

Modern JDBC drivers are usually added using Maven or Gradle.

For example, with MySQL:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>VERSION</version>
</dependency>
```

For PostgreSQL:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>VERSION</version>
</dependency>
```

The exact driver version should match the version appropriate for your project.

---

# 9. Database Connection

A connection represents a communication session between Java and the database.

Example:

```java
String url = "jdbc:mysql://localhost:3306/shop";
String username = "root";
String password = "password";

Connection connection =
    DriverManager.getConnection(
        url,
        username,
        password
    );
```

The JDBC URL generally contains:

```text
jdbc:<database>://<host>:<port>/<database>
```

Example:

```text
jdbc:mysql://localhost:3306/shop
```

---

# 10. Connection Interface

`Connection` represents a database connection.

```java
Connection connection =
    DriverManager.getConnection(url, username, password);
```

It provides operations such as:

```java
createStatement()
prepareStatement()
prepareCall()
commit()
rollback()
setAutoCommit()
setSavepoint()
close()
```

---

## Auto Commit

By default, JDBC connections generally operate with auto-commit enabled.

```java
connection.getAutoCommit();
```

If auto-commit is enabled:

```text
SQL Statement
     ↓
Executed
     ↓
Automatically committed
```

You can disable it:

```java
connection.setAutoCommit(false);
```

Now:

```text
SQL 1
SQL 2
SQL 3
   ↓
commit()
```

or:

```text
rollback()
```

---

# 11. Statement

`Statement` is used to execute static SQL statements.

Example:

```java
Statement statement =
    connection.createStatement();

ResultSet resultSet =
    statement.executeQuery(
        "SELECT * FROM users"
    );
```

For INSERT:

```java
int rows = statement.executeUpdate(
    "INSERT INTO users(name) VALUES ('Ahmed')"
);
```

For UPDATE:

```java
int rows = statement.executeUpdate(
    "UPDATE users SET name = 'Ali' WHERE id = 1"
);
```

For DELETE:

```java
int rows = statement.executeUpdate(
    "DELETE FROM users WHERE id = 1"
);
```

---

# 12. PreparedStatement

`PreparedStatement` is one of the most important JDBC concepts.

Instead of constructing SQL like:

```java
String sql =
    "SELECT * FROM users WHERE email = '" + email + "'";
```

use:

```java
String sql =
    "SELECT * FROM users WHERE email = ?";

PreparedStatement statement =
    connection.prepareStatement(sql);

statement.setString(1, email);

ResultSet resultSet =
    statement.executeQuery();
```

The `?` is a parameter placeholder.

---

## Setting Parameters

Example:

```java
PreparedStatement statement =
    connection.prepareStatement(
        "SELECT * FROM users WHERE age > ? AND city = ?"
    );

statement.setInt(1, 18);
statement.setString(2, "Cairo");
```

Parameter indexes start at:

```text
1
```

not:

```text
0
```

---

## Common Setter Methods

```java
setString()
setInt()
setLong()
setDouble()
setFloat()
setBoolean()
setDate()
setTimestamp()
setBigDecimal()
setObject()
```

Example:

```java
statement.setString(1, "Ahmed");
statement.setInt(2, 26);
statement.setBoolean(3, true);
```

---

# 13. Statement vs PreparedStatement

| Feature                  | Statement                   | PreparedStatement                           |
| ------------------------ | --------------------------- | ------------------------------------------- |
| Parameters               | No                          | Yes                                         |
| SQL Injection protection | Poor                        | Much better                                 |
| Reusability              | Lower                       | Higher                                      |
| Readability              | Lower for dynamic SQL       | Higher                                      |
| Performance              | Good for simple one-off SQL | Often better for repeated parameterized SQL |
| Recommended              | Limited cases               | Usually preferred                           |

For application code, prefer:

```java
PreparedStatement
```

over dynamically concatenating user input into SQL.

---

# 14. CallableStatement

`CallableStatement` is used to execute stored procedures.

Example SQL procedure:

```sql
CREATE PROCEDURE get_user(IN user_id INT)
BEGIN
    SELECT * FROM users WHERE id = user_id;
END;
```

Java:

```java
CallableStatement statement =
    connection.prepareCall(
        "{call get_user(?)}"
    );

statement.setInt(1, 10);

ResultSet resultSet =
    statement.executeQuery();
```

Stored procedures can also have OUT parameters.

Example:

```java
CallableStatement statement =
    connection.prepareCall(
        "{call calculate_total(?, ?)}"
    );

statement.setInt(1, 10);

statement.registerOutParameter(
    2,
    Types.DECIMAL
);

statement.execute();

BigDecimal result =
    statement.getBigDecimal(2);
```

---

# 15. ResultSet

`ResultSet` represents the data returned by a SQL query.

Example:

```java
PreparedStatement statement =
    connection.prepareStatement(
        "SELECT id, name, email FROM users"
    );

ResultSet resultSet =
    statement.executeQuery();
```

Conceptually:

```text
ResultSet

+----+--------+-------------------+
| id | name   | email             |
+----+--------+-------------------+
| 1  | Ahmed  | ahmed@example.com |
| 2  | Ali    | ali@example.com   |
| 3  | Omar   | omar@example.com  |
+----+--------+-------------------+
```

---

# 16. ResultSet Types

JDBC supports different cursor behaviors.

The main types are:

```java
ResultSet.TYPE_FORWARD_ONLY
ResultSet.TYPE_SCROLL_INSENSITIVE
ResultSet.TYPE_SCROLL_SENSITIVE
```

---

## TYPE_FORWARD_ONLY

The cursor can move only forward.

```text
START
  ↓
Row 1
  ↓
Row 2
  ↓
Row 3
  ↓
END
```

Example:

```java
Statement statement =
    connection.createStatement(
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.CONCUR_READ_ONLY
    );
```

This is the simplest and commonly used mode.

---

## TYPE_SCROLL_INSENSITIVE

The cursor can move in multiple directions.

```text
Row 1
 ↑ ↓
Row 2
 ↑ ↓
Row 3
```

You can use:

```java
resultSet.first();
resultSet.last();
resultSet.previous();
resultSet.next();
resultSet.absolute(5);
resultSet.relative(-2);
```

"Insensitive" means changes made to the underlying database after the `ResultSet` was created generally aren't reflected in the result set.

---

## TYPE_SCROLL_SENSITIVE

The cursor can move in multiple directions and is designed to potentially reflect changes in the underlying data.

However:

> Driver/database support varies.

You should not assume every database driver fully supports sensitivity.

---

# 17. ResultSet Concurrency

There are two major concurrency modes:

```java
ResultSet.CONCUR_READ_ONLY
ResultSet.CONCUR_UPDATABLE
```

---

## CONCUR_READ_ONLY

You can read the data:

```java
resultSet.getString("name");
```

but cannot update the database through the `ResultSet`.

---

## CONCUR_UPDATABLE

Allows certain updates through the `ResultSet`.

Example:

```java
Statement statement =
    connection.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE
    );
```

Then:

```java
resultSet.next();

resultSet.updateString(
    "name",
    "Ahmed Medhat"
);

resultSet.updateRow();
```

The `updateRow()` call applies the change.

Support depends on the database and driver.

---

# 18. ResultSet Holdability

Holdability determines what happens to a `ResultSet` when a transaction is committed.

Two modes:

```java
ResultSet.HOLD_CURSORS_OVER_COMMIT
```

and:

```java
ResultSet.CLOSE_CURSORS_AT_COMMIT
```

Example:

```java
connection.createStatement(
    ResultSet.TYPE_FORWARD_ONLY,
    ResultSet.CONCUR_READ_ONLY,
    ResultSet.HOLD_CURSORS_OVER_COMMIT
);
```

Driver/database behavior may vary.

---

# 19. ResultSet Cursor

A `ResultSet` contains a cursor.

Initially:

```text
        Cursor
          ↓
      before first row

+----+------+
| id | name |
+----+------+
| 1  | A    |
| 2  | B    |
| 3  | C    |
+----+------+
```

Calling:

```java
resultSet.next();
```

moves it to the first row.

```text
+----+------+
| id | name |
+----+------+
| 1  | A    | ← cursor
| 2  | B    |
| 3  | C    |
+----+------+
```

---

# 20. ResultSet Navigation

For scrollable result sets:

```java
next()
previous()
first()
last()
beforeFirst()
afterLast()
absolute()
relative()
```

Example:

```java
resultSet.first();

resultSet.last();

resultSet.previous();

resultSet.absolute(5);

resultSet.relative(-2);
```

Useful state methods:

```java
isBeforeFirst()
isFirst()
isLast()
isAfterLast()
```

---

# 21. Reading Data from ResultSet

Suppose:

```sql
SELECT id, name, age
FROM users;
```

Java:

```java
while (resultSet.next()) {

    int id =
        resultSet.getInt("id");

    String name =
        resultSet.getString("name");

    int age =
        resultSet.getInt("age");

    System.out.println(
        id + " " + name + " " + age
    );
}
```

---

## Reading by Column Index

```java
int id =
    resultSet.getInt(1);

String name =
    resultSet.getString(2);
```

Column indexes start at:

```text
1
```

---

## Reading by Column Name

Usually easier to maintain:

```java
resultSet.getInt("id");
resultSet.getString("name");
```

---

## Common Getter Methods

```java
getString()
getInt()
getLong()
getBoolean()
getDouble()
getFloat()
getBigDecimal()
getDate()
getTime()
getTimestamp()
getBytes()
getObject()
```

---

# 22. Updating Data Through ResultSet

With an updatable result set:

```java
resultSet.next();

resultSet.updateString(
    "name",
    "New Name"
);

resultSet.updateInt(
    "age",
    30
);

resultSet.updateRow();
```

Insert a row:

```java
resultSet.moveToInsertRow();

resultSet.updateString(
    "name",
    "Ahmed"
);

resultSet.updateInt(
    "age",
    26
);

resultSet.insertRow();

resultSet.moveToCurrentRow();
```

Delete the current row:

```java
resultSet.deleteRow();
```

Again, support depends on the driver and query.

---

# 23. RowSet

`RowSet` is an extension of the JDBC model designed to make working with tabular database data easier and more flexible.

The core interface is:

```java
javax.sql.RowSet
```

A `RowSet` extends:

```java
ResultSet
```

conceptually providing additional capabilities around configuration, events, and disconnected usage.

The RowSet API is particularly important for understanding older/standard JDBC APIs and disconnected data models.

---

# 24. Why RowSet?

A normal `ResultSet` is generally associated with an active JDBC statement/connection.

A `RowSet` can provide a more flexible abstraction.

Important benefits include:

* JavaBean-style properties
* Event notifications
* Easier configuration
* Connected and disconnected implementations
* Potential serialization
* Ability to work with data after disconnecting

Conceptually:

```text
ResultSet
    |
    +---- Directly tied to statement/connection lifecycle
    |
    v

RowSet
    |
    +---- More flexible abstraction
    |
    +---- Can be connected
    |
    +---- Can be disconnected
```

---

# 25. RowSet Types

The standard RowSet implementations/interfaces include:

```text
RowSet
 |
 +-- JdbcRowSet
 |
 +-- CachedRowSet
 |
 +-- WebRowSet
 |
 +-- FilteredRowSet
 |
 +-- JoinRowSet
```

The most important distinction is:

```text
Connected
   |
   +-- JdbcRowSet

Disconnected
   |
   +-- CachedRowSet
       |
       +-- WebRowSet
       +-- FilteredRowSet
       +-- JoinRowSet
```

---

# 26. Connected RowSet

A connected RowSet maintains a connection to the database while it operates.

The standard example is:

```text
JdbcRowSet
```

Conceptually:

```text
JdbcRowSet
     |
     v
Database
```

The database connection remains available while the RowSet is being used.

---

# 27. Disconnected RowSet

A disconnected RowSet retrieves data and can then operate without maintaining an active database connection.

The main example is:

```text
CachedRowSet
```

Conceptually:

```text
Database
    |
    | connection
    v
CachedRowSet
    |
    | disconnect
    v
Application
```

This is useful when you don't want to keep database resources occupied while processing data.

---

# 28. JdbcRowSet

`JdbcRowSet` is a connected RowSet.

It wraps JDBC functionality and provides a RowSet-style interface.

Example:

```java
JdbcRowSet rowSet =
    RowSetProvider.newFactory()
                  .createJdbcRowSet();

rowSet.setUrl(
    "jdbc:mysql://localhost:3306/shop"
);

rowSet.setUsername("root");
rowSet.setPassword("password");

rowSet.setCommand(
    "SELECT id, name FROM users"
);

rowSet.execute();

while (rowSet.next()) {
    System.out.println(
        rowSet.getString("name")
    );
}
```

Important:

```text
JdbcRowSet
```

is connected.

---

# 29. CachedRowSet

`CachedRowSet` is a disconnected RowSet.

Example:

```java
CachedRowSet rowSet =
    RowSetProvider.newFactory()
                  .createCachedRowSet();

rowSet.setUrl(
    "jdbc:mysql://localhost:3306/shop"
);

rowSet.setUsername("root");
rowSet.setPassword("password");

rowSet.setCommand(
    "SELECT id, name FROM users"
);

rowSet.execute();
```

After retrieving the data, the RowSet can operate independently from the database connection.

Conceptually:

```text
Connect
   ↓
Execute Query
   ↓
Load Data
   ↓
Disconnect
   ↓
Work with CachedRowSet
```

---

# 30. WebRowSet

`WebRowSet` extends the idea of `CachedRowSet` and provides XML representation of RowSet data.

It can:

* Read/write XML
* Represent RowSet data in XML
* Work in disconnected scenarios

Conceptually:

```text
Database
   ↓
WebRowSet
   ↓
XML
```

Example:

```java
WebRowSet rowSet =
    RowSetProvider.newFactory()
                  .createWebRowSet();
```

Writing XML:

```java
rowSet.writeXml(outputStream);
```

Reading XML:

```java
rowSet.readXml(inputStream);
```

---

# 31. FilteredRowSet

`FilteredRowSet` allows filtering rows without necessarily issuing another SQL query.

For example, suppose we have:

```text
Users

id | name  | age
---+-------+----
1  | Ahmed | 26
2  | Ali   | 17
3  | Omar  | 30
```

We could define a filter:

```text
age >= 18
```

The RowSet can expose only matching rows.

Conceptually:

```text
Cached Data
     |
     v
FilteredRowSet
     |
     v
Rows matching filter
```

A filter implements:

```java
Predicate
```

Example concept:

```java
public class AgeFilter
        implements Predicate {

    @Override
    public boolean evaluate(
            RowSet rowSet) throws SQLException {

        return rowSet.getInt("age") >= 18;
    }

    @Override
    public boolean evaluate(
            Object value,
            int column)
            throws SQLException {

        return true;
    }

    @Override
    public boolean evaluate(
            Object value,
            int column,
            CachedRowSet rowSet)
            throws SQLException {

        return true;
    }
}
```

Then:

```java
FilteredRowSet filtered =
    RowSetProvider.newFactory()
                  .createFilteredRowSet();

filtered.setFilter(
    new AgeFilter()
);
```

---

# 32. JoinRowSet

`JoinRowSet` allows RowSets to be joined without necessarily performing a new database-side SQL JOIN.

Conceptually:

```text
RowSet A
   |
   | JOIN
   |
RowSet B
   |
   v
JoinRowSet
```

For example:

```text
Users

id | name
---+------
1  | Ahmed
2  | Ali

Orders

user_id | amount
--------+-------
1       | 100
2       | 200
```

A `JoinRowSet` can combine related RowSets.

This is particularly useful for disconnected data processing.

---

# 33. ResultSet vs RowSet

| Feature             | ResultSet            | RowSet               |
| ------------------- | -------------------- | -------------------- |
| JDBC query results  | Yes                  | Yes                  |
| Extends ResultSet   | No                   | Yes                  |
| JavaBean properties | No                   | Yes                  |
| Events              | Limited              | Yes                  |
| Connected           | Usually              | Some types           |
| Disconnected        | No                   | Some types           |
| Serializable        | No general guarantee | Some implementations |
| Filtering           | SQL/driver dependent | `FilteredRowSet`     |
| Joining             | SQL                  | `JoinRowSet`         |
| XML support         | No                   | `WebRowSet`          |

The important mental model:

```text
ResultSet
    ↓
Immediate JDBC query result

RowSet
    ↓
More flexible ResultSet-based abstraction
```

---

# 34. Transactions

A transaction is a group of database operations that should behave as one logical unit.

Example:

```text
Transfer $100

Account A
   ↓
-100

Account B
   ↓
+100
```

Both operations should succeed together.

If one fails:

```text
Rollback
```

---

# 35. Commit and Rollback

Disable auto-commit:

```java
connection.setAutoCommit(false);
```

Execute operations:

```java
statement1.executeUpdate();

statement2.executeUpdate();
```

Commit:

```java
connection.commit();
```

If something fails:

```java
connection.rollback();
```

Complete structure:

```java
try {

    connection.setAutoCommit(false);

    // operation 1
    // operation 2
    // operation 3

    connection.commit();

} catch (SQLException e) {

    connection.rollback();

}
```

---

# 36. Savepoints

A savepoint allows partial rollback.

Example:

```java
connection.setAutoCommit(false);

statement1.executeUpdate();

Savepoint savepoint =
    connection.setSavepoint();

statement2.executeUpdate();

statement3.executeUpdate();
```

If something goes wrong:

```java
connection.rollback(savepoint);
```

This rolls the transaction back to the savepoint instead of completely rolling it back.

---

# 37. Batch Processing

Batch processing allows multiple SQL operations to be sent/executed as a group.

Instead of:

```text
INSERT
INSERT
INSERT
INSERT
INSERT
```

you can build a batch.

Example:

```java
PreparedStatement statement =
    connection.prepareStatement(
        "INSERT INTO users(name) VALUES (?)"
    );

statement.setString(1, "Ahmed");
statement.addBatch();

statement.setString(1, "Ali");
statement.addBatch();

statement.setString(1, "Omar");
statement.addBatch();

int[] results =
    statement.executeBatch();
```

Benefits:

* Fewer round trips
* Better performance
* Convenient bulk operations

---

# 38. Generated Keys

Many databases generate IDs automatically.

Example:

```sql
INSERT INTO users(name)
VALUES ('Ahmed');
```

You may want the generated ID.

Use:

```java
PreparedStatement statement =
    connection.prepareStatement(
        "INSERT INTO users(name) VALUES (?)",
        Statement.RETURN_GENERATED_KEYS
    );
```

Execute:

```java
statement.setString(1, "Ahmed");

statement.executeUpdate();
```

Retrieve:

```java
ResultSet keys =
    statement.getGeneratedKeys();

if (keys.next()) {

    long id =
        keys.getLong(1);

    System.out.println(id);
}
```

---

# 39. Database Metadata

`DatabaseMetaData` provides information about the database and JDBC driver.

Example:

```java
DatabaseMetaData metadata =
    connection.getMetaData();
```

You can retrieve information such as:

```java
metadata.getDatabaseProductName();

metadata.getDatabaseProductVersion();

metadata.getDriverName();

metadata.getDriverVersion();

metadata.getURL();

metadata.getUserName();
```

You can also inspect database capabilities.

---

# 40. ResultSet Metadata

`ResultSetMetaData` describes the columns returned by a query.

Example:

```java
ResultSetMetaData metadata =
    resultSet.getMetaData();
```

Number of columns:

```java
int count =
    metadata.getColumnCount();
```

Column information:

```java
metadata.getColumnName(1);

metadata.getColumnType(1);

metadata.getColumnTypeName(1);

metadata.getColumnLabel(1);

metadata.getColumnClassName(1);
```

This is useful when processing unknown/dynamic result sets.

---

# 41. JDBC Exceptions

The primary JDBC exception is:

```java
SQLException
```

Example:

```java
try {

    Connection connection =
        DriverManager.getConnection(
            url,
            username,
            password
        );

} catch (SQLException e) {

    e.printStackTrace();
}
```

`SQLException` can provide:

```java
e.getMessage();

e.getSQLState();

e.getErrorCode();

e.getCause();
```

---

## SQLException Chain

A database operation can potentially produce multiple chained SQL exceptions.

You can inspect them:

```java
SQLException current = e;

while (current != null) {

    System.out.println(
        current.getMessage()
    );

    current = current.getNextException();
}
```

---

# 42. Try-With-Resources

This is the preferred way to manage JDBC resources.

Instead of:

```java
Connection connection = null;
PreparedStatement statement = null;
ResultSet resultSet = null;

try {

    // ...

} finally {

    resultSet.close();
    statement.close();
    connection.close();
}
```

use:

```java
try (
    Connection connection =
        DriverManager.getConnection(
            url,
            username,
            password
        );

    PreparedStatement statement =
        connection.prepareStatement(
            "SELECT * FROM users"
        );

    ResultSet resultSet =
        statement.executeQuery()
) {

    while (resultSet.next()) {

        System.out.println(
            resultSet.getString("name")
        );
    }

}
```

Resources are automatically closed.

The usual ownership hierarchy is:

```text
Connection
   |
   +-- Statement
          |
          +-- ResultSet
```

Close the higher-level resources appropriately, and use try-with-resources.

---

# 43. SQL Injection

Never construct SQL by directly concatenating untrusted input.

Bad:

```java
String sql =
    "SELECT * FROM users WHERE email = '"
    + email
    + "'";
```

Suppose the user provides malicious SQL input.

This can change the meaning of the query.

---

## Correct Approach

Use:

```java
PreparedStatement
```

Example:

```java
PreparedStatement statement =
    connection.prepareStatement(
        "SELECT * FROM users WHERE email = ?"
    );

statement.setString(1, email);
```

The parameter is treated as data rather than being directly inserted into the SQL syntax.

---

# 44. Connection Pooling

Opening a database connection can be relatively expensive.

Creating a new connection for every request is inefficient:

```text
HTTP Request
    ↓
Create DB Connection
    ↓
Execute Query
    ↓
Close Connection
```

For backend applications, connection pooling is normally preferred:

```text
Application
     |
     v
Connection Pool
     |
 +---+---+---+
 |   |   |   |
 DB  DB  DB  DB
 Connections
```

A pool maintains reusable connections.

Application:

```text
borrow connection
       ↓
execute SQL
       ↓
return connection
```

Popular pooling solutions include:

* HikariCP
* Apache DBCP
* Apache Commons Pool-based solutions

When using frameworks such as Spring Boot, pooling is commonly configured for you.

---

# 45. JDBC Best Practices

## 1. Prefer PreparedStatement

Use:

```java
PreparedStatement
```

for parameterized SQL.

---

## 2. Use try-with-resources

Prefer:

```java
try (...) {
}
```

for JDBC resources.

---

## 3. Don't create unnecessary connections

Use connection pooling in long-running backend applications.

---

## 4. Keep transactions short

Avoid:

```text
BEGIN TRANSACTION
       ↓
Long business processing
       ↓
Network request
       ↓
User interaction
       ↓
COMMIT
```

Instead:

```text
BEGIN
 ↓
DB operations
 ↓
COMMIT
```

---

## 5. Don't expose database credentials

Avoid hardcoding:

```java
String password = "my-secret-password";
```

Use:

* Environment variables
* Secret managers
* Configuration systems

---

## 6. Don't ignore SQLException

Bad:

```java
catch (SQLException e) {
}
```

Handle or propagate the exception appropriately.

---

## 7. Use transactions for atomic operations

For example:

```text
Create Order
+
Decrease Inventory
+
Create Payment Record
```

may need to be handled as one transaction depending on your architecture.

---

## 8. Select only what you need

Instead of:

```sql
SELECT *
FROM users;
```

prefer:

```sql
SELECT id, name, email
FROM users;
```

when those are the only required columns.

---

# 46. Complete JDBC Example

Consider:

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL
);
```

Java:

```java
import java.sql.*;

public class Main {

    private static final String URL =
        "jdbc:mysql://localhost:3306/shop";

    private static final String USER =
        "root";

    private static final String PASSWORD =
        "password";

    public static void main(String[] args) {

        String sql =
            "SELECT id, name, email FROM users";

        try (
            Connection connection =
                DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
                );

            PreparedStatement statement =
                connection.prepareStatement(sql);

            ResultSet resultSet =
                statement.executeQuery()
        ) {

            while (resultSet.next()) {

                long id =
                    resultSet.getLong("id");

                String name =
                    resultSet.getString("name");

                String email =
                    resultSet.getString("email");

                System.out.println(
                    id + " | " +
                    name + " | " +
                    email
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

---

# 47. JDBC Mental Model

The most important thing is to understand the relationship between the objects.

```text
                    DATABASE
                        ▲
                        |
                   JDBC DRIVER
                        ▲
                        |
                    Connection
                        |
          +-------------+-------------+
          |             |             |
      Statement    PreparedStmt   CallableStmt
          |             |             |
          +-------------+-------------+
                        |
                     execute
                        |
                        v
                    ResultSet
                        |
                        v
                 Rows + Columns
```

And:

```text
                     RowSet
                       |
          +------------+------------+
          |            |            |
     JdbcRowSet   CachedRowSet   ...
          |            |
      Connected    Disconnected
                       |
            +----------+----------+
            |          |          |
       WebRowSet  FilteredRS  JoinRowSet
```

---

# 48. Learning Checklist

Before moving to JPA/Hibernate, you should be comfortable with all of the following.

## JDBC Fundamentals

* [ ] What JDBC is
* [ ] JDBC architecture
* [ ] JDBC Driver
* [ ] Driver types
* [ ] JDBC URL
* [ ] `DriverManager`
* [ ] `Connection`

## SQL Execution

* [ ] `Statement`
* [ ] `PreparedStatement`
* [ ] `CallableStatement`
* [ ] `executeQuery()`
* [ ] `executeUpdate()`
* [ ] `execute()`
* [ ] Parameter binding

## ResultSet

* [ ] What `ResultSet` represents
* [ ] Cursor concept
* [ ] `next()`
* [ ] `previous()`
* [ ] `first()`
* [ ] `last()`
* [ ] `absolute()`
* [ ] `relative()`
* [ ] `TYPE_FORWARD_ONLY`
* [ ] `TYPE_SCROLL_INSENSITIVE`
* [ ] `TYPE_SCROLL_SENSITIVE`
* [ ] `CONCUR_READ_ONLY`
* [ ] `CONCUR_UPDATABLE`
* [ ] Holdability
* [ ] Reading values
* [ ] Updating rows
* [ ] Inserting rows
* [ ] Deleting rows

## RowSet

* [ ] `RowSet`
* [ ] Connected vs disconnected RowSets
* [ ] `JdbcRowSet`
* [ ] `CachedRowSet`
* [ ] `WebRowSet`
* [ ] `FilteredRowSet`
* [ ] `JoinRowSet`
* [ ] RowSet events
* [ ] RowSet filtering
* [ ] Disconnected processing

## Transactions

* [ ] Auto-commit
* [ ] `commit()`
* [ ] `rollback()`
* [ ] `Savepoint`
* [ ] Transaction boundaries
* [ ] ACID concepts

## Advanced JDBC

* [ ] Batch processing
* [ ] Generated keys
* [ ] `DatabaseMetaData`
* [ ] `ResultSetMetaData`
* [ ] `SQLException`
* [ ] Try-with-resources
* [ ] Connection pooling
* [ ] SQL injection prevention

---

# Final JDBC Picture

The entire JDBC ecosystem can be remembered like this:

```text
                         Java Application
                                |
                                v
                         +--------------+
                         | JDBC API     |
                         +--------------+
                                |
              +-----------------+------------------+
              |                 |                  |
              v                 v                  v
        Connection        PreparedStatement   CallableStatement
              |                 |                  |
              +-----------------+------------------+
                                |
                                v
                         Execute SQL
                                |
                                v
                           ResultSet
                                |
                         +------+------+
                         |             |
                         v             v
                     Read Data     Update Data
                               
                               
                         RowSet API
                              |
              +---------------+----------------+
              |               |                |
              v               v                v
         JdbcRowSet      CachedRowSet       WebRowSet
         Connected       Disconnected       XML support
                              |
                    +---------+---------+
                    |                   |
                    v                   v
              FilteredRowSet       JoinRowSet


Additional JDBC Concepts:

Connection
    |
    +-- Transactions
    |      |
    |      +-- commit()
    |      +-- rollback()
    |      +-- Savepoint
    |
    +-- Metadata
    |      |
    |      +-- DatabaseMetaData
    |      +-- ResultSetMetaData
    |
    +-- Batch Processing
    |
    +-- Generated Keys
    |
    +-- Connection Pooling
```

## The Most Important Concepts to Master

If your goal is to become a strong Java backend developer, prioritize these in this order:

```text
1. Connection
       ↓
2. Statement
       ↓
3. PreparedStatement
       ↓
4. ResultSet
       ↓
5. Transactions
       ↓
6. Batch Processing
       ↓
7. Metadata
       ↓
8. RowSet
       ↓
9. Connection Pooling
```

The **most important practical JDBC concepts** are `Connection`, `PreparedStatement`, `ResultSet`, transactions, resource management, and connection pooling.

`RowSet` is valuable to understand because it is part of the JDBC API and teaches the connected/disconnected data model, but in modern Java backend development you will encounter `ResultSet` and `PreparedStatement` much more frequently than the various `RowSet` implementations.
