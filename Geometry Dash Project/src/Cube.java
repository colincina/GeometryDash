import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;


public class Cube implements DrawableObject{
	
	AbstractPhysicsObject cubeBox; 
	float velChange; 
	float desiredVel = 10;
	float force; 
	Vector2 vel; 
	Vector2 pos;
	boolean jump = false; 
	int size; 
	int remainingJumpSteps = 1 ; 
	
	public Cube(Vector2 position, int size){
		
		this.size = size;
		cubeBox = new PhysicsBox("Cube", position, size, size, 50f, 0, 0); 
	}
	
	public void draw(GdxGraphics g) {
		
		//linear x movement of
		cubeBox.applyBodyForceToCenter(100, 0, true); 
		cubeBox.setBodyLinearDamping(0.4f); 
		pos = cubeBox.getBodyPosition(); 
//		vel = cubeBox.getBodyLinearVelocity();
		if(jump){
			
			Transform yolo1 = cubeBox.getBodyTransform();
			Vector2 pos = new Vector2(cubeBox.getBodyPosition());
			yolo1.setPosition(new Vector2(pos.x, pos.y + 5f)); 
			jump = false; 
			float impulse = cubeBox.getBodyMass()/25; 
			cubeBox.applyBodyLinearImpulse(0, impulse, pos.x, pos.y, true); 
		}
		
		if(remainingJumpSteps % 100 == 0){
			jump = true; 
		}
		remainingJumpSteps++; 
		g.drawFilledRectangle(pos.x, pos.y, size, size, 0); 
		//Logger.log("Jumping status : " + jump); 
//		Logger.log("Cube x Velocity : " + cubeBox.getBodyLinearVelocity().x); 
	}
}

