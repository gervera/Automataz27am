package lenguaje;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Principal 
{
	static JFrame frame=new JFrame("Micro Java");
	static JTextArea jta=new JTextArea();
	static JTextArea tf=new JTextArea("Listo Para Trabajar");
	static File archivo=new File("Programa");
	static scanner AnalizadorLexico=null;
	static parser AnalizadorSintactico=null;
	
	public static void main(String arg[]) 
	{
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		jta.setWrapStyleWord(true);
		jta.setLineWrap(true);
		jta.setFont(new Font("Calibri", 1, 20));
		jta.setBackground(new Color(0xF8F8F8)); 
		
		TextLineNumber textLineNumber = new TextLineNumber(jta);

		JScrollPane scrolljta = new JScrollPane(); 
        scrolljta.setViewportView(jta);
        scrolljta.setRowHeaderView(textLineNumber);
		
		Border border = BorderFactory.createLineBorder(new Color(0xF8F8F8));
		jta.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 7, 1, 1)));
		
		
		tf.setBackground(new Color(0xC8C8C8));
		tf.setWrapStyleWord(true);
		tf.setEditable(false);
		tf.setFont(new Font("Calibri", 1, 16));
		Border border2 = BorderFactory.createLineBorder(new Color(0xC8C8C8));
		tf.setBorder(BorderFactory.createCompoundBorder(border2, BorderFactory.createEmptyBorder(1, 3, 1, 1)));
		JScrollPane scrolltf = new JScrollPane(); 
		scrolltf.setViewportView(tf);
		
		
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrolltf, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(1000, 130));
		
		JButton BTcompilar=new JButton("Compilar");
		BTcompilar.setBackground(new Color(0xD0D0D0));
		JMenuBar menuBar=new JMenuBar();
		JMenu menu_archivo=new JMenu("Archivo");
		JMenu menu_edicion=new JMenu("Edicion");
		JMenu menu_ayuda=new JMenu("Ayuda");
		menuBar.add(menu_archivo);
		menuBar.add(menu_edicion);
		menuBar.add(menu_ayuda);
		menuBar.add(BTcompilar);
		menuBar.setBackground(new Color(0xE8E8E8));
		
		BTcompilar.addActionListener(new ActionListener() 
		{
            public void actionPerformed(ActionEvent e) 
            {	                
                try {
                	BufferedWriter bw;
					bw = new BufferedWriter(new FileWriter(archivo));
					bw.write(jta.getText());
					bw.close();
					analisisLexico();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
		});

		frame.setJMenuBar(menuBar);
		frame.add(scrolljta, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
		
	public static void analisisLexico() 
	{
		AnalizadorLexico=new scanner(archivo);	
		if(AnalizadorLexico.resultado)
		{
			tf.setText(AnalizadorLexico.informe);
			analisisSintactico();
		}
		else
			tf.setText(AnalizadorLexico.informe);
    }
	
	public static void analisisSintactico() 
	{
		AnalizadorSintactico=new parser(AnalizadorLexico.tokens);
		if( AnalizadorSintactico.resultado)
			tf.append("Analisis Sintactico correcto"+"\n"+AnalizadorSintactico.informe);
		else
			tf.append("Analisis Sintactico incorrecto"+"\n"+AnalizadorSintactico.informe);
		
	}
	
}


