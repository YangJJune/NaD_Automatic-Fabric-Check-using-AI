from ultralytics import YOLO
import json
import argparse
import requests

model = YOLO("./best.pt")

# get result from arg
parser = argparse.ArgumentParser()
parser.add_argument("image_path", help="path to image")
args = parser.parse_args()

result = model(args.image_path)
json_result = result[0].tojson()

parsed_json = json.loads(json_result)

# Pretty-print the JSON
pretty_json = json.dumps(parsed_json, indent=2)
print(pretty_json)

print(len(parsed_json))
# create json for server
server_json = []
for i in range(len(parsed_json)):
    server_json.append({
        "parent_fabric": args.image_path,
        "issue_name": parsed_json[i]['name'],
        "x": (parsed_json[i]['box']['x1'] + parsed_json[i]['box']['x2']) / 2,
        "y": (parsed_json[i]['box']['y1'] + parsed_json[i]['box']['y2']) / 2,
        "image_path": './' + args.image_path
    })
pretty_json = json.dumps(server_json, indent=2)
print(pretty_json)

# send result to server
# url = "http://49.173.62.69:3000/add-defect"
# headers = {"Content-Type": "application/json"}
# response = requests.post(url, headers=headers, json=server_json)