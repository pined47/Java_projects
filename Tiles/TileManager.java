// Adolfo Pineda, TileManager, 08/09/17
//
// Displays tiles on a drawing panel. User can rearrange the tiles with certain 
// keyboard/mouseclick commands. The N key adds a tile; Left mouse clicks raises
// the chosen tile to the top; Right mouse clicks deletes the tile; shift + left mouse 
// clicks lowers the tile to the bottom; The S key randomly rearranges all the tiles. 

import java.util.*;
import java.awt.*;

public class TileManager {
   private ArrayList<Tile> list;
   
   public TileManager() {
      this.list = new ArrayList<Tile>();
   }
   
   // Passes in a tile and adds it to the current list.
   public void addTile(Tile rect) {
      this.list.add(rect);
   }
   
   // Passes in graphics and draws each
   // tile onto the graphical window.
   public void drawAll(Graphics g) {
      for (int i = 0; i < this.list.size(); i++) {
         Tile temp = this.list.get(i);
         temp.draw(g);
      }
   }
   
   // Passes in the pixel position of the tile that was
   // clicked on and raises it to the top. Does nothing
   // if a tile was not clicked on.
   public void raise(int x, int y) {
      boolean deleteAll = false;
      int place = tileDetect(x, y, deleteAll);
      if (place != -1) {
         Tile rise = this.list.remove(place);
         this.list.add(rise);
      }
   }

   // Passes in the pixel position of the tile that was
   // clicked on and lowers it to the bottom. Does nothing
   // if a tile was not clicked on.   
   public void lower(int x, int y) {
      boolean deleteAll = false;
      int place = tileDetect(x, y, deleteAll);
      if (place != -1) {
         Tile low = this.list.remove(place);
         this.list.add(0, low);
      }
   }

   // Passes in the pixel position of the tile that was
   // clicked on and deletes it from the list. Does nothing
   // if a tile was not clicked on.   
   public void delete(int x, int y) {
      boolean deleteAll = false;
      int place = tileDetect(x, y, deleteAll);
      if (place != -1) {
         Tile rise = this.list.remove(place);
      }
   }
   
   // Passes in the pixel position of the tile that was
   // clicked on deletes all of the tiles that share that
   // pixel position. Does nothing if a tile was not clicked on.   
   public void deleteAll(int x, int y) {
      boolean deleteAll = true;
      int place = tileDetect(x, y, deleteAll);
   }
   
   // Passes in the width and height of the graphical
   // window and shuffles the tiles within that space.
   public void shuffle(int width, int height) {
      Collections.shuffle(this.list);
      for (int i = 0; i < this.list.size(); i++) {
         Tile temp = this.list.get(i);
         int widthMax = width - temp.getWidth();
         int heightMax = height - temp.getHeight();
         Random ran = new Random();
         int x = ran.nextInt(widthMax + 1);
         int y = ran.nextInt(heightMax + 1);
         temp.setX(x);
         temp.setY(y);
      }
   }
   
   // Helper method that detects the position of the tile and returns
   // a value that determines whether or not a tile was clicked on.
   private int tileDetect (int x, int y, boolean deleteAll) {
      int place = -1;
      for (int i = 0; i < this.list.size(); i++) {
         Tile temp = this.list.get(i);
         int maxWidth = temp.getX() + temp.getWidth();
         int maxHeight = temp.getY() + temp.getHeight();
         
         if ((x >= temp.getX() && x < maxWidth) && 
            (y >= temp.getY() && y < maxHeight)) {
            if (!deleteAll) {
               place = i;
            } else {
               this.list.remove(i);
               i = i - 1;
            } 
         }
      }
      return place;
   }
}