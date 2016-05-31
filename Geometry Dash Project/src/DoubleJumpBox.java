import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.utils.Logger;


public class DoubleJumpBox extends PhysicsStaticBox implements DrawableObject {

	float width; 
	float height; 
	boolean cubeReJump = false; 
	
	public DoubleJumpBox(String name, Vector2 position, float width, float height, float angle) {
		super(name, position, width, height, angle);
		this.width = width; 
		this.height = height; 
		this.setSensor(true); 
		this.enableCollisionListener(); 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		if(theOtherObject.getClass() == Cube.class){
		cubeReJump = true; 
		Logger.log("Cube is allowed to jump once again");
		}
	}
	
	public void draw(GdxGraphics g) {
		
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.YELLOW);
	}

}
