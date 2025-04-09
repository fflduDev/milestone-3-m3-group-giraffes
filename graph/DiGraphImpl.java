package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 

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
		return null;
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		return null;
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		return null;
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		return 0;
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return List.of();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		return null;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		return null;
	}

	@Override
	public Boolean hasCycles() {
		return null;
	}

	@Override
	public List<GraphNode> getNodes() {
		return List.of();
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
