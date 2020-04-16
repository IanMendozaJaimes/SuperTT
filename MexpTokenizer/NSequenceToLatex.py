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
            if not (s == 1000 or s == 101):
                lat += self.mapper[tokens[s-1]]
        return lat
if __name__ == '__main__':
    c = Converter()
    print(c.seq2Lat([64, 33, 41, 74, 47, 58, 42, 65, 34]))