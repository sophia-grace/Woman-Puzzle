/*
Name: Sophia Trump
File: Graph.java
Description: A Graph abstract datatype, implemented using a HashMap,
where the keys are the vertices and the values are the edges (i.e., a list of
edges that are adjacent to the key/vertex). Populates the Graph and allows
users to query states.
*/

import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Graph {
  // attributes
  private Map<String, ArrayList<String>> adjList;
  int N;

  // constructor
  Graph(URL urlString) {
    adjList = new HashMap<String, ArrayList<String>>();
    N = 0;

    // populate the graph
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(urlString.openStream()));

      String inputLine;
      // skip the metadata
      in.readLine();
      // read each line in the dataset
      while ((inputLine = in.readLine()) != null) {
          // split the line into its pieces
          // separate the state from its neighbors
          String pieces[] = inputLine.split(",", 2);

          // add the state (vertex) to the Graph
          insertVertex(pieces[0]);

          // increment N, since a vertex has been added
          N++;

          // as long as the state has neighbors, than add its edges to the
          // graph
          if(!pieces[1].equals("")) {
            // separate the border states
            String borderStates[] = pieces[1].split(", ");
            for(int i = 0; i < borderStates.length; i++) {
              // remove quotes from each of the pieces
              borderStates[i] = borderStates[i].replaceAll("\"", "");
              // add the edges for that state
              insertEdge(pieces[0], borderStates[i]);
            }
          }
      }
      in.close();

    } catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
  } // Graph()

  public void insertVertex(String vertex) {
    // add the vertex to the Graph, with no edges yet
    adjList.put(vertex, new ArrayList<String>());
  } // insertVertex()

  public void insertEdge(String start, String end) {
    // first make sure that start and end are already
    // edges that are present in the Graph
    // If not, then add them
    if(!hasVertex(start)) {
      insertVertex(start);
    }
    if(!hasVertex(end)) {
      insertVertex(end);
    }

    // both vertices are in the Graph, so now add the edge between
    // start and end (add end as an adjacent vertex to start's list)
    adjList.get(start).add(end);
  } // insertEdge()

  public boolean hasVertex(String vertex) {
    return adjList.containsKey(vertex);
  } // hasVertex()

  public ArrayList<String> neighbors(String vertex) {
    // return all the edges (adjacent vertices) associated with vertex
    if(hasVertex(vertex)) {
      return adjList.get(vertex);
    }
    else {
      // if the vertex isn't in the graph, just return an empty list
      return new ArrayList<String>();
    }
  } // neighbors()

  public ArrayList<String> vertices() {
    // returns a list of of all the vertices in the graph
    Set<String> allVertices = adjList.keySet();

    // now add all the vertices to an ArrayList
    ArrayList<String> listOfAllVertices = new ArrayList<String>();

    for(String vertex : allVertices) {
      listOfAllVertices.add(vertex);
    }

    return listOfAllVertices;

  } // vertices()

  public String getMostNeighbors() {
    // returns the state with the most neighbors
    // if multiple states have the max number of neighbors,
    // it returns the first state
    String mostNeighbors = null;
    int currentNumNeighbors = 0;
    int maxNumNeighbors = 0;

    for(String vertex : adjList.keySet()) {
      // count the number of neighbors for the current vertex
      currentNumNeighbors = adjList.get(vertex).size();
      if(currentNumNeighbors > maxNumNeighbors) {
        maxNumNeighbors = currentNumNeighbors;
        mostNeighbors = vertex;
      }
    }
    return mostNeighbors;
  } // getMostNeighbors

  public static String getInput(String prompt) {
    System.out.print(prompt);
    String input = null;
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      input = br.readLine();
    } catch (IOException io) {
      io.printStackTrace();
    }
    return input;
  } // getInput()

  public static void main(String[] args) {
    try {
      URL url = new URL("https://cs.brynmawr.edu/Courses/cs330/spring2020/USStates.csv");

      // create and populate the Graph
      Graph USStates = new Graph(url);

      //  print the State with the most neighbors
      String stateWithMostNeighbors = USStates.getMostNeighbors();
      int numEdges = USStates.neighbors(stateWithMostNeighbors).size();
      System.out.println("The state with the most neighbors is " + stateWithMostNeighbors + ". This state has " + numEdges + " neighbors.");

      // start querying
      while(true) {
        String prompt = "Enter a state: ";
  			String state = getInput(prompt);

        List<String> nbrs = USStates.neighbors(state);

        // if there are no neighbors
        if(nbrs.size() == 0) {
          System.out.println(state + " has no neighbors.");
        }
        // if there are neighbors
        else {
          System.out.print(state + " has the following neighbors: ");
          for(String neighbor : nbrs) {
            System.out.print(neighbor + ", ");
          }
          System.out.print("\n");
        }
        System.out.print("\n");
      }
    }
    catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
  } // main()
} // Graph
