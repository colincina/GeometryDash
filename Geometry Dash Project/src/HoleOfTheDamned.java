import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HoleOfTheDamned extends PhysicsStaticBox implements DrawableObject {
	
	boolean playerDiedInMe = false; 
	SoundSample sDeath;
	int width; 

	public HoleOfTheDamned(String name, int width, Vector2 position, SoundSample sound) {
		super(name, position, width, Gsing.get().holeHeight, 0);
		this.width = width; 
		sDeath = sound; 
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		super.collision(theOtherObject, energy);
		
		if (theOtherObject instanceof Cube) {
			Cube cube1 = (Cube) theOtherObject;
//			cube1.cubeDead = true; 
			playerDiedInMe = true; 
			sDeath.play(); 
			System.out.println(theOtherObject.name + " Collided with " + this.name);
		}
	}

	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter();
		g.drawFilledRectangle(pos.x, pos.y, width, Gsing.get().holeHeight, 0, Color.RED);
	}
}
