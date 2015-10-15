package org.odyssey.api;

public class DataNode {

	/*
	 * Each element in the linked list will be a node structure which contains 
	 * 'val' and 'nextNode' fields.
	 * 
	 * Where,
	 * 		key - stores the key, which can be matched while retrieval 
	 * 		val - stores an Object/ intended data.
	 * 		nextNode - points to the next dataNode in the linked list.
	 * 
	 */
	String key;
	Object val;
	DataNode nextNode;

	// Create a single Data Node with no nextNode
	public DataNode(String key, Object val) {
		nextNode = null;
		this.val = val;
		this.key = key;
	}

	// Create a new node and attach it to the nextNode
	public DataNode(String key, Object val, DataNode nextNode) {
		this.val = val;
		this.nextNode = nextNode;
		this.key = key;
	}

	
	// getter and setter methods
	public Object getVal() {
		return val;
	}

	public void setVal(Object val) {
		this.val = val;
	}

	public DataNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(DataNode nextNode) {
		this.nextNode = nextNode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
