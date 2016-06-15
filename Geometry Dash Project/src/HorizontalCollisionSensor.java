import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HorizontalCollisionSensor extends PhysicsStaticBox implements DrawableObject{

	int width; 
	int height; 
	
	public HorizontalCollisionSensor(Vector2 position, int width, int height) {
		super(null, position, width, height, 0);
		this.setSensor(true);
		this.width = width; 
		this.height = height; 
	}

	
	public void draw(GdxGraphics g) {
		Vector2 pos = this.getBodyWorldCenter(); 
		g.drawFilledRectangle(pos.x, pos.y, width, height, 0, Color.GREEN);
	}
} 
	

