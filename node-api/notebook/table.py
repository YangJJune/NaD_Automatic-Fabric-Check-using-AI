import mysql.connector
from tabulate import tabulate


# Replace 'your_username', 'your_password', 'your_host', and 'your_database' with your actual MySQL credentials
db_config = {
    'user': 'root',
    'password': '11111111',
    'host': 'localhost',
    'database': 'NAD_test',
}

# Establish a connection
conn = mysql.connector.connect(**db_config)

# Create a cursor to execute SQL queries
cursor = conn.cursor()

cursor.execute("SELECT * FROM Fabric_information")
Fabric_information_rows = cursor.fetchall()

# fabric_information table
print("Fabric Information Table:")
print(tabulate(Fabric_information_rows, headers=cursor.column_names))


cursor.execute("SELECT * FROM Defect_information")
Defect_information_rows = cursor.fetchall()

# bad_information table
print("\n Defect Information Table:")
print(tabulate(Defect_information_rows, headers=cursor.column_names))


# Close the cursor and connection
cursor.close()
conn.close()
