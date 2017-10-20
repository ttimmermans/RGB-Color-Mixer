package rgb.color.mixer;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * @author Thomas Timmermans
 * @version 20-10-2017
 */
public class RGBColorMixer implements Runnable {
	
	/**
	 * Constructor
	 */
    public RGBColorMixer() {
    	SwingUtilities.invokeLater(this);
    }
    

    /**
     * 
     */
    public void run() {
    	buildGUI();
    }
    
    /**
     * 
     */
    public void buildGUI() {
        JFrame frame = new JFrame("RGB Color Mixer");
        frame.setLayout(new GridBagLayout());

        JPanel main = new JPanel(new GridLayout(1, 2));
        frame.add(main);

        JPanel left = new JPanel(new GridLayout(6, 1));
        main.add(left);

        JPanel right = new JPanel();
        right.setOpaque(true);
        main.add(right);
        
        JSlider redSlider = new JSlider(0, 255);
        JSlider greenSlider = new JSlider(0, 255);
        JSlider blueSlider = new JSlider(0, 255);
        
        JTextField redField = new JTextField("" + redSlider.getValue());
        JTextField greenField = new JTextField("" + greenSlider.getValue());
        JTextField blueField = new JTextField("" + blueSlider.getValue());
        
        left.add(redSlider);
        left.add(redField);
        left.add(greenSlider);
        left.add(greenField);
        left.add(blueSlider);
        left.add(blueField);
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * 
     * @param args
     */
	public static void main(String[] args) {
		RGBColorMixer test = new RGBColorMixer();
	}

}
