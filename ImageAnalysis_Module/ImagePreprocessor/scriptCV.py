import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt
from skimage import morphology
import numpy as np
import skimage
import os, sys

from skimage.filters import (threshold_otsu, threshold_niblack,
                             threshold_sauvola)
import skimage.io							 
from skimage.viewer import ImageViewer
from skimage.transform import resize

from enum import Enum

class ImageAlgorithm(Enum):
	OTSU = 1
	SAUVOLA = 2
class ImageProcessor:

	def __init__(self, pathImg):
		self.pathImg = pathImg
		self.img = cv.imread(pathImg, cv.IMREAD_GRAYSCALE)

	def processBinarization(self, algorithm = ImageAlgorithm.SAUVOLA):
		if algorithm == ImageAlgorithm.SAUVOLA:
			image = skimage.io.imread(fname=self.pathImg, as_gray=True)
			thresh_sauvola = threshold_sauvola(image, window_size=51)
			self.binary_sauvola = image > thresh_sauvola
		else:
			pass
	def saveImage(self, name, algorithm = ImageAlgorithm.SAUVOLA):
		if algorithm == ImageAlgorithm.SAUVOLA:
			try:
				image_resized = resize(self.binary_sauvola, (480, 640), anti_aliasing=False)
				plt.imsave(name, image_resized, cmap = plt.cm.gray)
			except Exception as e:
				print(e)
		else:
			pass

	"""
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
	"""