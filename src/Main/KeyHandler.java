/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.event.KeyEvent;

/**
 *
 * @author vangradomor
 * 
 * @description handles keypresses
 */
public class KeyHandler {
    
    
    /**
     * 
     * @param e keycode for key 
     * @return if left key 
     */
    public static boolean isKeyLeft(int e){
        if(e == KeyEvent.VK_LEFT)                                   return true;
        else if(e == KeyEvent.VK_A)                                 return true;
        else                                                       return false;
    }
    
    /**
     * 
     * @param e keycode for key 
     * @return if right key 
     */
    public static boolean isKeyRight(int e){
        if(e  == KeyEvent.VK_RIGHT)                                 return true;
        else if(e == KeyEvent.VK_D)                                 return true;
        else                                                       return false;
    }
    
    /**
     * 
     * @param e keycode for key 
     * @return if up key 
     */
    public static boolean isKeyUp(int e){
        if(e == KeyEvent.VK_UP)                                     return true;
        else if(e == KeyEvent.VK_W)                                 return true;
        else                                                       return false;
    }
    
    /**
     * 
     * @param e keycode for key 
     * @return if down key 
     */
    public static boolean isKeyDown(int e){
        if(e == KeyEvent.VK_DOWN)                                   return true;
        else if(e == KeyEvent.VK_S)                                 return true;
        else                                                       return false;
    }
   
    /**
     * 
     * @param e keycode for key 
     * @return if enter key 
     */
    public static boolean isKeyEnter(int e){
        return e == KeyEvent.VK_ENTER;
    }

    /**
     * 
     * @param e keycode for key 
     * @return if space key 
     */
    public static boolean isKeySpace(int e) {
        return e == KeyEvent.VK_SPACE;
    }

    public static boolean isKeyConsole(int e) {
        return e == KeyEvent.VK_1;
    }

    public static boolean isKeyPlus(int e) {
        return e == KeyEvent.VK_PLUS;
    }

    public static boolean isKeyMinus(int e) {
        return e == KeyEvent.VK_MINUS;
    }
}
