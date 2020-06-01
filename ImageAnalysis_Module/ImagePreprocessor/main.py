import os
from scriptCV import ImageAlgorithm, ImageProcessor

INP_FOLDER = "/Users/ianMJ/Documents/ESCOM/tt/im2markup/data/sample"
OUT_FOLDER = "/Users/ianMJ/Documents/ESCOM/tt/im2markup/data/sample/preprocessed"


image_sizes = [
  'images'
]


def processFolder():

	for image_size in image_sizes:

		input_folder = INP_FOLDER + '/' + image_size
		directory = OUT_FOLDER + '/' + image_size

		os.makedirs(directory)

		images =  os.listdir(input_folder)
		for im in images:
			print(f"{im}")
			ip = ImageProcessor(f"{input_folder}/{im}")
			ip.processBinarization()


			ip.saveImage(f"{directory}/{im}")


		
if __name__ == '__main__':
	processFolder()
	#ip = ImageProcessor("/home/qapolo/Escritorio/0051a61b-8d42-4e39-85ed-f9170d03b973.jpeg")
	#ip.processBinarization()
	#ip.saveImage("/home/qapolo/Escritorio/output.jpeg")






