from ply.lex import lex
import Rules
import sys

def toString(arr):
	s = ""
	for elem in arr:
		s += (str(elem)+" ")
	return s
class LexicalAnalyzer:

	def __init__(self):
		self.lexer = lex(module=Rules)
	#deprecated, removed later
	def analize(self, cadena):
		arr = []
		self.lexer.input(cadena)

		token = self.lexer.token()
		while token is not None:
			print(token)
			arr.append(Rules.tokens.index(token.type) )
			token = self.lexer.token()
		return arr

	def tokenizeDataset(self, file):

		fileTokenized = open("tokenized.csv", "w")

		for line in file:
			
			self.lexer.input(line.split(",")[1])
			#print(line.split(",")[1])
			#print(repr(line.split(",")[1]))
			token = self.lexer.token()
			arr = []
			while token is not None:
				print(token)
				arr.append(Rules.tokens.index(token.type) )
				token = self.lexer.token()
			
			fileTokenized.write(line.split(",")[0] + "," + toString(arr) + "\n")

		fileTokenized.close()

			
		
if __name__ == '__main__':
	
	al = LexicalAnalyzer()
	"""
	f = open("training.csv", "r")
	al.tokenizeDataset(f)
	f.close()"""
	

	if int(sys.argv[1]) == 0:
		f = open('file.txt','r')
		al.analize(f.read())
	else:
		while True:
			line = input('Ingresa una cadena: ')
			print(al.analize(line))
			if input('otra? s/N') != 's':
				break

	