/*
 Simple Tests with split and method in String. Class is not in the assignment
 */

/**
 * 
 * @author Fabio
 */
public class Test {
    public static void main(String[] args) {
      String nouns = "ola, oi, tudobem, ciao";
      int i = 1;
      String outcast = "";
      String[] s2 = nouns.split(",");
      for(String s : s2) {  
          if (i == 1) {
               outcast = s;
               i++;
          }             
      }
     System.out.println(outcast);
    }
   }
    

