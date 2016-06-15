import java.util.Random;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Particle extends PhysicsBox implements DrawableObject{
	
	int size; 
	Random random;
	int angle; 
	Vector2 impulse = new Vector2(0, 0); 

	public Particle(Vector2 position, int size, int angle, Random rand){
		super(null, position, size, size, 50f, 0.5f, 0.5f, 0); 
		this.size = size; 
		random = rand; 
		this.setCollisionGroup(-1); 
		impulse.x = (float) Math.sin(angle)*Gsing.get().pImpulse; 
		impulse.y = (float) Math.cos(angle)*Gsing.get().pImpulse; 
		this.applyBodyLinearImpulse(impulse, position, true); 
	}

	public Particle(Vector2 position){
		super(null, position, 0, 0);
	}
	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, size, size, this.getBodyAngle() * MathUtils.radiansToDegrees, Color.BLACK); 

	}

}
