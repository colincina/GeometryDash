import java.util.Vector;
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
		//Controls to make the camera move
		g.moveCamera(cube1.cubeBox.getBodyPosition().x - 100, 0);
	}

	public void onInit() {
		
		world.setGravity(new Vector2(0, -50f));
		movingForce = new Vector2(100, 0); 
		 
		//add the cube
		cube1 = new Cube(new Vector2(50, 120), 40); 
		Logger.log("Cube has been created");

		//add the background
		toDraw.add(new Background()); 
	
		//add the enemies
		int totalEnnemies = 0; 
		for(int i = 0; i < 20; i++){			
			toDraw.add(new Ennemies(200+(int)(Math.random()*1000)*i, (int)(Math.random()*300+500)));
			totalEnnemies++; 
		}
		Logger.log("Watch out!! " + totalEnnemies + " Ennemies have been created"); 
		
		//add the floor blocks
		int totalBlocks = 0; 
		int holeCounter = 0; 
		for(int i = 0; i < 100; i++)
		{		
			//pathWayToGoalOfVictory forces to create only floor blocks and no holes
			if((int)(Math.random()*100) > 30 || i < 10 || pathWayToGoalOfVictory)
			{
				Decor aDecor = new Decor(new Vector2(50+i*100, 50));
				toDraw.add(aDecor);
				lastCoordinates = aDecor.floorBox.getBodyPosition(); 
				totalBlocks++; 
			}
			
			//if a floor block is not created, a HoleOfTheDamned is created
			else
			{
				HoleOfTheDamned aHole = new HoleOfTheDamned("Hole "+holeCounter , new Vector2(50+i*100, 50), 100);
				toDraw.add(aHole); 
				aHole.setSensor(true);
				aHole.enableCollisionListener();
				holeCounter++; 
			}
		}
		//offset the lastCoordinates to display the portal on top of the floor
		lastCoordinates.y += 150; 
		//Create the portal and add it to the toDraw Vector
		PortalOfVictory aPortal = new PortalOfVictory("End Portal", lastCoordinates, 100); 
		toDraw.add(aPortal);
		aPortal.setSensor(true); 
		aPortal.enableCollisionListener(); 
		
		Logger.log(holeCounter + " Holes and " + totalBlocks + " Floor blocks have been created"); 
		debugRenderer = new DebugRenderer();
	}
	
	public static void main(String args[]){
		new GameWindow(); 
	}
}
