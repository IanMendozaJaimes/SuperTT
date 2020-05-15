from Rules import tokens
START_TAG_NUM = 1000
END_TAG_NUM = 1001
class Converter:
    def __init__(self):
        try:
            f = open("map.in", "r")
        except:
            f = open("../MexpTokenizer/map.in", "r")
        self.mapper = {}

        for line in f:
            tok = line.split(",")[0]
            seq = ",".join(line.split(",")[1:])
            self.mapper[tok] = seq.rstrip("\n")
        #print(self.mapper)
    def seq2Lat(self, seq):
        lat = ""
        for s in seq:
            if not (s == START_TAG_NUM or s == END_TAG_NUM):                
                if self.mapper.get(tokens[ s-1 ]) is None:
                    lat += (tokens[s-1] + " ")
                else:
                    lat += (self.mapper[tokens[ s-1 ]] + " ")

        return lat