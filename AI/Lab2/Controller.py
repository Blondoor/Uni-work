# -*- coding: utf-8 -*-

from Problem import Problem

class Controller:
    def __init__(self, size):
        self.problem = Problem(size)
        
    def __str__(self):
        return str(self.problem)
    
    def Greedy(self):
        queue = [self.problem]
        
        while len(queue) != 0:
            crtState = queue.pop(0)
            if crtState.final():
                return crtState
            
            nextStates = crtState.expand()
            
            aux = [[x,self.problem.heuristic()] for x in nextStates]
            aux.sort(key = lambda x:x[1])
            aux = [x[0] for x in aux]
            queue = aux[:] + queue
            
        return queue

    def DFS(self):
        stack = [self.problem]
        
        while len(stack) != 0:
            crtState = stack.pop()
            
            if crtState.final():
                return crtState
            
            stack = stack + crtState.expand()
            
        return stack