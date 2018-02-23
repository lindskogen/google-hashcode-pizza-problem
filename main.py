import numpy
import random

def parsePizza(input):
    lines = input.splitlines()
    header = lines[0]
    pizza = lines[1:]
    lines = []
    for l in pizza:
        lines += [list(l)]
    matrix = numpy.matrix(lines)

    headerParts = header.split()
    return {
        "height": int(headerParts[0]),
        "width": int(headerParts[1]),
        "L": int(headerParts[2]),
        "M": int(headerParts[3]),
        "grid": matrix
    }

def isSlice(elementsInSlice, pizza):
    if len(elementsInSlice) > pizza["M"]:
        return False

    countTomato = 0
    countMushroom = 0

    for x in elementsInSlice:
        if(x=="T"):
            countTomato += 1
        elif x=="M":
            countMushroom += 1

        if countTomato >= pizza["L"] and countMushroom >= pizza["L"]:
            return True

    if countTomato < pizza["L"] or countMushroom < pizza["L"]:
            return False

    return True


def sliceShapes(pizza):
    shapes = []

    for x in range(1,pizza["M"]+1):
        for y in range(pizza["M"], 0, -1):
            print("{} {}".format(x,y))
            if pizza["L"]*2 <= x*y and x*y <= pizza["M"]:
                shapes.append((x,y))
    return shapes

            
def stamp(pos, pizza, shapeDimensions):
    res = []

    for x in range(pos[0], pos[0]+shapeDimensions[0]):
        for y in range(pos[1], pos[1]+shapeDimensions[1]):
            res.append(pizza["grid"][y][x])

    return res

def main():
    content = open("example.in", "r")
    pizza = parsePizza(content.read())
    print(pizza)
    print(isSlice(["T","M","T","M","T","M"], pizza))
    print(sliceShapes(pizza))

    
    shapes = sliceShapes(pizza)
    for shapeDimensions in shapes:
        x = random.randrange(0, pizza["width"])
        y = random.randrange(0, pizza["height"])
        stamped = stamp((x,y), pizza, shapeDimensions)
        if isSlice(stamped):
            pass 



    

if __name__ == "__main__":
    main()
