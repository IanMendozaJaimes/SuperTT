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

    print(c.seq2Lat([111, 72, 60, 51, 14, 72, 60, 52, 14, 72, 60, 53, 14, 72, 60, 54, 47, 100, 112]))

    print(c.seq2Lat([111, 13, 72, 45, 13, 75, 45, 13, 63, 32, 72, 64, 33, 112])

