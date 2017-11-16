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
 * @version 20-10-2017
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
        
        System.out.println("buildGUI Method EDT? " + SwingUtilities.isEventDispatchThread());

        right = new JPanel();
        right.setOpaque(true);
        main.add(right);
        
        System.out.println("Making red slider...");
        redSlider = new JSlider(0, 255);
        System.out.println("Making green slider...");
        greenSlider = new JSlider(0, 255);
        System.out.println("Making blue slider...");
        blueSlider = new JSlider(0, 255);
        
        RGBField redField = new RGBField("" + redSlider.getValue());
        RGBField greenField = new RGBField("" + greenSlider.getValue());
        RGBField blueField = new RGBField("" + blueSlider.getValue());
        
        SliderListener redSliderListener = new SliderListener(redSlider, redField);
        SliderListener greenSliderListener = new SliderListener(greenSlider, greenField);
        SliderListener blueSliderListener = new SliderListener(blueSlider, blueField);
        
        redSlider.addChangeListener(redSliderListener);
        greenSlider.addChangeListener(greenSliderListener);
        blueSlider.addChangeListener(blueSliderListener);
        
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
            if (this.isActive()/* && textDocListener.isActive()*/) {
            	System.out.println("stateChanged! slidervalue: " + slider.getValue());
                //textDocListener.setActive(false);
            	String sliderValue = "" + slider.getValue();
            	System.out.println(sliderValue);
                field.setText(sliderValue);
                setRightPanelColor();
                //textDocListener.setActive(true);
            }
        }
        
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
    public class TextDocListener implements DocumentListener {

        RGBField field;
        JSlider slider;
        SliderListener sliderListener;
        AbstractDocument doc;
        boolean active;

        public TextDocListener(SliderListener sliderListener) {
            this.sliderListener = sliderListener;
            this.field = sliderListener.getField(); // ?????????????????
            this.slider = sliderListener.getSlider();
            doc = (AbstractDocument)field.getDocument();
            //DocFilter docFilter = new DocFilter(this.slider, this.field, doc, sliderListener);
            //doc.setDocumentFilter(docFilter);
            active = true;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            //setSlider();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            //setSlider();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            //setSlider();
        }

        public String getDocValue() {
            String value = "";
            try {
                value = field.getDocument().getText(0, field.getDocument().getLength());
                System.out.println("(String)value is: " + value);
            }
            catch (BadLocationException e) {
                e.printStackTrace();
            }
            return value;
        }

        public SliderListener getSliderListener() {
            return sliderListener;
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
