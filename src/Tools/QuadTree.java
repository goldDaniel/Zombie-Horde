/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entities.GameObject;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author vangradomor
 * 
 * @edscription splits screen into four quadrants repeatedly to reduce collision checks
 */
public class QuadTree {
    
    /*maximum objects before nodes split*/
    private static final int MAX_OBJECTS = 10;
    /*maximum amount of times tree can split*/
    private static final int MAX_LEVELS = 5;
 
    private int level;    
    private ArrayList<GameObject> objects;
    private Rectangle bounds;
    private QuadTree[] nodes;
 
    public QuadTree(int level, Rectangle bounds) {
        this.level = level;
        objects = new ArrayList<GameObject>();
        this.bounds = bounds;
        nodes = new QuadTree[4];
    }
  
    public void clear() {
        objects.clear();
 
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }
    
     private void split() {
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();
 
        /*creates each section for quadtree******************************************************/
        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
        /****************************************************************************************/
    }
     
    private int getIndex(GameObject object) {
        int index = -1;
        double verticalMid = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMid= bounds.getY() + (bounds.getHeight() / 2);
 
        // Object can completely fit within the top quadrants
        boolean topQuadrant = (object.getY() < horizontalMid && object.getY() + object.getHitHeight() < horizontalMid);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (object.getY() > horizontalMid);
 
        // Object can completely fit within the left quadrants
        if (object.getX() < verticalMid && object.getX() + object.getHitWidth() < verticalMid) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (object.getX() > verticalMid) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }
        return index;
    }
    
    public void insert(GameObject object) {
        if (nodes[0] != null) {
            int index = getIndex(object);
 
            if (index != -1) {
            nodes[index].insert(object);
            return;
            }
        }
 
        objects.add(object);
 
        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) { 
                split(); 
            }
 
            int i = 0;
            while (i < objects.size()) {
            int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.get(i));
                    objects.remove(i);
                }
                else i++;
            }
        }
    }
    
    public ArrayList retrieve(ArrayList returnObjects, GameObject object ){
        int index = getIndex(object);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, object);
        }
        returnObjects.addAll(objects);
        return returnObjects;
    }
}
