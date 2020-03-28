


import java.net.*;
import java.io.*;
import java.util.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Task2 {

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

  public static 




  public static void main(String[] args) {
    // create the Graph
    Graph<String> states = new Graph<String>();

    // populate the graph with the States dataset
    populateGraph(states);

    // start querying
    while(true) {
      String prompt = "Enter a state: ";
			String input = getInput(prompt);


  }
}
