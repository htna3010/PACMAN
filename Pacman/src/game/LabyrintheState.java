package game;

import java.awt.Color;

public class LabyrintheState implements State {

	@Override
	public void doAction(Board board) {
		 board.playerStateTimer.stop();
		  board.playerStateTimer.start();
		  Maze.board = Maze.newBoard;
		  board.foodLeft = board.countFood(Maze.board);
		  System.out.println(board.foodLeft);
		
		  board.player.setMode(Mode.LABYRINTHE);
		  board.player.setColor(Color.RED);
		  for(int i = 0; i < board.ghosts.length; i++) {
			  board.ghosts[i].setColor(Color.PINK);
		  }
		
	}

}
