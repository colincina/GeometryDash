import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Cube implements DrawableObject{
	
	AbstractPhysicsObject cubeBox; 
	boolean forward = false; 
	boolean backward = false; 
	boolean jump = false; 
	int size; 
	
	public Cube(Vector2 position, int size){
		
		cubeBox = new PhysicsBox("Cube", position, size, size, 2f, 0, 0); 
		this.size = size;
	}
	
	public void draw(GdxGraphics g) {
		if(jump){
//		cubeBox.applyBodyForceToCenter(0, 100f, true);
//		jump = false; 
		Vector2 impulse = new Vector2(0, 0.1f); 
		Vector2 pos = cubeBox.getBodyPosition(); 
		cubeBox.applyBodyLinearImpulse(0, 0.01f, pos.x, pos.y, true); 
		}
		jump = false; 
		if(forward){
		cubeBox.setBodyLinearVelocity(5f, 0);	 
		}
		if(backward){
			cubeBox.setBodyLinearVelocity(-5f, 0);	
			}
		Vector2 pos = cubeBox.getBodyPosition(); 
		g.drawFilledRectangle(pos.x, pos.y, size, size, 0); 
	}
}

