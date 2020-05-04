from Rules import tokens

class Converter:
    def __init__(self):
        f = open("map.in", "r")
        self.mapper = {}

        for line in f:
            tok = line.split(",")[0]
            seq = ",".join(line.split(",")[1:])
            self.mapper[tok] = seq.rstrip("\n")
        #print(self.mapper)
    def seq2Lat(self, seq):
        lat = ""
        for s in seq:
            if not (s == 1000 or s == 1001):                
                if self.mapper.get(tokens[ s-1 ]) is None:
                    lat += tokens[s-1]
                else:
                    lat += self.mapper[tokens[ s-1 ]]

        return lat
if __name__ == '__main__':
    #print(tokens.index('comma'))
    c = Converter()
    print(c.seq2Lat([102, 76, 76, 76, 76, 76, 102, 112, 102, 76, 102]))

