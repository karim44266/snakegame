package snakegame;
import javax.swing.JFrame;



public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1l;
	GameFrame(){
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("karim's first game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}
}
