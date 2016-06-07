import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class ME4Obstacle implements DrawableObject {
	
	int width = Gsing.get().obsW; 
	int height = Gsing.get().obsH; 
	Vector2 pos; 
	
	/*
	 * finish defining this superclass 
	 * the sub classes are ME4HighObstacle and ME4LowObstacle
	 */
	public ME4Obstacle(Vector2 position){
		this.pos = position; 
	}

	public void draw(GdxGraphics g) {
		
	}
	
	
}
