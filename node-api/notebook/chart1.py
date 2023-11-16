conn = mysql.connector.connect(**db_config)

cursor = conn.cursor()

cursor.execute("SELECT fabric_code, total_tests, defects_detected FROM fabric_information")
fabric_info = cursor.fetchall()


cursor.close()
conn.close()


fabric_info_sorted = sorted(fabric_info, key=lambda x: x[0])

fabric_codes, total_tests, defects_detected = zip(*fabric_info_sorted)


fig, ax = plt.subplots()
bar_width = 0.35
bar1 = ax.bar(range(1, len(fabric_codes) + 1), total_tests, bar_width, label='Total Tests')
bar2 = ax.bar([code + bar_width for code in range(1, len(fabric_codes) + 1)], defects_detected, bar_width, label='Defects Detected')

ax.set_xlabel('Fabric Code (Numerical Order)')
ax.set_ylabel('Count')
ax.set_title('Total Tests and Defects Detected for Each Fabric')
ax.set_xticks([code + bar_width / 2 for code in range(1, len(fabric_codes) + 1)])
ax.set_xticklabels(fabric_codes)
ax.legend()

plt.show()
