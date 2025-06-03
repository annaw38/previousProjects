import sys
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

#plot the number of frozen days vs the year from the given dataset
def plotVisual(years, days):
    #copied from https://www.geeksforgeeks.org/matplotlib-pyplot-xticks-in-python/
    plt.xticks(years, years) 
    plt.plot(years, days)
    plt.xlabel("Year")
    plt.ylabel("Number of Frozen Days")
    plt.savefig("data_plot.jpg")

#normalize the X's
def normalizeXs(x, m, M):
    # print("min: ", m)
    # print("max: ", M)
    X = np.ones([len(x), 2])
    for i in range(len(x)): 
        X[i][0] = (x[i]-m)/(M-m)
    # print(X)
    return X

#find weight w and bias b to minimize MSE loss
def findWeights(Y, X_normalized):
    weights = np.dot(np.linalg.inv(np.dot(X_normalized.T, X_normalized)), np.dot(X_normalized.T, Y))
    return weights

#linear regression with gradient descent 
def gradientDesc(alpha, T, initialization, X_normalized, Y):
    currWeights = initialization
    # print(currWeights)
    n = len(X_normalized)
    MSELoss = np.zeros(T)
    iter = np.zeros(T)
    # print(iter)
    # plt.plot(years, days)
    for t in range(T):
        if t % 10 == 0:
            print(currWeights)
        gradLoss = np.zeros(2)
        # MSELoss[t] = (1/(2*n))*math.sqrt((np.dot(X_normalized, currWeights) - Y)**2)
        MSELoss[t] = 0
        iter[t] = t
        for i in range(n):
            yhat = np.dot(currWeights.T, X_normalized[i])
            gradLoss += (yhat-Y[i])*X_normalized[i].T
            MSELoss[t] += (((currWeights[0]*X_normalized[i][0])+ currWeights[1])-Y[i])**2
        MSELoss[t] = (1/(2*n))*MSELoss[t]
        gradLoss = (1/n)*gradLoss
        currWeights -= alpha * gradLoss
    # print(MSELoss)
    #Q4e
    plt.figure(figsize=(10, 5))
    plt.plot(iter, MSELoss)
    # plt.xticks(iter, iter)
    plt.xticks(np.arange(0, T+1, 20)) 
    plt.xlabel("Iteration")
    plt.ylabel("Loss")
    plt.savefig("loss_plot.jpg")
    return currWeights

def main():
    if len(sys.argv) != 4:
        raise ValueError("Usage error: unexpected number of arguments. " +
            "Expected: python3 hw5.py filename.csv learning_rate iterations")
    fileName = sys.argv[1]
    learning_rate = float(sys.argv[2])
    iterations = int(sys.argv[3])
    try:
        df = pd.read_csv(fileName)
    except FileNotFoundError:
        raise ValueError("The file " +  f"{fileName}" +" was not found. Re-enter a valid filepath.")
    x = df["year"].to_numpy()
    y = df["days"].to_numpy()
    Y = y.T
    m = np.min(x)
    M = np.max(x)
    # print(years)
    #Q1
    plotVisual(x, y)
    
    #Q2
    print("Q2:")
    X_normalized = normalizeXs(x, m, M)
    print(X_normalized)
    
    #Q3
    weights = findWeights(Y, X_normalized)
    print("Q3:")
    print(weights)
    
    #Q4
    inital = np.zeros(2)
    # print(inital)
    #Q4A
    print("Q4a:")
    a = gradientDesc(learning_rate, iterations, inital, X_normalized, Y)
    # print("last grad" + str(a))
    # run gradient descent, every 10 iterations do print(weights)
    print("Q4b: 0.60")
    print("Q4c: 250")
    print("Q4d: I started with the learning rate of 0.01 with 100 iterations and looked at the last weight and bias and the plot to see how fast the MSE loss decreases. Then, I increased the learning rate by 0.1 and continued this process until I reached 0.60 because the last outputted weight and bias were close to the closed form weights, around 1.0 off. After that, I increased the inital 100 iterations by 50 until I reached 250 because that was the first iteration and learning rate combination where the last weight bias were within 0.01 of the closed form solution and has a smooth loss as the number of iterations increases and converges.")
    
    #Q5
    y_hat = weights[0]*((2023-m)/(M-m))+weights[1]
    print("Q5: " + str(y_hat))
    
    #Q6
    sign = np.sign(weights[0])
    symbol = ""
    if sign == 1:
        symbol = ">"
    elif sign == 0:
        symbol = "="
    else:
        symbol = "<"
    print("Q6a: " + symbol)
    print("Q6b: If w > 0, the number of ice days has a positive correlation with the year and suggests that the number of days Lake Medota is covered by ice positively influenced by the year, or that there is an increased amount of days of ice coverage over Lake Mendota that year. If w = 0, there is no correlation between the year and number of days that Lake Mendota is covered by ice, suggesting a constant or stable number of days Lake Mendota is covered by ice regardless of year. Lastly, if w < 0, there is a negative correlation between the year and number of days Lake Mendota is covered by ice, or the year negatively influences the number of days Lake Mendota is covered by ice, resulting in shorter durations of days that Lake Mendota is covered by ice.")
    
    #Q7
    x_star = ((-weights[1]/weights[0])*(M-m))+m
    print("Q7a: " + str(x_star))
    print("Q7b: x* is not a compelling prediction because it does not explicitly account for outside factors like climate change and policy changes that affects the environment due to its training on historic data that may not account for these factors. The historical data also may not account for the depth of the lake and weather conditions of the time period the lake was beginning to freeze over to the end, like amount of wind, temperature, precipitation, etc, since those factors can also affect the duration of the lake being covered by ice. However, if these factors are accounted for in the data, then x* is still not a compelling prediction because of its assumption that there is a linear relationship between the number of days Lake Mendota is covered by ice and the year, which can be non-existent or change as years increases.")   
    
if __name__=="__main__":
    main()