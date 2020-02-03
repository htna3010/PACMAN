package game;

import java.awt.Color;
import javax.swing.JFrame;

/*Author BUI Manh Trung et HO Thi Ngoc Anh
 *Game Frame 
 *Main
 */


public class GameFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Board game;
	
	//Constructor
	public GameFrame(){
		super("Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game= new Board(this);
		setFocusable(true);
		addKeyListener(this.game);
		this.getContentPane().add(this.game);
		getContentPane().setBackground(Color.BLACK);
		setSize(580,660);
		setVisible(true);	
	}
	
	//quand on a mange toutes pacgommes et on a perdu le pacmanLives -> the game termine
	public void endgame(int foodLeft){
		if (foodLeft == 0 || Board.pacmanLives == 0){
			dispose();
			this.game.initialisationLocations();
		}
	}

	//Main methode
	public static void main(String[] args){
		 new GameFrame();
	}	
}