from Code.Entity import toJSON, fromJSON
from Code.Parser import Parser
from Code.Intent import Intent


def testing(file_read_permission = 'r+', file_write_permission = 'w+', ):

    intent = Intent(name="Test", response="Intent: Test", subject="Empleat")
    intent.add_input("testing")

    follows = ["Test"]
    intent2 = Intent(name="Test2", response="Intent: Test2", follows=follows, subject="Empleat")
    inputs = ["testing"]
    intent2.set_inputs(inputs)


    follows = ["Test2"]
    intent3 = Intent(name="Test3", response="Intent: Test3", follows=follows, subject="Departament")
    inputs = ["testing"]
    intent3.set_inputs(inputs)

    follows = ["Test3"]
    intent4 = Intent(name="Test4", response="Intent: Test4", follows=follows, subject="Departament")
    inputs = ["testing"]
    intent4.set_inputs(inputs)

    # TODO: Fer un FOR per crear N intents automaticament
    intents = []
    intents.append(intent)
    intents.append(intent2)
    intents.append(intent3)
    intents.append(intent4)

    json_intent = toJSON(intents)

    open("./Data/intents.json", file_write_permission).write(json_intent)

    aux = open("./Data/intents.json", file_read_permission).read()

    # print(aux)

    intent3 = fromJSON(aux)

    for inten in intent3:
        inten.add_input("testN")

    print(toJSON(intent3))



def main():
    input_Data_directory_path = "./Data/"

    input_file_path = input_Data_directory_path + "intents.json"
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


    testing()






if __name__ == "__main__":
    main()
