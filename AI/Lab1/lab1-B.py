# -*- coding: utf-8 -*-
"""
Created on Tue Mar  3 14:37:28 2020

@author: Dan_B
"""

import matplotlib.pyplot as plt
#from scipy.stats import binom
import numpy as np

"""plt.plot([0,0,1,3,0,5], 'ro')
plt.axis([-1,10, 0, 20])
plt.ylabel('some numbers')
plt.show()"""

choice = int(input("choose the distribution"))
minlim = int(input("lower bound"))
maxlim = int(input("upper bound"))
if choice == 1: #normal

    mu, sigma = (maxlim+minlim)/2, maxlim-minlim
    s = np.random.normal(mu, sigma, 1000)

    
    count, bins, ignored = plt.hist(s, 30, density=True)
    plt.plot(bins, 1 / (sigma * np.sqrt(2 * np.pi)) * np.exp(- (bins - mu) ** 2 / (2 * sigma ** 2)), linewidth=2, color='r')
    plt.title("Normal distrib")
    plt.show()
elif choice == 2: #binomial
    n,p = 20, 0.5
    s = np.random.binomial(n,p,100)
    plt.plot(s, 'bo')
    plt.title("Binomial distrib")
    plt.show()