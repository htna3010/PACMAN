package game;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Mode;
import pacman.ThePacman;
import ghost.TheGhost;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import game.Maze;
/* Author : BUI Manh Trung et HO Thi Ngoc Anh
 * Name Board qui represente labyrinthe, 
 * Les mouvement de pacman et ghosts
 * KeyListenner
 * Dessiner les éléments
 */

public class Board extends JPanel implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int WIDTH = 800;
	public final static int HEIGHT = 800;
	public static final int TUNNELLEFT = 6;
	public static final int TUNNELRIGHT = 7;
	public static final int DOT = 0;
	public static final int GHOST = 7;
	public static final int VIDE = 3;
	public static final int WALL = 1;
	private static final int SCALE = 20;
	private static final int DOT_SIZE = 5;
	private static final int PACMAN_SIZE = 20;
	private static final int GHOST_SIZE = 20;
	
	 //the frame 
	private GameFrame parent;
	 //the pacman
	protected ThePacman player;
	 //the ghosts
	protected TheGhost[] ghosts;
	 //le numbre de ghosts
	private int ghostsOnScreen;
	 //le centre de ghosts
	private GhostCenter ghostBlock;
	 //le temps pour laisser sortir le ghost
	private Timer ghostTimer;
	 //le temps pour déplacer des éléments
	protected Timer moveTimer;
	 //le temps qui valoriser le pacman
	protected Timer playerStateTimer;
	 //dit si aucun déplacement réalise
	protected boolean firstMove;
	 //pour les mouvements plus doux
	protected int playerMoveCounter;
	protected int ghostMoveCounter;
	 //compte le pacgommes restes
	protected int foodLeft;
	 
	public Graphics2D g2d;
	 
	public static int pacmanScore = 0;
	public static int pacmanBonus = 0;
	public static int pacmanLives = 3;
	 
	private JLabel pacmanScoreLabel;
	private JLabel pacmanLivesLabel;
	 
	private State state;

	 //Constructor
	public Board(GameFrame parent) {
	  this.parent = parent;
	  this.foodLeft = countFood(Maze.board);
	  System.out.println(countFood(Maze.board));

	  initialisationLocations();
	  pacmanScoreLabel = new JLabel("Score: " + Board.pacmanScore, JLabel.RIGHT);
	  pacmanScoreLabel.setForeground(Color.WHITE);
	  add(pacmanScoreLabel);

	  pacmanLivesLabel = new JLabel("Lives: " + Board.pacmanLives, JLabel.LEFT);
	  pacmanLivesLabel.setForeground(Color.WHITE);
	  add(pacmanLivesLabel);
	}
	 
	 //Methode Paint
	public void paintComponent(Graphics g) {
	  g2d = (Graphics2D) g;
	  drawBoard();
	  drawPacman();
	  drawGhost();
	  pacmanScoreLabel.setText("Score:" + Board.pacmanScore);
	  pacmanLivesLabel.setText("Lives:" + Board.pacmanLives);

	}
	 
	 //Dessiner un carre noir
	private void drawBlackSquare(int x, int y) {
	  g2d.setColor(Color.BLACK);
	  g2d.fillRect(y * SCALE, x * SCALE, SCALE, SCALE);
	}

	 //Dessiner Mur, Pacgommes, La case vide
	public void drawBoard() {
		for (int i = 0; i < Maze.board.length; i++) {
				for (int j = 0; j < Maze.board[i].length; j++) {
					if (Maze.board[i][j] == 1) {
						g2d.setColor(Color.DARK_GRAY);
						g2d.fillRect(j * SCALE, i * SCALE, SCALE, SCALE);
					} else if (Maze.board[i][j] == 0) {
						drawBlackSquare(i, j);
						g2d.setColor(Color.BLUE);
						g2d.fillOval(j * SCALE + 7, i * SCALE + 7, DOT_SIZE, DOT_SIZE);
					} else if (Maze.board[i][j] == 2) {
						drawBlackSquare(i, j);
						g2d.setColor(Color.GREEN);
						g2d.fillOval(j * SCALE + 5, i * SCALE + 4, DOT_SIZE * 2, DOT_SIZE * 2);
					} else if (Maze.board[i][j] == 4) {
						drawBlackSquare(i, j);
						g2d.setColor(new Color(102, 0, 204));
						g2d.fillOval(j * SCALE + 5, i * SCALE + 4, DOT_SIZE * 2, DOT_SIZE * 2);
					} else if (Maze.board[i][j] == 5) {
						drawBlackSquare(i, j);
						g2d.setColor(Color.ORANGE);
						g2d.fillOval(j * SCALE + 5, i * SCALE + 4, DOT_SIZE * 2, DOT_SIZE * 2);
					} else if (Maze.board[i][j] == VIDE) {
						drawBlackSquare(i, j);
					} else {
						drawBlackSquare(i, j);
					} 
				}
		}
		
		for(int k = 13; k < 14; k++) {
	    		for(int h = 11; h <= 16; h++) {
	    				drawBlackSquare(k,h);
	    		}
		}
	}
	 
	 //Dessiner Pacman
	public void drawPacman() {
	  int pX = this.player.getX();
	  int pY = this.player.getY();
	  g2d.setColor(this.player.getColor());
	  g2d.fillOval(pY * SCALE, pX * SCALE, PACMAN_SIZE, PACMAN_SIZE);
	}

	 //Dessiner Ghost
	public void drawGhost() {
	  for (int i = 0; i < ghosts.length; i++) {
	   int pX = this.ghosts[i].getX();
	   int pY = this.ghosts[i].getY();
	   g2d.setColor(this.ghosts[i].getColor());
	   g2d.fillRect(pY * SCALE, pX * SCALE, GHOST_SIZE, GHOST_SIZE);
	  }
	}

	 //initialiser Location de Ghosts, Pacmans, Timer
	public void initialisationLocations() {
	  this.firstMove = true;
	  this.ghostsOnScreen = 0;
	  this.ghostBlock = new GhostCenter();
	  this.playerMoveCounter = 0;
	  this.ghostMoveCounter = 0;
	  this.player = new ThePacman(23, 14, this, Direction.UP, Direction.NONE, Mode.NORMAL);
	  this.ghosts = new TheGhost[4];
	  this.ghosts[0] = new TheGhost(11, 14, this);
	  this.ghosts[1] = new TheGhost(14, 12, this);
	  this.ghosts[2] = new TheGhost(14, 14, this);
	  this.ghosts[3] = new TheGhost(14, 16, this);
	  this.ghosts[0].setAlive(true);
	  this.ghostsOnScreen++;
	  this.ghostTimer= new Timer(5000, this);
	  this.moveTimer = new Timer(250/11, this);
	  this.playerStateTimer= new Timer(10000, this);
	  this.moveTimer.start();
	}

	 //decrement pacgommes par 1
	public void decFood() {
	  this.foodLeft--;
	}

	 //deplacer le pacman
	private void movePlayer() {
	  if (this.playerMoveCounter == 0) {
		  if ((this.player.getSavedDir() != Direction.NONE) && (this.player.checkCell(this.player.getSavedDir()))) {
			  this.player.move(this.player.getSavedDir(), false);
			  this.player.setSavedDir(Direction.NONE);
		  } else
			  this.player.move(this.player.getFacing(), false);
	  } else
	   setPlayerFacing();
	 // System.out.println("Move player");
	 }

	 //definir la direction quand il doit tourner
	private void setPlayerFacing() {
	  if ((this.playerMoveCounter != 0) && (this.player.getSavedDir() != Direction.NONE)) {
		  if (!this.player.checkCell(this.player.getFacing())) {
			  this.player.setFacing(this.player.getSavedDir());
			  this.player.setSavedDir(Direction.NONE);
		  } else {
			  if (isJunction(this.player) && (this.player.checkCell(this.player.getSavedDir()))) {
				  this.player.setFacing(this.player.getSavedDir());
				  this.player.setSavedDir(Direction.NONE);
			  }
		  }
	  }
	}

	 //verifier si il est dans un carrefour
	private boolean isJunction(ThePacman player) {
	  int x = player.getX();
	  int y = player.getY();
	  try {
		  if ((Maze.board[x][y - 1] == 1) && (Maze.board[x][y + 1] == 1))
			  return false;
		  if ((Maze.board[x - 1][y] == 1) && (Maze.board[x + 1][y] == 1))
			  return false;
	  }
		catch (Exception e){
			return false;
		}
	  		return true;
	  
	}

	 //deplacer de ghosts
	private void moveGhosts() {
	  if (this.ghostMoveCounter == 0) {
		  for (int i = 0; i < 4; i++) {
			  if (this.ghosts[i].getAlive() == true) {
				  this.ghosts[i].move();
			  }
		  }
	  }
	}

	 //deplacer pacman et ghosts dans le jeu
	private void moveAll() {
	  int playerTick = (20) / this.player.getSpeed();
	  this.playerMoveCounter = (this.playerMoveCounter + 1) % playerTick;
	  this.ghostMoveCounter = (this.ghostMoveCounter + 1) % 10;
	  System.out.println(playerMoveCounter);
	  movePlayer();
	  checkCollision();
	  moveGhosts();
	  checkCollision();
	  this.parent.endgame(this.foodLeft);
	}

	 //laisser sortir des ghosts
	private void releaseGhosts() {
	  if (this.ghostsOnScreen < 4) {
		  int loc = this.ghostBlock.removeGhost(this.ghosts);  
		  this.ghosts[loc].setAlive(true);
		  this.ghostsOnScreen++;
	  } else
		  this.ghostTimer.stop();
	}

	 //tuer un ghosts et le laisser dans le cage
	private void killGhost(int num) {
	this.ghostBlock.addGhost(this.ghosts[num], num);
	if (this.ghostsOnScreen == 4)
	   this.ghostTimer.start();
	  this.ghostsOnScreen--;
	}

	 //verifier si le pacman et ghost sont dans un bloc
	private void checkCollision() {
		for (int i = 0; i < 4; i++) {
			int pX = this.player.getX();
			int pY = this.player.getY();
			int gX = this.ghosts[i].getX();
			int gY = this.ghosts[i].getY();
			if ((pX == gX) && (pY == gY)) {
				if (Mode.isSUPERPACMAN(this.player)) {
					killGhost(i);
				} else if (Mode.isINVISIBLE(this.player)) {
	    
				} else {
					if (Board.pacmanLives > 0) {
						System.out.println(Board.pacmanLives);
						Board.pacmanLives--;
						this.player.setX(23);
						this.player.setY(14);
					} else {
						parent.endgame(foodLeft);
					}
				}
			}
		}
	}

	 //augmenter le point du joueur
	public void incScore(int score) {
	  Board.pacmanScore += score;
	  Board.pacmanBonus += score;
	  if (Board.pacmanBonus >=5000) {
		  Board.pacmanLives += 1;
		  Board.pacmanBonus = 0;
	  }
	}
	 
	 // compter des pacgommes dans le jeu
	public int countFood(int[][] board) {
	  int count = 0;
	  for (int i = 0; i < board.length; i++) {
		  for (int j = 0; j < board[i].length; j++) {
			  if (board[i][j] == 0 || board[i][j] == 2 || board[i][j] == 4 || board[i][j] == 5) {
				  count = count + 1;
			  }
		  }
	  }
	  	return count;
	}

	 //KeyListenner
	 @Override
	public void keyTyped(KeyEvent e) {}

	 @Override
	public void keyPressed(KeyEvent e) {}

	 @Override
	public void keyReleased(KeyEvent e) {
		 System.out.println("KP");
		 
	  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	   this.player.setSavedDir(Direction.LEFT);
	   
	  } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	   this.player.setSavedDir(Direction.RIGHT);
	   
	  } else if (e.getKeyCode() == KeyEvent.VK_UP) {
	   this.player.setSavedDir(Direction.UP);
	   
	  } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	   this.player.setSavedDir(Direction.DOWN);
	  }
	  
	  if (this.firstMove) {
	   this.firstMove = false;
	   this.ghostTimer.start();
	   System.out.println("start");
	   this.repaint();
	  }
	}
	 
	 //ActionListener
	 @Override
	public void actionPerformed(ActionEvent e) {
	  if (!this.firstMove) {
		  if (e.getSource() == this.moveTimer) {
			  moveAll();
			  this.repaint();
		  } else if (e.getSource() == this.ghostTimer) {
			  releaseGhosts();
			  this.repaint();
		  } else if (e.getSource() == this.playerStateTimer) {
			  state = new NormalState();
			  state.doAction(this);
			  this.repaint();
		  }
	  }
	}
}