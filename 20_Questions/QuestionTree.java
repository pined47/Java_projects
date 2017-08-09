// Adolfo Pineda
// March 5, 2015
// T.A. Ashley Donaldson: Section AH
//
// This program can be used to play a game of "20 questions." In this game,
// the player thinks of an object and the computer will ask the player yes
// or no questions so that it can correctly guess the player's object. 

import java.util.*;
import java.io.*;

public class QuestionTree {
   private int totalGames;
   private int gamesWon;
   private UserInterface ui;
   private QuestionNode overallRoot;
   
   // Accepts a user interface that allows the player to interact with the computer.
   // It also throws an IllegalArgumentException if the user interface is null. 
   // At first, a single "answer" with the object "computer" will be created.
   public QuestionTree(UserInterface ui) {
      if (ui == null) {
         throw new IllegalArgumentException();
      }
      this.overallRoot = new QuestionNode("computer");
      this.ui = ui;
      this.gamesWon = 0;
      this.totalGames = 0;
   }
   
   // Plays one game of "20 Questions." If the computer's guess is wrong,
   // then it will allow the player to add an object and a yes/no question
   // that distinguishes their object so that it can be added correctly.
   public void play() {
     totalGames++;
     overallRoot = play(overallRoot);
   }

   // Private helper method that passes in the current tree state.
   // If the computer makes the wrong guess, then it will ask the
   // player for the object they were thinking of, a yes/no question
   // to distingush that object, and then adds this information to 
   // the current tree. After this, the new tree is then returned.
   private QuestionNode play(QuestionNode root) {
      if (root.left == null && root.right == null) {
         ui.print("Would your object happen to be " + root.data + "?");
         if (ui.nextBoolean()) {
            gamesWon++;
            System.out.println("I win!");
         } else {
            ui.print("I lose. What is your object?");
            String object = ui.nextLine();
            ui.print("Type a yes/no question to distinguish your item from " + root.data + ":");
            String question = ui.nextLine();
            ui.print("And what is the answer for your object?");
            QuestionNode objectNode = new QuestionNode(object);
            if (ui.nextBoolean()) {
               root = new QuestionNode(question, objectNode, root);
            } else {
               root = new QuestionNode(question, root, objectNode);
            }
         }
      } else {
         ui.print(root.data);
         if (ui.nextBoolean()) {
            root.left = play(root.left);
         } else {
            root.right = play(root.right);
         } 
      }
      return root;
   }
   
   // Passes in a print stream to save the current tree state
   // into an output file in pre-order tree format. Throws an
   // IllegalArgumentException if the passed-in print stream is null.
   public void save(PrintStream output) {
      if (output == null) {
         throw new IllegalArgumentException();
      }
      save(output, overallRoot);
   }
   
   // Private helper method that saves each "question" and "answer" into
   // an output file. Labels each "question" with a "Q:" label, and each
   // "answer" with an "A:" label. The tree is saved in pre-order format.
   private void save(PrintStream output, QuestionNode root) {
      if(root != null) {
         // only checks one side because a "question" will always have two
         // branches and an answer will have none. If the current root has
         // a left branch, then it must have a right branch.
         if(root.right != null) {
            output.println("Q:" + root.data);
         } else {
            output.println("A:" + root.data);
         }
         save(output, root.left);
         save(output, root.right);
      }
   }
   
   // Replaces the current tree with the passed-in scanner file (scanner must
   // conatain that tree). The passed-in tree must be in pre-order format.
   // Not only that, but the file itself must have its objects begin with either
   // a "Q:" or an "A:" so that each object can be properly placed. Example:
   // Q:Is it a monster?
   // A:my old high school teacher
   // A:my dog
   // Throws an IllegalArgumentException if the passed-in scanner is null.
   public void load(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException();
      }
      overallRoot = load(input, overallRoot);
   }
   
   // Private helper that passes in the current scanner file and the type of 
   // object that is passed in (whether it is a "Q" (question) or an "A" (answer)).
   // returns the newly structured tree so that it can be used for "20 Questions." 
   private QuestionNode load(Scanner input, QuestionNode root) {
      String line = input.nextLine();
      String[] content = line.split(":");
      root = new QuestionNode(content[1]);
      if (line.startsWith("Q")) {
         root.left = load(input, root.left);
         root.right = load(input, root.right);
      }
      return root;
   }
   
   // Returns the total number of games that have been played.
   public int totalGames() {
      return totalGames;
   }
   
   // Returns the total number of games that the computer has won.
   public int gamesWon() {
      return gamesWon;
   }
   
}