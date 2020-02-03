package game;

import pacman.ThePacman;

/*Author BUI Manh Trung et HO Thi Ngoc ANh
 * Mode de Pacman quand il a mange des speciales pacgommes
 */

public enum Mode {
	NORMAL, INVISIBLE, SUPERPACMAN, LABYRINTHE;
	
	 /** Returns true if superpacman */
	 public static boolean isSUPERPACMAN(ThePacman pm) {
	  return pm.getMode() == Mode.SUPERPACMAN;
	 }

	 /** Returns true if invisible*/
	 public static boolean isINVISIBLE(ThePacman pm) {
	  return pm.getMode() == Mode.INVISIBLE;
	 }

	 /** Returns true if invisible*/
	 public boolean isNORMAL(ThePacman pm) {
	  return pm.getMode() == Mode.NORMAL;
	 }

	 /** Returns true if invisible*/
	 public boolean isLABYRINTHE(ThePacman pm) {
	  return pm.getMode() == Mode.LABYRINTHE;
	 }
}
