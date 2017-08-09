// Adolfo Pineda
// March 5, 2015
// T.A. Ashley Donaldson: Section AH
//
// QuestionNode made specifically for the game called "20 Questions."

public class QuestionNode {
   
   public String data;        // data stored at this node
   public QuestionNode left;  // reference to left subtree
   public QuestionNode right; // reference to right subtree

   // Constructs an "answer" node with the given data.
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // Constructs a leaf/"answer" node or a branch/"question" 
   // node with the given data and links.
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
   
}