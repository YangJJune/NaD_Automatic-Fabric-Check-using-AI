from ultralytics import YOLO
from PIL import Image
import json
import argparse
import requests

model = YOLO("./best.pt")

# get result from arg
parser = argparse.ArgumentParser()
parser.add_argument("image_path", help="path to image")
parser.add_argument("parent_code")

args = parser.parse_args()
image = Image.open(args.image_path)

result = model(image)
json_result = result[0].tojson()

parsed_json = json.loads(json_result)



# Pretty-print the JSON
pretty_json = json.dumps(parsed_json, indent=2)
print(pretty_json)

# print(len(parsed_json))
# create json for server
server_json = []
if len(parsed_json) == 0 :
        url = "http://49.173.62.69:3000/ok-fabric/"+args.parent_code
        headers = {"Content-Type": "application/json"}
        requests.get(url, headers=headers)
        
else :
        for i in range(len(parsed_json)):
            cropped_defect = image.crop((parsed_json[i]['box']['x1'], parsed_json[i]['box']['y1'], parsed_json[i]['box']['x2'], parsed_json[i]['box']['y2']))
            cropped_defect.save("./" + args.image_path + "_" + str(i) + ".jpg")
            server_json.append({
                "parent_fabric": args.parent_code,
                "issue_name": parsed_json[i]['name'],
                "x": (parsed_json[i]['box']['x1'] + parsed_json[i]['box']['x2']) / 2,
                "y": (parsed_json[i]['box']['y1'] + parsed_json[i]['box']['y2']) / 2,
                "image_path": './' + args.image_path
            })
        pretty_json = json.dumps(server_json, indent=2)
        print(pretty_json)

        # send result to server
        url = "http://49.173.62.69:3000/add-defect"
        headers = {"Content-Type": "application/json"}
        response = requests.post(url, headers=headers, json=server_json)
