/**
 * PackageManager created by Weihang Guo on MacBook in p4
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class PackageManager {
    
    private Graph graph;//graph that represents packages
    private List<Package> packages; //a list to store all packages
	private List<String> visited = new ArrayList<String>();//a list of visited packages
    
	
	/*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
    	graph = new Graph();
    }
    

    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
    	packages = new ArrayList<Package>();//initiate the package list
    	JSONParser parser = new JSONParser();//create new JSON parser
    	FileReader reader = new FileReader(jsonFilepath);//create a FileReader for JSON file
    	Object obj = parser.parse(reader);//parse the JSON file reader into an object
    	JSONObject jo = (JSONObject) obj;//cast the object into a JSON object
    	//get the "packages" object and cast it into a JSON array
    	JSONArray packageArray = (JSONArray) jo.get("packages"); 
    	//add packages into the packageArray
    	for (int i = 0; i < packageArray.size(); ++i) {
			JSONObject jsonPackage = (JSONObject) packageArray.get(i);
			String name = (String) jsonPackage.get("name");
			JSONArray dependencies = (JSONArray) jsonPackage.get("dependencies");
			
			String[] pred = new String[dependencies.size()];
			for(int j = 0; j < dependencies.size(); ++j){
				pred[j] = (String) dependencies.get(j);
			}
			packages.add(new Package(name, pred));
		}

		// add all packages and edges to the graph
		for (int i = 0; i < packages.size(); ++i) {
			graph.addVertex(packages.get(i).getName());
			for (int j = 0; j < packages.get(i).getDependencies().length; ++j) {
				graph.addEdge(packages.get(i).getDependencies()[j], packages.get(i).getName());
			}
		}
		
		//add all the vertices
		for (String vertex: graph.getAllVertices()) {
			for (int i = 0; i <= packages.size(); ++i) {
				if (i == packages.size()) {
					packages.add(new Package(vertex, new String[0]));
				}
				if (packages.get(i).getName().equals(vertex)) {
					break;
				}
			}
		}
		
	}

    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
    	return graph.getAllVertices();
    }
    
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {

    	ArrayList<String> order = new ArrayList<String>(); // the ArrayList of the order
		
		// if package not exist in the graph
		if (!getAllPackages().contains(pkg)) {
			throw new PackageNotFoundException();
		}
		
		return installHelper(pkg, order);
		

    }
    
    /**
  	 * Helper method to get the Installation order for a given package
  	 * @param pkg the current package
  	 * @param order the installation order
  	 * @return the installation order
  	 * @throws CycleException if there is a cycle
  	 */
  	private ArrayList<String> installHelper(String pkg, ArrayList<String> order) throws CycleException{
  		Package current = new Package();//to store the current package
  		for(Package p: packages) {
			if(p.getName().equals(pkg)) {
				current = p;
			}
		}
  		
  		if(visited.contains(pkg)) {
  			throw new CycleException();
  		}
  		
  		visited.add(current.getName());
  		
  		//works as a queue
  		//if the vertex is in the list
  		//push it to the front
  		if (order.contains(current.getName())) {
  			order.remove(current.getName());
  		}
  		order.add(0, current.getName());
  		
  		
  		// empty  visited ArrayList
  		String[] predArray = current.getDependencies();
  		if (predArray.length == 0) {
  			visited = new ArrayList<String>();
  		}
  		
  		for(int i = 0; i < predArray.length; ++i) {
  			order = installHelper(predArray[i], order);
  		}
  		return order;
  	}

    
    
  
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
		if (!getAllPackages().contains(newPkg) || !getAllPackages().contains(installedPkg)) {
			throw new PackageNotFoundException();
		}
		
		List<String> installed = getInstallationOrder(installedPkg);//packages already installed
		List<String> all = getInstallationOrder(newPkg);//all packages needed to install the newPkg
		ArrayList<String> toBeInstalled = new ArrayList<String>();//packages to be installed
		//add the packages that are not in the installed list to the needed list
		for(String pckg: all) {
			if(!installed.contains(pckg)) {
				toBeInstalled.add(pckg);
			}
		}
		return toBeInstalled;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
    	Stack<String> stack = new Stack<>();//a stack that stores all the packages
		List<String> visited = new ArrayList<String>();//a list of visited packages
		List<String> order = new ArrayList<String>();//the installation order
		//add packages with no predecessors
		for (Package pckg: packages) {
			if(pckg.getDependencies().length == 0) {
				visited.add(pckg.getName());
				stack.push(pckg.getName());
			}
		}
		
		while (!stack.isEmpty()) {//when the stack is not empty
			String current = stack.peek();
			int numOfSuc = graph.getAdjacentVerticesOf(current).size();
			if (numOfSuc == 0 || visited.contains(graph.getAdjacentVerticesOf(current).get(numOfSuc -1))) { 
				for(String adj: graph.getAdjacentVerticesOf(current)) {
					if (stack.contains(adj)) {
						throw new CycleException();
						//throw CycleException if the stack already contains one of the current package's successor
					}
				}
				current = stack.pop();
				order.add(0, current);//add c into the order list
			}
			else { //if c has successors, mark each of them as visited and add to stack
				for(String suc : graph.getAdjacentVerticesOf(current)) {
					if(!visited.contains(suc)) {
						visited.add(suc);
						stack.push(suc);
						break;
					}
				}
			}
		}
		
		
		if(order.size() != packages.size()) {
			throw new CycleException();
		}
		
		return order;

    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {

		int max = 0; // max dependencies
		String maxName = null; 
		ArrayList<String> pred = new ArrayList<String>(); // ArrayList of the dependencies
		
		// find dependencies and store the value
		for(Package each: packages) {
			int predNum = maxHelper(each.getName(), pred).size() - 1;
			if (predNum >= max) {
				max = predNum;
				maxName = each.getName();
			}
			pred = new ArrayList<String>();
		}
		return maxName;

    }
    /**
  	 * Helper method to determine the max dependencies
  	 * @param pkg the current package
  	 * @param visited visited packages
  	 * @return visited list
  	 * @throws CycleException if detects a cycle
  	 */ 
    private List<String> maxHelper(String pkg, ArrayList<String> visited) throws CycleException{
		if(visited.contains(pkg)) {
			throw new CycleException();
		}
		Package current = new Package();//to store the current package
		for(Package p: packages) {
			if(p.getName().equals(pkg)) {
				current = p;
			}
		}
		// visit current's predecessors
		visited.add(pkg);
		String[] predArray = current.getDependencies();
		for(int i = 0; i < predArray.length; ++i) {
			visited = installHelper(predArray[i], visited);
		}
		return visited;
	}


  	/**
	 * Main method of the program
	 * 
	 * @param args the string arguments from the command line
	 */
    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
    }
    
}




