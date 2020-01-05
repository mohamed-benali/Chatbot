from Code.Entity import Entity, fromJSON



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
        context_name = 'CONTEXT_' + name
        lifespan = 3

        if self.follows != None:
            follows_name = 'CONTEXT_' + follows

        # File .intent
        self.intent_intent += 'intent ' + name + ' '

        self.intent_intent += '{' + '\n'

        if self.follows != None:
            self.intent_intent += '\t' + 'requires context ' + follows_name  + '\n'

        self.intent_intent += '\t' + 'inputs {' + '\n'

        for input in inputs:
            self.intent_intent += '\t\t' + '"' + input + '"' + '\n'

        self.intent_intent += '\t' + '}' + '\n'

        self.intent_intent += '\t' + 'creates context ' + context_name + ' with lifespan ' + str(lifespan) + '\n'

        self.intent_intent += '}' + '\n\n'
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
