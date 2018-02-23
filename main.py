import numpy

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
    

def main():
    content = open("example.in", "r")
    pizza = parsePizza(content.read())
    print(pizza)
    print(isSlice(["T","M","T","M","T","M"], pizza))
    

if __name__ == "__main__":
    main()
