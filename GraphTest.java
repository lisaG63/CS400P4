/**
 * GraphTest created by Weihang Guo on MacBook in p4
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
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * GraphTest - test the methods in Graph
 * @author Weihang Guo
 * 
 */
public class GraphTest {

	protected Graph graph;
    
	
    /**
     * the code runs before every test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	graph = new Graph();
    }
    

    
    /** 
     * Tests that the graph adds a vertex correctly
     */
    @Test
    public void test000_addVertex() {
    	try {
    		graph.addVertex("A");
    		Assert.assertEquals(graph.getAllVertices().size(), 1);
    	} catch (Exception e) {
            fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the graph adds an edge correctly
     */
    @Test
    public void test001_addEdge() {
    	try {
    		graph.addVertex("A");
    		Assert.assertEquals(graph.getAllVertices().size(), 1);
        	graph.addEdge("A", "B");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	Assert.assertEquals(graph.getAdjacentVerticesOf("A").size(), 1);
        	Assert.assertEquals(graph.getAdjacentVerticesOf("A").get(0), "B");
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    	
    }
    
    
    /** 
     * Tests that the graph removes a vertex correctly
     */
    @Test
    public void test002_removeVertex() {
    	try {
    		graph.addVertex("A");
    		Assert.assertEquals(graph.getAllVertices().size(), 1);
        	graph.addEdge("A", "B");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	graph.removeVertex("A");
        	Assert.assertEquals(graph.getAllVertices().size(), 1);
        	Assert.assertEquals(graph.getAdjacentVerticesOf("A").size(), 0);
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    	
    }
    
    
    /** 
     * Tests that the graph removes an existing edge correctly
     */
    @Test
    public void test003_removeExistingEdge() {
    	try{
    		graph.addVertex("A");
    		Assert.assertEquals(graph.getAllVertices().size(), 1);
        	graph.addEdge("A", "B");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	graph.removeEdge("A", "B");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	Assert.assertEquals(graph.getAdjacentVerticesOf("A").size(), 0);
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    	
    }
    
    /** 
     * Tests that the graph removes a nonexistent edge correctly
     */
    @Test
    public void test004_removeNonexistentEdge() {
    	try{
    		graph.addVertex("A");
    		Assert.assertEquals(graph.getAllVertices().size(), 1);
        	graph.addEdge("A", "B");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	graph.removeEdge("B", "A");
        	Assert.assertEquals(graph.getAllVertices().size(), 2);
        	Assert.assertEquals(graph.getAdjacentVerticesOf("A").size(), 1);
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    	
    }
	

}
