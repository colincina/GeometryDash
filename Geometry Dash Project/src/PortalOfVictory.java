import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;


public class PortalOfVictory extends PhysicsStaticBox implements DrawableObject{
int size; 
	
	public PortalOfVictory(String name, Vector2 position, int size) {
		super(name, position, size, size*2, 0);
		this.size = size; 
	}
		@Override
		public void collision(AbstractPhysicsObject theOtherObject, float energy) {
//			super.collision(theOtherObject, energy);
			System.out.println("Yaaaay, " + theOtherObject.name + " made it to " + this.name);
		}
		public void draw(GdxGraphics g) {
			Vector2 pos = this.getBodyPosition(); 
			g.drawFilledRectangle(pos.x, pos.y, size, size*2, 0, Color.GREEN);
		}
}
