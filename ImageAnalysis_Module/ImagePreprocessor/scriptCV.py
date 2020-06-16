import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt
from skimage import morphology
import numpy as np
import skimage
import os, sys

from skimage.filters import (threshold_otsu, threshold_niblack,
                             threshold_sauvola, gaussian)
import skimage.io							 
from skimage.viewer import ImageViewer
from skimage.transform import resize

from skimage.morphology import erosion
from skimage.morphology import disk, skeletonize,thin
from skimage.util import invert
from skimage.morphology import convex_hull_image

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
			thresh_sauvola = threshold_sauvola(image, window_size=81)
			self.binary_sauvola = image > thresh_sauvola
			"""
			self.binary_sauvola = invert(self.binary_sauvola)
			chull = convex_hull_image(self.binary_sauvola)
			[rows, columns] = np.where(chull)
			EPS = 50
			row1 = min(rows) - EPS
			row2 = max(rows) + EPS
			col1 = min(columns) - EPS
			col2 = max(columns) + EPS
			
			self.binary_sauvola = self.binary_sauvola[row1:row2, col1:col2]
			self.binary_sauvola = invert(self.binary_sauvola)
			"""
			self.binary_sauvola = invert(self.binary_sauvola)
			#selem = disk(6)
			
			self.binary_sauvola = thin(self.binary_sauvola, np.int(15))
			self.binary_sauvola = np.invert(self.binary_sauvola)

			#thresh_sauvola = threshold_sauvola(self.binary_sauvola, window_size=21)
			#self.binary_sauvola = self.binary_sauvola > thresh_sauvola

			self.binary_sauvola = erosion(self.binary_sauvola)

			self.binary_sauvola = gaussian(self.binary_sauvola)
			
		elif algorithm == ImageAlgorithm.OTSU:
			self.otsuBinarization()


			

	def otsuBinarization(self):
		blur = cv.GaussianBlur(self.img,(5,5),0)
		ret3,th3 = cv.threshold(blur,0,255,cv.THRESH_BINARY+cv.THRESH_OTSU)
		self.imgTrans = th3

		As = 640 * 480
		At = self.imgTrans.shape[0] * self.imgTrans.shape[1]

		scale_percent = (As * 100 // At)
		
		width = int( self.img.shape[1] * scale_percent / 100 )
		height = int( self.img.shape[0] * scale_percent / 100 )

		self.imgTrans = cv.resize(self.imgTrans, (640, 480))


	def saveImage(self, name, algorithm = ImageAlgorithm.SAUVOLA, res = False):

		"""
		import numpy as np
		[rows, columns] = np.where(chull)
		row1 = min(rows)
		row2 = max(rows)
		col1 = min(columns)
		col2 = max(columns)
		newImage = original[row1:row2, col1:col2]
		"""
		if algorithm == ImageAlgorithm.SAUVOLA:
			try:
				if res:
					self.binary_sauvola = resize(self.binary_sauvola, (480, 640), anti_aliasing=False)
				
				plt.imsave(name, self.binary_sauvola, cmap = plt.cm.gray)
			except Exception as e:
				print(e)
		elif algorithm == ImageAlgorithm.OTSU:
			try:
				cv.imwrite(name, self.imgTrans)
			except Exception as e:
				print(e)
		
