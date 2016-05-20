import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


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
	float impulse; 
	
	public Cube(Vector2 position, int size){
		this.size = size;
		cubeBox = new PhysicsBox("Cube", position, size, size, 10f, 0, 0); 
//		cubeBox.getBody().getFixtureList().get(0).setRestitution(0);
		cubeBox.getBody().setFixedRotation(true);
		impulse = cubeBox.getBodyMass()*15; 
	}
	
	public void update(){
		//linear x movement of the cube 
		cubeBox.applyBodyForceToCenter(50, 0, true); 
		cubeBox.setBodyLinearDamping(0.4f); 
		pos = cubeBox.getBodyWorldCenter(); 
		if(jump){
			jump = false; 
			cubeBox.applyBodyLinearImpulse(new Vector2(0, impulse), pos, true); 
		}
	}
	
	public void draw(GdxGraphics g) {
		update(); 
		Vector2 centerPos = cubeBox.getBodyWorldCenter(); 
		g.drawFilledRectangle(centerPos.x, centerPos.y, size, size, 0); 
		g.drawFilledCircle(centerPos.x, centerPos.y, 15, Color.RED); 
	}
}

