package game;

import java.awt.Color;

public class SuperPacmanState implements State {

	@Override
	public void doAction(Board board) {
		  board.playerStateTimer.stop();
		  board.playerStateTimer.start();
		  board.player.setMode(Mode.SUPERPACMAN);
		  board.player.setColor(Color.ORANGE);
		  for(int i = 0; i < board.ghosts.length; i++) {
			  board.ghosts[i].setColor(Color.BLUE);
		  }
		
	}
	

}
