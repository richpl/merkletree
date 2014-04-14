package merkletree;

import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for the MerkleTree class.
 */

public class MerkleTreeTest 
{
	private MessageDigest md;

	private Leaf leftLeaf, rightLeaf;
	
	// Merkle tree consisting only of leaves
	private MerkleTree leafTree;
	
	// Merkle tree consisting of subtrees
	private MerkleTree merkleTree;
	
	private String leftLeafString, rightLeafString;
	
	@Before
	public void setUp() throws Exception 
	{
		try
		{
			// Define the message digest algorithm to use
			md = null;
			try 
			{
				md = MessageDigest.getInstance("SHA");
			} 
			catch (NoSuchAlgorithmException e) 
			{
				// Should never happen, we specified SHA, a valid algorithm
				assert false;
			}
			
			// Create some data blocks to be assigned to left leaf node
			final byte[] block1 = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
			final byte[] block2 = {(byte) 0xae, (byte) 0x45, (byte) 0x98, (byte) 0xff};
		
			// Create left leaf node containing these blocks
			List<byte[]> blocks1and2 = new ArrayList<byte[]>();
			blocks1and2.add(block1);
			blocks1and2.add(block2);
		
			leftLeaf = new Leaf(blocks1and2);
			
			leftLeafString = "[01,02,03,04][AE,45,98,FF]";
		
			// Create some data blocks to be assigned to right leaf node
			final byte[] block3 = {(byte) 0x99, (byte) 0x98, (byte) 0x97, (byte) 0x96};
			final byte[] block4 = {(byte) 0xff, (byte) 0xfe, (byte) 0xfd, (byte) 0xfc};
		
			// Create right leaf node containing these blocks
			List<byte[]> blocks3and4 = new ArrayList<byte[]>();
			blocks3and4.add(block3);
			blocks3and4.add(block4);
		
			rightLeaf = new Leaf(blocks3and4);
			
			rightLeafString = "[99,98,97,96][FF,FE,FD,FC]";
		
			// Create a tree containing only leaves
			leafTree = new MerkleTree(md);
			leafTree.add(leftLeaf, rightLeaf);
			
			// Create a tree containing two subtrees
			merkleTree = new MerkleTree(md);
			merkleTree.add(leafTree, leafTree);
		}
		catch (Exception e)
		{
			// Should not throw and exception
			assert false;
		}
	}

	@Test
	public final void testLeftTree() 
	{
		MerkleTree leftTree = merkleTree.leftTree();
		Leaf leftLeaf = leftTree.leftLeaf();
		Leaf rightLeaf = leftTree.rightLeaf();
		
		assertTrue(leftLeaf.toString().equals(leftLeafString));
		assertTrue(rightLeaf.toString().equals(rightLeafString));
	}

	@Test
	public final void testRightTree() 
	{
		MerkleTree rightTree = merkleTree.rightTree();
		Leaf leftLeaf = rightTree.leftLeaf();
		Leaf rightLeaf = rightTree.rightLeaf();
		
		assertTrue(leftLeaf.toString().equals(leftLeafString));
		assertTrue(rightLeaf.toString().equals(rightLeafString));
	}

	@Test
	public final void testLeftLeaf() 
	{
		Leaf leftLeaf = leafTree.leftLeaf();
		
		assertTrue(leftLeaf.toString().equals(leftLeafString));
	}

	@Test
	public final void testRightLeaf() 
	{
		Leaf rightLeaf = leafTree.rightLeaf();
		
		assertTrue(rightLeaf.toString().equals(rightLeafString));
	}

	@Test
	public final void testDigest() 
	{
		byte[] merkleTreeDigest = merkleTree.digest();
		
		byte[] testDigest = 
			{(byte) 0xA8, (byte) 0xA3, (byte) 0x36, (byte) 0x1D,
			 (byte) 0x40, (byte) 0x1C, (byte) 0xFD, (byte) 0xA3,
			 (byte) 0xDD, (byte) 0x26, (byte) 0xF1, (byte) 0x0B,
			 (byte) 0xB4, (byte) 0x0E, (byte) 0x1E, (byte) 0xC2,
			 (byte) 0xD1, (byte) 0xD8, (byte) 0x94, (byte) 0x6A};
		
		boolean isValid = true;
		for (int index=0; index<testDigest.length; index++)
		{
			if (merkleTreeDigest[index] != testDigest[index])
			{
				isValid = false;
			}
		}
		
		assertTrue("Merkle tree digest incorrect", isValid);
		
		byte[] leafTreeDigest = merkleTree.leftTree().digest();
			
		byte[] testLeafDigest  =
			{(byte) 0x90, (byte) 0xE0, (byte) 0xE8, (byte) 0xD5,
			 (byte) 0xC8, (byte) 0xCC, (byte) 0x1E, (byte) 0x9F,
			 (byte) 0xFC, (byte) 0x54, (byte) 0x16, (byte) 0x2A,
			 (byte) 0x1D, (byte) 0x2C, (byte) 0xAB, (byte) 0x0A,
			 (byte) 0x02, (byte) 0x33, (byte) 0x79, (byte) 0x2B};
		
		isValid = true;
		for (int index=0; index<testLeafDigest.length; index++)
		{
			if (leafTreeDigest[index] != testLeafDigest[index])
			{
				isValid = false;
			}
		}
		
		assertTrue("Leaf digest incorrect", isValid);
	}

}
