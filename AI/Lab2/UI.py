# -*- coding: utf-8 -*-
from Controller import Controller

class UI:
    def __init__(self, cntrl):
        self.cntrl = Controller(4)
        
    def menu(self):
        s = ''
        s += "0. Exit\n"
        s += "1. Read Board\n"
        s += "2. Resolve the problem using DFS\n"
        s += "3. Resolve the problem using Greedy"
        print(s)
        
    def readConfig(self):
        try:
            n = int(input("Dimension of the board (implicit value is 4): "))
            self.ctrl = Controller(n)
        except:
            print("Invalid number, constructing the board with the implicit dimension 4")
            self.ctrl = Controller(4)
            
    def run(self):
       
        while True:
            try:
                self.menu()
                cmd = int(input(">"))
                if cmd == 0:
                    return False
                elif cmd == 1:
                    self.readConfig()
                elif cmd == 2:
                    queue = self.cntrl.DFS()
                    print(queue)
                elif cmd == 3:
                    queue = self.cntrl.Greedy()
                    print(queue)
            except:
                print("Invalid command")
