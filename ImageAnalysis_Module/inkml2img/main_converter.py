import inkml2img, glob, os

import os

defult_folder_dataset = "./CROHME"
defult_folder_converted = "converted_expressions"
class DatasetConverter:
	def __init__(self):
		if not os.path.exists(defult_folder_converted):
			os.mkdir(defult_folder_converted)

	def make_conversion(self):
		try:
			files =	os.listdir(defult_folder_dataset)
		except:
			print( ("*" * 20) + "This folder does not exists" + ("*" * 20))			
		#standard_name = "expression"

		#count = 0
		for filename in files:
			#inkml2img.inkml2img(defult_folder_dataset + "/" + filename, defult_folder_converted + "/"+ filename[0: -4] + str(count) + ".png")
			inkml2img.inkml2img(defult_folder_dataset + "/" + filename, defult_folder_converted + "/"+ filename[0: -4] + ".png")
			#count += 1

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

if __name__ == "__main__":
	main()
