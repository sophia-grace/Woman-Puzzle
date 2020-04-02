
import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class WomanPuzzle {

  public static ArrayList<String> dfs(Graph g, String s, String t) {
    // local variables needed in the function for dfs
    HashMap<String, String> pred = new HashMap<String, String>();
    HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
    Stack<String> frontier;

    // get all the vertices in g
    ArrayList<String> vertices = g.vertices();

    // initialize pred to all nulls
    // initialize visited to all false
    for(String v : vertices) {
      pred.put(v, null);
      visited.put(v, false);
    }

    // Initialize frontier to empty
    frontier = new Stack<String>();

    // Insert s into frontier
    frontier.push(s);

    while(!frontier.empty()) {
      // remove a vertex from frontier
      String u = frontier.pop();

      if(u.equals(t)) {
        // found destination!
        // Return path from s to t, using pred
        return path(s, t, pred);
      }
      else {
        // done with u, set up exploring its adjacencies
        visited.put(u, true);

        // find all neighbors for u
        ArrayList<String> womanOnlyNeighbors = womanOnly(g.neighbors(u), t);

        for(String v: womanOnlyNeighbors) {
          if(visited.get(v).equals(false) && (frontier.search(v) == -1)) {
            pred.put(v, u);
            // insert v into the frontier
            frontier.push(v);
          }
        }
      }
    }

    // Return failure
    return null;
  } // dfs()


  public static ArrayList<String> path(String s, String t, HashMap<String, String> pred) {
    // initialize P
    ArrayList<String> P = new ArrayList<String>();

    String u = t;
    while(pred.get(u) != null) {
      P.add(0, u);
      u = pred.get(u);
    }

    P.add(0, u);

    return P;
  } // path()

  public static ArrayList<String> womanOnly(ArrayList<String> vertices, String t) {
    // returns a list of only those vertices whose first letter is either in the word WOMAN or is the destination (t)
    // returns a list of of all the vertices in the graph

    ArrayList<String> womanOnlyVertices = new ArrayList<String>();
    String woman = "WOMAN";

    for(String vertex : vertices) {
      if(woman.indexOf(vertex.charAt(0)) != -1) {
        womanOnlyVertices.add(vertex);
      }
      if(vertex.equals(t)) {
        womanOnlyVertices.add(vertex);
      }
    }

    return womanOnlyVertices;
  } // womanOnly()

  public static void main(String[] args) {
    try {
      URL url = new URL("https://cs.brynmawr.edu/Courses/cs330/spring2020/USStates.csv");

      // define the variables for use in the search function
      // create and populate the Graph
      Graph G = new Graph(url);
      String start = "Washington";
      String destination = "District of Columbia";

      ArrayList<String> path = dfs(G, start, destination);

      if(path != null) {
        System.out.println("Yes. To get from " + start + " to " + destination + " march as follows:");
        for(String p: path) {
          System.out.print(p + ", ");
        }
        System.out.println();
      }
      else {
        System.out.println("No. There is no way to get from " + start + " to " + destination + ".");
      }

    }
    catch(Exception IOException) {
      System.out.println("Unable to access the data.");
    }
  }
}
