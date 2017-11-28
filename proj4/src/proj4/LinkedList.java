package proj4;

/**
 * Provides custom implementation of a recursive linked list 
 * that does not allow for null or duplicate elements as 
 * defined by the equals() method. A special contains method
 * returns the last index as a negative number if the given 
 * String is not found so that probes can still be counted 
 * for unfound words.
 * @author aehandlo
 *
 */
public class LinkedList {

	private ListNode front; 
	private int size;
	private int index;
	
	/**
	 * Constructor for LinkedListRecursive, initializes front to
	 * null and size to zero
	 */
	public LinkedList() {
		front = null;
		size = 0;
	}
	
	/**
	 * Returns whether the LinkedListRecursive is empty
	 * @return true if list is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Returns the size of the LinkedListRecursive
	 * @return size is the size of the list
	 */
	public int size() {
		if (front == null) {
			return 0;
		} else {
			return size;
		}
	}
	
	
	/**
	 * Adds the element to the LinkedListRecursive
	 * @param element is the element to be added 
	 * @return true if the element was added at the index,
	 * false otherwise
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the list
	 * already contains element
	 */
	public boolean add(String element) {
		if (element == null) {
			throw new NullPointerException();
		}
	
		if (isEmpty()) {
			front = new ListNode(element, front);
			size++;
			return true;
		} else {
			index = 0;
			if (front.contains(element, index) >= 0) {
				throw new IllegalArgumentException();
			}
			return front.add(element);
		
		}
	}
	
	/**
	 * Adds the element to the LinkedListRecursive at the given index
	 * @param element is the element to be added 
	 * @param idx is the index where the element should be added
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the list
	 * already contains element
	 * @throws IndexOutOfBoundsException is the index less than zero
	 * or greater than list size
	 */
	public void add(int idx, String element) {
		index = 0;
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (isEmpty()) {
			front = new ListNode(element, front);
			size++;
	
		} 
		else if (front.contains(element, index) >= 0) {
			throw new IllegalArgumentException();
			
		} 
		else if (idx == 0) {
			front = new ListNode(element, front);
			size++;
		} else {
			front.add(idx, element);
		}
		
	}
	
	/**
	 * Returns the element at the given index
	 * @param idx is the index of the element to be returned
	 * @return element at specified index
	 * @throws IndexOutOfBoundsException is the index less than zero
	 * or greater than list size
	 * 
	 */
	public String get(int idx) {
		
		if (idx < 0 || idx > size - 1) {
			throw new IndexOutOfBoundsException();
		} else if (idx == 0) {
			return front.data;
		} else {
			return front.get(idx);
		}
	}
	
	/**
	 * Removes the first occurrence of the given element in the list
	 * @param element is the element to be removed
	 * @return true if element is removed from list, false if not
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if list is empty
	 * @throws IllegalArgumentException if front is null
	 * 
	 */
	public boolean remove(String element) {
		if (element == null) {
			return false;
		} else if (isEmpty()) {
			return false;
		} else if (front == null) {
			throw new IllegalArgumentException();
		} else {
			if (element.equals(front.data)) {
				front = front.next;
				size--;
				return true;
				}
		} 
		
		return front.remove(element);
		
	}
	
	/**
	 * Removes the element in the list at the specified index
	 * @param idx is the index where element should be removed
	 * @return the element is removed from list
	 * @throws IndexOutOfBoundsException is the index less than zero
	 * or greater than list size - 1
	 * @throws IllegalArgumentException if list is empty
	 * @throws IllegalArgumentException if front is null
	 * 
	 */
	public String remove(int idx) {
		String e = null;
		if (idx < 0 || idx > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if (front == null) {
			throw new IllegalArgumentException();
		}
		
		if (isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if (idx == 0) {
			e = front.data;
			front = front.next;
			size--;
			return e;
		}
		 
			
		return front.remove(idx);
	}
	
	/**
	 * Checks to see whether list contains specified String.
	 * @param element is String to be searched for in list
	 * @return index of where element is in the list, 
	 * 			or negative length of list if element not found
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if list is empty
	 */
	public int contains(String element) {
		if (element == null) {
			throw new NullPointerException();
		}
		
		if (front == null) {
			throw new IllegalArgumentException();
		} else {
			index = 0;
			return front.contains(element, index);
		}
	}	
	
	
	/**
	 * Inner class which provides functionality for ListNode
	 * @author aehandlo
	 *
	 */
	private class ListNode {
		
		public String data;
		public ListNode next;
		
		/**
		 * Constructor for ListNode
		 * @param data is the data element for the node
		 * @param next is the link element for the node
		 */
		public ListNode(String data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Adds the element to the LinkedListRecursive at the given index
		 * @param element is the element to be added 
		 * @param idx is the index where the element should be added
		 */
		public void add(int idx, String element) {
			idx--;
			// if idx = 0, the next index is what we want to add to
			if (idx == 0) {
				next = new ListNode(element, next);
				size++;
			} else {
				next.add(idx, element);
			}
			
		}


		/**
		 * Checks to see whether list contains specified String.
		 * @param element is String to be searched for in list
		 * @return index of where element is in the list, 
		 * 			or negative length of list if element not found
		 */
		public int contains(String element, int index) {
			if (this.data.equals(element)) {
				return ++index;
			} else if (next == null) {
				return (index + 1) * -1;
			} else {
				index++;
				return next.contains(element, index);
			}

		}

		/**
		 * Adds the element to the LinkedListRecursive
		 * @param element is the element to be added 
		 * @return true if the element was added at the index,
		 * false otherwise
		 */
		public boolean add(String element) {
			if (next == null) {
				next = new ListNode(element, next);
				size++;
				return true;
			} else {
				return next.add(element);
			}
				
		}
		
		/**
		 * Returns the element at the given index
		 * @param idx is the index of the element to be returned
		 * @return element at specified index
		 * 
		 */
		public String get(int idx) {
			if (idx == 0) {
				return this.data;
			} else {
				idx--;
				return next.get(idx);
			}
		}
		
		/**
		 * Removes the first occurence of the given element in the list
		 * @param element is the element to be removed
		 * @return true if element is removed from list, false oter
		 * 
		 */
		public boolean remove(String element) {
			if (next == null) {
				return false;
			} else if (next.data.equals(element)) {
				next = next.next;
				size--;
				return true; 
			} else {
				return next.remove(element);
			}
		}
		
		/**
		 * Removes the element in the list at the specified index
		 * @param idx is the index where element should be removed
		 * @return the element is removed from list
		 */
		public String remove(int idx) {
			String e = null;
			idx--;
			if (idx == 0) {
				e = next.data;
				next = next.next;
				size--;
				return e;
			} else {
				return next.remove(idx);	
			}
		}
	}
}