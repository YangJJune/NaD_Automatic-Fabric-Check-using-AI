import matplotlib.pyplot as plt
conn = mysql.connector.connect(**db_config)

cursor = conn.cursor()


cursor.execute("SELECT x_coordinate, y_coordinate FROM Defect_information")
bad_locations = cursor.fetchall()

cursor.close()
conn.close()

x_coordinates, y_coordinates = zip(*bad_locations)


plt.scatter(x_coordinates, y_coordinates, color='red', marker='o', s=60, label='Bad Locations')
plt.title('Bad Locations Scatter Plot')
plt.xlabel('X Coordinate')
plt.ylabel('Y Coordinate')
plt.legend()
plt.grid(True, linestyle='--', alpha=0.7)
plt.show()
