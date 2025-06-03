import numpy as np
from matplotlib import pyplot as plt
import csv
import scipy

#Takes in a string with a path to a CSV file and returns the data points as a list of dictionaries.
def load_data(filepath):
    data = []
    with open(filepath, "r") as f:
        reader = csv.DictReader(f)
        for row in reader:
            data.append(row)
    return data

#Takes in one row dictionary from the data loaded from the previous function,
#calculates the corresponding feature vector for that country as specified, and returns it as a NumPy
#array of shape (9,). The dtype of this array should be float64.
def calc_features(row):
    fVector = np.array(list(row.values())[1:]).astype('float64')
    return fVector

#Performs complete linkage hierarchical agglomerative clustering on the countries
#using the (x1, . . . , x9) feature representation and returns a NumPy array representing the clustering.
def hac(features):
    #number starting data points
    #distance matrix nxn
    #print(features)
    n = len(features)
    distance_matrix = np.zeros((n,n))
    #calculate the euclidian distances of each pair of points
    for i in range(n):
        for j in range(i, n):
            #copied from https://www.geeksforgeeks.org/calculate-the-euclidean-distance-using-numpy/
            distance_matrix[i][j] = np.linalg.norm(features[i]-features[j])
            distance_matrix[j][i] =  distance_matrix[i][j]
    # print(distance_matrix)
    
    Z = np.zeros((n-1, 4))
    #discussed with a TA
    currClusters = {i: [i] for i in range(n)} #key = cluster number and value = i
    #next index for new cluster
    nextCluster = n
    # print(currClusters)
    #go through dictionary and find pairs with lowest distances
    for row in range(len(Z)):
        #used chatgpt with prompt "where are potential failure spots" to help debug the logic
        #copied from https://stackoverflow.com/questions/7604966/maximum-and-minimum-values-for-ints
        minDist = float('inf')
        minIndex0, minIndex1 = -1,-1
        #loop over list of keys or the current clusters
        clusterKeys = list(currClusters.keys())
        for i in range(len(clusterKeys)):
            for j in range(i+1, len(clusterKeys)):
                #compare the ith cluster with the i+1st cluster
                cluster1 = currClusters[clusterKeys[i]]
                cluster2 = currClusters[clusterKeys[j]]
                
                #complete linkage - max distance between points
                maxDist = max(distance_matrix[ind1][ind2] for ind1 in cluster1 for ind2 in cluster2)
                
                #update min distance/tie breaker 
                if maxDist < minDist or (maxDist == minDist and clusterKeys[i] < minIndex0) \
                    or (maxDist == minDist and clusterKeys[i] == minIndex0 and clusterKeys[j] < minIndex1):
                    minDist = maxDist
                    minIndex0 = clusterKeys[i]
                    minIndex1 = clusterKeys[j]
                    
        #searched "add two dictionary values to new key python" and used AI result
        currClusters[nextCluster] = currClusters[minIndex0] + currClusters[minIndex1]
        
        #remove the 2 previous clusters from the dict since they are now duplicates
        currClusters.pop(minIndex0)
        currClusters.pop(minIndex1)
        
        #update Z with the information about the merged clusters 
        Z[row, 0] = minIndex0
        Z[row, 1] = minIndex1
        Z[row, 2] = minDist
        Z[row, 3] = len(currClusters[nextCluster])
        nextCluster += 1
    return Z

#Visualizes the hierarchical agglomerative clustering of the countries’ feature representation. 
def fig_hac(Z, names):
    fig = plt.figure()
    fig.tight_layout()
    scipy.cluster.hierarchy.dendrogram(Z, labels=names, leaf_rotation = 90)
    return fig

#Takes a list of feature vectors and computes the normalized values.
#The output should be a list of normalized feature vectors in the same format as the input.
def normalize_features(features):
    mus = np.mean(features, axis = 0)
    sigmas = np.std(features, axis = 0)
    xPrime = np.divide((features - mus), sigmas)
    return xPrime.tolist()

def main():
    # countries = load_data("Country-data.csv")
    # print(countries[0])
    # print(countries[1])
    # a = calc_features(countries[0])
    # b = calc_features(countries[1])
    # c = calc_features(countries[2])
    # points = [a, b, c]
    # print(a)
    # print(b)
    # clusters =  hac(points)
    # print(clusters)
    # fig_hac(clusters, )
    # print(type(normalize_features(points)))
    data = load_data("Country-data.csv")
    features = [calc_features(row) for row in data]
    names = [row["country"] for row in data]
    features_normalized = normalize_features(features)
    np.savetxt("output.txt", features_normalized)
    n = 20
    Z = hac(np.array(features_normalized[:n]))
    print(Z)
    fig = fig_hac(Z, names[:n])
    plt.show()

if __name__ == "__main__":
    main() 