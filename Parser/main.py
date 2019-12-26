from Parser import Parser



def main():
    file_name = "input.txt"
    file_read_permission = 'r+'
    file_write_permission = 'w+'

    parser = Parser(file_name, file_read_permission, file_write_permission)

    content = parser.get_content()
    for state in content:
        print(state)

    #open(file_name, file_write_permission).write("4 4 ")















if __name__ == "__main__":
    main()
