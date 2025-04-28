package tree;

import java.util.*;

public class OrgChartImpl implements OrgChart{

	//Employee is your generic 'E'..
	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	private GenericTreeNode<Employee> root;

	public void addRoot(Employee e) {
		if(root == null) {
			root = new GenericTreeNode<>(e);
			nodes.add(root);
		}
	}
	// if there is no orgchart, start it


	public void clear() {
		nodes = new ArrayList<>();
		root = null;
	}
	// get rid of the org chart

	public void addDirectReport(Employee manager, Employee newPerson) {

		for(int i =0; i < nodes.size(); i++) {
			GenericTreeNode<Employee> currentEmployee = nodes.get(i);
			if(currentEmployee.data.equals(manager)){
				GenericTreeNode<Employee> newEmployee = new GenericTreeNode<>(newPerson);

				currentEmployee.addChild(newEmployee); //adds child to current employee (manager)
				nodes.add(newEmployee);//add the new employee to the tree
				break;
			}

		}
	}
	// add the newPerson as a direct report (child) of manager

	public void removeEmployee(Employee firedPerson) {

		for(int i =0; i < nodes.size(); i++) {
			GenericTreeNode<Employee> currentEmployee = nodes.get(i);
			if (currentEmployee.data.equals(firedPerson)) { //find fired person in nodes list
				GenericTreeNode<Employee> parent = currentEmployee.parent; //get fired person's parent

				if(parent !=null){ //ensures fired person isnt the root
					for(GenericTreeNode<Employee> child : currentEmployee.children) { //goes through fired person children and adds them
						parent.addChild(child);
					}
					parent.removeChild(currentEmployee); //removes fired person
				}
				nodes.remove(i);
				break;
			}
		}

	}
	// remove the employee, give their direct reports to their supervisor

	public void showOrgChartDepthFirst() {
		System.out.println("\nOrg Chart (DFS) of the company is:");

		if(root == null) {
			System.out.println("Root is null");
			return;
		}
		Stack<GenericTreeNode<Employee>> stack = new Stack<>();
		ArrayList<Employee> alreadyVisited = new ArrayList<>();

		stack.push(root);
		while(!stack.isEmpty()) {
			GenericTreeNode<Employee> current = stack.pop();
			if(!alreadyVisited.contains(current.data)) {
				alreadyVisited.add(current.data);
				System.out.println(" - " + current.data);
			}

			for(int i = current.children.size()-1; i>= 0; i--) {
				stack.push(current.children.get(i));
			}
		}

	}

	public void showOrgChartBreadthFirst() {
	/*
Push the root node into the queue
For each level, Push the next-level nodes into the queue.
Add the nodes in the queue to a temp list at that particular level.
At the end of each traversal add the temp list to the result list
*/


		System.out.println("\nOrg Chart (BFS) of the company is:");

		Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();
		if (root == null) {
			System.out.println("Root is null");
			return;
		}

		queue.add(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.size();
			for (int i = 0; i < levelSize; i++) {
				GenericTreeNode<Employee> current = queue.poll();
				System.out.print(current.data + " ");
					if(i < levelSize - 1) { // This adds the dash mark between each persons information
						System.out.print(" - ");
					}
				for (GenericTreeNode<Employee> child : current.children) {
					queue.add(child);
				}
			}
			System.out.println();
		}
	}
}
