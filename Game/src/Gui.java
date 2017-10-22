import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gui extends JPanel implements ActionListener{
	
	Image img;
	int key;
	int lauf;
	
	public Gui(){
		key = 0;
		lauf = 0;
		
		setFocusable(true);
		ImageIcon u = new ImageIcon("backgrund.png");
		img = u.getImage();
	}
	
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D f2 =(Graphics2D) g;
		
		f2.drawImage(img, 0, 0, null);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}
	
	private class AL extends KeyAdapter{
		public AL(){
			
		}
		
		public void keyPressed(KeyEvent e){
			key = e.getKeyCode();
			
			if(key == KeyEvent.VK_LEFT){
				lauf = 1;
			}
			
			
			if(key == KeyEvent.VK_RIGHT){
				lauf = -1;
			}
		}
		
		public void keyReleased(KeyEvent e){
			key = e.getKeyCode();
			
			if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT){
				lauf = 0;
			}
		}
		
		
	}
}
