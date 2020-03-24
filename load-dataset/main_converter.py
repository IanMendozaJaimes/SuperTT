import inkml2img, glob, os
import sys
import os
import shutil
import xml.etree.ElementTree as ET
import csv
from random import randint

defult_folder_dataset = "./CROHME"
defult_folder_converted = "converted_expressions"
class DatasetConverter:
	def __init__(self):
		if not os.path.exists(defult_folder_converted):
			os.mkdir(defult_folder_converted)

	def inkml2tag(self,  inkml_path):
		tree = ET.parse(inkml_path)
		root = tree.getroot()
		prefix = "{http://www.w3.org/2003/InkML}"
		GT_tag = [GT for GT in root.findall(prefix + 'annotation') if GT.attrib == {'type': 'truth'}]
		cadena_random = ' '.join([str(randint(0,10)) for a in GT_tag[0].text.split(' ')])
		return cadena_random

	def make_conversion(self):
		try:
			files =	os.listdir(defult_folder_dataset)
		except:
			print( ("*" * 20) + "This folder does not exists" + ("*" * 20))			
		#standard_name = "expression"

		#count = 0
		self.total_converted = len(files)
		f = open('training.csv', 'w')
		with f:
			writer = csv.writer(f)
			writer.writerow(['imagen', 'expresion'])
			for filename in files:			
				#inkml2img.inkml2img(defult_folder_dataset + "/" + filename, defult_folder_converted + "/"+ filename[0: -4] + str(count) + ".png")

				inkml2img.inkml2img(defult_folder_dataset + "/" + filename, defult_folder_converted + "/"+ filename[0: -4] + ".png")
				writer.writerow([defult_folder_converted + "/"+ filename[0: -4] + ".png", self.inkml2tag(defult_folder_dataset + "/"+ filename)])
				#count += 1
	def splitIntoFolders(self,testPercentage, trainPercentage):

		if not os.path.exists("test"):
			os.mkdir("test")

		if not os.path.exists("train"):
			os.mkdir("train")
		
		if not os.path.exists("validate"):
			os.mkdir("validate")

		try:
			files =	os.listdir(defult_folder_converted)
		except:
			print( ("*" * 20) + "This folder does not exists" + ("*" * 20))	
		total = len(files)
		test_size = testPercentage * total // 100
		train_size = trainPercentage * total // 100
		total = total - test_size - train_size

		for i in range(0, test_size):
			shutil.copyfile(defult_folder_converted+"/"+files[i], "test/"+files[i])
		
		for i in range(test_size, train_size + test_size):
			shutil.copyfile(defult_folder_converted+"/"+files[i], "train/"+files[i])

		for i in range(train_size+test_size, total + train_size+test_size):
			shutil.copyfile(defult_folder_converted+"/"+files[i], "validate/"+files[i])

def main():
	print("\nWARNING! make sure to have a valid folder with dataset in inkml format:")
	print("-" * 10)
	print("This folder should only contain inkml files")
	print("default folder name for CROHME dataset: [CROHME] press enter to agree or specify yours here:")
	print("-" * 10)
	curr_dir = input()

	if len(curr_dir) > 0:
		defult_folder_dataset = curr_dir
	print("converted mathematical expressions will be stored in [converted_expressions] folder ")

	dc = DatasetConverter()
	dc.make_conversion()
	#dc.splitIntoFolders(int(sys.argv[1]), int(sys.argv[2]))


if __name__ == "__main__":
	main()
	#print(inkml2tag("test.inkml"))