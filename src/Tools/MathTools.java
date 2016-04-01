/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author vangradomor
 * 
 * @description does math that is required throughout code
 */
public class MathTools {
    
    
    /**
     * 
     * @param l lowest possible integer
     * @param h highest possible integer
     * @return  random integer between low and high
     */
    public static int getRandomInteger(int l, int h){
        double seed   = Math.random();
        double L      = (double)l;
        double H      = (double)h;
        double random = (H - L + 1) * seed + L;
        return (int)random;
    }
    
    /**
     * 
     * @param l lowest possible double
     * @param h highest possible double
     * @return  random double between low and high
     */
    public static double getRandomDouble(double l, double h){
        double seed = Math.random();
        return (h - l + 1) * seed + l;
    }
}
