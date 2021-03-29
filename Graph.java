
/**
 * Graph created by Weihang Guo on MacBook in p4
 * 
 * Author: Weihang Guo(wguo63@wisc.edu)
 * Date:   @4.9
 * 
 * Course: CS400
 * Semester: Spring 2020
 * Lecture: 002
 * 
 * IDE: Eclipse IDE for Java Developers
 * Version: 2019-12(4.14.0)
 * Build id: 20191212-1212
 * 
 * Device: LisaG's MACBOOK
 * OS: macOS Mojave
 * Version: 10.14.4
 * OS Build: 1.8 GHz Intel Core i5
 * 
 * List Collaborators: None
 * 
 * Other Credits: None
 * 
 * Known Bugs: None
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    Weihang Guo
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
	
	List<Graphnode> vertexList;//list of all vertices, in order added
	int size;//the number of edges
	
	/**
	 * Graphnode - subclass which represents a node in a graph
	 * @author Weihang Guo
	 * 
	 */
	private class Graphnode {
		String data;
		private List<Graphnode> adjacent;
		
		private Graphnode(String data) {
			this.data = data;
			adjacent = new ArrayList<Graphnode>();
		}
		
		private String getData() {
			return data;
		}
		
		private void addAdjacent(Graphnode adjacentNode) {
			adjacent.add(adjacentNode);
		}
		
		private List<Graphnode> getAdjacent() {
			return adjacent;
		}

	}
	
	

	
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		vertexList = new ArrayList<Graphnode>();
		size = 0;
	}
	
	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     * 
     * @param vertex the vertex to be added
     */
	@Override
	public void addVertex(String vertex) {
		if (vertex == null || exist(vertex)) {
			return;
		}
		Graphnode node = new Graphnode(vertex);
		vertexList.add(node);
		
	}

	
	/**
	 * Determine if a vertex exists in the graph
	 * @param vertex the given vertex
	 * @return true if the vertex exists, false if the vertex does not exist
	 */
	private boolean exist(String vertex) {
		for (int i = 0; i < vertexList.size(); i ++) {
			if (vertexList.get(i).getData().equals(vertex)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     *  
     * @param vertex the vertex to be removed
     */
	@Override
	public void removeVertex(String vertex) {
		if (vertex == null || !exist(vertex)) {
			return;
		}
		
		for (int i = 0; i < vertexList.size(); i ++) {
			
			if (vertexList.get(i).getData().equals(vertex)) {
				size = size - vertexList.get(i).getAdjacent().size();
				vertexList.remove(vertexList.get(i));
			} else {
				for (int j = 0; j < vertexList.get(i).getAdjacent().size(); j ++) {
					if (vertexList.get(i).getAdjacent().get(j).getData().equals(vertex)) {
						vertexList.get(i).getAdjacent().remove(vertexList.get(i).getAdjacent().get(j));
						size --;
					}
				}
			}
			
		}

		
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * 
     * If either vertex does not exist,
     * VERTEX IS ADDED and then edge is created.
     * No exception is thrown.
     *
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
     *  
     * @param vertex1 the first vertex (src)
     * @param vertex2 the second vertex (dst)
     */
	@Override
	public void addEdge(String vertex1, String vertex2) {
		if (vertex1 == null || vertex2 == null) {
			return;
		}
		if (!exist(vertex1)) {
			addVertex(vertex1);
		}
		if (!exist(vertex2)) {
			addVertex(vertex2);
		}
		for (int i = 0; i < vertexList.size(); i ++) {
			if (vertexList.get(i).getData().equals(vertex1)) {
				for (int j = 0; j < vertexList.size(); j ++) {
					if (vertexList.get(j).getData().equals(vertex2)) {
						for (int c = 0; c < vertexList.get(i).getAdjacent().size(); c ++) {
							if (vertexList.get(i).getAdjacent().get(c).getData().equals(vertex2)) {
								return;
							}
						}
						vertexList.get(i).addAdjacent(vertexList.get(j));
						size ++;
					}
				}
				
			}
		}
		
	}

	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     *  
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
	@Override
	public void removeEdge(String vertex1, String vertex2) {
		if (vertex1 == null || vertex2 == null || (!exist(vertex1)) || (!exist(vertex2))) {
			return;
		}
		for (int i = 0; i < vertexList.size(); i ++) {
			if (vertexList.get(i).getData().equals(vertex1)) {
				for (int j = 0; j < vertexList.get(i).getAdjacent().size(); j ++) {
					if (vertexList.get(i).getAdjacent().get(j).getData().equals(vertex2)) {
						vertexList.get(i).getAdjacent().remove(vertexList.get(i).getAdjacent().get(j));
						size --;
					}
				}
			}
		}
		
	}

	/**
     * Returns a Set that contains all the vertices
     * 
     * @return a Set<String> which contains all the vertices in the graph
     */
	@Override
	public Set<String> getAllVertices() {
		// TODO Auto-generated method stub
		Set<String> vertices = new HashSet<String>();
		for (int i = 0; i < vertexList.size(); i ++) {
			vertices.add(vertexList.get(i).getData());
		}
		return vertices;
	}

	/**
     * Get all the neighbor (adjacent-dependencies) of a vertex
     * 
     * For the example graph, A->[B, C], D->[A, B] 
     *     getAdjacentVerticesOf(A) should return [B, C]. 
     * 
     * In terms of packages, this list contains the immediate 
     * dependencies of A and depending on your graph structure, 
     * this could be either the predecessors or successors of A.
     * 
     * @param vertex the specified vertex
     * @return an List<String> of all the adjacent vertices for specified vertex
     */
	@Override
	public List<String> getAdjacentVerticesOf(String vertex) {
		List<String> adjacentVertices = new ArrayList<String>();
		for (int i = 0; i < vertexList.size(); i ++) {
			if (vertexList.get(i).getData().equals(vertex)) {
				for (int j = 0; j < vertexList.get(i).getAdjacent().size(); j ++) {
					adjacentVertices.add(vertexList.get(i).getAdjacent().get(j).getData());
				}
			}
		}
		return adjacentVertices;
	}

	/**
     * Returns the number of edges in this graph.
     * @return number of edges in the graph.
     */
	@Override
	public int size() {
		return size;
	}
	
	/**
     * Returns the number of vertices in this graph.
     * @return number of vertices in graph.
     */
	@Override
	public int order() {
		return vertexList.size();
	}


}
