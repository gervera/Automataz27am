package Compiler2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Compiler extends JFrame {

	public String marginLR = "<html><p style='margin: 0 10'>";
	public String backE = "<html><p style='padding: 0 15; background: rgb(3,103,130); border:black 1px solid;'>";
	public String backC = "<html><p style='padding: 0 15; background: rgb(100,10,10); border:black 1px solid;'>";
	public JLabel positionLabel = new JLabel("Line 0 | Column 0 ");
	public JTextArea editorArea = new JTextArea();
	public JTextArea consoleArea = new JTextArea();
	public JFileChooser chooser = new JFileChooser();
	public FileNameExtensionFilter filter = new FileNameExtensionFilter("MicroJava Files", "mic");
	static Compiler compiler = new Compiler();
	public String FileName = null;
			
	// Constructor
	public Compiler() {
		setTitle("Compiler");
		setSize(1000, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Add Elements
		menuBar();
		editor();
		statusBar();
		console();
		init();
		openFile();
			
		// Close Event
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg) {
				System.exit(0);
			}
		});
	}
	
	// Options
	public void menuBar() {
		JMenuBar menu = new JMenuBar();
		menu.setBackground(new Color(25, 25, 112));
		JMenu file = new JMenu(marginLR + "File");
		file.setForeground(Color.WHITE);
		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { openFile(); } 
		});
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		JMenuItem newFile = new JMenuItem("New");
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { init(); }
		});
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { saveFile(); }
		});
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		JMenu help = new JMenu(marginLR + "Help");
		help.setForeground(Color.WHITE);
		JMenuItem aboutUs = new JMenuItem("About Us");
		aboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "MicroJava Compiler v3.0 (Beta) \n"
												  + "> Lenguajes y Automatas II <\n"
												  + "P.M.: Rodrigo \n"
												  + "Dev.: Gerardo German Velarde Ramirez", "About Us", 1);
			}
		});
		aboutUs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		JMenu exit = new JMenu(marginLR + "Exit");
		exit.setForeground(Color.WHITE);
		JMenuItem EXIT = new JMenuItem("Exit");
		EXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		EXIT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		JButton compile = new JButton();
		compile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consoleArea.setText(null);
				consoleArea.append("> Executing: " + FileName);
				runCompiler();
			}
		});
		ImageIcon imageIcon = new ImageIcon("Compile.png");
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); 
		imageIcon = new ImageIcon(newimg);  
		compile.setIcon(imageIcon);
		
		file.add(open);
		file.addSeparator();
		file.add(newFile);
		file.add(save);
		help.add(aboutUs);
		exit.add(EXIT);
		menu.add(file);
		menu.add(help);
		menu.add(exit);
		menu.add(compile, BorderLayout.EAST);
		add(menu, BorderLayout.NORTH);
	}
	
	// Code Area
	public void editor() {
		JPanel editorPanel = new JPanel();
		editorPanel.setPreferredSize(new Dimension(700, 0));
		editorPanel.setBackground(new Color(255, 87, 51));

		JLabel editorLabel = new JLabel(backE + "~ Editor ~");
		editorLabel.setForeground(Color.WHITE);
		
		editorArea.setBorder(BorderFactory.createLineBorder(new Color(3,103,130), 2));
		editorArea.setPreferredSize(new Dimension(680, 484));
		editorArea.setLineWrap(true);
		editorArea.setWrapStyleWord(true);
		
		// Line and Column (Caret)
		editorArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea) e.getSource();
 				try {
 					int line = 1, column = 1;
					int caretPosition = editArea.getCaretPosition();
					line = editArea.getLineOfOffset(caretPosition);
					column = caretPosition - editArea.getLineStartOffset(line);
					line++;
					positionLabel.setText("Line " + line + " | Column " + column + " ");
				} 
 				catch(Exception e2) {
 					consoleArea.append(e2.getMessage() + "\n"); 
 				}
			}
		});

		editorPanel.add(editorLabel);
		editorPanel.add(editorArea);
		add(editorPanel, BorderLayout.WEST);
	}
	
	// Logs
	public void console() {
		JPanel consolePanel = new JPanel();
		consolePanel.setPreferredSize(new Dimension(300, 0));
		consolePanel.setBackground(new Color(255, 87, 51));
		
		JLabel consoleLabel = new JLabel(backC + " Console ");
		consoleLabel.setBackground(new Color(100,10,10));
		consoleLabel.setForeground(Color.WHITE);
		
		consoleArea.setBorder(BorderFactory.createLineBorder(new Color(100,10,10), 2));
		consoleArea.setPreferredSize(new Dimension(268, 484));
		consoleArea.setEditable(false);
		consoleArea.setLineWrap(true);
		consoleArea.setWrapStyleWord(true);
		
		consolePanel.add(consoleLabel);
		consolePanel.add(new JScrollPane(consoleArea));
		add(consolePanel, BorderLayout.EAST);
	}
	
	// Editor Status
	public void statusBar() {
		JPanel statusBar = new JPanel();
		statusBar.setLayout(new BorderLayout());
		statusBar.setBackground(new Color(25, 25, 112));
		statusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		JLabel temp = new JLabel(" Ready");
		temp.setForeground(Color.WHITE);
		positionLabel.setForeground(Color.WHITE);
		statusBar.add(temp, BorderLayout.WEST);
		statusBar.add(positionLabel, BorderLayout.EAST);

		add(statusBar, BorderLayout.SOUTH);
	}
	
	// Clear Areas and Set Title
	public void init() {
		setTitle("Compiler - * New File *");
		editorArea.setText(null);
		consoleArea.setText(">>> Welcome to MicroJava!\n");
	}
	
	// Option: Open File
	public void openFile() {
		chooser.setFileFilter(filter);
		int answer = chooser.showOpenDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			try {
				FileReader fileReader = new FileReader(chooser.getSelectedFile().getPath());
				FileName = chooser.getSelectedFile().getName();
				BufferedReader bufferReader = new BufferedReader(fileReader);
				String text = "";
				init();
				
				while ((text = bufferReader.readLine()) != null) 
					editorArea.append(text + "\n");
				
				consoleArea.append("> Open File: " + FileName +"\n");
				setTitle("Compiler - Editing " + FileName);		
				bufferReader.close();	
			}
			catch(Exception e) {
				consoleArea.append(e.getMessage() + "\n");
			}
		}
	}
	
	// Option: Save File -------------------------------------- Awaiting Review(Are the new files saved?)
	public void saveFile() {
		chooser.setFileFilter(filter);
		int answer = chooser.showSaveDialog(null);
		if (answer == JFileChooser.SAVE_DIALOG-1) {
			try {
				String code[] = editorArea.getText().split("\n");
				File archivo = new File(chooser.getSelectedFile().getAbsolutePath());
		        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(archivo));
	            int i = 0;
	            while(i < code.length)
	            	bufferWriter.write(code[i++]);
		        
		        if(archivo.exists())
					consoleArea.append("> Saved File: " + chooser.getSelectedFile().getName()+"\n");
		        else 
					consoleArea.append("> New Saved File: " + chooser.getSelectedFile().getName()+"\n");
		        bufferWriter.close();
			}
			catch(Exception e) {
				consoleArea.append(e.getMessage() + "\n");
			}
		}
	}
	
	// Run Parser
	public void runCompiler() {
		new Parser(editorArea.getText(), compiler);
	}
	
	// Main
	public static void main(String[] args) {
		compiler.setVisible(true);
	}
	
}















