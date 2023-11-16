def connect():
    return mysql.connector.connect(**db_config)

def add_fabric_information():
    fabric_code = input("Enter fabric code: ")
    total_tests = int(input("Enter total tests: "))
    defects_detected = int(input("Enter defects detected: "))
    test_end_time = input("Enter test end time (YYYY-MM-DD HH:mm:ss): ")
    image_path = input("Enter image path: ")
    status = input("Enter status: ")

    connection = connect()
    cursor = connection.cursor()

    query = "INSERT INTO Fabric_information (fabric_code, total_tests, defects_detected, test_end_time, image_path, status) VALUES (%s, %s, %s, %s, %s, %s)"
    values = (fabric_code, total_tests, defects_detected, test_end_time, image_path, status)

    cursor.execute(query, values)
    connection.commit()

    cursor.close()
    connection.close()
    print("Fabric information added successfully!")

def delete_fabric_information():
    fabric_code = input("Enter fabric code to delete: ")

    connection = connect()
    cursor = connection.cursor()

    query = "DELETE FROM Fabric_information WHERE fabric_code = %s"
    values = (fabric_code,)

    cursor.execute(query, values)
    connection.commit()

    cursor.close()
    connection.close()
    print(f"Fabric information with code {fabric_code} deleted successfully!")

def lookup_fabric_information():
    fabric_code = input("Enter fabric code to look up: ")

    connection = connect()
    cursor = connection.cursor()

    query = "SELECT * FROM Fabric_information WHERE fabric_code = %s"
    values = (fabric_code,)

    cursor.execute(query, values)
    fabric_data = cursor.fetchall()

    cursor.close()
    connection.close()

    if fabric_data:
        print("Fabric Information:")
        for row in fabric_data:
            print(row)
    else:
        print(f"No fabric information found for code {fabric_code}")

# Receive command input from the user (add , delete, lookup)
command = input("Enter a command (add, delete, lookup): ").lower().strip()

if not command:
    print("Please enter a valid command.")
elif command == 'add':
    add_fabric_information()
elif command == 'delete':
    delete_fabric_information()
elif command == 'lookup':
    lookup_fabric_information()
else:
    print("Invalid command. Please enter 'add', 'delete', or 'lookup'.")


