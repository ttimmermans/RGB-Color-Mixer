package rgb.color.mixer;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.DocumentFilter.FilterBypass;

import java.awt.event.*;
import java.util.ArrayList;

/**
 * Write a description of class RGBField here.
 * 
 * @author Thomas Timmermans
 * @version 24-10-2017
 */
public class RGBField extends JTextField {
	
    JFrame frame;
    AbstractDocument doc;
    DocFilter docFilter;
    
    String fieldValue = null;
    
    

    /**
     * Constructor for RGBField
     * @param text  The text the RGBField will be initialized with.
     */
    public RGBField(String text) {
    	super(text);
        doc = (AbstractDocument)this.getDocument();
    	System.out.println("RGBField constructor EDT? " + SwingUtilities.isEventDispatchThread());
        docFilter = new DocFilter();
        doc.setDocumentFilter(docFilter);
    }

    /**
     * Return the content the document currently contains as a string.
     * @return value  The document's content as a string.
     */
    public String getDocValue() {
        String value = "";
        try {
            value = doc.getText(0, doc.getLength());
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * RGBField's DocumentFilter
     */
    public class DocFilter extends DocumentFilter {

        // The removal offset used by the DocFilter's remove method.
        private int removalOffset = 1;

        /**
         * DocFilter Constructor. Also registers the removalOffsetAdapter as a
         * KeyListener to the field.
         */
        public DocFilter() {
            RemovalOffsetAdapter removalOffsetAdapter = new RemovalOffsetAdapter();
            RGBField.this.addKeyListener(removalOffsetAdapter);
        }

        /**
         * This adapter listens for use of the back space and delete keys to set
         * the removal offset used by the DocFilter's remove method to 1 or 0.
         * This is because using backspace should remove the character to the left 
         * of where the cursor is while delete should remove the character to the 
         * right of the cursor (only applies when user has nothing selected).
         */
        public class RemovalOffsetAdapter extends KeyAdapter {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    removalOffset = 1;
                }
                else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removalOffset = 0;
                }           
            }
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) {
        	System.out.println("DocFilter.insertString() executing!");
        	try {
				super.replace(fb, 0, doc.getLength(), fieldValue, null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
        }

        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) {
            // If true one or more characters from the field are currently selected
            if (RGBField.this.getSelectionStart() != RGBField.this.getSelectionEnd()) {
                try {
                    super.remove(fb, RGBField.this.getSelectionStart(), 
                    		RGBField.this.getSelectionEnd() - RGBField.this.getSelectionStart());
                }
                catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    super.remove(fb, RGBField.this.getCaretPosition() - removalOffset, 1);
                }
                catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            if (doc.getLength() == 0) {
            	RGBField.this.setText("0");
            }
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {

            String onlyDigits = removeNonDigits(text);

            // Insert the newly typed, or pasted, digit(s) to construct current value
            StringBuilder sb = new StringBuilder(getDocValue());
            if (RGBField.this.getSelectionStart() != RGBField.this.getSelectionEnd()) {
                sb.delete(RGBField.this.getSelectionStart(), RGBField.this.getSelectionEnd());
                sb.insert(RGBField.this.getSelectionStart(), onlyDigits);
            }
            else {
                sb.insert(RGBField.this.getSelectionStart(), onlyDigits);
            }

            try {
                // Parse the StringBuilder constructed above and check that number's size
                if (Integer.parseInt(sb.toString()) < 256) {
                    try {
                        if (RGBField.this.getSelectionStart() != RGBField.this.getSelectionEnd()) {
                            // One or more characters from the field are currently selected
                            super.replace(fb, RGBField.this.getSelectionStart(), 
                            		RGBField.this.getSelectionEnd() - RGBField.this.getSelectionStart(), 
                            		onlyDigits, null);
                        }
                        else {
                            // No selection present at this time
                            super.replace(fb, RGBField.this.getCaretPosition(), 0, onlyDigits, null);
                        }
                    }
                    catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
                else {  // Numerical value too large! - Set it to max (255)
                    try {
                        super.replace(fb, 0, doc.getLength(), "255", null);
                    }
                    catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (NumberFormatException e) {
                /* Do nothing - This exception will only be thrown here IF the textfield
                 * is initialized with an empty string AND the first character the user
                 * enters in the field is 'illegal input' (ie. something other than a 
                 * latin decimal digit). */
            }

            // If length of doc > 1 remove all leading zero's, if any.
            if (doc.getLength() > 1 && getDocValue().startsWith("0")) {
                // Search for the position of the first non-zero character  
                int firstNonZero = -1;
                for (int i = 0; i < getDocValue().length(); i++) {
                    if (! new Character('0').equals(getDocValue().charAt(i))) {
                        firstNonZero = i;
                        break;
                    }
                }

                if (firstNonZero == -1) {
                    // If firstNonZero == -1 user has entered only zero's
                    try {
                        // Remove all zero's except the first one
                        super.remove(fb, 0, doc.getLength() - 1);
                    }
                    catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // In this case user has entered a non-zero after a zero
                    try {
                        // Remove the zero's preceding the non-zero character
                        super.remove(fb, 0, firstNonZero);
                    }
                    catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Return the current value of the field parsed as an integer.
     * @return  The current value.
     */
    public int getFieldValue() {
        return Integer.parseInt(this.getText());
    }
    
    
 
    /**
     * Set the value of this RGBField.
     */
    @Override
    public void setText(String value) {
    	doc = (AbstractDocument)this.getDocument();
    	System.out.println("is docu nulll" + doc == null);
     	System.out.println("RGBField setText Method EDT? " + SwingUtilities.isEventDispatchThread());
    	try {
    		doc.insertString(0, value, null);
    		System.out.println("sysout-value in setText() : " + value);
    		fieldValue = value;
    	}
    	catch (BadLocationException e) {
    		System.out.println("BadLocationException caused by RGBField -> setText() !");
    	}
    }


    /**
     * Create a string by removing all characters except non-latin decimal digits
     * from another string.
     * @param value  The string to construct a digit-only string from.
     * @return newValue  The new string stripped from any non-digits. 
     */
    public String removeNonDigits(String value) {
        char[] chars = value.toCharArray();
        ArrayList<Character> charList = new ArrayList<>();
        for (char ch: chars) {
            if (isDigit(ch)) {
                charList.add(ch);
            }
        }
        String newValue = "";
        for (Character ch: charList) {
            newValue += ch.toString();
        }
        return newValue;
    }

    /**
     * Determine if the specified character value is a latin decimal digit.
     * @param ch  The character value to evaluate.
     */
    public boolean isDigit(char ch) {
        boolean digit = false;
        String seq = "0123456789";
        for (char sqchar: seq.toCharArray()) {
            if (Character.valueOf(sqchar).equals(Character.valueOf(ch))) {
                digit = true;
            }
        }
        return digit;
    }
}