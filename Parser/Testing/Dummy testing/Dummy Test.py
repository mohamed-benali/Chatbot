import numpy as np


def list_wrapper(size, value):
    return [value]*size

def matrix2(rows, cols, value):
    return list_wrapper(rows, list_wrapper(cols, value))

def matrix3(rows, cols, depth, value):
    #return list_wrapper(rows, list_wrapper(cols, list_wrapper(depth,value)))
    return list_wrapper(rows, matrix2(cols, depth, value))

def create_list():
    size = 3
    initialize_value = '2'
    list = list_wrapper(size, initialize_value)
    print(list)

def create_matrix():
    rows = 4
    cols = 4
    initialize_value = "hola"
    mat2 = matrix2(rows,cols,initialize_value)
    print(np.matrix(mat2))

    print()

    depth = 3
    mat3 = matrix3(rows, cols, depth,initialize_value)
    print(mat3)

def a():
    x = set()
    x.add("a")
    print(x)
    n = set([0, 1, 2, 3, 4])
    print(n)

def main():
    create_matrix()










if __name__ == "__main__":
    main()
