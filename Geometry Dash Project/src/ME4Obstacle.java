import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/*
 * the fact that the class is abstract forces the children to redefine every method inherited 
 * from an intreface (e.g draw from DrawableObject) this delegates the methods to the children
 */
public abstract class ME4Obstacle implements DrawableObject {
	
	protected int width1 = Gsing.get().obsW1; 
	protected int height = Gsing.get().obsH; 
	protected Vector2 pos; 
	
	/*
	 * finish defining this superclass 
	 * the sub classes are ME4HighObstacle and ME4LowObstacle
	 */
}
