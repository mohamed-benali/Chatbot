from Code.Entity import Entity


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


