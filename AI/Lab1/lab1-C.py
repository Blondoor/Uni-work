# -*- coding: utf-8 -*-
"""
Created on Tue Mar  3 14:46:47 2020

@author: Dan_B
"""

import numpy as np
import math

def read(Filename):
    ResultBoard = []
    f = open(Filename, "r")
    for x in f:
        ResultRow = []
        for nr in x.split(','):
            ResultRow.append(int(nr))
        ResultBoard.append(ResultRow)
    
    return ResultBoard


def check_S_Line(i, n, m):
    l = []
    for j in range(n, m + 1):
        l.append(0)
    for j in range(n, m):
        if l[i[j]] != 0:
            return False
        l[i[j]] += 1
    return True

def check_S_Column(Board, i, n, m):
    l = []
    for j in range(m + 1):
        l.append(0)
    
    for j in range(n, m):
        if l[Board[i][j]] != 0:
            return False
        l[Board[i][j]] += 1
    
    return True

def check_S_smallsq(Board, n, m):
    for i in Board:
        if check_S_Line(i, n, m) == False:
            return False
    
    for i in range(n, m):
        if check_S_Column(Board, i, n, m) == False:
            return False

def check_S(Board, n, m):
    for i in Board:
        if check_S_Line(i, n, m) == False:
            return False
    
    for i in range(n, m):
        if check_S_Column(Board, i, n, m) == False:
            return False
        
    for i in range(n, m, int(math.sqrt(m))):
        if check_S_smallsq(Board, i, i + math.sqrt(m)) == False:
            return False
    
    return True


def try_to_solve(Board, n):
    for i in range(n):
        for j in range(n):
            if Board[i][j] == 0:
                Board[i][j] = np.random.randint(1, n)
        
    if check_S(Board, 0, n):
        return True
    
    return False

def Cryptarithmetic(s1, s2, s3, op):
    dict={}
    s = s1 + s2 + s3
    for k in s:
        if k not in dict.keys():
            dict[k] = np.random.randint(0,15)
    
    nr1 = 0
    nr2 = 0
    nr3 = 0
    
    for k in s1:
        nr1 = (nr1 << 4) + dict[k]
    for k in s2:
        nr2 = (nr2 << 4) + dict[k]
    for k in s3:
        nr3 = (nr3 << 4) + dict[k]
    
    
        
    if op == "+":
        return nr1 + nr2 == nr3
    elif op == "-":
        return nr1 - nr2 == nr3
        
    
        
def printBoard(Board, n):
    s = ""
    for i in Board:
        for j in i:
            s += str(j) + "|"
        s += "\n"
    print(s)

print("1. Sudoku game")
print("2. Cryptarithmetic game")
print("3. Geometric forms")

choice = int(input("choose the problem: "))

if choice == 1: #Sudoku
    n = (int(input("Choose the size of the sudoku board (4 or 9): ")))
    Board = []
    if n == 4:
        name = "Sudoku-4.txt"
        Board = read(name)
    elif n == 9:
        name = "Sudoku-9.txt"
        Board = read(name)
        
    ntries = int(input("Give the nr of tries: "))
        
    for k in range(ntries):
        if try_to_solve(Board, n) == True:
            printBoard(Board, n)
            break
        #printBoard(Board, n)
        Board = read(name)
elif choice == 2: #Cryptarithmetic
    n_tries = int(input("Give the number of tries"))
    print("1. SEND+MORE=MONEY")
    print("2. TAKE+A=KATE")
    print("3. EAT+THAT=APPLE")
    print("4. NEVER-DRIVE=RIDE")
    n = int(input("Choose one: "))
    if n == 1:
        for k in range(n_tries):
            if Cryptarithmetic("SEND", "MORE", "MONEY", "+"):
                print("Success!")
                break
    if n == 2:
        for k in range(n_tries):
            if Cryptarithmetic("TAKE", "A", "KATE", "+"):
                print("Success!")
                break 
    if n == 3:
        for k in range(n_tries):
            if Cryptarithmetic("EAT", "THAT", "APPLE", "+"):
                print("Success!")
                break
    if n == 4:
        for k in range(n_tries):
            if Cryptarithmetic("NEVER", "DRIVE", "RIDE", "-"):
                print("Success!")
                break        
    
    
    