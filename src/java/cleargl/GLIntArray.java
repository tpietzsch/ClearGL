package cleargl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class GLIntArray
{
	private final int mNumberOfElements;
	private final int mElementSize;
	private final IntBuffer mIntBuffer;

	public GLIntArray(int pNumberOfElements, int pElementSize)
	{
		super();
		mNumberOfElements = pNumberOfElements;
		mElementSize = pElementSize;
		mIntBuffer = ByteBuffer.allocateDirect(pNumberOfElements * pElementSize
																							* 4)
															.order(ByteOrder.nativeOrder())
															.asIntBuffer();
	}

	public void add(int... pElementInts)
	{
		for (int lInt : pElementInts)
		{
			mIntBuffer.put(lInt);
		}
	}

	public void rewind()
	{
		mIntBuffer.rewind();
	}

	public IntBuffer getIntBuffer()
	{
		rewind();
		return mIntBuffer;
	}

	public int getNumberOfElements()
	{
		return mNumberOfElements;
	}

	public int getElementSize()
	{
		return mElementSize;
	}

}