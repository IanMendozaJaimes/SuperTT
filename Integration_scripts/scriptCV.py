import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt
from skimage import morphology
import numpy as np
import skimage
import os, sys

class ImageProcessor:

	def __init__(self, pathImg):
		self.img = cv.imread(pathImg, 0)
		#self.img = cv.medianBlur(self.img, 9)

	def GaussianTransform(self):
		#self.imgTrans = cv.adaptiveThreshold(self.img, 255, cv.ADAPTIVE_THRESH_GAUSSIAN_C,\
            #cv.THRESH_BINARY, 23, 2)
		#cv.imwrite("transformed.png", self.imgTrans)

		#kernel = np.ones((5,5),np.uint8)

		#opening2 = cv.morphologyEx(self.imgTrans, cv.MORPH_OPEN, kernel)

		#otsu's 2
		#ret3,th3 = cv.threshold(self.img,0,255,cv.THRESH_BINARY+cv.THRESH_OTSU)

		# Otsu's thresholding after Gaussian filtering
		print("processing image...")
		blur = cv.GaussianBlur(self.img,(5,5),0)
		ret3,th3 = cv.threshold(blur,0,255,cv.THRESH_BINARY+cv.THRESH_OTSU)
		self.imgTrans = th3

		As = 640 * 480
		At = self.imgTrans.shape[0] * self.imgTrans.shape[1]

		scale_percent = (As * 100 // At)
		print(scale_percent)
		
		width = int( self.img.shape[1] * scale_percent / 100 )
		height = int( self.img.shape[0] * scale_percent / 100 )

		print(str(width)+"," + str(height))
		self.imgTrans = cv.resize(self.imgTrans, (640, 480))

		#cv.imwrite("out4.png", opening2)
	def getImage(self):
		return self.imgTrans
	def saveImage(self, name, path = None):
		if path == None:
			cv.imwrite(name, self.imgTrans)
		else:
			cv.imwrite(path+"/"+name, self.imgTrans)
		print("saved")

	def comparisonPlot(self):
		titles = ['Original Image','Threshold']
		images = [self.img, self.imgTrans]

		for i in range(0, 2):
		    plt.subplot(2,2,i+1),plt.imshow(images[i],'gray')
		    plt.title(titles[i])
		    plt.xticks([]),plt.yticks([])

		plt.show()

"""
if __name__ == '__main__':
	dirs = os.listdir("./")

	# This would print all the files and directories
	for file in dirs:
		if len(file.split(".")) > 1 and file.split(".")[1] == "jpg":
			ip = ImageProcessor(file)
			ip.GaussianTransform()
			ip.saveImage("out_"+str(file.split(".")[0])+".png")
			#ip.comparisonPlot()
	#ip.comparisonPlot()
"""
