# -*- coding: utf-8 -*-

import copy
from State import State
from Check import Check

class Problem:
    def __init__(self, size):
        self.initState = State(size)
    
    def __str__(self):
        return str(self.initState)
    
    def setVal(self, i, j, value):
        self.initState.board[i][j] = value
        
    def heuristic(self):
        number_squares = 0
        checker = Check(self.initState.board)
        
        for i in range (self.initState.size):
            if sum(self.initState.board[i]) == 0:
               for j in range(self.initState.size):
                   if checker.checkALL(i, j):
                       number_squares += 1
        
        return number_squares
    
    def expand(self):
        nextStates = []
        
        checker = Check(self.initState.board)
        
        for i in range(self.initState.size):
            for j in range(self.initState.size):
                if checker.checkALL(i, j):
                    nextState = copy.deepcopy(self)
                    nextState.setVal(i, j, 1)
                    nextStates.append(nextState)
        return nextStates
    
    def final(self):
        for i in range(self.initState.size):
            ok = 0
            for j in range(self.initState.size):
                if self.initState.board[i][j] == 1:
                    ok = 1
            if ok == 0:
                return False
        
        return True