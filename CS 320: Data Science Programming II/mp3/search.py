class Node():
    def __init__(self, key):
        self.key = key
        self.values = []
        self.left = None
        self.right = None
    
    def __len__(self):
        size = len(self.values)
        if self.left != None:
            size += len(self.left.values)
        if self.right != None:
            size += len(self.right.values)
            
        return size


    def lookup(self, key):
        if(self.key == key):
            return self.values
        if(self.key > key and self.left != None):
            return self.left.lookup(key)
        if(self.key < key and self.right != None):
            return self.right.lookup(key)
        else:
            return []
        
class BST():
    def __init__(self):
        self.root = None

    def add(self, key, val):
        if self.root == None:
            self.root = Node(key)

        curr = self.root
        while True:
            if key < curr.key:
                # go left
                if curr.left == None:
                    curr.left = Node(key)
                curr = curr.left
            elif key > curr.key:
                # go right
                if curr.right == None:
                    curr.right = Node(key)
                curr = curr.right
            else:
                # found it!
                assert curr.key == key
                break

        curr.values.append(val)
        
    def __dump(self, node):
        if node == None:
            return
        self.__dump(node.left)             # 3
        print(node.key, ":", node.values)  # 2
        self.__dump(node.right)            # 1
        
    def dump(self):
        self.__dump(self.root)
    
    def __getitem__(self, key):
        curr = self.root
        if(curr.key == key):
            return curr.values
        if(curr.key > key and curr.left != None):
            curr = curr.left
            return curr.lookup(key)
        if(curr.key < key and curr.right != None):
            curr = curr.right
            return curr.lookup(key)
        else:
            return 0

    def height(self, node):
        if node is None:
            return -1
        
        left_height = self.height(node.left)
        right_height = self.height(node.right)
        
        return max(left_height, right_height) + 1
    
    def findLeaves(self, root):
        if root is None:
            return 0
        if root.left is None and root.right is None:
            return 1
        return self.findLeaves(root.left) + self.findLeaves(root.right) 
    
        
        