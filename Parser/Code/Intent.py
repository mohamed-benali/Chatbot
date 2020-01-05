from Code.Entity import Entity, fromJSON



class Intent(Entity):
    def __init__(self, name='', response='', follows = None, subject = None):
        super().__init__()
        self.name = name
        self.inputs = []
        self.response = response
        self.follows = follows

        self.subject = subject
        #TODO: Is posible that i will have to create another attribute called "behaviour"
        #TODO: To be able to add code automaticaly, but is possible that i wil not need that

    def set_inputs(self, inputs):
        self.inputs = inputs

    def add_input(self, input):
        self.inputs.append(input)

    def set_name(self, name):
        self.name = name

    def set_response(self, response):
        self.response = response

    def set_follows(self, follows):
        self.follows = follows

    def add_follows(self, follows):
        if self.follows != None:
            self.follows.append(follows)

    def set_subject(self, subject):
        self.subject = subject



    def context_name(self, name, type):
        ''' Context tipus *type* '''
        return type + 'CONTEXT_' + name

    def context_intent_name(self, name):
        ''' Context propi del intent '''
        return self.context_name(name, "")

    def context_subject_name(self, subject):
        ''' Context del subjecte del intent '''
        return self.context_name(subject, "SUBJECT_")



    def add_create_context(self, intent_intent, context_name, lifespan):
        intent_intent += '\t' + 'creates context ' + context_name + ' with lifespan ' + str(lifespan) + '\n'
        return intent_intent

    def add_requires_context(self, intent_intent, follows_context_name):
        intent_intent += '\t' + 'requires context ' + follows_context_name + '\n'
        return intent_intent

    def add_intent_file_input(self, intent_intent, input):
        intent_intent += '\t\t' + '"' + input + '"' + '\n'
        return intent_intent




    def add_subject_intent_on_intent_format(self, name, inputs, follows, subject):
        intent_intent = ''

        # File .intent
        intent_intent += 'intent ' + name + ' '

        intent_intent += '{' + '\n'

        if follows != None:
            for follow in follows:
                follows_context_name = self.context_subject_name(follow)
                intent_intent = self.add_requires_context(intent_intent, follows_context_name)

        intent_intent += '\t' + 'inputs {' + '\n'

        for input in inputs:
            intent_intent = self.add_intent_file_input(intent_intent, input)

        intent_intent += '\t' + '}' + '\n'

        intent_intent += '}' + '\n\n'
        return intent_intent

    def add_intent_on_intent_format(self, name, inputs, follows, subject):
        own_context_name = self.context_intent_name(name)
        subject_context_name = self.context_subject_name(subject)

        lifespan_own = 4
        lifespan_subject = lifespan_own

        intent_intent = ''

        # File .intent
        intent_intent += 'intent ' + name + ' '

        intent_intent += '{' + '\n'

        if follows != None:
            for follow in follows:
                follows_context_name = self.context_intent_name(follow)
                intent_intent = self.add_requires_context(intent_intent, follows_context_name)

        intent_intent += '\t' + 'inputs {' + '\n'

        for input in inputs:
            intent_intent = self.add_intent_file_input(intent_intent, input)

        intent_intent += '\t' + '}' + '\n'

        intent_intent = self.add_create_context(intent_intent, own_context_name, lifespan_own)
        intent_intent = self.add_create_context(intent_intent, subject_context_name, lifespan_subject)


        intent_intent += '}' + '\n\n'
        return intent_intent

    def add_intent_on_execution_format(self, name, response):
        # File .execution
        intent_execution = ''
        intent_execution += 'on intent ' + name + ' do' + '\n'
        intent_execution += '\t' + 'ChatPlatform.Reply("'
        intent_execution += response
        intent_execution += '")' + '\n' + '\n'
        return intent_execution

    def parse(self):
        self.intent_intent      = self.add_intent_on_intent_format(self.name, self.inputs, self.follows, self.subject)
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

    # TODO: FUNCIO COPIADA DE LA CLASE INTENT!!!!!
    # TODO: FER QUE NOMES SIGUI UNA FUNCIO QUE CRIDIN TOTS 2
    def context_name(self, name, type):
        ''' Context tipus *type* '''
        return type + 'CONTEXT_' + name

    def context_subject_name(self, subject):
        ''' Context del subjecte del intent '''
        return self.context_name(subject, "SUBJECT_")

    def parse(self):
        subjects = set()
        for intent in self.intents:
            [intent_intent, intent_execution] = intent.parse()
            self.intents_intent += intent_intent
            self.intents_execution += intent_execution

            subjects.add(intent.subject)

        for subject in subjects:
            subject_context_name = self.context_subject_name(subject)
            subject_intent = Intent(name= subject_context_name,
                                    response=subject)

            inputs = ["Who does this task?",
                      "Who does it?",
                      "Who performs this task"]
            follows = [subject]
            self.intents_intent += subject_intent.add_subject_intent_on_intent_format(name=subject_context_name, inputs=inputs,  follows=follows, subject=subject)
            self.intents_execution += subject_intent.add_intent_on_execution_format(name=subject_context_name,
                                                                                    response=subject)


        default_fallback_intent = Intent(   name='Default_Fallback_Intent',
                                            response="Sorry I didn't get it")
        self.intents_execution += default_fallback_intent.add_intent_on_execution_format(name='Default_Fallback_Intent',
                                                                                         response="Sorry I didn't get it")

        return self.intents_intent, self.intents_execution
