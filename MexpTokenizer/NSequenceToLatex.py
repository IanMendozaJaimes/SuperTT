from Rules import tokens

class Converter:
    def __init__(self):
        f = open("map.in", "r")
        self.mapper = {}

        for line in f:
            tok, seq = line.split(",")
            self.mapper[tok] = seq.rstrip("\n")
        #print(self.mapper)
    def seq2Lat(self, seq):
        lat = ""
        for s in seq:
            if not (s == 1000 or s == 1001):
                if s == 40: #if s == comma
                    lat += ","
                else:
                    lat += self.mapper[tokens[ s-1 ]]

        return lat
if __name__ == '__main__':
    print(tokens.index('comma'))
    c = Converter()
    print(c.seq2Lat([51, 51, 61, 61, 51, 58, 46, 12, 40, 11, 40, 51, 41, 40, 51, 51, 40, 12, 40, 53, 95, 41, 33, 41]))
