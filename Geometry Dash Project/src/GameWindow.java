import java.util.Vector;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class GameWindow extends PortableApplication{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	float travelSpeed = 10f; 
	Vector2 movingForce; 
	//to know when the map ends so we can place the PortalOfVictory
	Vector2 lastCoordinates; 
	Cube cube1; 
	DebugRenderer debugRenderer;
	boolean pathWayToGoalOfVictory = true; 
	//Constructor
	public GameWindow(){
		super(2000,1000);	
	}
	
	public void onGraphicRender(GdxGraphics g) {
		
		g.clear(); 
		debugRenderer.render(world, g.getCamera().combined);
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		
		//Draw the objects
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		
		//Controls to make the camera move
		g.moveCamera(cube1.cubeBox.getBodyPosition().x - 100, 0);
	}

	public void onInit() {
		
		world.setGravity(new Vector2(0, -25f));
		movingForce = new Vector2(100, 0); 
		//add the background
		toDraw.add(new Background()); 
		 
		//add the cube
		cube1 = new Cube(new Vector2(100, 122), 40); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");

	
		
		//add the enemies
		int totalEnnemies = 0; 
		for(int i = 0; i < 20; i++){			
			toDraw.add(new Ennemies(200+(int)(Math.random()*1000)*i, (int)(Math.random()*300+500)));
			totalEnnemies++; 
		}
		Logger.log("Watch out!! " + totalEnnemies + " Ennemies have been created"); 
		
		toDraw.add(new StartPlatform(600)); 
		
		for(int i = 0; i < 10; i++){
			toDraw.add(new MapEntity1(new Vector2(700 + (1200*i), 50), 200)); 
		}

		debugRenderer = new DebugRenderer();
	}
	
//	@Override
//	public void onKeyDown(int keycode) {
//		super.onKeyDown(keycode);
//
//		if (keycode == Keys.SPACE)
//		{
//			cube1.jump = true; 
//		}
//	}
//	
//	public void onKeyUp(int keycode){
//		super.onKeyUp(keycode); 
//		if (keycode == Keys.SPACE){
//			cube1.jump = false;
//		}
//	}
	public static void main(String args[]){
		new GameWindow(); 
	}
}
