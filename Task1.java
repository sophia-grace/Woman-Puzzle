/*
Name: Sophia Trump
File: Task1.java
Description:
*/

import java.net.*;
import java.io.*;
import java.util.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Task1 {

  public static void populateGraph(Graph<String> graph) {
    try {
      URL url = new URL("https://cs.brynmawr.edu/Courses/cs330/spring2020/USStates.csv");
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

      String inputLine;
      // skip the metadata
      in.readLine();
      // read each line in the dataset
      while ((inputLine = in.readLine()) != null) {
          // split the line into its pieces
          String pieces[] = inputLine.split(",", 2);

          // add the state (vertex) to the Graph
          graph.insertVertex(pieces[0]);

          // as long as the state has neighbors, than add its edges to the
          // graph
          if(!pieces[1].equals("")) {
            // add all the edges for that state
            String borderStates[] = pieces[1].split(", ");
            for(int i = 0; i < borderStates.length; i++) {
              // remove quotes from each of the pieces
              borderStates[i] = borderStates[i].replaceAll("\"", "");
              graph.insertEdge(pieces[0], borderStates[i]);
            }
          }
      }
      in.close();

    } catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
  }

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
	}

  public static void main(String[] args) {
    // create the Graph
    Graph<String> states = new Graph<String>();

    // populate the graph with the States dataset
    populateGraph(states);

    //  print the State with the most neighbors
    String stateWithMostNeighbors = states.getMostNeighbors();
    int numEdges = states.getEdges(stateWithMostNeighbors).size();
    System.out.println("The state with the most neighbors is " + stateWithMostNeighbors + ". This state has " + numEdges + " neighbors.");

    // start querying
    while(true) {
      String prompt = "Enter a state: ";
			String input = getInput(prompt);

      List<String> neighbors = states.getEdges(input);

      if(neighbors.size() == 0) {
        System.out.println(input + " has no neighbors.");
      }
      else {
        System.out.print(input + " has the following neighbors: ");
        System.out.print(neighbors.remove(0));
        for(String neighbor : neighbors) {
          System.out.print(", " + neighbor);
        }
      }
      System.out.print("\n");
    }
  }
}
