/*
Name: Sophia Trump
File: Graph.java
Description: A Graph abstract datatype, implemented using a HashMap,
where the keys are the vertices and the values are the edges (i.e., a list of
edges that are adjacent to the key/vertex)
*/

import java.util.*;
import java.net.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class Graph<V> {
  private Map<V, List<V>> verticesAndEdges;

  // constructor
  Graph() {
    verticesAndEdges = new HashMap<V, List<V>>();
  }

  public void insertVertex(V vertex) {
    // add the vertex to the Graph, with no edges yet
    verticesAndEdges.put(vertex, new LinkedList<V>());
  }

  public void insertEdge(V start, V end) {
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
    verticesAndEdges.get(start).add(end);
  }

  public boolean hasVertex(V vertex) {
    return verticesAndEdges.containsKey(vertex);
  }

  public List<V> getEdges(V vertex) {
    // return all the edges (adjacent vertices) associated with vertex
    if(hasVertex(vertex)) {
      return verticesAndEdges.get(vertex);
    }
    else {
      // if the vertex isn't in the graph, just return an empty list
      return new LinkedList<V>();
    }
  }

  public V getMostNeighbors() {
    V mostNeighbors = null;
    int currentNumNeighbors = 0;
    int maxNumNeighbors = 0;
    for(V vertex : verticesAndEdges.keySet()) {
      currentNumNeighbors = verticesAndEdges.get(vertex).size();
      if(currentNumNeighbors > maxNumNeighbors) {
        maxNumNeighbors = currentNumNeighbors;
        mostNeighbors = vertex;
      }
    }
    return mostNeighbors;
  }
}
