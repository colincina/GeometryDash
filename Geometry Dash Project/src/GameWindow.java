import java.util.Random;
import java.util.Vector;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class GameWindow extends PortableApplication{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	Cube cube1; 
	DebugRenderer debugRenderer;
	private boolean debugDraw; 
	
	//Constructor
	public GameWindow(){
		super(2000,1000);	
	}
	
	public void onGraphicRender(GdxGraphics g) {
		g.clear(); 
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		cube1.update();
		g.moveCamera(cube1.cubeBox.getBodyPosition().x - 100, 0);
		debugRenderer.render(world, g.getCamera().combined);
//		Draw the objects
		if (!debugDraw)
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		g.drawFPS(Color.WHITE);
	}

	public void onInit() {
		
		world.setGravity(new Vector2(0, -25f));
		long seed = (long)(Math.random()*10000); 
		Random randomLength = new Random(seed); 
		
		//add the background
		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 150), 100); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");
		
		//add the obstacles
//		int totalObstacles = 0; 
//		for(int i = 0; i < 20; i++){			
//			toDraw.add(new Obstacle(200+(int)(Math.random()*1000)*i, (int)(Math.random()*300+500)));
//			totalObstacles++; 
//		}
//		Logger.log("Watch out!! " + totalObstacles + " Obstacles have been created"); 
		
		toDraw.add(new StartPlatform(1000)); 

		Vector2 position = new Vector2(1000, 50); 
		int platformLength = 0; 
		int holeWidth = 500; 
		
		for(int i = 0; i < 50; i++){
			if((int)(randomLength.nextDouble()*10 + 1) > 5)
			{
				platformLength = (int)(randomLength.nextDouble() * 500) + 300;
				position.x += platformLength/2; 
				MapEntity2 mapPart = new MapEntity2(platformLength, 500, position); 
				position.x += platformLength/2 + 10; 
				toDraw.add(mapPart); 
			}
			else
			{
				platformLength = (int)(randomLength.nextDouble() * 2500) + 600;
				position.x += holeWidth/2;  
				MapEntity1 mapPart = new MapEntity1(platformLength, holeWidth, position);
				position.x += (holeWidth/2 + platformLength); 
				toDraw.add(mapPart); 
			}
		}
//		PhysicsStaticLine line = new PhysicsStaticLine("ground Line", new Vector2(0, 100), new Vector2(100000, 100)); 
		debugRenderer = new DebugRenderer();
	}

	@Override
	public void onKeyDown(int keycode) {
		if(keycode == Keys.SPACE)
			cube1.jump = true; 
		
		if (keycode == Keys.D)
			debugDraw = !debugDraw;
	}
	

	public static void main(String args[]){
		new GameWindow(); 
	}
}
