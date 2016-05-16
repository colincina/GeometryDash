import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HoleOfTheDamned extends PhysicsStaticBox implements DrawableObject {

	int width; 
	
	public HoleOfTheDamned(String name, Vector2 position, int width) {
		super(name, position, width, 100, 0);
		this.width = width; 
	}
		@Override
		public void collision(AbstractPhysicsObject theOtherObject, float energy) {
//			super.collision(theOtherObject, energy);
			System.out.println(theOtherObject.name + " Collided with " + this.name);
		}
		public void draw(GdxGraphics g) {
			Vector2 pos = this.getBodyPosition(); 
			g.drawFilledRectangle(pos.x, pos.y, width, 100, 0, Color.RED);
		}
}
