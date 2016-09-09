import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class bst{
    
    Node root;

    /*
     * Backbone of the binary search tree. Holds the keyword and records with that keyword.
     */
    private class Node{
    	
        String keyword;
        Record record;
        Node l;
        Node r;
        Node parent;

        private Node(String k){
        	keyword = k;
        }

        private void update(Record r){
        	r.next = record;
        	record = r;
        }
        
        public String toString() {
        	return String.format("%s", keyword);
        }
    }

    public bst(){
        this.root = null;
    }
    
    /*
     * Inserts a file into a tree with a given keyword.
     * @param keyword the keyword of the file
     * @param fd the file data to add to the tree
     */
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        if (root == null) {
        	root = new Node(keyword);
        	root.update(recordToAdd);
        } else {
        	insert(keyword, recordToAdd, root);
        }
    }
    
    /*
     * Helper insert method. Recursive method that inserts the Record into the tree with a
     * given keyword. 
     * @param keyword the keyword of the file
     * @param recordToAdd the record that will be added to the tree
     * @param curr the current node
     */
    private void insert(String keyword, Record recordToAdd, Node curr) {
    	if (curr.keyword.equals(keyword)) {
    		curr.update(recordToAdd);
    	} else if (curr.keyword.compareTo(keyword) > 0) {
    		if (curr.l == null) {
    			curr.l = new Node(keyword);
    			curr.l.parent = curr;
    		}
    		insert(keyword, recordToAdd, curr.l);
    	} else {
    		if (curr.r == null) {
    			curr.r = new Node(keyword);
    			curr.r.parent = curr;
    		}
    		insert(keyword, recordToAdd, curr.r);
    	}
    }
    
    /*
     * Returns true if the tree contains a node with the given keyword.
     * @param keyword the keyword to search for
     * @return true if the keyword was found in the tree
     */
    public boolean contains(String keyword){
    	Node curr = root;
		if (curr == null) {
			return false;
		}
		while (!curr.keyword.equals(keyword)) {
    		if (keyword.compareTo(curr.keyword) < 0) {
    			curr = curr.l;
    		} else {
    			curr = curr.r;
    		}
    		if (curr == null) {
    			return false;
    		}
    	}
		return true;
    }

    /*
     * Returns all the records that have the given keyword
     * @param keyword the keyword to search for
     * @return linked list of records with the given keyword
     */
    public Record get_records(String keyword){
    	Node curr = root;
		if (curr == null) {
			return null;
		}
		while (!curr.keyword.equals(keyword)) {
    		if (keyword.compareTo(curr.keyword) < 0) {
    			curr = curr.l;
    		} else {
    			curr = curr.r;
    		}
    		if (curr == null) {
    			return null;
    		}
    	}
    	return curr.record;
    }

    /*
     * Deletes all the records with the given keyword.
     * @param keyword the keyword to delete
     */
    public void delete(String keyword){
    	Node toDelete = findNode(keyword);
    	if (toDelete != null) {
    		delete(toDelete);
    	}
    }
    
    /*
     * Helper method for the delete method. Finds the node with the keyword.
     * @param keyword the keyword to look for
     * @return the node with a given keyword, null if it is not found
     */
    private Node findNode(String keyword) {
    	Node curr = root;
		if (curr == null) {
			return null;
		}
		while (!curr.keyword.equals(keyword)) {
    		if (keyword.compareTo(curr.keyword) < 0) {
    			curr = curr.l;
    		} else {
    			curr = curr.r;
    		}
    		if (curr == null) {
    			return null;
    		}
    	}
		return curr;
    }
    
    /*
     * Helper delete method. Deletes the given node from the tree.
     * @param curr the node to delete from the tree
     */
    private void delete(Node curr) {
    	if (curr.l == null && curr.r == null) {
    		if (curr == root) {
    			root = null;
    		} else if (curr.parent.l == curr) {
    			curr.parent.l = null;
    		} else {
    			curr.parent.r = null;
    		}
			curr.parent = null;
    	} else if (curr.l != null && curr.r != null) {
	    	Node replacement = getReplacementNode(curr);
	    	swap(replacement, curr);
	    	if (curr == root) {
	    		root = replacement;
	    	}
	    	delete(curr);
    	} else {
    		Node child;
    		if (curr.l != null) {
    			child = curr.l;
    		} else {
    			child = curr.r;
    		}
    		if (curr == root) {
    			root = null;
    			if (curr.l != null) {
    				root = curr.l;
    			} else {
    				root = curr.r;
    			}
    		} else {
	    		if (curr.parent.l == curr) {
	    			curr.parent.l = child;
	    		} else {
	    			curr.parent.r = child;
	    		}
	    		curr.parent = null;
    		}
    	}
    }
    
    /*
     * Helper method for the delete method. Swaps two nodes.
     * @param one the first node
     * @param two the second node
     */
    private void swap(Node one, Node two) {
    	Node tempP = one.parent;
    	Node tempL = one.l;
    	Node tempR = one.r;
    	if (two.parent != null && two.parent != one) {
	    	if (two.parent.l == two) {
	    		two.parent.l = one;
	    	} else {
	    		two.parent.r = one;
	    	}
    	}
    	if (one.l != null && one.l != two) {
    		one.l.parent = two;
    	}
    	if (one != two.l) {
    		one.l = two.l;
    	} else {
    		one.l = two;
    	}
    	if (one.r != null && one.r != two) {
    		one.r.parent = two;
    	}
    	if (one != two.r) {
    		one.r = two.r;
    	} else {
    		one.r = two;
    	}
    	if (one != two.parent) {
    		one.parent = two.parent;
    	} else {
    		one.parent = two;
    	}
    	if (two.l != null && two.l != one) {
    		two.l.parent = one;
    	}
    	if (two != tempL) {
    		two.l = tempL;
    	} else {
    		two.l = one;
    	}
    	if (two.r != null && two.r != one) {
    		two.r.parent = one;
    	}
    	if (two != tempR) {
    		two.r = tempR;
    	} else {
    		two.r = one;
    	}
    	if (two != tempP) {
    		two.parent = tempP;
    	} else {
    		two.parent = one;
    	}
    	if (tempP != null && two != tempP) {
    		if (tempP.l == one) {
    			tempP.l = two;
    		} else {
    			tempP.r = two;
    		}
    	}
    }
    
    /*
     * Helper method for the delete method. Returns the minimum node in the right subtree of
     * the given node.
     * @param node the node to find the minimum in the right subtree
     * @return the minimum node of the right subtree of the given node
     */
    private Node getReplacementNode(Node node) {
    	Node replacement = node.r;
    	while (replacement.l != null) {
    		replacement = replacement.l;
    	}
    	return replacement;
    }

    /*
     * Returns a List of all the nodes in the tree.
     * @return a List of all the nodes in the tree
     */
    public List<Node> getAllNodes() {
    	return getAllNodes(root);
    }
    
    /*
     * Helper getAllNodes method. Returns a List of all nodes in the given node's left
     * and right subtree
     * @param n the node to get the subtrees of
     * @return a List of all the nodes in the subtree of the given node
     */
    private List<Node> getAllNodes(Node n) {
    	if (n == null) {
    		return new ArrayList<Node>();
    	}
    	String s = n.keyword;
    	List<Node> toReturn = new ArrayList<Node>();
    	toReturn.add(n);
    	toReturn.addAll(getAllNodes(n.l));
    	toReturn.addAll(getAllNodes(n.r));
    	return toReturn;
    }
    
   /*
    * Prints all the records in the tree in alphabetical order according to
    * their keywords.
    */
    public void print(){
        print(root);
    }

    /*
     * Helper print method. Prints the in-order traversal of the given node (left, 
     * node, right).
     * @param t the node to print the in-order traversal of
     */
    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
    
    /*
     * Prints the tree in a binary tree format.
     */
    public void printTreeFormat() {
    	printTreeFormat(0, root);
    }
    
    /*
     * Helper printTreeFormat method. Prints the current node then recursively prints
     * the left and right subtrees.
     * @param depth the current depth in the tree
     * @param curr the current node to print
     */
    private void printTreeFormat(int depth, Node curr) {
    	if (curr == null) {
    		System.out.println("( )");
    	} else {
	    	System.out.println(curr);
	    	for (int i = 0; i < depth; i++) {
	    		System.out.print("   ");
	    	}
	    	System.out.print(" R-");
	    	printTreeFormat(depth+1, curr.r);
	    	for (int i = 0; i < depth; i++) {
	    		System.out.print("   ");
	    	}
	    	System.out.print(" L-");
	    	printTreeFormat(depth+1, curr.l);
    	}
    }
}
