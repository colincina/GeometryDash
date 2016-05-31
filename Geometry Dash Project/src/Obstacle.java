import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Obstacle extends PhysicsStaticBox implements DrawableObject {

	int width; 
	int height; 
	boolean iKilledTheCube = false; 
	public Obstacle(int width, int height, Vector2 position) {
		super("Obstacle 1", position, width, height); 
		this.width = width; 
		this.height = height;
		this.setSensor(true); 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		this.iKilledTheCube = true;
		System.out.println(theOtherObject.name + " Collided with " + this.name);
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.BLACK);
	}
}
