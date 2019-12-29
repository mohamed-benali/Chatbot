import json
import jsonpickle


def toJSON(object, unpicklable=True): # Si un unpicklable es False, llavors els "py/object": "Parser.Intent" NO apareix i no es pot recupera
    # la clase. Tot i que es posible que no sigui neccessari
    return json.dumps(json.loads(jsonpickle.encode(object, unpicklable=unpicklable)), indent=4, sort_keys=True)

def fromJSON(json_value):
    result = jsonpickle.decode(json_value)
    return result

# Class created in order to have a common superclass where all my class will inherit
class Entity:
    def __init__(self):
        """do nothing for now"""

    def toJSON(self,  unpicklable=True):
        return json.dumps(json.loads(jsonpickle.encode(self, unpicklable=unpicklable)), indent=4, sort_keys=True)#json.dumps(self, default=lambda o: o.__dict__, sort_keys=True, indent=4)

    def fromJSON(self, json_value):
        result = jsonpickle.decode(json_value)
        return result

class Intent(Entity):
    def __init__(self, name='', response='', follows = None):
        super().__init__()
        self.name = name
        self.inputs = []
        self.response = response
        self.follows = follows
        #TODO: Is posible that i will have to create another attribute called "behaviour"
        #TODO: To be able to add code automaticaly, but is possible that i wil not need that

        self.intent_intent = ''
        self.intent_execution = ''

    def add_input(self, input):
        self.inputs.append(input)

    def set_name(self, name):
        self.name = name

    def set_response(self, response):
        self.response = response

    def set_follows(self, follows):
        self.follows = follows

    def add_intent_on_intent_format(self, name, inputs, follows):
        # File .intent
        self.intent_intent += 'intent ' + name + ' '
        if self.follows != None:
            self.intent_intent += "follows" + ' ' + follows + ' '

        self.intent_intent += '{' + '\n'
        self.intent_intent += '\t' + 'inputs {' + '\n'

        for input in inputs:
            self.intent_intent += '\t\t' + '"' + input + '"' + '\n'

        self.intent_intent += '\t' + '}' + '\n'
        self.intent_intent += '}' + '\n'
        return self.intent_intent

    def add_intent_on_execution_format(self, name, response):
        # File .execution
        self.intent_execution += 'on intent ' + name + ' do' + '\n'
        self.intent_execution += '\t' + 'ChatPlatform.Reply("'
        self.intent_execution += response
        self.intent_execution += '")' + '\n' + '\n'
        return self.intent_execution

    def parse(self):
        self.intent_intent      = self.add_intent_on_intent_format(self.name, self.inputs, self.follows)
        self.intent_execution   = self.add_intent_on_execution_format(self.name, self.response)

        return self.intent_intent, self.intent_execution






class Intents(Entity):
    def __init__(self, content):
        ''' *content* is a .json containing intents. The JSON is prepared to be converted to a class '''
        super().__init__()
        self.intents = []
        self.content = content

        self.intents_intent = ''
        self.intents_execution = ''

    def init(self):
        self.intents = fromJSON(self.content)  # Array de intents

    def parse(self):
        for intent in self.intents:
            [intent_intent, intent_execution] = intent.parse()
            self.intents_intent += intent_intent
            self.intents_execution += intent_execution

        default_fallback_intent = Intent(   name='Default_Fallback_Intent',
                                            response="Sorry I didn't get it")
        self.intents_execution += default_fallback_intent.add_intent_on_execution_format(name='Default_Fallback_Intent',
                                                                                         response="Sorry I didn't get it")

        return self.intents_intent, self.intents_execution



class Imports(Entity):
    '''Imports: HARDCODED FOR NOW'''

    def __init__(self):
        super().__init__()
        self.imports_intent = ''
        self.imports_execution = ''


    def init(self):
        self.imports_intent += "Library GreetingsBotLibrary" + '\n'  + '\n'

        self.imports_execution += 'import library "GreetingsBot-Extended/src/GreetingsBot.intent" as GreetingsBotLib' + '\n'
        self.imports_execution += 'import library "CoreLibrary"' + '\n'
        self.imports_execution += 'import platform "ChatPlatform"' + '\n' + '\n'

        self.imports_execution += 'use provider ChatPlatform.ChatProvider' + '\n' + '\n'

    def parse(self):
        return self.imports_intent, self.imports_execution





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



