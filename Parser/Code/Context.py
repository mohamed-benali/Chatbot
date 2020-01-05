from Code.Entity import Entity


class Context(Entity):
    def __init__(self, name, lifespan=3):
        super().__init__()
        self.name = name
        self.lifespan = lifespan
