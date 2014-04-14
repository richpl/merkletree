package merkletree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import merkletree.Leaf;
import merkletree.MerkleTree;

/**
 * Test class to build and print a Merkle Tree.
 */
public class TreeBuilder 
{
	/**
	 * Main method creates a simple Merkle Tree consisting of 
	 * two subtrees, each with two leaf nodes, each of these consisting
	 * of two data blocks. Then pretty prints the tree to show its structure.
	 * 
	 * @param noargs
	 */
	public static void main(String[] noargs) 
	{
		// Define the message digest algorithm to use
		MessageDigest md = null;
		try 
		{
			md = MessageDigest.getInstance("SHA");
		} 
		catch (NoSuchAlgorithmException e) 
		{
			// Should never happen, we specified SHA, a valid algorithm
			assert false;
		}

		// Create some data blocks to be assigned to leaf nodes
		final byte[] block1 = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
		final byte[] block2 = {(byte) 0xae, (byte) 0x45, (byte) 0x98, (byte) 0xff};
		final byte[] block3 = {(byte) 0x5f, (byte) 0xd3, (byte) 0xcc, (byte) 0xe1};
		final byte[] block4 = {(byte) 0xcb, (byte) 0xbc, (byte) 0xc4, (byte) 0xe2};
		final byte[] block5 = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
		final byte[] block6 = {(byte) 0xae, (byte) 0x45, (byte) 0x98, (byte) 0xff};
		final byte[] block7 = {(byte) 0x5f, (byte) 0xd3, (byte) 0xcc, (byte) 0xe1};
		final byte[] block8 = {(byte) 0xcb, (byte) 0xbc, (byte) 0xc4, (byte) 0xe2};
		
		// Create leaf nodes containing these blocks
		final List<byte[]> blocks1and2 = new ArrayList<byte[]>();
		blocks1and2.add(block1);
		blocks1and2.add(block2);
		
		final List<byte[]> blocks3and4 = new ArrayList<byte[]>();
		blocks3and4.add(block3);
		blocks3and4.add(block4);
		
		final List<byte[]> blocks5and6 = new ArrayList<byte[]>();
		blocks5and6.add(block5);
		blocks5and6.add(block6);
		
		final List<byte[]> blocks7and8 = new ArrayList<byte[]>();
		blocks7and8.add(block7);
		blocks7and8.add(block8);
		
		final Leaf leaf1 = new Leaf(blocks1and2);
		final Leaf leaf2 = new Leaf(blocks3and4);
		final Leaf leaf3 = new Leaf(blocks5and6);
		final Leaf leaf4 = new Leaf(blocks7and8);
		
		// Build up the Merkle Tree from the leaves
		final MerkleTree branch1 = new MerkleTree(md);
		branch1.add(leaf1, leaf2);
		
		final MerkleTree branch2 = new MerkleTree(md);
		branch2.add(leaf3, leaf4);
		
		final MerkleTree merkleTree = new MerkleTree(md);
		merkleTree.add(branch1, branch2);
		
		// Return the digest for the entire tree
		merkleTree.prettyPrint();
	}

}
