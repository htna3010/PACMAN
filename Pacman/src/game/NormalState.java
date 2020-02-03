package game;

import java.awt.Color;

public class NormalState implements State {

	@Override
	public void doAction(Board board) {
		 board.playerStateTimer.stop();
		 board.player.setMode(Mode.NORMAL);
		  board.player.setColor(Color.RED);
		  for(int i = 0; i < board.ghosts.length; i++) {
			  board.ghosts[i].setColor(Color.PINK);
		  }
		
	}

}
