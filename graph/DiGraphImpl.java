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
		// Search through the nodeList for a node matching the given nodeValue
		for (GraphNode node : nodeList) {
			// Compare node's value with the provided value
			if (node.getValue().equals(nodeValue)) {
				// If match found, return this node
				return node;
			}
		}
		// If no node matches, return null
		return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		Queue<GraphNode> queue = new LinkedList<>(); // Queue for BFS
		Map<GraphNode, Integer> hops = new HashMap<>();// Track # of hops to reach each node
		Set<GraphNode> visited = new HashSet<>();// Track visited nodes

		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) return -1;

		// Initialize BFS
		queue.add(start);
		hops.put(start, 0); // 0 hops to itself
		visited.add(start);

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();

			// If we found the destination node
			int currentHops = hops.get(current);

			if (current == end) {
				return currentHops;
			}

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					queue.add(neighbor);
					visited.add(neighbor);
					hops.put(neighbor, currentHops + 1);
				}
			}
		}

		return -1; // if unreachable
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		Map<GraphNode, Integer> distance = new HashMap<>();
		PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparingInt(distance::get));
		Set<GraphNode> visited = new HashSet<>();

		GraphNode start = getNode(fromNode.getValue());
		GraphNode end = getNode(toNode.getValue());

		if (start == null || end == null) return -1;

		for (GraphNode node : nodeList) {
			distance.put(node, Integer.MAX_VALUE);
		}

		distance.put(start, 0);
		queue.add(start);

		while (!queue.isEmpty()) {
			GraphNode current = queue.poll();

			if (current == end) {
				return distance.get(current);
			}

			visited.add(current);

			for (GraphNode neighbor : current.getNeighbors()) {
				if (!visited.contains(neighbor)) {
					int newDist = distance.get(current) + current.getDistanceToNeighbor(neighbor);
					if (newDist < distance.get(neighbor)) {
						distance.put(neighbor, newDist);
						queue.add(neighbor);
					}
				}
			}
		}

		return -1; // if unreachable
	}
}
