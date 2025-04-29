package graph;

import java.util.*;


public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();


	@Override
	public Boolean addNode(GraphNode node) {
		for (GraphNode n : nodeList) {
			if (n.getValue().equals(node.getValue())) {
				return false;
			}
		}
		nodeList.add(node);
		return true;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		Iterator<GraphNode> iterator = nodeList.iterator();
		while (iterator.hasNext()) {
			GraphNode n = iterator.next();
			if (n.getValue().equals(node.getValue())) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		if (node.getValue().equals(newNodeValue)) {
			return false;
		}
		for (GraphNode n : nodeList) {
			if (n.getValue().equals(node.getValue())) {
				n.setValue(newNodeValue);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getNodeValue(GraphNode node) {

		for (GraphNode n : nodeList) {
			if (n.getValue().equals(node.getValue())) {
				return n.getValue();
			}
		}
		return null;
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		if (fromNode.getValue().equals(toNode.getValue())) {
			return false;
		}
		if (nodeList.contains(fromNode) && nodeList.contains(toNode)){
			fromNode.addNeighbor(toNode, weight);
			return true;
		}
		return false;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		if (fromNode.getValue().equals(toNode.getValue())) {
			return false;
		}
		if (nodeList.contains(fromNode) && nodeList.contains(toNode)){
			fromNode.removeNeighbor(toNode);
			return true;
		}
		return false;
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		if (nodeList.contains(fromNode) && nodeList.contains(toNode)){
			fromNode.addNeighbor(toNode, newWeight);
			return true;
		}
		return false;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getDistanceToNeighbor(toNode);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getNeighbors().contains(toNode);
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		Set<GraphNode> visited = new HashSet<GraphNode>();


		//start from the targetFromNode

		queue.add(fromNode);
		visited.add(fromNode);

		//for all neighbors:
		//check if visited.  If not, add to the queue.
		//if targetToNode has been visited, return true

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					if (neighbor == toNode) {
						return true;
					}
					visited.add(neighbor);
					queue.add(neighbor);
				}
			}
		}

		//if u get here
		return false;
	}

	@Override
	public Boolean hasCycles() {
		Set<GraphNode> visited = new HashSet<>();
		Set<GraphNode> recStack = new HashSet<>();

		Stack<GraphNode> stack = new Stack<>();
		Map<GraphNode, Iterator<GraphNode>> neighborsMap = new HashMap<>();

		for (GraphNode startNode : nodeList) {
			if (!visited.contains(startNode)) {
				stack.push(startNode);
				neighborsMap.put(startNode, startNode.getNeighbors().iterator());
				recStack.add(startNode);
				visited.add(startNode);

				while (!stack.isEmpty()) {
					GraphNode current = stack.peek();
					Iterator<GraphNode> neighbors = neighborsMap.get(current);

					if (neighbors.hasNext()) {
						GraphNode neighbor = neighbors.next();
						if (!visited.contains(neighbor)) {
							visited.add(neighbor);
							recStack.add(neighbor);
							stack.push(neighbor);
							neighborsMap.put(neighbor, neighbor.getNeighbors().iterator());
						} else if (recStack.contains(neighbor)) {
							return true; // Found a cycle
						}
					} else {
						recStack.remove(current);
						stack.pop();
					}
				}
			}
		}

		return false;
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		return 0;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		return 0;
	}
}
