package fmph.simulator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
    
        long x = Long.MIN_VALUE;  
        double y = Double.MIN_VALUE+10;
        long z = Long.MIN_VALUE+1;
        System.out.println(" "+ (x==y) + " " + (y==z)+ " " + (x==z));
        System.out.println(" "+x+" "+y+" "+z);
    }
}
