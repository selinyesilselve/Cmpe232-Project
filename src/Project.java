
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 
 * @author ekinsilahlioglu,Kadir Akgül,Selin Yesilselve,Simge Erek, Ezgi Koc
 * FINAL ALGORITHM PROJECT
 *
 */

public class Project {
	
	  private static  ST<String, Integer> st1; //symbol table
	  private static ST<Integer,String> st2;
	  private String[] keys; // index -> String
	  public static Edge MyEdge;

	    public Project(String stream, String sp) {
	        st1 = new ST<String,Integer>();
	        In in = new In(stream); // First pass
	        while (in.hasNextLine()){
	        	
	            String[] a = in.readLine().split(sp); 
	            for (int i = 0; i < a.length-1; i++){
	            	
	                if (!st1.contains(a[i])){
	                    st1.put(a[i], st1.size()); 
	                }
	            }
	        }
	    }//end of the constructor
	    
	    
	    
	   
	    public static ST<Integer,String> ProjectReverse() {
	        st2 = new ST<Integer,String>(); 
	           for(String key : st1.keys()) {
	               st2.put(st1.get(key), key);
	            }
	             return st2;
	   }//end of the consrtuctor
	    
	  
	  
	    
	    
	    public static Hashtable<Edge, Integer> CountShortestPath(EdgeWeightedGraph ewg){
	    	Hashtable<Edge,Integer> mySPATH =new Hashtable<Edge,Integer>();
	    	for (int i = 0; i < ewg.V(); i++) {
				DijkstraUndirectedSP sp = new DijkstraUndirectedSP(ewg, i);
				for (int j = 0; j < ewg.V(); j++) {	
					if(sp.hasPathTo(j)) {	//looking if there is any path between the source vertex and its target vertex	
						for(Edge edge :sp.pathTo(j)) { //looking for target vertices edges
							if(!mySPATH.containsKey(edge)){
									mySPATH.put(edge, 1); //if does not exists put 1
							}
							if(mySPATH.containsKey(edge)){
									mySPATH.put(edge, mySPATH.get(edge) + 1); //if it is already exist add 1
							}
						}
					}
				}
			}
	    	return mySPATH;
	    }//end of method
	    
	
	    public static void main(String[] args) {
	    	
	    	
			Project p = new Project("BabelText.txt",","); //reading data.txt file
	    	ProjectReverse(); //for id -> String	
			EdgeWeightedGraph ewg = new EdgeWeightedGraph(st1.size());

		    //Edge Weighted Graph
			In in = new In("BabelText.txt"); //reading text file
	        while (in.hasNextLine()){
	            String[] temp = in.readLine().split(",");  //split each word when it sees comma.
	        	int source= st1.get(temp[0]);
	            for (int i = 1; i < temp.length-1; i++){
	    			Edge e =new Edge(source, st1.get(temp[i]), Double.parseDouble(temp[i+1])); //we create an edge that takes source, target and their weight .
	    			ewg.addEdge(e)  ;                                                         //Then we add it into the edgeweighted graph. 
	            }	       
	        }
	      
	        System.out.println("ALL SHORTEST PATHS");
	        System.out.println("******************************************************************************************************************");
	        System.out.println("");
	          //All shortes Paths
	          for(int i = 0;i<st1.size();i++) { //printing each vertex, its target vertex and their edge weight 
			        int s = i;
			        System.out.println("SOURCE : " + st2.get(i));
			        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(ewg, s);
			        for (int t = 0; t < ewg.V(); t++) {
			            if (sp.hasPathTo(t)) {
			                StdOut.printf("%s to %s (%.2f)  ",st2.get(s),st2.get(t), sp.distTo(t));
			                for (Edge e : sp.pathTo(t)) { //find the path between two vertices.
			                    StdOut.print(e.toString2(st2) + "   ");
			                }
			                StdOut.println();
			            }
			            else {
			                StdOut.printf("%s to %s         no path\n", st2.get(s),st2.get(t));
			            }
			         }
			        	System.out.println();
			        
			        }//end of for loop
	           System.out.println("");
	           System.out.println("******************************************************************************************************************");
	           System.out.println("");
	           
	          for(int r = 1; r<=9;r++){ 
	        	  
	           int find = 0 ;
	           int max = 0;
	           Object[] myArray = CountShortestPath(ewg).values().toArray(); //storing values
	           for(int x = 0;x<myArray.length;x++){
	        	   if(max < Integer.parseInt(myArray[x].toString())){
	        		   max = Integer.parseInt(myArray[x].toString()); //find the maximum value(that means the number of shortest paths passing through that edge)
	        		   find = x;
	        	   }
	           }
	           System.out.println("Max shortest path number : " + max);
	           System.out.println("Its index no : " + find);
	           
	          
	           int IndexNO = 0 ;
	           int result = 0;
	           for(Edge key : CountShortestPath(ewg).keySet()){ //looking for the index number 
	    	       if(CountShortestPath(ewg).get(key) == max){ 
	       			result = IndexNO;
	       		}
	       		IndexNO = IndexNO + 1;
	        }//end of for loop
	       
	        EdgeWeightedGraph ewgNew = new EdgeWeightedGraph(st1.size());
	       
	           int counterBegins  = 0;
	           for(Edge key : CountShortestPath(ewg).keySet()){
	    	       if(find==counterBegins) {
	    	         System.out.println(key.toString2(st2) + " Counter : " + counterBegins);
	    	         }
	    	   if(counterBegins == result){
	    		     counterBegins = counterBegins + 1;
	    		     continue;
	    		 }
	    	    ewgNew.addEdge(new Edge(key.either(), key.other(key.either()), key.weight())); //creating new graph and we are not going to add the edge we want to remove
	    	    counterBegins = counterBegins + 1;
	       }
	        
	       System.out.println("OLD GRAPH SIZE: " + CountShortestPath(ewg).size());
	       System.out.println("NEW GRAPH SIZE: " + CountShortestPath(ewgNew).size());
	       
	       ewg = ewgNew; //change the old graph to new one
	       
	     
	        
	        System.out.println("--------------------------------------------");
	     
	        //All Shortes Pathes For Every New Graph
	        /*  for(int i = 0;i<st1.size();i++) { //printing each vertex, its target vertex and their edge weight 
		        int s = i;
		        System.out.println("SOURCE : " + st2.get(i));
		        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(ewg, s);
		        for (int t = 0; t < ewg.V(); t++) {
		            if (sp.hasPathTo(t)) {
		                StdOut.printf("%s to %s (%.2f)  ",st2.get(s),st2.get(t), sp.distTo(t));
		                
		                for (Edge e : sp.pathTo(t)) { //find the path between two vertices.
		                    StdOut.print(e.toString2(st2) + "   ");
		                }
		                StdOut.println();
		            }
		            else {
		              //  StdOut.printf("%s to %s        no path\n", st2.get(s),st2.get(t));
		            }
		            
		        }
		        	System.out.println();
		            
		        }*/
	
	       }
			
	
		}//end of main

}//end of class     