import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener{

	private JButton schliessen;
	private JButton einstellungen;
	private JButton info;
	private JButton ende;
	
	
	public static void main(String[] args) {
		
		Frame frame = new Frame("Menü");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	
	public Frame(String title){
		super(title);
		
		schliessen = new JButton("Spiel starten");
		schliessen.setBounds(120, 40, 160, 40);
		schliessen.addActionListener(this);
		add(schliessen);
		
		einstellungen = new JButton("Einstellungen");
		einstellungen.setBounds(120, 120, 160, 40);
		einstellungen.addActionListener(this);
		add(einstellungen);
		
		info = new JButton("Credits");
		info.setBounds(120, 200, 160, 40);
		info.addActionListener(this);
		add(info);
		
		ende = new JButton("Ende");
		ende.setBounds(120, 280, 160, 40);
		ende.addActionListener(this);
		add(ende);
	}
	
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == schliessen ){
			fenster();
		}
		
		if(e.getSource() == info){
			Object[] options = {"OK"};
			
			JOptionPane.showOptionDialog(null, "Programmiert von Conriano", "Informationen", 
										 JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		}
		
		if(e.getSource() == einstellungen){
			auswahl();
		}
		
		if(e.getSource() == ende){
			System.exit(0);
		}
	}
	
	
	public static void fenster(){
		JFrame fenster = new JFrame();
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenster.setSize(650, 350);
		fenster.setVisible(true);
		fenster.setLocationRelativeTo(null);
		fenster.setTitle("The Awesome Game");
		fenster.add(new Gui());
	}
	
	public static void auswahl(){
		
	}


}
