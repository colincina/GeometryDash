import java.util.Vector;

import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class Window extends PortableApplication{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	float travelSpeed = 10f; 
	Vector2 movingForce; 
	Cube cube1; 
	
	//Constructor
	public Window(){
		super(2000,1000);	
	}
	
	public void onGraphicRender(GdxGraphics g) {
		
		g.clear(); 
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		//Draw the objects
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		cube1.draw(g); 
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			cube1.forward = true; 
			cube1.backward = false;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			cube1.backward = true; 
			cube1.forward = false;     
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			cube1.jump = true; 
		}
//		cube1.jump = false; 
		//Controls to make the camera move
		g.moveCamera(cube1.cubeBox.getBodyPosition().x - 100, cube1.cubeBox.getBodyPosition().y - 120);
	}

	public void onInit() {
		
		world.setGravity(new Vector2(0, -9.81f));
		movingForce = new Vector2(100, 0); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 200)); 
		Logger.log("Cube has been created");

		//add the background
		toDraw.add(new Background()); 
	
		//add the enemies
		int totalEnnemies = 0; 
		for(int i = 0; i < 20; i++){			
			toDraw.add(new Ennemies(100 + (int)(Math.random()*200) * i, (int)(Math.random()*200 + 500)));
			totalEnnemies++; 
		}
		Logger.log(totalEnnemies + "Ennemies have been created"); 
		
		//add the floor blocks
		int totalBlocks = 0; 
		for(int i = 0; i < 100; i++){			
			toDraw.add(new Decor(new Vector2(50+i*100, 50)));
			totalBlocks++; 
		}
		Logger.log(totalBlocks + "Floor blocks have been created"); 
	
	}
	
	public static void main(String args[]){
		new Window(); 
	}
}
