from ultralytics import YOLO
import json

model = YOLO("./best.pt")

result = model('./hole_bg_clean_1.jpg')
json_result = result[0].tojson()

# Assuming 'json_results' contains your JSON string
parsed_json = json.loads(json_result)

# Pretty-print the JSON
pretty_json = json.dumps(parsed_json, indent=2)
print(pretty_json)

# Optionally, you can write the pretty-printed JSON to a file
with open('pretty_yolov8_results.json', 'w') as file:
    file.write(pretty_json)

# results = model(['./hole_bg_clean_1.jpg', './puckering_bg_clean_2.jpg', './stain_bg_clean_1_.jpg'])

# for result in results:
#     json_results = result.tojson()
#     # save the results
#     with open('results.json', 'w') as f:
#         json.dump(json_results, f)