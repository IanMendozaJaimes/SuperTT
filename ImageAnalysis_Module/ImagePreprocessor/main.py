import os
from scriptCV import ImageAlgorithm, ImageProcessor

INP_FOLDER = "/home/qapolo/Escritorio/SuperTT/ImageAnalysis_Module/inkml2img/converted_expressions"
OUT_FOLDER = "/home/qapolo/Escritorio/CHROME_DATASET_sauvola"
def processFolder():
	images =  os.listdir(INP_FOLDER)
	for im in images:
		print(f"{im}")
		ip = ImageProcessor(f"{INP_FOLDER}/{im}")
		ip.processBinarization()
		ip.saveImage(f"{OUT_FOLDER}/{im}")
		
if __name__ == '__main__':
	processFolder()
	#ip = ImageProcessor("/home/qapolo/Escritorio/0051a61b-8d42-4e39-85ed-f9170d03b973.jpeg")
	#ip.processBinarization()
	#ip.saveImage("/home/qapolo/Escritorio/output.jpeg")
	
