class Intent:
    def __init__(self):
        print("")

class Parser:
    def __init__(self, file_name, file_read_permission = 'r+', file_write_permission = 'w+'):
        self.file_name = file_name
        self.file_read_permission = file_read_permission
        self.file_write_permission = file_write_permission

        self.content = open(self.file_name, file_read_permission).read()


    def get_content(self):
        return self.content



