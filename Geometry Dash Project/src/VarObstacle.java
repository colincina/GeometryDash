import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class VarObstacle implements DrawableObject {

	int width; 
	int height; 
	DamageSensor sensor; 
//	boolean iKilledTheCube = false; 
	
	public VarObstacle(Vector2 position) {
		sensor = new DamageSensor(position, Gsing.get().VarObsW1, Gsing.get().VarObsH1); 
	}
	
	public void draw(GdxGraphics g) {
		sensor.draw(g); 
	}
}
