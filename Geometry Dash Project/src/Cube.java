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
	
	public Cube(Vector2 position){
		
		//Careful!!! the size of the box and the cube may not be the same 
		cubeBox = new PhysicsBox("Cube", position, 40, 40, 0, 0, 0); 
	}
	
	public void draw(GdxGraphics g) {
		if(jump){
		cubeBox.applyBodyForceToCenter(0, 20, true);
		jump = false; 
		}
		if(forward){
		cubeBox.setBodyLinearVelocity(5f, 0);	
		}
		if(backward){
			cubeBox.setBodyLinearVelocity(-5f, 0);	
			}
		Vector2 pos = cubeBox.getBodyPosition(); 
		g.drawFilledRectangle(pos.x, pos.y, 40, 40, 0); 
	}
}

