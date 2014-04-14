package merkletree;

import java.util.List;

/**
 * Represents a Merkle Tree leaf, consisting of a list
 * of blocks of arbitrary data. The arbitrary data in each block
 * is represented as a byte array.
 */
public class Leaf
{
	// The data to be stored in this node
	private final List<byte[]> dataBlock;
	
	/**
	 * Initialises the leaf node, which consists
	 * of the specified block of data.
	 * 
	 * @param dataBlock Data block to be placed in the leaf node
	 */
	public Leaf(final List<byte[]> dataBlock)
	{
		this.dataBlock = dataBlock;
	}
	
	/**
	 * @return The data block associated with this leaf node
	 */
	public List<byte[]> getDataBlock()
	{
		return (dataBlock);
	}

	/**
	 * Returns a string representation of the specified
	 * byte array, with the values represented in hex. The
	 * values are comma separated and enclosed within square
	 * brackets.
	 * 
	 * @param array The byte array
	 * 
	 * @return Bracketed string representation of hex values
	 */
	private String toHexString(final byte[] array)
	{
		final StringBuilder str = new StringBuilder();
		
		str.append("[");
		
		boolean isFirst = true;
		for(int idx=0; idx<array.length; idx++)
		{
			final byte b = array[idx];
			
			if (isFirst)
			{			
				//str.append(Integer.toHexString(i));
				isFirst = false;
			}
			else
			{
				//str.append("," + Integer.toHexString(i));
				str.append(",");
			}
			
			final int hiVal = (b & 0xF0) >> 4;
	        final int loVal = b & 0x0F;
	        str.append((char) ('0' + (hiVal + (hiVal / 10 * 7))));
	        str.append((char) ('0' + (loVal + (loVal / 10 * 7))));
		}
		
		str.append("]");
		
		return(str.toString());
	}
	
	/**
	 * Returns a string representation of the data block
	 */
	public String toString()
	{
		final StringBuilder str = new StringBuilder();
		
		for(byte[] block: dataBlock)
		{
			str.append(toHexString(block));
		}
		
		return(str.toString());
	}
	
}
