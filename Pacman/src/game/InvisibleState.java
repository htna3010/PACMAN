package game;

import java.awt.Color;

public class InvisibleState implements State {

	
	@Override
	public void doAction(Board board) {
		  board.playerStateTimer.stop();
		  board.playerStateTimer.start();
		  board.player.setMode(Mode.INVISIBLE);
		  board.player.setColor(new Color(255, 255, 102));
		  for(int i = 0; i < board.ghosts.length; i++) {
			 board.ghosts[i].setColor(Color.PINK);
		  }
		
	}
	

}
