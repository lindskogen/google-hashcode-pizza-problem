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
        "height": headerParts[0],
        "width": headerParts[1],
        "L": headerParts[2],
        "M": headerParts[3],
        "pizza": matrix
    }

def main():
    content = open("example.in", "r")
    pizza = parsePizza(content.read())
    print(pizza)
    

if __name__ == "__main__":
    main()
