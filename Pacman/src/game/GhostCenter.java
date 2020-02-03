package game;

import java.util.ArrayList;

/*Author BUI Manh Trung et HO Thi Ngoc Anh
 *Le Cage des Ghosts 
 */

import ghost.TheGhost;

public class GhostCenter {
	
	
	//nombre de ghost dans le jeu
	private int numOfGhosts;
	//tenir l'ordre de ghosts dans un arraylist
	private ArrayList<Integer> ghostNums = new ArrayList<>();
	
	
	/*Constructors*/
	public GhostCenter(){
		this.numOfGhosts= 3;
		this.ghostNums.add(1);
		this.ghostNums.add(2);
		this.ghostNums.add(3);
	}
	
	//ajouter ghost 'g' dans le cage
	public void addGhost(TheGhost g, int ghostNum){
			g.setAlive(false);
			if (numOfGhosts == 0){
				g.setX(14);
				g.setY(16);
			}
			else if (numOfGhosts == 1){
				g.setX(14);
				g.setY(14);
			}
			else if (numOfGhosts == 2){
				g.setX(14);
				g.setY(12);
			}
			else if (numOfGhosts == 3){
				g.setX(15);
				g.setY(14);
			}
			this.ghostNums.add(ghostNum);
			this.numOfGhosts++;
	}
	
	//laisser sortir des ghosts
		public int removeGhost(TheGhost[] g){
			int loc;
			if (this.numOfGhosts == 4)
				loc= this.ghostNums.remove(3);
			else
				loc= this.ghostNums.remove(0);
			g[loc].setX(11);
			g[loc].setY(14);
			if (Math.random() > 0.5) {
				g[loc].setDir(Direction.LEFT);
				System.out.println("a");
			}else {
				g[loc].setDir(Direction.RIGHT);
			}
			g[loc].setAlive(true);
			this.numOfGhosts--;
			return loc;
		}
	
	
		
}
