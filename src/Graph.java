/**
 *Graph.java is a class to creat undirected random graphs
 * that was used for the project of CSE 101
 *@author J.Tang
 **/

public class Graph{

  private int[][] edgeMatrix;
  private int numberOfVertices;
  private boolean weighted;

  /**
   *Constructs an graph with n vertices and no edges
   *@param vertices the number of vertices (must be greater than 0)
   *@param weighted true if the graph is to be weighted
   *@param directed true if the graph is directed
   **/
  private Graph(int vertices, boolean weighted){
    if(vertices<1) numberOfVertices = 0;
    else numberOfVertices = vertices;
    edgeMatrix = new int[numberOfVertices][numberOfVertices];
    this.weighted = weighted;
  }

 /**
   *Constructs an unwieghted, undirected graph with n vertices and no edges
   *@param vertices the number of vertices (must be greater than 0)
   **/
  Graph(int vertices){
    this(vertices, false);
  }

  /**
   *Adds an edge of the given weight to the graph.
   *If the graph is undirected the reverse edge is also added.
   *If the graph is unweighted the weight is set to one.
   *If an edge already exists it is overwritten with the new weight.
   *@param u the start of the edge
   *@param v the end of the edge
   *@param weight the weight of the edge
   **/
  private void addEdge(int u, int v, int weight){
    if(0<=u&&0<=v&&u<=numberOfVertices&&v<=numberOfVertices){
      if(weight<1) weight = 0;
      else if(!weighted) weight = 1;
      edgeMatrix[u][v] = weight;
      edgeMatrix[v][u] = weight;
    }
    else throw new RuntimeException("Vertex out of bounds");
  }

 /**
   *Adds an edge of weight 1 to the graph.
   *If the graph is undirected the reverse edge is also added.
   *@param u the start of the edge
   *@param v the end of the edge
   **/
  private void addEdge(int u, int v){
    this.addEdge(u,v,1);
  }

  /**
   *Removes the edge from the graph (sets the edge weight to 0).
   *If the graph is undirected the reverse edge is also removed.
   *@param u the start of the edge
   *@param v the end of the edge
   **/
  private void removeEdge(int u, int v){
    this.addEdge(u,v,0);
  }

  /**
   *@return the weight of an edge.
   *@param u the start of the edge
   *@param v the end of the edge
   **/
  public int getWeight(int u, int v){
    if(0<=u&&0<=v&&u<=numberOfVertices&&v<=numberOfVertices)
      return edgeMatrix[u][v];
    else throw new RuntimeException("Vertex out of bounds");
  }

  /**
   *@return the number of vertices
   **/
  public int getNumberOfVertices(){
    return numberOfVertices;
  }

  /**
   *@return true if the graph is directed
   **/

  /**
   *@return true if the graph is weighted
   **/
  public boolean isWeighted(){
    return weighted;
  }

  /**
   *@return a clone of the edgeMatrix
   **/
  public int[][] getEdgeMatrix(){
    int[][] clone = new int[edgeMatrix.length][edgeMatrix.length];
	for(int i = 0; i<edgeMatrix.length; i++)
		clone[i] = (int[])edgeMatrix[i].clone();
	return clone;	
  }
  
  /**
   *Creates a random unweighted graph
   *@return a random graph of the specified density
   *@param numberOfVertices the nukmber of vertices
   *@param density between 0 and 1, the probablity of an edge existing.
   **/
  public static Graph randomGraph(int numberOfVertices, double density){
    Graph g = new Graph(numberOfVertices);
    for(int i = 0; i<g.getNumberOfVertices(); i++){
      for(int j = 0; j<i+1; j++){
		if(Math.random()<density){
		  g.edgeMatrix[i][j] = 1;
		  g.edgeMatrix[j][i] = 1;
	}
      }
    }
    return g;
  }


  /**
   *Creates a random weighted graph. The weights will be even distributed between 1 and
   *the maximum edge weight (inclusive).
   *@return a random graph of the specified density
   *@param numberOfVertices the nuxkmber of vertices
   *@param directed true if the graph is to be directed
   *@param maxWeight the maximum edgeWeight for the Graph.
   *@param density between 0 and 1, the probablity of an edge existing.
   **/
  public static Graph randomWeightedGraph(int numberOfVertices, double density,int maxWeight){
	Graph g = new Graph(numberOfVertices, true);
    for(int i = 0; i<g.getNumberOfVertices(); i++){
      for(int j = 0; j<i+1; j++){
		if(Math.random()<density){
			g.edgeMatrix[i][j] = (int)(Math.random()*(maxWeight-1))+1;
			g.edgeMatrix[j][i] = g.edgeMatrix[i][j];
		}
      }
    }
    return g;
  }


  /**
   *Creates a random weighted bipartite graph. The weights will be even distributed between 1 and
   *the maximum edge weight (inclusive), the vertices will be partitioned into odd and even vertices
   *@return a random graph of the specified density
   *@param numberOfVertices the nuxkmber of vertices
   *@param directed true if the graph is to be directed
   *@param maxWeight the maximum edgeWeight for the Graph.
   *@param density between 0 and 1, the probablity of an edge existing.
   **/
  public static Graph randomWeightedBipartiteGraph(int numberOfVertices, boolean directed, double density,int maxWeight){
	Graph g = new Graph(numberOfVertices, true);
    for(int i = 0; i<g.getNumberOfVertices(); i+=2){
      for(int j = (directed?1:i+1); j<g.getNumberOfVertices(); j+=2){
		if(Math.random()<density){
			g.edgeMatrix[i][j] = (int)(Math.random()*(maxWeight-1))+1;
			if(!directed) g.edgeMatrix[j][i] = g.edgeMatrix[i][j];
		}
      }
    }
    return g;
  }


  /**
   *Creates a random unweighted bipartite graph
   *@return a random graph of the specified density
   *@param numberOfVertices the nukmber of vertices
   *@param directed true if the graph is to be directed
   *@param density between 0 and 1, the probablity of an edge existing.
   **/
  public static Graph randomBipartiteGraph(int numberOfVertices, boolean directed, double density){
    Graph g = new Graph(numberOfVertices, false);
    for(int i = 0; i<g.getNumberOfVertices(); i+=2){
      for(int j = (directed?1:i+1); j<g.getNumberOfVertices(); j+=2){
	if(Math.random()<density){
	  g.edgeMatrix[i][j] = 1;
	  if(!directed) g.edgeMatrix[j][i] = 1;
	}
      }
    }
    return g;
  }
  /** reads an adjacency matrix from a file and returns it.  The
   * adjacency matrix is assumed to have the following format in
   * the file:
   * n 
   * 0 6 0 0 ... 0
   * 0 0 0 3 ... 0
   * ...
   * 1 0 7 0 ... 0
   * where the first line contains an integer that denotes the dimensions
   * of the square matrix of integers that follow, and there are n integers
   * (tab separated) on each of the subsequent lines.
   *@author Du Huynh (modifed by Tim French)
   *@return A graph read fro the file.
   *@param filename the name of the file to be read
   *@param weighted true if the Graph is to be weighted
   *@param directed true if the graph is to be directed
   */
  public static Graph readFile(String filename, boolean weighted, boolean directed) throws Exception{
    String delim = " \t";		// the delimiters (spaces and tabs)
    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.FileReader(filename));
    String s = in.readLine();

    /* read in the number of vertices */
    int n = Integer.parseInt(s);
    
    Graph g = new Graph(n, weighted);
    for (int i=0; i < n; i++) {
      java.util.StringTokenizer stoken = new java.util.StringTokenizer(in.readLine().trim(), delim);
      for (int j=0; j < n; j++) {
			String subs = stoken.nextToken();
			g.addEdge(i,j,Integer.parseInt(subs));
      }
    }
    in.close();
    return g;
  }

  /**
   *This method produces a representation of the
   *graph that corresponds to the adjacency 
   *matrix used by the readFile method.
   *@return a String representation of the graph,
   **/
  public String toString(){
    StringBuffer s = new StringBuffer(getNumberOfVertices()+"\n");
    for(int i = 0; i<numberOfVertices; i++){
      for(int j = 0; j<numberOfVertices; j++){
		s.append(edgeMatrix[i][j]);
		s.append("\t");
      }
      s.append("\n");
    }
    return s.toString();
  }

}