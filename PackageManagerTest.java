/**
 * PackageManagerTest created by Weihang Guo on MacBook in p4
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

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * PackageManagerTest - test the methods in PackageManager
 * @author Weihang Guo
 * 
 */
public class PackageManagerTest {
	
	protected PackageManager manager;
	/**
     * the code runs before every test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    	manager = new PackageManager();
    }

    /** 
     * Tests that the PackageManager constructs graph with a valid input correctly
     */
    @Test
    public void test000_constructGraphWithValidInput() {
    	try {
    		manager.constructGraph("valid.json");
    		Assert.assertEquals(manager.getAllPackages().size(), 5);
    	} catch (Exception e) {
            fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the PackageManager constructs graph with an invalid input correctly
     */
    @Test
    public void test001_constructGraphWithInvalidInput() {
    	try {
    		manager.constructGraph("vali.json");
    		fail("should throw FileNotFoundException");
    	} catch (FileNotFoundException e) {
            //expected
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the PackageManager gets the installation order without cycle correctly
     */
    @Test
    public void test002_getInstallationOrder() {
    	try {
    		manager.constructGraph("valid.json");
    		List<String> order = manager.getInstallationOrder("A");
    		for (String pkg : order) {
    			System.out.print(pkg + "  ");
    		}
    		Assert.assertEquals(order.size(), 4);
    		Assert.assertEquals(order.get(0), "D");
    		Assert.assertEquals(order.get(1), "C");
    		Assert.assertEquals(order.get(2), "B");
    		Assert.assertEquals(order.get(3), "A");
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the PackageManager's toInstall method works correctly
     */
    @Test
    public void test003_toInstall() {
    	try {
    		manager.constructGraph("valid.json");
    		List<String> order = manager.toInstall("A", "C");
    		Assert.assertEquals(order.size(), 3);
    		Assert.assertEquals(order.get(0), "D");
    		Assert.assertEquals(order.get(1), "B");
    		Assert.assertEquals(order.get(2), "A");
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the PackageManager gets the installation order with a cycle correctly
     */
    @Test
    public void test004_getCyclicInstallationOrder() {
    	try {
    		manager.constructGraph("cyclic.json");
    		manager.getInstallationOrder("A");
    		fail("should throw CycleException");
    	} catch (CycleException e) {
    		//expected
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }

    /** 
     * Tests that the PackageManager's getInstallationOrderForAllPackages method works correctly
     */
    @Test
    public void test005_getInstallationOrderForAllPackages() {
    	try {
    		manager.constructGraph("shared_dependencies.json");
    		List<String> order = manager.getInstallationOrderForAllPackages();
    		Assert.assertEquals(order.size(), 4);
    		Assert.assertEquals(order.get(0), "D");
    		Assert.assertEquals(order.get(1), "C");
    		Assert.assertEquals(order.get(2), "B");
    		Assert.assertEquals(order.get(3), "A");
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }
    
    /** 
     * Tests that the PackageManager's getPackageWithMaxDependencies method works correctly
     */
    @Test
    public void test006_getPackageWithMaxDependencies() {
    	try {
    		manager.constructGraph("shared_dependencies.json");
    		Assert.assertEquals(manager.getPackageWithMaxDependencies(), "A");
    	} catch (Exception e) {
    		fail(e.getClass().getName());
    	}
    }

}
