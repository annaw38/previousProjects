from collections import deque
import os
import pandas as pd
from selenium.webdriver.common.by import By
import requests
import time
from IPython.display import display, Image

class GraphSearcher:
    def __init__(self):
        self.visited = set()
        self.order = []

    def visit_and_get_children(self, node):
        """ 
        Leave this method as is! It will be over-written the child classes
        Each child class should perform the following:
            Record the node value in self.order AND return its children
            parameter: node
            return: children of the given node
        """
        raise Exception("must be overridden in sub classes -- don't change me here!")

    def dfs_search(self, node):
        # 1. clear out visited set and order list
        # 2. start recursive search by calling dfs_visit
        self.visited.clear()
        self.order.clear()
        self.dfs_visit(node)

    def dfs_visit(self, node):
        # 1. if this node has already been visited, just `return` (no value necessary)
        # 2. mark node as visited by adding it to the set
        # 3. call self.visit_and_get_children(node) to get the children
        # 4. in a loop, call dfs_visit on each of the children
        if node in self.visited:
            return
        self.visited.add(node)
        children = self.visit_and_get_children(node)
        for child in children:
            self.dfs_visit(child)
            
    def bfs_search(self, node):
        self.visited.clear()
        self.order.clear()
        self.queue = deque([node]) 
        self.bfs_visit()             
    
    def bfs_visit(self):
        while len(self.queue) > 0:
            currNode = self.queue.popleft()
            # print(currNode)
            if currNode in self.visited:
                continue
            self.visited.add(currNode)
            children = self.visit_and_get_children(currNode)
            # print(children)
            # print(self.visited)
            for child in children:
                if child in self.visited:
                    continue
                self.queue.append(child)
            self.bfs_visit()
                       
class MatrixSearcher(GraphSearcher):
    def __init__(self, df):
        super().__init__() # call constructor method of parent class
        self.df = df

    def visit_and_get_children(self, node):
        # TODO: Record the node value in self.order
        self.order.append(node)
        # print(value)
        children = []
        # TODO: use `self.df` to determine what children the node has and append them
        #cols = list(df.columns.values)
        for node, has_edge in self.df.loc[node].items():
            if has_edge == 1:
                children.append(node)
        return children
            
class FileSearcher(GraphSearcher):
    def __init__(self):
        super().__init__()
        
    def visit_and_get_children(self, file):
        # got help from Juan
        filePath = os.path.join("file_nodes", file)
        with open(filePath, 'r') as f:
            value = f.readline().strip()
            self.order.append(value)
            nextLine = f.readline().strip()
            children = nextLine.split(",")
            return children
        
    def concat_order(self):
        #copied from https://www.geeksforgeeks.org/python-string-join-method/
        return "".join(self.order) 
    
class WebSearcher(GraphSearcher):
    def __init__(self, driver):
        super().__init__() 
        self.driver = driver
        self.tables = []
        
    def visit_and_get_children(self, url):
        self.order.append(url)
        self.driver.get(url)
        table = pd.read_html(url)[0]
        self.tables.append(table)
        children = []
        for element in self.driver.find_elements(By.TAG_NAME, "a"):
            children.append(element.get_attribute("href"))
        return children
    
    def table(self):
        table = pd.concat(self.tables, ignore_index=True)
        return table

    
def reveal_secrets(driver, url, travellog):
    # def show_screen(width, height):
    #     driver.save_screenshot("out.png")
    #     driver.set_window_size(width, height)
    #     display(Image("out.png"))
    # adapted from https://www.geeksforgeeks.org/how-to-convert-pandas-columns-to-string/
    travellog["clue"] = travellog["clue"].astype(str)
    #get the password from clue column
    password = "".join(travellog["clue"])
    driver.get(url)
    textBox = driver.find_element("id", "password-textbox")
    button = driver.find_element("id", "submit-button")
    textBox.send_keys(password)
    button.click()
    #wait for the page to load
    time.sleep(1)
    locButton = driver.find_element("id", "location-button")
    locButton.click()
    #wait for image to load
    time.sleep(3)
    # show_screen(500, 500)
    #get the image's url
    imageUrl = driver.find_element(By.TAG_NAME, "img").get_attribute("src")
    #searched "requests to download image python" and adapted from https://www.geeksforgeeks.org/how-to-download-an-image-from-a-url-in-python/
    #put the image in a file
    data = requests.get(imageUrl).content 
    with open('Current_Location.jpg','wb') as f:
        f.write(data) 
    # print(imageUrl)
    # print(driver.current_url)
    #get location
    location = driver.find_element(By.TAG_NAME, "p").text
    return location