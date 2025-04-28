package tree;
import java.util.ArrayList;
import java.util.List;

public class GenericTreeNode<E> {
	E data;
	GenericTreeNode<E> parent;
	List<GenericTreeNode<E>> children;
	
	public GenericTreeNode(E theItem) {
		data = theItem;
		children = new ArrayList<>();
		parent = null;
	}
	
	public void addChild(GenericTreeNode<E> child) {
		if(child != null && !this.children.contains(child)) {
			this.children.add(child);
			child.setParent(this);
		}

	}
	
	public void removeChild(GenericTreeNode<E> child) {
		if(child != null && this.children.contains(child)) {
			this.children.remove(child);
			child.setParent(null);
		}


	}

	public void transferChildrenToParent() {
		if (parent != null) {
			for (GenericTreeNode<E> child : children) {
				child.setParent(parent);
				parent.addChild(child);
			}
			parent.removeChild(this);
		}
	}
	public E getData() {
		return data;
	}

	public List<GenericTreeNode<E>> getChildren() {
		return children;
	}

	public GenericTreeNode<E> getParent() {
		return parent;
	}

	public void setParent(GenericTreeNode<E> parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return data.toString();
	}
} 
