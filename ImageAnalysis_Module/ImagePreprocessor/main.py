from scriptCV import ImageAlgorithm, ImageProcessor

if __name__ == '__main__':
	
	ip = ImageProcessor("path_image")
	ip.processBinarization()
	ip.saveImage("path_to_save")
	
