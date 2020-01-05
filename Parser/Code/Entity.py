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