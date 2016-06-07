import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;


public class JumpDetector extends PhysicsStaticBox implements DrawableObject{
	
	
	public JumpDetector(Vector2 position) {
		super(null, position, Gsing.get().detectorW, Gsing.get().detectorH, 0);
	}
		@Override
		public void collision(AbstractPhysicsObject theOtherObject, float energy) {
			super.collision(theOtherObject, energy);
			if (theOtherObject instanceof Cube) {
				System.out.println(theOtherObject.name + "Should not be grounded annymore");
				Cube cube = (Cube) theOtherObject;
				cube.checkJump = true; 
			}
		}
		public void draw(GdxGraphics g) {
			Vector2 pos = this.getBodyWorldCenter(); 
			g.drawFilledRectangle(pos.x, pos.y, Gsing.get().detectorW, Gsing.get().detectorH, 0, Color.BLUE);
		}
}
