from Rules import tokens
START_TAG_NUM = 1000
END_TAG_NUM = 1001
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
            if not (s == START_TAG_NUM or s == END_TAG_NUM):                
                if self.mapper.get(tokens[ s-1 ]) is None:
                    lat += (tokens[s-1] + " ")
                else:
                    lat += (self.mapper[tokens[ s-1 ]] + " ")

        return lat
if __name__ == '__main__':
    #print(tokens.index('comma'))
    c = Converter()
    print(c.seq2Lat([74, 61, 38, 77, 62, 73, 68, 33, 12, 74, 77, 34, 62, 73, 14, 33, 77, 34, 62, 52, 47, 74, 62, 53, 73, 34, 113]))
