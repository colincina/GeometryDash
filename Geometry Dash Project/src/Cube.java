import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.math.Vector2;


public class Cube implements DrawableObject{
	
	PhysicsBox cubeBox;
	
	public Cube(Vector2 position){
		
		//Careful!!! the size of the box and the cube may not be the same 
		cubeBox = new PhysicsBox("CubeBox", position, 40, 40);  	
	}
	
	public void draw(GdxGraphics g) {

		Vector2 pos = cubeBox.getBodyPosition(); 
		g.drawFilledRectangle(pos.x, pos.y, g.getScreenHeight()/25, g.getScreenHeight()/25, 0); 
	}

	public void move(){
		Vector2 velocity = new Vector2(5, 0); 
		cubeBox.applyBodyAngularImpulse(5f, true); 
	}
}

