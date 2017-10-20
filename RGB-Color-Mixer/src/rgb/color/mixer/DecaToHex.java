package rgb.color.mixer;


/**
 * Convert decimal to Hexadecimal.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DecaToHex
{
    private static final String hexa = "0123456789ABCDEF";
    
    // https://www.w3schools.com/colors/colors_converter.asp

    /**
     * Constructor for objects of class DecaToHex
     */
    public DecaToHex() {

    }

    /**
     * Convert decimal to hexadecimal
     */
    public static /*String*/ void blub(int dec) {
        
        int nrOfLoops = dec / 16 + 1;

        //System.out.println();
        System.out.println(nrOfLoops);
        
        //return "" + hexa.charAt(dec);
        
        System.out.println();
        
        /*for (int i = 0; i < nrOfLoops; i++) {
        	
        }*/
        
        //int remainder = dec; // 17 bijv.
        
        //if (remainder % 16 == 0) 

    }
    
    public static void bla(int x /* 256 bijv. */) {
    	//int n = x / 16 + 1;
    	//System.out.println("n = " + n);
    	//System.out.println("x % 16 = " + x % 16);
    	
    	System.out.println(x / 16);
    	System.out.println(x % 16); // Als dit 0 is eindigt hex nr. ook op 0 !!
    	
    }
    
    public static void loop(int x) {
    	System.out.println();
    	char hexChar = '\u0000';
    	for (int i = 0; i <= x; i++) {
    		/*if (i == 16) {
    			i = 0;
    		}
    		hexChar = hexa.charAt(i);*/
    	}
    	System.out.println(hexChar);
    }
    
    public static void recurz(int x) {
    	// bijv. 256
    	
    	String hex = "";
    	int n = x / 16; // 16 in vb.
    	
    	
    }
    
    public static void main(String[] args) {
    	/*DecaToHex.blub(15); // 1
    	DecaToHex.blub(16); // 2
    	DecaToHex.blub(255); // 16
    	DecaToHex.blub(256); // 17
    	DecaToHex.bla(256);*/
    	//DecaToHex.loop(16);
    }
    
}
