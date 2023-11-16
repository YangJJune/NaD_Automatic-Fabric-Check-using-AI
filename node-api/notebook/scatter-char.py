# Establish a connection
conn = mysql.connector.connect(**db_config)

# Create a cursor to execute SQL queries
cursor = conn.cursor()

# Fetch data from Fabric_information table
cursor.execute("SELECT fabric_code, total_tests, defects_detected FROM Fabric_information")
fabric_data = cursor.fetchall()

# Unpack the data for plotting
fabric_codes, total_tests, defects_detected = zip(*fabric_data)

# Scatter plot for Fabric_information
plt.scatter(total_tests, defects_detected, color='blue', label='Fabric Information')
plt.title('Fabric Information Scatter Plot')
plt.xlabel('Total Tests')
plt.ylabel('Defects Detected')
plt.legend()
plt.show()
