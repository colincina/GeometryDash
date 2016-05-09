import com.badlogic.gdx.graphics.Color;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class Decor implements DrawableObject {
	int xPos; 
	int yPos; 
	
	public Decor(int x, int y){
		
		xPos = x; 
		yPos = y; 	
	}
	
	public void draw(GdxGraphics g) {
		g.drawRectangle(xPos, yPos, g.getScreenHeight()/10, g.getScreenHeight()/10, 0);  
	}
}
