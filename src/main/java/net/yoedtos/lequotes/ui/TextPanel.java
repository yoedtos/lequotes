package net.yoedtos.lequotes.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextPanel extends JPanel {

	
	private static final long serialVersionUID = 1L;
	
	private JTextPane panelText;
	
	public TextPanel() {
		
		SimpleAttributeSet simpleAtribute = new SimpleAttributeSet();		
		StyleConstants.setFontFamily(simpleAtribute, "Sans");
		StyleConstants.setAlignment(simpleAtribute, StyleConstants.ALIGN_JUSTIFIED);

	    setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelText = new JTextPane();
        panelText.setEditable(false);
    	panelText.setParagraphAttributes(simpleAtribute, true);
        panelText.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPanel = new JScrollPane(panelText);
    	scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	scrollPanel.setPreferredSize(new Dimension(400, 500));
    	add(scrollPanel);
	}
	
	public JTextPane getTxtPanel() {
		return panelText;
	}
	
}
