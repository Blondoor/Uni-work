# -*- coding: utf-8 -*-

class State:
    def __init__(self, size):
        self.board = []
        for i in range(size):
            self.board += [[0] * size]
        self.size = size
    
    def __str__(self):
        res = ''
        for i in self.board:
            for j in i:
                res += str(j)
                res += ' '
            res += '\n'
        return res