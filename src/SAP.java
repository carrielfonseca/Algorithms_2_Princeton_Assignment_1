
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
This class runs with a text file name as an argument (see main class)
 */


/**
 *
 * @author Fabio
 * This implementation relies heavily on the class  BreadthFirstDirectedPaths from the course
 * very interesting to see how the search is still linear even for a set of vertices
 */
public final class SAP {
    
    private final Digraph G;    

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
       this.G =  new Digraph(G); // makes a deep copy of the Digraph to ensure it is immutable       
   }
   
   private void checkIndex(Iterable<Integer> v) {
       for(int vertex : v) {
         if (vertex < 0 || vertex >= G.V())   throw new java.lang.IndexOutOfBoundsException("");
       }
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
       if (v < 0 || v >= G.V())             throw new java.lang.IndexOutOfBoundsException("");
       if (w < 0 || w >= G.V())             throw new java.lang.IndexOutOfBoundsException("");
       int dist = -1;
       BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(G, v);
       BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(G, w);       
       for (int i = 0; i < G.V(); i ++) {
           // if the vertex is reachable both from v and w, then they have a common ancestor
           // updates distance everytime there is a new minimum distance OR if this is the first common ancestor found (dist = -1)
           if (bf1.hasPathTo(i) && bf2.hasPathTo(i)) {
              if ( dist > (bf1.distTo(i) + bf2.distTo(i)) || dist == -1 ) { 
                 dist = bf1.distTo(i) + bf2.distTo(i);
              }
           }
       }       
       return dist;       
   }
  
   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       if (v < 0 || v >= G.V())             throw new java.lang.IndexOutOfBoundsException("");
       if (w < 0 || w >= G.V())             throw new java.lang.IndexOutOfBoundsException("");
       int dist = -1;
       int ancestor = -1;
       BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(G, v);
       BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(G, w);       
       for (int i = 0; i < G.V(); i ++) {
           // if the vertex is reachable both from v and w, then they have a common ancestor
           // updates distance everytime there is a new minimum distance OR if this is the first common ancestor found (dist = -1)
           if (bf1.hasPathTo(i) && bf2.hasPathTo(i)) {
              if ( dist > (bf1.distTo(i) + bf2.distTo(i)) || ancestor == -1 ) { 
                 dist = bf1.distTo(i) + bf2.distTo(i);
                 ancestor = i;
              }
           }
       }           
       return ancestor;
   }
 
   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w){
       if (v == null || w == null) throw new java.lang.NullPointerException("argument is null");  
       checkIndex(v);
       checkIndex(w);
       int dist = -1;
       BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(G, v);
       BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(G, w);       
       for (int i = 0; i < G.V(); i ++) {
           // if the vertex is reachable both from v and w, then they have a common ancestor
           // updates distance everytime there is a new minimum distance OR if this is the first common ancestor found (dist = -1)
           if (bf1.hasPathTo(i) && bf2.hasPathTo(i)) {
              if ( dist > (bf1.distTo(i) + bf2.distTo(i)) || dist == -1 ) { 
                 dist = bf1.distTo(i) + bf2.distTo(i);
              }
           }
       }       
       return dist;  
   }           

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       if (v == null || w == null) throw new java.lang.NullPointerException("argument is null");
       checkIndex(v);
       checkIndex(w);
       int dist = -1;
       int ancestor = -1;
       BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(G, v);
       BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(G, w);       
       for (int i = 0; i < G.V(); i ++) {
           // if the vertex is reachable both from v and w, then they have a common ancestor
           // updates distance everytime there is a new minimum distance OR if this is the first common ancestor found (dist = -1)
           if (bf1.hasPathTo(i) && bf2.hasPathTo(i)) {
              if ( dist > (bf1.distTo(i) + bf2.distTo(i)) || ancestor == -1 ) { 
                 dist = bf1.distTo(i) + bf2.distTo(i);
                 ancestor = i;
              }
           }
       }           
       return ancestor;
   }

   // do unit testing of this class
   // provided from the course
   public static void main(String[] args) {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
   }
  
}