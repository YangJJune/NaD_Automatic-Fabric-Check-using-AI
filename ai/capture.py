import os
import ftplib
import argparse
import requests
import json

from datetime import datetime

parser = argparse.ArgumentParser()
parser.add_argument("id")
args = parser.parse_args()

timestamp_format = "%Y%m%d_%H%M%S"
str = datetime.now().strftime(timestamp_format)
os.system('libcamera-jpeg -t 1000 -o '+ str+'.jpg')

session = ftplib.FTP(host='49.173.62.69')
session.set_pasv(False)

session.login(user="nad_ftp",passwd="1234")

uploadfile = open('./'+str+'.jpg',mode='rb')

session.encoding='UTF-8'
session.storbinary('STOR ' + str + '.jpg',fp=uploadfile)
uploadfile.close()
session.quit()

print('complete sending file '+str)

server_json= {
		"image_path": str+'.jpg'
}

pretty_json = json.dumps(server_json)
url = "http://49.173.62.69:3000/add-fabric/"+args.id
headers = {"Content-Type": "application/json"}
response = requests.post(url, headers=headers, data=pretty_json)
print(response)

os.system('python /home/nad/NaD_Automatic-Fabric-Check-using-AI/ai/inference.py ./'+str+'.jpg '+args.id)
