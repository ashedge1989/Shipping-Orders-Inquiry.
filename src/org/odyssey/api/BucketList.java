package org.odyssey.api;

/*
 * The objects which have similar 'hash code' will land in same buckets. 
 * The objects inside this bucket have been stored as a linked list (here known as BucketList) 
 */
public class BucketList {

	private DataNode head;
	private int size;

	
	// A constructor to initialize the List
	public BucketList() {
		head = new DataNode(null, null);
		size = 0;
	}

	
	
	// Remove an element from a particular location in the List.  
	public boolean remove(int index){
		if (index < 1 || index > getSize()){ // No element at these locations. 
			return false;
		}else{
			// first traverse the List up to that index. 
			DataNode current = head;
			for (int index1 = 1; index1 < index; index1++) {
				if (current.getNextNode() == null){
					return false;
				}
				current = current.getNextNode();
			}
			// remove the element at intended index  
			current.setNextNode(current.getNextNode().getNextNode());
			size--; 
			return true;
		}
	}


	
	// Add a element to the end of the List
	public void add(String key, Object data){
		DataNode newNode = new DataNode(key, data);
		DataNode curr = head;
		// Traverse to the end of the list
		while (curr.getNextNode() != null) {
			curr = curr.getNextNode();
		}
		curr.setNextNode(newNode);
		// The total element increases by 1. 
		size++;
	}

	
	
	// Retrieve/Get a value from the List at specific position. 
	public Object get(int index){
		if (index <= 0){
			return null;
		}else{	
			DataNode current = head.getNextNode();
			for (int index1 = 1; index1 < index; index1++) {
				if (current.getNextNode() == null){
					return null;
				}
				current = current.getNextNode();
			}
		return current;
		}
	}
	
	
	
	// getter and setter methods
	public DataNode getHead() {
		return head;
	}

	public void setHead(DataNode head) {
		this.head = head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	

}