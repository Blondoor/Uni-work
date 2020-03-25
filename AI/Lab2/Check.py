# -*- coding: utf-8 -*-

class Check:
    def __init__(self, board):
        self.matrix = board
        self.size = len(board)
        
    def check_horizontally(self, row, column):
        for i in range(self.size):
            if self.matrix[row][i] == 1:
                return False
        return True
    
    def check_vertically(self, row, column):
        for i in range(self.size):
            if self.matrix[i][column] == 1:
                return False
        return True
    
    def check_main_diagonal(self, row, column):
        if row != self.size - 1 and column != self.size -1:
                if self.matrix[row + 1][column + 1] == 1:
                    return False
        elif self.matrix[row -1][column - 1] == 1:
            return False
        
        return True
    
    def check_secondary_diagonal(self, row, column):
        if row != 0 and column != self.size - 1:
            if self.matrix[row -1][column + 1] == 1:
                return False
        elif row != self.size -1:
            if self.matrix[row + 1][column -1] == 1:
                return False
            
        return True
    
    def checkALL(self, row, column):
        if(self.check_horizontally(row, column) and self.check_vertically(row, column) and self.check_main_diagonal(row, column) and self.check_secondary_diagonal(row, column) and self.matrix[row][column] == 0):
            return True
        return False
