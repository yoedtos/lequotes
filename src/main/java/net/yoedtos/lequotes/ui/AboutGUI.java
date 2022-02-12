package net.yoedtos.lequotes.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.yoedtos.lequotes.entity.About;

public class AboutGUI extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton close;
	
	public AboutGUI(About about) {
		this.setTitle("About");
		this.setResizable(false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.setPreferredSize(new Dimension(280, 315));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel table = new JPanel(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		
		JLabel title = new JLabel(about.getTitle());
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		constraint.gridx = 0;
		constraint.gridwidth = 2;
		constraint.gridy = 0;
		table.add(title, constraint);
		
		Border cellBorder = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		JLabel name = new JLabel(about.getName()) ;
		name.setBorder(cellBorder);
		constraint.gridx = 0;
		constraint.gridwidth = 2;
		constraint.gridy = 1;
		table.add(name, constraint);
		
		constraint.gridwidth = 1;
		constraint.anchor = GridBagConstraints.WEST;
		constraint.gridx = 0;
		constraint.gridy = 2;
		table.add(new JLabel("Version: "), constraint);
		
		JLabel version = new JLabel(about.getVersion());
		constraint.gridx = 1;
		constraint.gridy = 2;
		table.add(version, constraint);
		
		constraint.gridx = 0;
		constraint.gridy = 3;
		table.add(new JLabel("Author: "), constraint);
		
		JLabel author = new JLabel(about.getAuthor());
		author.setBorder(cellBorder);
		constraint.gridx = 1;
		constraint.gridy = 3;
		table.add(author, constraint);
		
		Border inside = BorderFactory.createEmptyBorder(20, 15, 20, 15);
		Border outside = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border compound = BorderFactory.createCompoundBorder(outside, inside);
		
		JTextPane credits = new JTextPane();
		credits.setEditable(false);
		credits.setBackground(this.getBackground());
		credits.setBorder(BorderFactory.createTitledBorder(compound,
				"Credits", TitledBorder.CENTER, TitledBorder.CENTER));
		credits.setText(about.getCredits());
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridx = 0;
		constraint.gridwidth = 2;
		constraint.gridy = 4;
		table.add(credits, constraint);
		
		mainPanel.add(table);
		mainPanel.add(credits);
		
		Container button = new Container();
		button.setLayout(new FlowLayout(FlowLayout.CENTER));
		close = new JButton("Ok");
		close.addActionListener(this);
	    button.add(close);
		mainPanel.add(button);
		
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		String command = event.getActionCommand();
		
		if(command.equals("Ok")) {
			closeDialog();
		}
	}	
	
	private void closeDialog() {
		setVisible(false);
		dispose();
	}
}
