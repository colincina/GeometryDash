import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class DamageSensor extends PhysicsStaticBox implements DrawableObject {

	int height; 
	int width; 
	boolean alreadyCollided = false; 
	
	public DamageSensor(Vector2 position, int width, int height) {
		super(null, position, width, height);
		this.width = width; 
		this.height = height; 
		this.enableCollisionListener(); 
		this.setSensor(true); 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		if (theOtherObject instanceof Cube) {
			Cube cube = (Cube) theOtherObject;
			System.out.println(theOtherObject.name + " has been hurt by " + this.name);
		}
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.LIGHT_GRAY); 
	}

	
}
