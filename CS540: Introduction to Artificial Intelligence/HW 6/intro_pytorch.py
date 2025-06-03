import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms
import numpy as np

def get_data_loader(training = True):
    """
    INPUT: 
        An optional boolean argument (default value is True for training dataset)

    RETURNS:
        Dataloader for the training set (if training = True) or the test set (if training = False)
    """
    transform=transforms.Compose([transforms.ToTensor(),transforms.Normalize((0.1307,), (0.3081,))])    
    
    loader = None 
    if training:
        train_set=datasets.FashionMNIST('./data',train=True, download=True,transform=transform)
        loader = torch.utils.data.DataLoader(train_set, batch_size = 64, shuffle=True)
    else:
        test_set=datasets.FashionMNIST('./data', train=False, transform=transform)
        loader = torch.utils.data.DataLoader(test_set, batch_size = 64, shuffle=False)
        
    return loader

def build_model():
    """
    INPUT: 
        None

    RETURNS:
        An untrained neural network model
    """
    model = nn.Sequential(
                nn.Flatten(),
                nn.Linear(28*28, 128),
                nn.ReLU(),
                nn.Linear(128, 64),
                nn.ReLU(),
                nn.Linear(64,10)
            )

    return model

def build_deeper_model():
    """
    INPUT: 
        None

    RETURNS:
        An untrained neural network model
    """
    deeperModel = nn.Sequential(
                nn.Flatten(),
                nn.Linear(28*28, 256),
                nn.ReLU(),
                nn.Linear(256, 128),
                nn.ReLU(),
                nn.Linear(128, 64),
                nn.ReLU(),
                nn.Linear(64,32),
                nn.ReLU(),
                nn.Linear(32,10)
            )

    return deeperModel


def train_model(model, train_loader, criterion, T):
    """
    INPUT: 
        model - the model produced by the previous function
        train_loader  - the train DataLoader produced by the first function
        criterion   - cross-entropy 
        T - number of epochs for training

    RETURNS:
        None
    """
    criterion = nn.CrossEntropyLoss()
    opt = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)
    #copied from https://pytorch.org/tutorials/beginner/blitz/cifar10_tutorial.html#sphx-glr-beginner-blitz-cifar10-tutorial-py
    for i in range(T):
        runningLoss = 0.0
        total = 0
        correct = 0
        for data in train_loader:
            model.train()
            inputs, labels = data 
            opt.zero_grad()
            outputs = model(inputs)
            loss = criterion(outputs, labels)
            loss.backward()
            opt.step()
           
            _, predicted = torch.max(outputs, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item() 

            runningLoss += loss.item()
            #Train Epoch: ? Accuracy: ?/?(??.??%) Loss: ?.???
        accuracy = correct/total
        runningLoss /= len(train_loader)
        print(f"Train Epoch: {i} Accuracy: {correct}/{total}({accuracy:.2%}) Loss: {runningLoss:.3f}")

def evaluate_model(model, test_loader, criterion, show_loss = True):
    """
    INPUT: 
        model - the the trained model produced by the previous function
        test_loader    - the test DataLoader
        criterion   - cropy-entropy 

    RETURNS:
        None
    """
    correct = 0
    total = 0
    totalLoss = 0
    criterion = nn.CrossEntropyLoss()

    with torch.no_grad():
        for data, labels in test_loader:
            model.eval()
            outputs = model(data)
            loss = criterion(outputs, labels)
            totalLoss += loss.item()

            _, predicted = torch.max(outputs, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item() 

    accuracy = correct/total
    avgLoss = totalLoss/len(test_loader)
    
    if show_loss:
        #print both avg loss and accuracy
        #Average loss: 0.4116
        #Accuracy: 85.39%
        print(f"Average loss: {avgLoss:.4f}")
        print(f"Accuracy: {accuracy:.2%}")
    else:
        #only accuracy
        print(f"Accuracy: {accuracy:.2%}")

def predict_label(model, test_images, index):
    """
    INPUT: 
        model - the trained model
        test_images   -  a tensor. test image set of shape Nx1x28x28
        index   -  specific index  i of the image to be tested: 0 <= i <= N - 1

    RETURNS:
        None
    """
    model.eval()
    with torch.no_grad():
        #for data, labels in test_loader:
        #print(test_images)
        logits = model(test_images[index])

    #logits = model(test_images)
        prob = F.softmax(logits, dim=1)
        class_names = ['T-shirt/top','Trouser','Pullover','Dress','Coat','Sandal','Shirt','Sneaker','Bag','Ankle Boot']
        #copied from google ai overview for search "predict labels pytorch top x options"
        top3Probs, top3Classes = torch.topk(prob, 3)

        for p, ind in zip(top3Probs[0], top3Classes[0]):
            className = class_names[ind.item()]  
            #prob = prob.item() * 100
            print(f"{className}: {p.item()*100:.2f}%")

if __name__ == '__main__':
    '''
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    '''
    train_loader = get_data_loader()
    print(type(train_loader))
    print(train_loader.dataset)
    test_loader = get_data_loader(False)
    criterion = nn.CrossEntropyLoss()
    model = build_model()
    model2 = build_deeper_model()
    print(model)
    #print(model2)
    train_model(model, train_loader, criterion, 5)
    train_model(model2, train_loader, criterion, 5)
    test_loader = get_data_loader(training = False)
    evaluate_model(model, test_loader, criterion, True)
    evaluate_model(model2, test_loader, criterion, True)
    #model2 = build_deeper_model()
    test_images = next(iter(test_loader))[0]
    predict_label(model, test_images, 1)
