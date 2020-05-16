
import os
from scriptCV import ImageProcessor
import time

###################### DO NOT DELETE THIS CHUNK ##################3
BASE_DIR = "../supertt/media/proyectos"
filename = "imagespath.dat"

def process_all():
	tmp_path = BASE_DIR + "/" + filename
	if not os.path.exists("images"):
		os.mkdir("images")
	while True: ######## CONSTANTLY CEHCK FOR NEW FILES
		if os.path.exists(tmp_path):
			print("found file...")
			with open(tmp_path, "r") as file_data:
				v = file_data.read()
				#new_file = open(BASE_DIR + "/" + v.rstrip("\n"), "r")
				
				print(v)
				print("full path: "+BASE_DIR + "/" + v.rstrip("\n"))
				ip = ImageProcessor(BASE_DIR + "/" + v.rstrip("\n"))
				ip.GaussianTransform()
				ip.saveImage("images/" + "_".join( str( v.rstrip("\n").split(".")[0]  ).split("/") )+ ".png")
				
				#new_file.close()
			os.remove(tmp_path)
		time.sleep(1)
############################################################3
if __name__ == '__main__':
	process_all()
		
	
