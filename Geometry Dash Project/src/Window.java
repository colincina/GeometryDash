import java.util.Vector;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
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
	Cube cube1; 
	//Constructor
	public Window(){
		super(2000,1000);	
	}
	
	public void onGraphicRender(GdxGraphics g) {
		g.clear(); 
		
		//Draw the objects
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		
		//Controls to make the camera move
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			g.scroll(travelSpeed, 0); 
			cube1.move(); 
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			g.scroll(travelSpeed*-1, 0); 
			cube1.move(); 
		}
		cube1.draw(g); 
	}

	public void onInit() {
		Logger.log("Welcome to the jumping cube game!"); 
		
		//add the cube
		/*
		 * always add the cube first so we know its position in the vector
		 */
		cube1 = new Cube(new Vector2(100, 120)); 
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
			toDraw.add(new Decor(50+i*100, 50));
			totalBlocks++; 
		}
		Logger.log(totalBlocks + "Floor blocks have been created"); 
	}
	
	public static void main(String args[]){
		new Window(); 
	}
}
