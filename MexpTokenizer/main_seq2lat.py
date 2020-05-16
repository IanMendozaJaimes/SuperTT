from NSequenceToLatex import*
import sys

import http.client
import urllib
from urllib import request, parse
import json
import socket

def sendTranslation(idPro, idTrans, translation, tokenVal):
    body_content = {"idproyecto": idPro, "traduccion": translation}
    header = {"Authorization":"Token " + tokenVal}
    
    ip_address = sys.argv[3]

    data = parse.urlencode(body_content).encode()
    req = urllib.request.Request(url='http://'+ ip_address +'/traducciones/' + idTrans, headers=header, method='PUT', data = data)
    res = urllib.request.urlopen(req)

if __name__ == '__main__':
    #print(tokens.index('comma'))
    c = Converter()

    if len(sys.argv) == 0:
        print("No arguments passed")
        
    seq = sys.argv[1].split(",")
    seq = [int(e) for e in seq]
    translation = c.seq2Lat(seq)
    print(translation)

    #split idusuario_idproy_idtrad
    data_req = sys.argv[2].split("_")
    print(data_req)
    token = ""
    
    try:
        token_file = open("Integration_scripts/token.in", "r")
    except:
        token_file = open("../Integration_scripts/token.in", "r")
    token = token_file.read().rstrip("\n")
    sendTranslation(data_req[1], data_req[2], translation, token)    
    
    #print(c.seq2Lat([111, 72, 60, 51, 14, 72, 60, 52, 14, 72, 60, 53, 14, 72, 60, 54, 47, 100, 112]))
