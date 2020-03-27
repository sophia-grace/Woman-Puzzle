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
        //  System.out.println(graph.hasVertex(pieces[0]));

        //  System.out.println(pieces[0] + ":" + pieces[1]);


          // add all the edges for that state
          String borderStates[] = pieces[1].split(", ");
          for(int i = 0; i < borderStates.length; i++) {
            // remove quotes from each of the pieces
            borderStates[i] = borderStates[i].replaceAll("\"", "");
      //      System.out.println("Inserting " + pieces[0] + borderStates[i]);
            graph.insertEdge(pieces[0], borderStates[i]);
          }
      }
      in.close();

    } catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
  }



  public static void main(String[] args) {
    // create the Graph
    Graph<String> states = new Graph<String>();

    // populate the graph with the States dataset
    populateGraph(states);

  //  print the State with the most neighbors
    List<String> statesWithMostNeighbors = states.getMostNeighbors();
    for(String state : statesWithMostNeighbors) {
      System.out.println(state + ": " + states.getEdges(state).size());
    }
  // states.getMostNeighbors();
  }
}
