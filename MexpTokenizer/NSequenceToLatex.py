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
                    if self.mapper.get(tokens[ s-1 ]) is None:
                        lat += tokens[s-1]
                    else:
                        lat += self.mapper[tokens[ s-1 ]]

        return lat
if __name__ == '__main__':
    print(tokens.index('comma'))
    c = Converter()
    print(c.seq2Lat([117, 51, 51, 61, 61, 51]))
