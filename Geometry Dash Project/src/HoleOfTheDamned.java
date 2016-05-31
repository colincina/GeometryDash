import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HoleOfTheDamned extends PhysicsStaticBox implements DrawableObject {

	int width;
	int height;
	boolean playerDiedInMe = false;

	public HoleOfTheDamned(String name, Vector2 position, int width, int height) {
		super(name, position, width, height, 0);
		this.width = width;
		this.height = height;
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		
		if(theOtherObject.getClass() == Cube.class){
			this.playerDiedInMe = true;
		}
		System.out.println(theOtherObject.name + " Collided with " + this.name);
	}

	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter();
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.RED);
	}
}
