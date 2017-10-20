package rgb.color.mixer;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

import java.util.ArrayList;

/**
 * Write a description of class ConstrainedTextField here.
 * 
 * @author Thomas Timmermans
 * @version 21-07-2017
 */
public class ConstrainedTextField
{
    JFrame frame;
    JTextField field;
    AbstractDocument doc;
    DocFilter docFilter;

    /**
     * Constructor for objects of class ConstrainedTextField
     */
    public ConstrainedTextField()
    {
        frame = new JFrame();
        JPanel main = (JPanel)frame.getContentPane();
        main.setLayout(new GridBagLayout());

        field = new JTextField(6);
        main.add(field);

        doc = (AbstractDocument)field.getDocument();
        docFilter = new DocFilter();
        doc.setDocumentFilter(docFilter);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    public class DocFilter extends DocumentFilter {

        // The removal offset used by the DocFilter's remove method.
        private int removalOffset = 1;

        /**
         * DocFilter Constructor. Also registers the removalOffsetAdapter as a
         * KeyListener to the field.
         */
        public DocFilter() {
            RemovalOffsetAdapter removalOffsetAdapter = new RemovalOffsetAdapter();
            field.addKeyListener(removalOffsetAdapter);
        }

        /**
         * This adapter listens for use of the back space and delete keys to set
         * the removal offset used by the DocFilter's remove method to 1 or 0.
         * This is because using backspace should remove the charater to the left 
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
        }

        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) {
            
            if (field.getSelectionStart() != field.getSelectionEnd()) {
            	// If true one or more characters from the field are selected
                try {
                    super.remove(fb, field.getSelectionStart(), field.getSelectionEnd() - field.getSelectionStart());
                }
                catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    super.remove(fb, field.getCaretPosition() - removalOffset, 1);
                }
                catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            if (doc.getLength() == 0) {
                field.setText("0");
            }
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {

            String onlyDigits = removeNonDigits(text);

            // Insert the newly typed, or pasted, digit(s) to construct current value
            StringBuilder sb = new StringBuilder(getDocValue());
            if (field.getSelectionStart() != field.getSelectionEnd()) {
                sb.delete(field.getSelectionStart(), field.getSelectionEnd());
                sb.insert(field.getSelectionStart(), onlyDigits);
            }
            else {
                sb.insert(field.getSelectionStart(), onlyDigits);
            }

            try {
                // Parse the StringBuilder constructed above and check that number's size
                if (Integer.parseInt(sb.toString()) < 256) {
                    try {
                        if (field.getSelectionStart() != field.getSelectionEnd()) {
                            super.replace(fb, field.getSelectionStart(), 
                                field.getSelectionEnd() - field.getSelectionStart(), onlyDigits, null);
                        }
                        else {
                            super.replace(fb, field.getCaretPosition(), 0, onlyDigits, null);
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

                try {
                    super.remove(fb, 0, firstNonZero);
                }
                catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Return the current value of the field parsed as an integer.
     * @return  The current value.
     */
    public int getFieldValue() {
        return Integer.parseInt(field.getText());
    }

    /**
     * Create a string by removing all characters except non-latin decimal digits
     * from another string.
     * @param value  The string to construct a digit-only string from.
     * @return newValue  The new string stripped of any non-digits. 
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

