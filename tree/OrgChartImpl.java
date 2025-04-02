package tree;

import java.util.*;

public class OrgChartImpl implements OrgChart{

	//Employee is your generic 'E'..
	private List<GenericTreeNode<Employee>> nodes = new ArrayList<>();
	private GenericTreeNode<Employee> root;

	public void addRoot(Employee e) {
		if(root == null) {
			GenericTreeNode<Employee> newRoot = new GenericTreeNode<>(e);
			nodes.add(newRoot);
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

	}
	// remove the employee, give their direct reports to their supervisor

	public void showOrgChartDepthFirst() {
		System.out.println("Org Chart (DFS) of the company is:");
		Queue<GenericTreeNode<Employee>> queue = new LinkedList<>();
		ArrayList<Employee> alreadyVisited= new ArrayList<>();

		for(int i = 0; i <nodes.size(); i++) {
			queue.add(nodes.get(i));

			while(!queue.isEmpty()) {
				GenericTreeNode<Employee> currentEmployee = queue.remove();

			if(!alreadyVisited.contains(currentEmployee.data)) {
				alreadyVisited.add(currentEmployee.data);
				}
			if(currentEmployee.children.size() > 0) {
				queue.addAll(currentEmployee.children);
				}
			}

		}
		for(Employee e: alreadyVisited){
			System.out.println(e);
		}

	}

	public void showOrgChartBreadthFirst() {
	}

}
