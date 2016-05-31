import java.util.Random;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Particle extends PhysicsBox implements DrawableObject{
	
	int size; 
	Random r;
	long seed; 
	
	public Particle(String name, Vector2 position, int size, long seed){
		super(name, position, size, size, 50f, 0, 5f, 0); 
		this.size = size; 
		this.seed = seed; 
		r = new Random(seed);
	}

	public Particle(String name, Vector2 position){
		super(name, position, 0, 0);
	}
	
	public void draw(GdxGraphics g) {
		
		r.setSeed(seed);
		int red = (int)(r.nextInt()*255);
		int green = (int)(r.nextInt()* 255);
		int blue = (int)(r.nextInt()* 255);
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, size, size, this.getBodyAngle(), new Color(red, green, blue, 0)); 
	}

}
