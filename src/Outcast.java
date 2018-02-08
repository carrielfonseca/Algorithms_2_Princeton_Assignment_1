
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fabio
 */
public class Outcast {
    private WordNet wordnet;
        
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet)    {
        if (wordnet == null) throw new java.lang.NullPointerException("argument is null");
        this.wordnet = wordnet;
   }
    
   // given an array of WordNet nouns, return an outcast   
   public String outcast(String[] nouns)  {
      if (nouns == null) throw new java.lang.NullPointerException("argument is null");
      String outcast = "";
      int distanceOfOutcast, distance;
      distanceOfOutcast = 0;
      distance = 0;
      for(String s : nouns) {
          for(int j = 0; j < nouns.length; j++) {
             distance =  distance + wordnet.distance(s,nouns[j]);    
          }   
          if (distanceOfOutcast < distance || distanceOfOutcast == 0) {
              distanceOfOutcast = distance;
              outcast = s;
          }
          distance = 0;
      }
      return outcast;
   }  
           
           
   public static void main(String[] args)  {  // see test client below
       /*WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) {
       In in = new In(args[t]);
       String[] nouns = in.readAllStrings();
       StdOut.println(args[t] + ": " + outcast.outcast(nouns));
               
    }
      */    
       
       WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt"); // needs to be in the root folder
       Outcast outcast = new Outcast(wordnet);
       // String[] s = {"horse", "zebra", "cat", "bear", "table"};
       // String[] s = { "water", "soda", "bed", "orange_juice" ,"milk", "apple_juice", "tea", "coffee"};
       String[] s = {"apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry", "mango", "watermelon", "potato" };
      // String[] s = {"beer", "water", "juice", "wire", "apple", "drink"};
       System.out.println("outcast is " + outcast.outcast(s));
       
   }
           
}
