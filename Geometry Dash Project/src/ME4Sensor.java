import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class Sensor extends PhysicsStaticBox implements DrawableObject {

	int width = Gsing.get().obsW; 
	int height = Gsing.get().obsH; 
	
	public Sensor(Vector2 position) {
		super(null, position, Gsing.get().obsW, Gsing.get().obsH);
		this.enableCollisionListener(); 
		this.setSensor(true); 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		if (theOtherObject instanceof Cube) {
			Cube cube1 = (Cube) theOtherObject;
			cube1.isHurt = true; 
			System.out.println(theOtherObject.name + " has been hurt by " + this.name);
		}
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.LIGHT_GRAY); 
	}

	
}
