
/******************Preprocessor Directives******************/
#include"AvlTree.h"
#include<queue>

/*******************Constructors And Destructor*****************/

template<typename K, typename V>
AvlTree<K,V>::AvlTree()
{
	//Initializing 
	root = nullptr;
	maxSubtreeSizes[nullptr] = -1;
}

template<typename K, typename V>
AvlTree<K,V>::~AvlTree()
{
	std::queue<TreeNode<K,V>*> nodesQueue;
	if(root != nullptr) nodesQueue.push(root);

	while (!nodesQueue.empty())
	{
		TreeNode<K,V>* node = nodesQueue.front();
		nodesQueue.pop();

		if (node->left != nullptr) nodesQueue.push(node->left);
		if (node->right != nullptr) nodesQueue.push(node->right);

		delete node;
	}
}

/*******************Methods******************/

template<typename K, typename V>
void AvlTree<K,V>::insert(K key, V val)
{
	root = insertIntoTree(key, val, root); //Inserting the value
}

template<typename K, typename V>
bool AvlTree<K,V>::remove(K key)
{
	TreeNode<K,V>* currentNode = root; //The node being processed;
	std::stack<TreeNode<K,V>*> traversedPath; //The nodes along the traversed path

	bool nodeFound = false;
	while (currentNode)
	{
		if (currentNode->key > key)
		{
			//Traversing the left subtree

			traversedPath.push(currentNode);
			currentNode = currentNode->left;
		}
		else if(currentNode->key < key)
		{
			//Traversing the right subtree

			traversedPath.push(currentNode);
			currentNode = currentNode->right;
		}
		else
		{
			//The required node was found
			nodeFound = true;

			//The node doesnt have any children
			if (!currentNode->left && !currentNode->right)
			{
				//The node can be removed without needing any further action

				//Repointing the parent node
				if (!traversedPath.empty())
				{
					if (traversedPath.top()->left == currentNode)
						traversedPath.top()->left = nullptr;
					else traversedPath.top()->right = nullptr;
				}
				else this->root = nullptr;

				//Recalculating the parent node's max subtree size
				if (!traversedPath.empty())
				{
					maxSubtreeSizes[traversedPath.top()] =
						std::max(0, maxSubtreeSizes[traversedPath.top()]);
				}

				maxSubtreeSizes.erase(maxSubtreeSizes.find(currentNode)); //Removing the node from the subtree sizes map
				delete currentNode; //Removing the node
				break;
			}
			//The node only has a left child
			else if (currentNode->left && !currentNode->right)
			{
				//The node is replaced by its left child

				//Repointing the parent node
				if (!traversedPath.empty())
				{
					if (traversedPath.top()->left == currentNode)
						traversedPath.top()->left = currentNode->left;
					else traversedPath.top()->right = currentNode->left;
				}
				else this->root = currentNode->left;

				//Recalculating the parent node's max subtree size
				if (!traversedPath.empty())
				{
					maxSubtreeSizes[traversedPath.top()] =
						std::max(maxSubtreeSizes[currentNode->left] + 1, maxSubtreeSizes[traversedPath.top()]);
				}

				maxSubtreeSizes.erase(maxSubtreeSizes.find(currentNode)); //Removing the node from the subtree sizes map
				delete currentNode; //Removing the node
				break;
			}
			//The node only has right child
			else if (!currentNode->left && currentNode->right)
			{
				//The node is replaced by its right child

				//Repointing the parent node
				if (!traversedPath.empty())
				{
					if (traversedPath.top()->left == currentNode)
						traversedPath.top()->left = currentNode->right;
					else traversedPath.top()->right = currentNode->right;
				}
				else this->root = currentNode->right;

				//Recalculating the parent node's max subtree size
				if (!traversedPath.empty())
				{
					maxSubtreeSizes[traversedPath.top()] =
						std::max(maxSubtreeSizes[currentNode->right] + 1, maxSubtreeSizes[traversedPath.top()]);
				}

				maxSubtreeSizes.erase(maxSubtreeSizes.find(currentNode)); //Removing the node from the subtree sizes map
				delete currentNode; //Removing the node
				break;
			}
			//The node has two children
			else if (currentNode->left && currentNode->right)
			{
				//Replacing the node with its inorder successor

				traversedPath.push(currentNode); //Adding the node to the path
				TreeNode<K,V>* inorderSucc = getInorderSuccessor(currentNode, traversedPath); /*The
				inorder successor of the node*/

				//Replacing the key and value of the node
				currentNode->key = inorderSucc->key;
				currentNode->val = inorderSucc->val;

				//The inorder successor will be removed
				//Repointing the parent node of the successor
				if (!traversedPath.empty())
				{
					if (traversedPath.top()->left == inorderSucc)
						traversedPath.top()->left = inorderSucc->right;
					else traversedPath.top()->right = inorderSucc->right;
				}
				else this->root = inorderSucc->right;

				//Recalculating the successor's parent node's max subtree size
				if (!traversedPath.empty())
				{
					maxSubtreeSizes[traversedPath.top()] =
						std::max(maxSubtreeSizes[inorderSucc->right] + 1, maxSubtreeSizes[traversedPath.top()]);
				}

				maxSubtreeSizes.erase(maxSubtreeSizes.find(inorderSucc)); //Removing the successor node from the subtree sizes map
				delete inorderSucc; //Removing the successor node
				break;

			}
		
		}

	}

	if (nodeFound)
	{
		//Rebalancing the tree

		while (!traversedPath.empty())
		{
			TreeNode<K,V>* node = traversedPath.top();
			traversedPath.pop();
			
			//Checking if the node is balanced
			if (!isBalanced(node))
			{
				TreeNode<K,V>* y = maxSubtreeSizes[node->left] + 1 > maxSubtreeSizes[node->right] + 1
					? node->left : node->right; //The larger height child of the node
				TreeNode<K,V>* x = maxSubtreeSizes[y->left] + 1 > maxSubtreeSizes[y->right] + 1 ?
					y->left : y->right; //The larger height child of y

				//Left case
				if (y == node->left)
				{
					//Left Left Case
					if (x == y->left)
					{
						//Right rotation at the node
						if (!traversedPath.empty())
						{
							if (traversedPath.top()->left == node)
								traversedPath.top()->left = rightRotation(node);
							else traversedPath.top()->right = rightRotation(node);
						}
						else this->root = rightRotation(node);

					}
					//Left right case
					else
					{
						//Left rotation at y
						node->left = leftRotation(y);

						//Right rotation at the node
						if (!traversedPath.empty())
						{
							if (traversedPath.top()->left == node)
								traversedPath.top()->left = rightRotation(node);
							else traversedPath.top()->right = rightRotation(node);
						}
						else this->root = rightRotation(node);
					}
				}
				//Right case
				else
				{
					//Right Right case
					if (x == y->right)
					{
						//Left rotation at the node
						if (!traversedPath.empty())
						{
							if (traversedPath.top()->left == node)
								traversedPath.top()->left = leftRotation(node);
							else traversedPath.top()->right = leftRotation(node);
						}
						else this->root = leftRotation(node);

					}
					//Right Left case
					else
					{
						//Right rotation at y
						node->right = rightRotation(y);

						//Left rotation at the node
						if (!traversedPath.empty())
						{
							if (traversedPath.top()->left == node)
								traversedPath.top()->left = leftRotation(node);
							else traversedPath.top()->right = leftRotation(node);
						}
						else this->root = leftRotation(node);
					}
				}
			}
		}
	}

	return nodeFound;
}

template<typename K, typename V>
V* AvlTree<K, V>::find(K key)
{
	TreeNode<K, V>* node = this->getNode(key); //The node with the given key

	return node == nullptr ? nullptr : &node->val;
}

/***************Private Functions********************/

template<typename K, typename V>
TreeNode<K,V>* AvlTree<K,V>::insertIntoTree(K& key, V& val, TreeNode<K,V>* root)
{
	if (root == nullptr) //Adding a node with the given value to the tree
	{
		root = new TreeNode<K,V>(key,val);
		maxSubtreeSizes[root] = 0; //The node's right and left subtrees are empty
		return root;
	}

	//Traversing the left subtree as the node key is greater than the key to be inserted
	if (root->key > key)
		root->left = insertIntoTree(key, val, root->left);
	//Traversing the right subtree as the node key is lower than the key to be inserted
	else
		root->right = insertIntoTree(key, val, root->right);

	//Setting the max-subtree-size for the node i.e the max distance from leaf for its left or right subtree
	maxSubtreeSizes[root] = (maxSubtreeSizes[root->left] + 1) * (maxSubtreeSizes[root->left] + 1 > maxSubtreeSizes[root->right] + 1)
		+ (maxSubtreeSizes[root->right] + 1) * (maxSubtreeSizes[root->left] + 1 <= maxSubtreeSizes[root->right] + 1);

	//Checking if the nodes is balanced
	if (!isBalanced(root))
	{
		//Left Subtree
		if (root->key > key)
		{
			//Left Left Case
			if (root->left->key > key)
				root = rightRotation(root);
			//Left Right Case
			else
			{
				root->left = leftRotation(root->left);
				root = rightRotation(root);
			}
		}
		//Right subtree
		else
		{
			//Right Right case
			if (root->right->key < key || (root->right->key == key && 
				maxSubtreeSizes[root->right->right]+1 > maxSubtreeSizes[root->right->left]+1))
				root = leftRotation(root);
			//Right Left case
			else
			{
				root->right = rightRotation(root->right);
				root = leftRotation(root);
			}
		}
	}

	return root;
}

template<typename K, typename V>
bool AvlTree<K,V>::isBalanced(TreeNode<K,V>* node)
{
	/*The node is balanced if the difference between the heights of its left and right 
	subtrees is less than or equal to 1*/

	return std::abs(maxSubtreeSizes[node->left] - maxSubtreeSizes[node->right]) <= 1;
}

template<typename K, typename V>
TreeNode<K,V>* AvlTree<K,V>::rightRotation(TreeNode<K,V>* node)
{
	TreeNode<K,V>* y = node->left;
	TreeNode<K,V>* b = y->right;

	y->right = node;
	node->left = b;

	maxSubtreeSizes[node] = maxSubtreeSizes[b] + 1 > maxSubtreeSizes[node->right] + 1 ?
		maxSubtreeSizes[b] + 1 : maxSubtreeSizes[node->right] + 1;
	maxSubtreeSizes[y] = maxSubtreeSizes[node] + 1 > maxSubtreeSizes[y->left] + 1 ?
		maxSubtreeSizes[node] + 1 : maxSubtreeSizes[y->left] + 1;

	return y;
}

template<typename K, typename V>
TreeNode<K,V>* AvlTree<K,V>::leftRotation(TreeNode<K,V>* node)
{
	TreeNode<K,V>* y = node->right;
	TreeNode<K,V>* a = y->left;

	y->left = node;
	node->right = a;

	maxSubtreeSizes[node] = maxSubtreeSizes[a] + 1 > maxSubtreeSizes[node->left] + 1 ?
		maxSubtreeSizes[a] + 1 : maxSubtreeSizes[node->left] + 1;
	maxSubtreeSizes[y] = maxSubtreeSizes[node] + 1 > maxSubtreeSizes[y->right] + 1 ?
		maxSubtreeSizes[node] + 1 : maxSubtreeSizes[y->right] + 1;

	return y;
}

template<typename K, typename V>
TreeNode<K,V>* AvlTree<K,V>::getInorderSuccessor(TreeNode<K,V>* node, 
	std::stack<TreeNode<K,V>*>& pathStack)
{
	node = node->right;
	while (node->left)
	{
		pathStack.push(node);
		node = node->left;
	}

	return node;
}

template<typename K, typename V>
TreeNode<K, V>* AvlTree<K, V>::getNode(K& key)
{
	TreeNode<K, V>* node = this->root; //The currently processed node

	//Searching for node
	while (node)
	{
		//The node was found
		if (node->key == key) return node;

		//Traversing the left subtree as the required key is smaller than the key of the node
		if (node->key > key) node = node->left;
		//Traversing the right subtree as the required key is greater than the key of the node
		else node = node->right;
	}

	//The node was not found
	return nullptr;
}