import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;


public class Decor implements DrawableObject {

	AbstractPhysicsObject floorBox; 
	
	public Decor(Vector2 position){
	
		floorBox = new PhysicsStaticBox("Floor Part", position, 100, 100); 
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = floorBox.getBodyPosition(); 
		g.drawRectangle(pos.x, pos.y, 100, 100, 0);  
	}
}
