import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class Cube implements DrawableObject{

	
	int xPos; 
	int yPos; 
	
	public Cube(int x, int y){
		
		xPos = x; 
		yPos = y; 	
	}
	
	public void draw(GdxGraphics g) {
		g.drawFilledRectangle(xPos, yPos, g.getScreenHeight()/25, g.getScreenHeight()/25, 0); 
	}
}

