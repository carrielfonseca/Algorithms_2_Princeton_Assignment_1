
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fabio
 */
public class WordNet {
    
    private RedBlackBST<String,SET<Integer> > wordTree; //Symbol Table to store the words of the synset and the set of ids this word appears
    private RedBlackBST<Integer, String> synsetTree;
    private int numberOfWords, V;
    private Digraph G;
    private SAP sap;
    
    
   /// constructor takes the name of the two input files  
   public WordNet(String synsets, String hypernyms) { 
       if (synsets == null || hypernyms == null) throw new java.lang.NullPointerException("argument is null");
       wordTree = new RedBlackBST<String,SET<Integer> >();
       synsetTree = new RedBlackBST<Integer, String>();
       readSynsets(synsets);
       G = new Digraph(V);
       readHypernyms(hypernyms); 
       sap = new SAP(G);
   }   
   
    // To read the synsets file: first reads a line with the In class, 
    // then splits each line into 3 parts with the String method split and then... 
    // splits the second part of the string into with the spaces
   private void readSynsets(String synsets) { 
       int id; //id of the synset
       SET<Integer> set;
       In in = new In(synsets); 
    
       while (in.hasNextLine()) {
            String s = in.readLine();
            String[] s2 = s.split(",");
            id = Integer.parseInt(s2[0]);
            synsetTree.put(id, s2[1]); // records the synset 
            String[] wordsInSynset = s2[1].split(" +"); //attention to this Regex. Eliminates one OR more spaces. 
            for(String word : wordsInSynset) {
                set = wordTree.get(word);
                if(set != null) {  // if set is already there
                    set.add(id);
                }
                else {             // if this is a new word, create the set and insert the word with the new set in the tree
                    set = new SET<Integer>();
                    set.add(id);
                    wordTree.put(word, set);
                    numberOfWords++; 
                    //System.out.println(word);
                }
            }          
            V++; //number of synsets, which will be the number of vertices in the Digraph
       }      
    }
   
   private void readHypernyms(String hypernyms) { 
       int synsetID, hypernymId;
       In in = new In(hypernyms); 
       
       while(in.hasNextLine()) {
           String s = in.readLine();
           String[] s2 = s.split(",");
           synsetID = Integer.parseInt(s2[0]);
           for(int i = 1; i <  s2.length; i++) {
               hypernymId = Integer.parseInt(s2[i]);
               G.addEdge(synsetID, hypernymId);
           }           
       }
    }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return wordTree.keys();
   }

           
   // is the word a WordNet noun?
   public boolean isNoun(String word) {
       if (word == null) throw new java.lang.NullPointerException("argument is null");       
       return wordTree.contains(word); //can improve by putting making the program not case sensitive
   }
   
   
   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
       if (nounA == null || nounB == null) throw new java.lang.NullPointerException("argument is null");
       if (!wordTree.contains(nounA) || !wordTree.contains(nounB)) throw new java.lang.IllegalArgumentException("argument(s) do not belong to WordNet");       
       //SAP sap = new SAP(G);
       Iterable<Integer> itA = wordTree.get(nounA);
       Iterable<Integer> itB = wordTree.get(nounB);
       int dist = sap.length(itA, itB);
       return dist;
   }

           
 
   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
       if (nounA == null || nounB == null) throw new java.lang.NullPointerException("argument is null");
       if (!wordTree.contains(nounA) || !wordTree.contains(nounB)) throw new java.lang.IllegalArgumentException("argument(s) do not belong to WordNet");    
       //SAP sap = new SAP(G);
       Iterable<Integer> itA = wordTree.get(nounA);
       Iterable<Integer> itB = wordTree.get(nounB);
       int ancestorId = sap.ancestor(itA, itB);
       String ancestor = synsetTree.get(ancestorId);
       return ancestor;       
   }
   

   // do unit testing of this class
   public static void main(String[] args) {
      WordNet wn = new WordNet("synsets.txt", "hypernyms.txt"); 
      System.out.println("number of synsets is " + wn.V);
      System.out.println("number of words is " + wn.numberOfWords);
      System.out.println("number of edges is " + wn.G.E());
      
      /*Iterable<String> it = wn.wordTree.keys();
      for (String s : it) {
          System.out.println(s);
          System.out.println(wn.wordTree.get(s));
      }
              */
       
   }
}