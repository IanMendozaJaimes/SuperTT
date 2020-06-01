from NSequenceToLatex import Converter

import os


csv_file = '/Users/ianMJ/Downloads/CROHME_dataset_v5/tokenized.csv'

BEGIN = 1000
END = 1001

c = Converter()

file = open(csv_file, 'r')
info = file.read().split('\n')

tokens = open('expressions.txt', 'w')


for moreInfo in info:

	temp = moreInfo.split(',')
	sequence = temp[1].split(' ')
	end_pos = 0

	for x in range(0, len(sequence)):

		if int(sequence[x]) == END:
			end_pos = x
			break

		sequence[x] = int(sequence[x])

	tokens.write(temp[0] + '$' + c.seq2Lat(sequence[1:end_pos]).replace('\\right ', '').replace('\\left ', '') + '\n')











