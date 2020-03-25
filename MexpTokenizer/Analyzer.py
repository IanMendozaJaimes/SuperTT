from ply.lex import lex
import Rules
import sys

class LexicalAnalyzer:

	def __init__(self):
		self.lexer = lex(module=Rules)

	def analize(self, cadena):
		arr = []
		self.lexer.input(cadena)

		token = self.lexer.token()
		arr.append(Rules.tokens.index(token.type) )
		while token is not None:
			print(token)
			arr.append(Rules.tokens.index(token.type) )
			token = self.lexer.token()
		return arr
			
		
if __name__ == '__main__':
	al = LexicalAnalyzer()

	if int(sys.argv[1]) == 0:
		f = open('file.txt','r')
		al.analize(f.read())
	else:
		while True:
			line = input('Ingresa una cadena: ')
			print(al.analize(line))
			if input('otra? s/N') != 's':
				break

