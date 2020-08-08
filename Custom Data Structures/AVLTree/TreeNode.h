
template<typename K, typename V>
struct TreeNode //The struct representing the nodes of the tree
{
	K key; //The key of the node
	V val; //The value stored in the node
	TreeNode<K,V>* left; //The left child of the node
	TreeNode<K,V>* right; //The right child of the node
	int (*cmp)(K&, K&); //The function to be used for comparing node keys

	TreeNode(K key, V val,int (*compare)(K&, K&) = nullptr)
	{
		this->key = key;
		this->val = val;
		this->left = nullptr;
		this->right = nullptr;
		this->cmp = compare;
	}
	
	TreeNode(K key, V val, TreeNode<K,V>* left, TreeNode<K,V>* right, int (*compare)(K&, K&) = nullptr)
	{
		this->key = key;
		this->val = val;
		this->left = left;
		this->right = right;
		this->cmp = compare;
	}

	bool operator==(TreeNode<K,V>& node)
	{
		return (cmp != nullptr && cmp(this->key, node.key) == 0) || 
			(cmp == nullptr && this->key == node.key);
	}

	bool operator<(TreeNode<K,V>& node)
	{
		return (cmp != nullptr && cmp(this->key, node.key) < 0) || 
			(cmp == nullptr && this->key < node.key);
	}

	bool operator>(TreeNode<K,V>& node)
	{
		return (cmp != nullptr && cmp(this->key, node.key) > 0) || 
			(cmp == nullptr && this->key > node.key);
	}
};