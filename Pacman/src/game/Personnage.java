package game;

import java.awt.Color;

import game.Board;

public abstract class Personnage {
	private int x;
	private int y;
	
	private Color color;
	
	private Board game;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Board getGame() {
		return game;
	}

	public void setGame(Board game) {
		this.game = game;
	}
	
	

}
