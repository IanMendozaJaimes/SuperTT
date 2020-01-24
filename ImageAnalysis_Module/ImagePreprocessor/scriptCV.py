import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt


class ImageProcessor:

	def __init__(self, pathImg):
		self.img = cv.imread(pathImg, 0)
		self.img = cv.medianBlur(self.img, 5)

	def GaussianTransform(self):
		self.imgTrans = cv.adaptiveThreshold(self.img, 255, cv.ADAPTIVE_THRESH_GAUSSIAN_C,\
            cv.THRESH_BINARY, 23, 2)

	def saveImage(self, name):
		cv.imwrite(name, self.imgTrans)

	def comparisonPlot(self):
		titles = ['Original Image','Threshold']
		images = [self.img, self.imgTrans]

		for i in range(0, 2):
		    plt.subplot(2,1,i+1),plt.imshow(images[i],'gray')
		    plt.title(titles[i])
		    plt.xticks([]),plt.yticks([])

		plt.show()


if __name__ == '__main__':
	ip = ImageProcessor("unnamed.jpg")
	ip.GaussianTransform()
	ip.saveImage('transformada.jpg')
	#ip.comparisonPlot()
