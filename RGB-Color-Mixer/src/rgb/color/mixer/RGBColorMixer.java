package rgb.color.mixer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;

/**
 * @author Thomas Timmermans
 * @version 17-11-2017
 */
public class RGBColorMixer implements Runnable {
	
	JPanel right;
    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
	
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

        JPanel left = new JPanel(new GridLayout(7, 1));
        main.add(left);

        right = new JPanel();
        right.setOpaque(true);
        main.add(right);
        
        redSlider = new JSlider(0, 255);
        greenSlider = new JSlider(0, 255);
        blueSlider = new JSlider(0, 255);
        
        RGBField redField = new RGBField("" + redSlider.getValue(), redSlider);
        RGBField greenField = new RGBField("" + greenSlider.getValue(), greenSlider);
        RGBField blueField = new RGBField("" + blueSlider.getValue(), blueSlider);
        
        SliderListener redSliderListener = new SliderListener(redSlider, redField);
        SliderListener greenSliderListener = new SliderListener(greenSlider, greenField);
        SliderListener blueSliderListener = new SliderListener(blueSlider, blueField);
        
        redSlider.addChangeListener(redSliderListener);
        greenSlider.addChangeListener(greenSliderListener);
        blueSlider.addChangeListener(blueSliderListener);
        
        //TextDocListener redDocListener = new TextDocListener(redSlider, redSliderListener);
        //TextDocListener greenDocListener = new TextDocListener(greenSlider, greenSliderListener);
        //TextDocListener blueDocListener = new TextDocListener(blueSlider, blueSliderListener);
        
        //redField.getDocument().addDocumentListener(redDocListener);
        //greenField.getDocument().addDocumentListener(greenDocListener);
        //blueField.getDocument().addDocumentListener(blueDocListener);
        
        JTextField hexLabel = new JTextField("#ZZZZZZ");
        hexLabel.setEditable(false);
        
        left.add(redSlider);
        left.add(redField);
        left.add(greenSlider);
        left.add(greenField);
        left.add(blueSlider);
        left.add(blueField);
        left.add(hexLabel);
        
        setRightPanelColor();
        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setRightPanelColor() {
        right.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
    }
    
    
    
    public class SliderListener implements ChangeListener {
    	
        JSlider slider;
        RGBField field;
        //TextDocListener textDocListener;
    	boolean active;

        public SliderListener(JSlider slider, RGBField field) {
            this.slider = slider;
            this.field = field;
            active = true;
        }
        
        @Override
        public void stateChanged(ChangeEvent e) {
            if (this.isActive() /*&& textDocListener.isActive()*/) {
            	System.out.println("stateChanged! slidervalue: " + slider.getValue());
                //textDocListener.setActive(false);
            	String sliderValue = "" + slider.getValue();
            	System.out.println(sliderValue);
                field.setText(sliderValue);
                setRightPanelColor();
                //textDocListener.setActive(true);
            }
        }
        /*
        public void setDocListener(TextDocListener listener) {
        	this.textDocListener = listener;
        }
        */
        public RGBField getField() {
            return field;
        }
        
        public JSlider getSlider() {
            return slider;
        }
        
        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isActive() {
            return active;
        }

    }
    
    
    /*
    /**
     * TextDocListener
     *
    public class TextDocListener implements DocumentListener {
    	
        //////////////
    	
        RGBField field;
        JSlider slider;
        SliderListener sliderListener;
        AbstractDocument doc;
        boolean active;

        public TextDocListener(JSlider slider, SliderListener sliderListener) {
            this.sliderListener = sliderListener;
            this.field = sliderListener.getField(); // ?????????????????
            this.slider = slider; //sliderListener.getSlider();
            doc = (AbstractDocument)field.getDocument();
            active = true;
            sliderListener.setDocListener(this);
        }
    	
    	//////////////
    	
        public void setSlider() {
        	sliderListener.setActive(false);
        	slider.setValue(Integer.parseInt(field.getText()));
        	sliderListener.setActive(true);
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            //setSlider();
        	System.out.println("TextDocListener insertUpdate! Current value: " + field.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            //setSlider();
        	System.out.println("TextDocListener removeUpdate! Current value: " + field.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //setSlider();
        	System.out.println("TextDocListener changedUpdate! Current value: " + field.getText());
        }
        
        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isActive() {
            return active;
        }
    	
    }
    */
    
    
    
    /**
     * 
     * @param args
     */
	public static void main(String[] args) {
		RGBColorMixer test = new RGBColorMixer();
	}

}
