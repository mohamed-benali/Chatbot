
from Code.Entity import Entity
from Code.Intent import Intents
from Code.Imports import Imports











class Parser(Entity):

    """ Initialize Parser: Reads file and saves content"""
    def __init__(self, input_file_path, output_intent_path, output_execution_path, file_read_permission = 'r+', file_write_permission = 'w+', ):
        super().__init__()
        self.file_name = input_file_path

        self.file_read_permission = file_read_permission
        self.file_write_permission = file_write_permission

        self.output_intent_path     = output_intent_path
        self.output_execution_path  = output_execution_path

        self.content = open(self.file_name, self.file_read_permission).read() # Content is a list of Intents

        self.imports = Imports() # HARDCODED FOR NOW
        self.intents = Intents(self.content)




    def get_content(self):
        return self.content

    def set_content(self, content):
        self.content = content

    def get_intents(self):
        return self.intents

    def set_intents(self, intents):
        self.intents = intents


    def init(self):
        ''' Initialize and loads the intents'''
        self.intents.init()
        self.imports.init()

    def parse(self):
        '''
            X_intent    are for the .intent file.
            X_execution are for the .execution file.
        '''


        [imports_intent, imports_execution] = self.imports.parse()
        [intents_intent, intents_execution] = self.intents.parse()

        INTENT = imports_intent + intents_intent
        open(self.output_intent_path, self.file_write_permission).write(INTENT)

        EXECUTION = imports_execution + intents_execution
        open(self.output_execution_path, self.file_write_permission).write(EXECUTION)



