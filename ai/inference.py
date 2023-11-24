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

# send result to server
url = "http://localhost:3000/api/result"