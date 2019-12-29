from Parser import Parser, Intent, Entity, toJSON, fromJSON

import json
import jsonpickle


def main():
    input_file_path = "intents.json"
    file_read_permission = 'r+'
    file_write_permission = 'w+'

    chatbot_src_path = "../xatkit/Chatbot/src/"
    output_intent_path      = chatbot_src_path + "GreetingsBot.intent"
    output_execution_path   = chatbot_src_path + "GreetingsBot.execution"



    parser = Parser(input_file_path,
                    output_intent_path,
                    output_execution_path,
                    file_read_permission,
                    file_write_permission)

    parser.init()
    parser.parse()


    intent = Intent(name="Test", response="Intent: Test")
    intent.add_input("testing")

    intent2 = Intent(name="Test2", response="Intent: Test2", follows="Test")
    intent2.add_input("testing")

    intent3 = Intent(name="Test3", response="Intent: Test3", follows="Test2")
    intent3.add_input("testing")

    intent4 = Intent(name="Test4", response="Intent: Test4", follows="Test3")
    intent4.add_input("testing")

    # TODO: Fer un FOR per crear N intents automaticament
    intents = []
    intents.append(intent)
    intents.append(intent2)
    intents.append(intent3)
    intents.append(intent4)


    json_intent = toJSON(intents)

    open("intents.json", file_write_permission).write(json_intent)

    aux = open("intents.json", file_read_permission).read()

    #print(aux)

    intent3 = fromJSON(aux)

    for inten in intent3:
        inten.add_input("testN")

    print(toJSON(intent3))






if __name__ == "__main__":
    main()
