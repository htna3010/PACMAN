package ghost;

import java.awt.Color;

import game.Board;

import game.Direction;
import game.Maze;
import game.Personnage;

/*Author BUI Manh Trung et HO Thi Ngoc Anh
 * Ghosts
 * 
 */

public class TheGhost extends Personnage{
	
	private Direction lastDir;
	private boolean isAlive;
	
	/*Constructors*/
	public TheGhost(int x, int y, Board game){
		super.setX(x);
		super.setY(y);
		this.isAlive = false;
		super.setGame(game);
		super.setColor(Color.PINK);
	}
	
	//return direction derniere
	public Direction getDir(){
		return this.lastDir;
	}
	
	// setter de direction derniere 
	public void setDir(Direction dir){
		this.lastDir= dir;
	}
	
	// verifier si cest le tunnel
	private boolean isTunnel(int x, int y){
		return ((x == 14) && ((y == 5) || (y == 22)));
	}
		
	//verifier si le bloc est dans le carrefour
	private boolean isJunction(int[][] gameBoard){
		int x = super.getX();
		int y = super.getY();
		if ((gameBoard[x][y-1] == Board.WALL) && (gameBoard[x][y+1] == Board.WALL))
			return false;
		if ((gameBoard[x-1][y] == Board.WALL) && (gameBoard[x+1][y] == Board.WALL))
			return false;
		return true;
	}
		
		//verifier le bloc
	private boolean checkCell(Direction dir, int[][] gameBoard){
			int x = super.getX();
			int y = super.getY();
			try {
			if (dir == Direction.LEFT)
				return ((!isTunnel(x, y-1)) && (gameBoard[x][y-1] != Board.WALL));
			else if (dir == Direction.UP)
				return ((!isTunnel(x-1, y)) && (gameBoard[x-1][y] != Board.WALL));
			else if (dir == Direction.RIGHT)
				return ((!isTunnel(x, y+1)) && (gameBoard[x][y+1] != Board.WALL));
			else
				return ((!isTunnel(x+1, y)) && (gameBoard[x+1][y] != Board.WALL));
			}
			catch (Exception e){
				return false;
			}
	}
		
		//return un array avec des direction aleatoire
	private Direction[] randArr(){
			Direction[] ans= {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};
			for (int i=0; i < 4; i++){
				int rand= ((int)(Math.random()*4));
				Direction temp= ans[rand];
				ans[rand]= ans[3-i];
				ans[3-i]= temp;		
			}
			return ans;
	}
		
		//deplacer ghost
	public void move(){
			if (isJunction(Maze.board)){
				Direction[] dirArr= randArr();
				boolean moved= false;
				int ind= 0;
				while (!moved){	//trys to go to all the directions in the array
					if (checkCell(dirArr[ind], Maze.board)){
						move(dirArr[ind]);
						moved = true;
					}
					else
						ind++;
				}
			}
			else {
				move(this.getDir());
			}
	}
		
		
		//deplacer ghost dans cette direciton
	private void move(Direction dir){
			if ((dir == Direction.LEFT) && (getDir() != Direction.RIGHT)){
				setY(getY()-1);
				setDir(dir);
			}
			else if ((dir == Direction.UP) && (getDir() != Direction.DOWN)){
				setX(getX()-1);
				setDir(dir);
			}
			else if ((dir == Direction.RIGHT) && (getDir() != Direction.LEFT)){
				setY(getY()+1);
				setDir(dir);
			}
			else if ((dir == Direction.DOWN) && (getDir() != Direction.UP)){
				setX(getX()+1);
				setDir(dir);
			}
	}


	public boolean getAlive() {
			return isAlive;
	}


	public void setAlive(boolean isAlive) {
			this.isAlive = isAlive;
	}

	
}
