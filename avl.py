import sys
import random

# Create tree node
class TreeNode(object):
    def __init__(node, key):
        node.key = key
        node.left = None
        node.right = None
        node.height = 1


class AVLTree(object):

    # Function to insert a node
    def insert_node(node, root, key):

        # Find the correct location and insert the node
        if not root:
            return TreeNode(key)
        elif key < root.key:
            root.left = node.insert_node(root.left, key)
        else:
            root.right = node.insert_node(root.right, key)

        root.height = 1 + max(node.getHeight(root.left),
                              node.getHeight(root.right))

        # Update the balance factor and balance the tree
        balanceFactor = node.getBalance(root)
        if balanceFactor > 1:
            if key < root.left.key:
                return node.rightRotate(root)
            else:
                root.left = node.leftRotate(root.left)
                return node.rightRotate(root)

        if balanceFactor < -1:
            if key > root.right.key:
                return node.leftRotate(root)
            else:
                root.right = node.rightRotate(root.right)
                return node.leftRotate(root)

        return root

    # Function to delete a node
    def delete_node(node, root, key):

        # Find the node to be deleted and remove it
        if not root:
            return root
        elif key < root.key:
            root.left = node.delete_node(root.left, key)
        elif key > root.key:
            root.right = node.delete_node(root.right, key)
        else:
            if root.left is None:
                temp = root.right
                root = None
                return temp
            elif root.right is None:
                temp = root.left
                root = None
                return temp
            temp = node.getMinValueNode(root.right)
            root.key = temp.key
            root.right = node.delete_node(root.right,
                                          temp.key)
        if root is None:
            return root

        # Update the balance factor of nodes
        root.height = 1 + max(node.getHeight(root.left),
                              node.getHeight(root.right))

        balanceFactor = node.getBalance(root)

        # Balance the tree
        if balanceFactor > 1:
            if node.getBalance(root.left) >= 0:
                return node.rightRotate(root)
            else:
                root.left = node.leftRotate(root.left)
                return node.rightRotate(root)
        if balanceFactor < -1:
            if node.getBalance(root.right) <= 0:
                return node.leftRotate(root)
            else:
                root.right = node.rightRotate(root.right)
                return node.leftRotate(root)
        return root

    # Function to perform left rotation
    def leftRotate(node, z):
        y = z.right
        T2 = y.left
        y.left = z
        z.right = T2
        z.height = 1 + max(node.getHeight(z.left),
                           node.getHeight(z.right))
        y.height = 1 + max(node.getHeight(y.left),
                           node.getHeight(y.right))
        return y

    # Function to perform right rotation
    def rightRotate(node, z):
        y = z.left
        T3 = y.right
        y.right = z
        z.left = T3
        z.height = 1 + max(node.getHeight(z.left),
                           node.getHeight(z.right))
        y.height = 1 + max(node.getHeight(y.left),
                           node.getHeight(y.right))
        return y

    # Get the height of the node
    def getHeight(node, root):
        if not root:
            return 0
        return root.height

    # Get balance factore of the node
    def getBalance(node, root):
        if not root:
            return 0
        return node.getHeight(root.left) - node.getHeight(root.right)

    def getMinValueNode(node, root):
        if root is None or root.left is None:
            return root
        return node.getMinValueNode(root.left)

    def preOrder(node, root):
        if not root:
            return
        print("{0} ".format(root.key), end="")
        node.preOrder(root.left)
        node.preOrder(root.right)

    # Print the tree
    def printHelper(node, currPtr, indent, last):
        if currPtr != None:
            sys.stdout.write(indent)
            if last:
                sys.stdout.write("R----")
                indent += "     "
            else:
                sys.stdout.write("L----")
                indent += "|    "
            print(currPtr.key)
            node.printHelper(currPtr.left, indent, False)
            node.printHelper(currPtr.right, indent, True)


myTree = AVLTree()
root = None
nums = random.sample(range(0, 100), 8)
for num in nums:
    root = myTree.insert_node(root, num)
myTree.printHelper(root, "", True)
key = nums[7]
root = myTree.delete_node(root, key)
print("After Deletion: ")
myTree.printHelper(root, "", True)