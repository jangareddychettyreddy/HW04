package com.abc.sync;

/**
 * Implementation of {@link LongFifo} which uses a circular array internally.
 * <p>
 * Look at the documentation in LongFifo to see how the methods are supposed to
 * work.
 * <p>
 * The data is stored in the slots array. count is the number of items currently
 * in the FIFO. When the FIFO is not empty, head is the index of the next item
 * to remove. When the FIFO is not full, tail is the index of the next available
 * slot to use for an added item. Both head and tail need to jump to index 0
 * when they "increment" past the last valid index of slots (this is what makes
 * it circular).
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/Circular_buffer">Circular Buffer
 * on Wikipedia</a> for more information.
 */
public class CircularArrayLongFifo implements LongFifo {
	// do not change any of these fields:
	private final long[] slots;
	private int head;
	private int tail;
	private int count;
	private final Object lockObject;

	private boolean flag = false;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private RemoveResult flag4 = null;
	private long counter;
	private int z;

	// this constructor is correct as written - do not change
	public CircularArrayLongFifo(int fixedCapacity, Object proposedLockObject) {

		lockObject = proposedLockObject != null ? proposedLockObject : new Object();

		slots = new long[fixedCapacity];
		head = 0;
		tail = 0;
		count = 0;
	}

	// this constructor is correct as written - do not change
	public CircularArrayLongFifo(int fixedCapacity) {
		this(fixedCapacity, null);
	}

	// this method is correct as written - do not change
	@Override
	public int getCount() {
		synchronized (lockObject) {
			return count;
		}
	}

	@Override
	public boolean isEmpty() {
		
		if (count == 0) {
			if (slots[head] == 0 && slots[tail] == 0) {
				flag = true;
			}
		} else
			flag = false;
		return flag;
	}

	@Override
	public boolean isFull() {
		
		if (head==tail&& count > 0) {
			flag2 = true;
		}
		return flag2;
	}

	@Override
	public void clear() {

		for (int z = tail; z < head; z++) {
			remove();
		}
	}

	@Override
	public int getCapacity() {
		return slots.length;
	}

	@Override
	public boolean add(long value) {

		if (isFull() == false) {
			slots[head] = value;
			count++;
			head++;
			for (z = 0; z < count; z++) {
				System.out.println("\n"+slots[z] + " number is at position " + z);
			}
			System.out.println("Head position is at " + head + " and count is: " + count + "\n");
			flag3 = true;
		} else
			flag3 = false;
		return flag3;
	}

	@Override
	public LongFifo.RemoveResult remove() {
		if (isEmpty() == false && count == 0) {
			counter = slots[0];
		} else {
			counter = slots[tail];

		}
		System.out.println("\n"+"Removed number is: " + counter +" and tail position is at "+tail);
		tail++;
		count--;
		flag4 = LongFifo.RemoveResult.createValid(counter);
		return flag4;
	}

	// this method is correct as written - do not change
	@Override
	public Object getLockObject() {
		return lockObject;
	}
}
