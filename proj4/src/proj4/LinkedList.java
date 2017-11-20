package proj4;


import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Custom implementation of a linked list that doesn't allow for null elements
 * or duplicate elements. Extends AbstractSequentialList meaning it utilizes
 * an iterator for traversing, adding, removing and setting.
 * 
 * @author aehandlo
 * @param <String> Generic data type
 *
 */
public class LinkedList extends AbstractSequentialList<String> {

	/** Front node that is always null */
	private ListNode front;
	/** Last node that is always null */
	private ListNode back;
	/** Number of elements in the list */
	private int size;
	/**
	 * Constructor to initialize instance variables
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	@Override
	public ListIterator<String> listIterator(int index) {
		return new LinkedListIterator(index);
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractSequentialList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, String element) {
		if(this.contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractSequentialList#set(int, java.lang.Object)
	 */
	@Override
	public String set(int index, String element) {
		if(this.contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	@Override
	public int size() {
		return size;
	}
	/**
	 * Defines a node of linked list to have data, a link to
	 * the next node, and a link to the previous node
	 * @author aehandlo
	 *
	 */
	private class ListNode {
		public String data;
		public ListNode next;
		public ListNode prev;
		/**
		 * Constructor to initialize new node given no prev or next
		 * @param data Generic data being held by the node
		 */
		public ListNode(String data) {
			this.data = data;
			next = null;
			prev = null;
		}
		/**
		 * Constructor to initialize new node given prev and next
		 * @param data Generic data being held by node
		 * @param prev Previous node in the list
		 * @param next Next node in the list
		 */
		public ListNode(String data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	/**
	 * Iterator used to traverse, add, remove and set nodes
	 * @author aehandlo
	 *
	 */
	private class LinkedListIterator implements ListIterator<String> {
		/** Previous node relative to the iterator's position */
		public ListNode previous;
		/** Next node relative to the iterator's position */ 
		public ListNode next;
		/** Index of previous node */
		public int previousIndex;
		/** Index of next node */
		public int nextIndex;
		/** Node that was last retrieved by next() or previous() */
		private ListNode lastRetrieved;
		/**
		 * Constructor to initialize iterator at a given position in the list
		 * @param index Index to place the iterator before
		 * @throws IndexOutOfBoundsException if index is not a legal index
		 */
		public LinkedListIterator(int index) {
			if(index < 0 || index > size && size > 0 || index != size && size == 0) {
				throw new IndexOutOfBoundsException();
			}
			
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
			
			ListNode current = front;
			do {
				previous = current;
				current = current.next;
				index--;
			} while(index >= 0);
			next = current;
		}
		@Override
		public void add(String element) {
			if(element == null) {
				throw new NullPointerException();
			}
			next.prev = new ListNode(element, previous, next);
			previous.next = next.prev;
			next = next.prev;
			lastRetrieved = null;
			size++;
		}

		@Override
		public boolean hasNext() {
			if(next.data != null) {
				return true;
			}
			return false;
		}

		@Override
		public boolean hasPrevious() {
			if(previous.data != null) {
				return true;
			}
			return false;
		}

		@Override
		public String next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			previous = next;
			next = next.next;
			previousIndex++;
			nextIndex++;
			return lastRetrieved.data;
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public String previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = previous;
			previous = previous.prev;
			previousIndex--;
			nextIndex--;
			return lastRetrieved.data;
		}

		@Override
		public int previousIndex() {
			return previousIndex;
		}

		@Override
		public void remove() {
			if(lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			lastRetrieved = null;
			size--;
		}

		@Override
		public void set(String element) {
			if(lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			if(element == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = element;
			
		}
		
	}

}
