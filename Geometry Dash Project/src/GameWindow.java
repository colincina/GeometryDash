import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
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
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;


public class GameWindow extends PortableApplication{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	WorldManifold manifold; 
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	Vector<HoleOfTheDamned> holes = new Vector<HoleOfTheDamned>(); 
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
		holeCollisionNotifier(holes); 
		isGrounded(); 
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

		Vector2 globalXPosition = new Vector2(1000, 50); 
		int platformLength = 0; 
		int holeWidth = 500; 
		int stepWidth = 400; 
		int stepHeight = 200; 
		boolean noMore2 = false; 
		boolean noMore3 = false; 
		long seed = (long)(Math.random()*10000); 
		Random randomLength = new Random(seed); 
		BitmapImage cubeSkin = new BitmapImage("data/images/qr3.png");
		 
		world.setGravity(new Vector2(0, -25f));
		
		//add the background
		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 150), 100, cubeSkin); 
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

		
		for(int i = 0; i < 50; i++){
		
			if((int)(randomLength.nextDouble()*10 + 1) < 4 && !noMore2)
			{
				platformLength = (int)(randomLength.nextDouble() * 500) + 300;
				globalXPosition.x += platformLength/2; 
				globalXPosition.y = 50; 
				MapEntity2 mapPart = new MapEntity2(platformLength, 500, globalXPosition); 
				globalXPosition.x += platformLength/2; 
				toDraw.add(mapPart); 
				noMore2 = true; 
				noMore3 = false; 
			}
			else if ((int)(randomLength.nextDouble()*10 + 1) >= 4 && (int)(randomLength.nextDouble()*10 + 1) < 7)
			{
				platformLength = (int)(randomLength.nextDouble() * 2500) + 600;
				globalXPosition.x += holeWidth/2;  
				globalXPosition.y = 50; 
				MapEntity1 mapPart = new MapEntity1(platformLength, holeWidth, globalXPosition);
				globalXPosition.x += (holeWidth/2 + platformLength); 
				toDraw.add(mapPart); 
				holes.add(mapPart.hole);
				noMore3 = false; 
				noMore2 = false; 
			}
			else if ((int)(randomLength.nextDouble()*10 + 1) >= 7 && !noMore3)
			{
				platformLength = (int)(randomLength.nextDouble() * 1000) + 200;
				globalXPosition.x += stepWidth/2; 
				globalXPosition.y = 200;
				MapEntity3 mapPart = new MapEntity3(stepWidth, stepHeight, platformLength, globalXPosition); 
				globalXPosition.x += platformLength/2;
				toDraw.add(mapPart); 
				noMore3 = true; 
				noMore2 = false; 
			}
		}
//		PhysicsStaticLine line = new PhysicsStaticLine("ground Line", new Vector2(0, 100), new Vector2(100000, 100)); 
		debugRenderer = new DebugRenderer();
	}

	@Override
	public void onKeyDown(int keycode) {
		if(keycode == Keys.SPACE && cube1.grounded){
				cube1.jump = true; 
			}
		
		if (keycode == Keys.D){
			debugDraw = !debugDraw;
		}
	}
	
	public boolean isGrounded(){
		Array<Contact> contactList = world.getContactList(); 
		for(int i = 0; i < contactList.size; i++){
			Contact contact = contactList.get(i);
			//***still have doubts on the cube1.cubeBox.getBodyFixturelist().first()***
			if(contact.isTouching() && (contact.getFixtureA() == cube1.cubeBox.getBodyFixtureList().first()||
					contact.getFixtureB() == cube1.cubeBox.getBodyFixtureList().first())) 
			{				
				manifold = contact.getWorldManifold();
				for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
				Logger.log("y point " + j + " : " +manifold.getPoints()[j].y); 
				}
				Logger.log("Grounded");
				cube1.grounded = true; 
				return true; 
			}
			else
			{
				Logger.log("Not Grounded"); 
				cube1.grounded = false; 
				return false;
			}
		}
			return false; 
	}

	public boolean holeCollisionNotifier(Vector<HoleOfTheDamned> holes){
		for (HoleOfTheDamned holeOfTheDamned : holes) {
			if(holeOfTheDamned.name == "Collided Hole"){
				Logger.log("Cube is dead"); 
				return true; 
			}
		}
		return false; 
	}
	
	public static void main(String args[]){
		new GameWindow(); 
	}
}
