def loadSettings(path):
    f = open(path, "r")
    allstring = [line for line in f.read().split("\n") if (len(line) > 2)]
    f.close()
    prop = {}
    for line in allstring:
        if "#" not in line:
            l = line.split("=")
            prop[l[0]] = l[1]
    return prop

def persistSettings(path, text):
    f = open(path, "w")
    f.write(text)
    f.close()
    
