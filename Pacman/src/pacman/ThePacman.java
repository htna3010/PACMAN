package pacman;
import game.Direction;
import game.InvisibleState;
import game.LabyrintheState;
import game.Maze;
import game.Mode;
import game.Personnage;
import game.State;
import game.SuperPacmanState;

import java.awt.Color;

import game.Board;

/*Author BUI Manh Trung et HO Thi Ngoc Anh
 * PacMan
 * 
 */

public class ThePacman extends Personnage {
	
	//la direction de pacman facing (left ,up, right ,down) 
	private Direction facing;
	//la direction desire de player
	private Direction savedDir;
	private int speed = 3;
	private Mode mode;
	private State state;
	
	/*Constructors*/
	//creates a new pacman
	public ThePacman(int x, int y, Board game, Direction facing, Direction savedDir, Mode mode){
		super.setColor(Color.RED);
		super.setX(x);
		super.setY(y);
		this.facing= facing;
		this.savedDir= savedDir;
		super.setGame(game);
		this.mode = mode;
	}  
	
	//getter speed
	public int getSpeed() {
		return this.speed;
	}
	
		public Direction getFacing(){
			return this.facing;
		}
		
		public void setFacing(Direction f){
			this.facing= f;
		}
		
		//returns this pacman's desiredDirection
		public Direction getSavedDir(){
			return this.savedDir;
		}
		
		//sets this pacman's desiredDirection
		public void setSavedDir(Direction dir){
			this.savedDir= dir;
	}
		
		//verifier si le case peut accepter le deplacement
	public boolean checkCell(Direction dir){
			int[][] gameBoard= Maze.board;
			int x = super.getX();
			int y = super.getY();
			try {
			if (dir == Direction.LEFT)
				return (gameBoard[x][y-1] != Board.WALL);
			else if (dir == Direction.UP)
				return (gameBoard[x-1][y] != Board.WALL);
			else if (dir == Direction.RIGHT)
				return (gameBoard[x][y+1] != Board.WALL);
			else
				return (gameBoard[x+1][y] != Board.WALL);
			}
			catch (Exception e){
				return false;
			}
	}
		
		//deplacer le pacman dans la direc donnee
	private void moveToCell( Direction dir, int[][] gameBoard){
			int x = super.getX();
			int y = super.getY();
		//	System.out.println(x);
		//	System.out.println(y);
			if (dir == Direction.LEFT){
				if (gameBoard[x][y-1] == Board.TUNNELLEFT)
					y= 27;
				else
					y --;
					super.setY(y);
			}
			else if (dir == Direction.UP) {
				x--;
				super.setX(x);
			}else if (dir == Direction.RIGHT) {
				if (gameBoard[x][y+1] == Board.TUNNELRIGHT)
					y= 0;
				else
					y++;
					super.setY(y);
			}else if (dir == Direction.DOWN) {
				x++;
				super.setX(x);
			}else
				return;
			this.facing= dir;
		
			
			//manger des pacgommes dans le case
			if (gameBoard[x][y] == Board.DOT){	
				gameBoard[x][y]= Board.VIDE;
				super.getGame().incScore(100);
				super.getGame().decFood();
			}
			else if (gameBoard[x][y] == 2){
				gameBoard[x][y]= Board.VIDE;
				super.getGame().incScore(300);
				super.getGame().decFood();
				//this.game.labyrintheMode();	
				state = new LabyrintheState();
				state.doAction(super.getGame());
			}
			else if (gameBoard[x][y] == 4){
				gameBoard[x][y]= Board.VIDE;
				super.getGame().incScore(500);
				super.getGame().decFood();
				//this.game.invisibleMode();
				state = new InvisibleState();
				state.doAction(super.getGame());
			}	
			else if (gameBoard[x][y] == 5){
				gameBoard[x][y]= Board.VIDE;
				super.getGame().decFood();
				super.getGame().incScore(1000);
				//this.game.superpacmanMode();
				state = new SuperPacmanState();
				state.doAction(super.getGame());
			}
			
	}
		
		//deplacer le pacman dans cette Direction dir
	public void move(Direction dir, boolean didPlayerSend){
			int[][] gameBoard= Maze.board;
			if (checkCell(dir))
				moveToCell(dir, gameBoard);
			else if (didPlayerSend)
				this.savedDir= dir;
			else {
				if (this.savedDir != dir)
					this.savedDir= dir;
				else
					this.savedDir= Direction.NONE;
			}
	}
		
	 @Override
	public String toString() { 
		    return "Pacman. X: " + super.getX() + "\tY: " + super.getY();
	}
	 
	public Mode getMode() {
			return mode;
	}

	public void setMode(Mode mode) {
			this.mode = mode;
	}

}
