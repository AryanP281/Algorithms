#pragma once

/******************Preprocessor Directives******************/
#include "TreeNode.h"
#include<unordered_map>
#include <stack>

template<typename K, typename V> class AvlTree
{
private :
	/***************Private Variables*****************/
	TreeNode<K, V>* root; //The root of the tree
	std::unordered_map<TreeNode<K,V>*, int> maxSubtreeSizes; //The size of the largest subtrees of the nodes

	/***************Private Functions********************/
	TreeNode<K,V>* insertIntoTree(K& key, V& val, TreeNode<K,V>* root); /*Inserts a new node with the given
	key and value in the tree*/
	bool isBalanced(TreeNode<K,V>* node); //Checks if a node is balanced
	TreeNode<K,V>* leftRotation(TreeNode<K,V>* node); //Performs left rotation at the given node
	TreeNode<K,V>* rightRotation(TreeNode<K,V>* node); //Performs right rotation at the given node
	TreeNode<K,V>* getInorderSuccessor(TreeNode<K,V>* node, std::stack<TreeNode<K,V>*>& pathStack); /*
	Finds and returns the inorder successor of the given node*/
	TreeNode<K, V>* getNode(K& key); /*Returns pointer to the node with the given value.
	Returns null pointer if node is not found*/

public : 
	/*******************Constructors And Destructor*****************/
	AvlTree();
	~AvlTree();

	/*******************Methods******************/
	void insert(K key, V val); //Adds a new node with the given key and value to the tree
	bool remove(K key); //Removes the node with the given key from the tree
	V* find(K key); /*Returns pointer to the value of the node with the given key.
	Returns null pointer if key is not found*/
};
