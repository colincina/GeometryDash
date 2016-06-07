import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class VarObstacle extends PhysicsStaticBox implements DrawableObject {

	int width; 
	int height; 
//	boolean iKilledTheCube = false; 
	
	public VarObstacle(int width, int height, Vector2 position) {
		super("Obstacle 1", position, width, height); 
		this.width = width; 
		this.height = height;
		this.setSensor(true); 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		super.collision(theOtherObject, energy);
		if (theOtherObject instanceof Cube) {
			Cube cube = (Cube) theOtherObject;
			System.out.println(theOtherObject.name + " Collided with " + cube.name);
		}
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.BLACK);
	}
}
