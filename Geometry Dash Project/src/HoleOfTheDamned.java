import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

	
	public class HoleOfTheDamned extends PhysicsStaticBox implements DrawableObject {
		
		int width; 
		boolean playerDiedInMe = false; 
		JumpDetector detector; 
		
		public HoleOfTheDamned(int width, Vector2 position) {
			super(null, position, width, Gsing.get().holeHeight, 0);
			detector = new JumpDetector(new Vector2(position.x - width/2 + Gsing.get().detectorW/2 + Gsing.get().cSize, position.y + Gsing.get().detectorH/2)); 
			detector.enableCollisionListener(); 
			detector.setSensor(true); 
			this.width = width; 
		}
	
		@Override
		public void collision(AbstractPhysicsObject theOtherObject, float energy) {
			super.collision(theOtherObject, energy);
			
			if (theOtherObject instanceof Cube) {
				Cube cube1 = (Cube) theOtherObject;
				cube1.cubeDead = true; 
				playerDiedInMe = true; 
				System.out.println(theOtherObject.name + " Collided with " + this.name);
			}
		}
	
		public void draw(GdxGraphics g) {
			
			Vector2 pos = this.getBodyWorldCenter();
			g.drawFilledRectangle(pos.x, pos.y, width, Gsing.get().holeHeight, 0, Color.RED);
		}
	}
