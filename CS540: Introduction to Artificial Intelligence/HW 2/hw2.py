import sys
import math
import string

def get_parameter_vectors():
    '''
    This function parses e.txt and s.txt to get the  26-dimensional multinomial
    parameter vector (characters probabilities of English and Spanish) as
    descibed in section 1.2 of the writeup

    Returns: tuple of vectors e and s
    '''
    #Implementing vectors e,s as lists (arrays) of length 26
    #with p[0] being the probability of 'A' and so on
    e=[0]*26
    s=[0]*26

    with open('e.txt',encoding='utf-8') as f:
        for line in f:
            #strip: removes the newline character
            #split: split the string on space character
            char,prob=line.strip().split(" ")
            #ord('E') gives the ASCII (integer) value of character 'E'
            #we then subtract it from 'A' to give array index
            #This way 'A' gets index 0 and 'Z' gets index 25.
            e[ord(char)-ord('A')]=float(prob)
    f.close()

    with open('s.txt',encoding='utf-8') as f:
        for line in f:
            char,prob=line.strip().split(" ")
            s[ord(char)-ord('A')]=float(prob)
    f.close()

    return (e,s)

def shred(filename):
    #Using a dictionary here. You may change this to any data structure of
    #your choice such as lists (X=[]) etc. for the assignment
    #copied from https://www.geeksforgeeks.org/python-string-ascii_uppercase/
    X = {letter: 0 for letter in string.ascii_uppercase}
    try:
        with open (filename,encoding='utf-8') as f:
            #counts number of chars in f and case-folding
            for line in f:
                newLine = line.strip()
                for char in newLine:
                    #current character is a letter
                    if char.isalpha():
                        #character does not have an accent 
                        if char in X.keys() or char.upper() in X.keys():
                            X[char.upper()] += 1
        f.close()
        return X
    except FileNotFoundError:
        print("Unexpected error. Letter file given was not found.")
        sys.exit(1)

def calculateFs(X, Pe, Ps, e, s): 
    priorE = float(Pe)
    priorS = float(Ps)
    #sigma English X_i * log(e_i)
    sE = 0
    #sigma Spanish X_i * log(s_i)
    sS = 0
    for i in range(26):
        #discussed with peer mentor when debugging
        if X[i] > 0:
            sE += X[i]*math.log(e[i])
            sS += X[i]*math.log(s[i])
    #F(English)
    FE = math.log(priorE)+sE
    #F(Spanish)
    FS = math.log(priorS)+sS
    return [FE, FS]
    
def calculateP(FEnglish, FSpanish):
    if FSpanish - FEnglish >= 100:
        return 0
    elif FSpanish - FEnglish <= -100:
        return 1
    else:
        return (1/(1+math.exp(FSpanish-FEnglish)))

def main():
    #modified from piazza post @45
    if len(sys.argv) != 4:
        raise ValueError("Usage error: unexpected number of arguments. " +
            "Expected: python3 hw2.py [letter file] [english prior] [spanish prior]")
    
    #shread the document 
    x = shred(sys.argv[1])

    #Q1: print letter counts
    print("Q1")
    for letter in x.keys():
        print(letter + " "  + str(x.get(letter)))

    #Q2: X_1*log(e_1) and X_1*log(s_1), up to 4 decimal places
    X = list(x.values())
    # print(X)
    
    e,s = get_parameter_vectors()
     
    sumE = 0
    sumS = 0
    #check if any of the e_i or s_i are negative
    for i in range(26):
        if e[i] < 0 or s[i] < 0:
            print("Unexpected error: the probability of a letter in English or Spanish is negative, " +
                  "probabilities must be positive.")
            sys.exit(1)
        sumE += e[i]
        sumS += s[i] 

    print("Q2")
    #googled "print 4 digits python" and copied format of second option
    print(f"{X[0]*math.log(e[0]):.4f}") 
    print(f"{X[0]*math.log(s[0]):.4f}") 

    #Q3 F(English) and F(Spanish), up to 4 decimal places
    print("Q3")
    F = calculateFs(X, sys.argv[2], sys.argv[3], e, s)
    print(f"{F[0]:.4f}")
    print(f"{F[1]:.4f}")

    #Q4 F(Y=English|X), up to 4 decimal places
    print("Q4")
    print(f"{calculateP(F[0], F[1]):.4f}")

if __name__=="__main__":
    main()