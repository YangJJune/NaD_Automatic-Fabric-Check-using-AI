import seaborn as sns

conn = mysql.connector.connect(**db_config)


cursor = conn.cursor()

cursor.execute("SELECT x_coordinate, y_coordinate FROM Defect_information")
bad_locations = cursor.fetchall()

cursor.close()
conn.close()

x_coordinates, y_coordinates = zip(*bad_locations)

plt.figure(figsize=(6,3))
sns.kdeplot(x=x_coordinates, y=y_coordinates, cmap="Reds", fill=True)
plt.title('Bad Locations Heatmap')
plt.xlabel('X Coordinate')
plt.ylabel('Y Coordinate')
plt.show()


plt.scatter(x_coordinates, y_coordinates, c='red', alpha=0.3, s=50, label='Bad Locations')
plt.title('Bad Locations Bubble Chart')
plt.xlabel('X Coordinate')
plt.ylabel('Y Coordinate')
plt.legend()
plt.grid(True, linestyle='--', alpha=0.7)
plt.show()
