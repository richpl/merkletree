merkletree
==========

A simple Java implementation of Merkle Trees, with Ant build file and JUnit test classes

The leaves of the tree are represented by Leaf objects, each of which can contain an arbitrarily long list of blocks of data. Each block is represented as an array of bytes.

Non-leaf nodes are represented by MerkleTree objects, each of which consists of a message digest or hash, and two child nodes. The digest algorithm is specified when the tree is created. The child nodes can either be Leaf objects, or further MerkleTree objects (i.e. subtrees).

A TreeBuilder class is provided that builds an example MerkleTree object, and uses the pretty print method provided by MerkleTree to print out the resulting tree.

JUnit test suites are provided to exercise the Leaf and MerkleTree classes.
