from ultralytics import YOLO
from PIL import Image
import json
import argparse
import requests
import ftplib

model = YOLO("./v15.pt")

# get result from arg
parser = argparse.ArgumentParser()
parser.add_argument("image_path", help="path to image")
parser.add_argument("parent_code")

args = parser.parse_args()
image = Image.open(args.image_path)

result = model(image)
json_result = result[0].tojson()

# save the cropped images
result[0].save_crop("./" + args.image_path + "_crop")


im_array = result[0].plot()
im = Image.fromarray(im_array[..., ::-1])
# im.show()
# im.save("./" + args.image_path + "_result.jpg")
parsed_json = json.loads(json_result)


# Pretty-print the JSON
pretty_json = json.dumps(parsed_json, indent=2)
print(pretty_json)

# print(len(parsed_json))
# create json for server


def remove_adjacant_coords(parsed_json):
    if len(parsed_json) == 0:
        return parsed_json
    else:
        for i in range(len(parsed_json) - 1):
            if (
                abs(parsed_json[i]["box"]["x1"] - parsed_json[i + 1]["box"]["x1"]) < 10
                and abs(parsed_json[i]["box"]["y1"] - parsed_json[i + 1]["box"]["y1"])
                < 10
            ):
                parsed_json.remove(parsed_json[i + 1])
                remove_adjacant_coords(parsed_json)
        return parsed_json


if len(parsed_json) == 0:
    url = "http://49.173.62.69:3000/ok-fabric/" + args.parent_code
    headers = {"Content-Type": "application/json"}
    requests.get(url, headers=headers)

else:
    session = ftplib.FTP(host="49.173.62.69")
    session.set_pasv(False)

    session.login(user="nad_ftp", passwd="1234")

    previous_json = []

    for i in range(len(parsed_json)):
        cropped_defect = image.crop(
            (
                parsed_json[i]["box"]["x1"],
                parsed_json[i]["box"]["y1"],
                parsed_json[i]["box"]["x2"],
                parsed_json[i]["box"]["y2"],
            )
        )
        cropped_defect.save("./" + args.image_path + "_" + str(i) + ".jpg")
        server_json = [
            {
                "issue_name": parsed_json[i]["name"],
                "x": (parsed_json[i]["box"]["x1"] + parsed_json[i]["box"]["x2"]) / 2,
                "y": (parsed_json[i]["box"]["y1"] + parsed_json[i]["box"]["y2"]) / 2,
                "image_path": args.image_path + "_" + str(i) + ".jpg",
            }
        ]
        previous_json.append(server_json[0])
        uploadfile = open("./" + args.image_path + "_" + str(i) + ".jpg", mode="rb")
        session.encoding = "UTF-8"
        session.storbinary(
            "STOR " + args.image_path + "_" + str(i) + ".jpg", fp=uploadfile
        )
        uploadfile.close()

        pretty_json = json.dumps(server_json)
        print(pretty_json)

        # send result to server
        url = "http://49.173.62.69:3000/add-defect/" + args.parent_code
        headers = {"Content-Type": "application/json"}
        response = requests.post(url, headers=headers, data=pretty_json)

    session.quit()
