import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
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
import com.sun.xml.internal.org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;


public class GameWindow extends RenderingScreen{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	WorldManifold manifold;
	
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	Vector<HoleOfTheDamned> holes = new Vector<HoleOfTheDamned>(); 
	Vector<SoundSample> cubeSounds = new Vector<SoundSample>();  
	Vector<SoundSample> entity3Sounds = new Vector<SoundSample>(); 
	Vector<Spritesheet> entity3Sprites = new Vector<Spritesheet>(); 
	
	
	Vector2 globalPosition = new Vector2(1000, 50); 
	
	Cube cube1;
	DoubleJumpBox d; 
	DebugRenderer debugRenderer;
	
	private int platformLength = 0; 
	
	private float xPos = 0; 

	private boolean debugDraw; 
	private boolean noMore2 = false; 
	private boolean noMore3 = false; 
	
	Spritesheet bStep, sStep; 
	
	Random randomLength; 
	
	SoundSample sBirth, sDeath, sImpact, sLowPulse, sGameMusic; 
	
	
	public void onInit() {
		
		long seed = (long)(Math.random()*10000); 
		randomLength = new Random(seed); 
		BitmapImage cubeSkin = new BitmapImage("data/images/qr3.png");
		
		sBirth = new SoundSample("data/sounds/birth.mp3");
		sDeath = new SoundSample("data/sounds/death.mp3");
		sDeath.setVolume(0.2f); 
		sImpact= new SoundSample("data/sounds/impact.mp3");
		sLowPulse = new SoundSample("data/sounds/lowpulse.wav");
		sGameMusic = new SoundSample("data/sounds/Space Travel.mp3"); 
		sImpact.setVolume(0.2f); 
		cubeSounds.add(sBirth); 
		cubeSounds.add(sImpact); 
		entity3Sounds.add(sDeath); 
		entity3Sounds.add(sLowPulse);
		
		bStep = new Spritesheet("data/Spritesheets/bigME3SpriteSheet.png", 300, 300); 
		sStep = new Spritesheet("data/Spritesheets/smallME3SpriteSheet.png", 150, 150); 
		entity3Sprites.add(bStep); 
		entity3Sprites.add(sStep); 
		
		world.setGravity(new Vector2(0, -25f));
		
		//add the background
//		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 1000), cubeSkin, cubeSounds); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");
		
		//start platform added 
		toDraw.add(new StartPlatform(1000)); 
//		globalPosition.x += 1000; 
//		globalPosition.y = 50; 
//		toDraw.add(new MapEntity4("Air Time", globalPosition, 2000, 20, sDeath)); 
		
		for(int i = 0; i < 50; i++){
		
			if((int)(randomLength.nextDouble()*10 + 1) < 4){
				createMapEntity1();
			}
			
			else if ((int)(randomLength.nextDouble()*10 + 1) >= 4 && (int)(randomLength.nextDouble()*10 + 1) < 8 && !noMore2){
				 createMapEntity2(); 
			}
			
			else if ((int)(randomLength.nextDouble()*10 + 1) >= 8 && !noMore3){
				createMapEntity3(); 
			}
		}
		debugRenderer = new DebugRenderer();
	}
	
	public void onGraphicRender(GdxGraphics g) {
		
		g.clear(); 
		g.setBackgroundColor(Color.WHITE); 
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		holeCollisionNotifier(holes); 
//		cube1.isTouching = isInContactWithBox(); 
		moveCamera(g, cube1); 
		debugRenderer.render(world, g.getCamera().combined);

		//Draw the objects
		if (!debugDraw)
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		
		//display some text to see if the cube is grounded 
		if(cube1.isTouching)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube grounded"); 
		}
		else if (!cube1.isTouching)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube not grounded"); 
		}
		
		//check if the cube has encountered an obstacle
		if(cube1.isHurt)
		{
			cube1.emitParticle(); 
		}
		
		if(cube1.cubeDead){
			ScreenHub.s.transitionTo(2, ScreenManager.TransactionType.SLICE); 
		}
		
		g.drawFPS(Color.WHITE);
	}
	
	@Override
	public void dispose() {
		PhysicsWorld.dispose();
		super.dispose();
	}

	@Override
	public void onKeyDown(int keycode) {

		if(keycode == Keys.SPACE && cube1.isTouching){
			cube1.jump = true;
		}
		
		if (keycode == Keys.D){
			debugDraw = !debugDraw;
		}
		
		if (keycode == Keys.UP){
			Gsing.get().cSpeed += 20; 
		}
		if (keycode == Keys.DOWN){
			Gsing.get().cSpeed -= 20; 
		}
	}
	
	public boolean holeCollisionNotifier(Vector<HoleOfTheDamned> holes){
		for (HoleOfTheDamned holeOfTheDamned : holes) {
			if(holeOfTheDamned.playerDiedInMe){
				Logger.log("Cube is dead in " + holeOfTheDamned.name); 
				return true; 
			}
		}
		return false; 
	}
	
	public void moveCamera(GdxGraphics g, Cube cube)
	{
		float h = 3*(g.getScreenHeight()/4); 
		float y = cube.getBodyWorldCenter().y ;
		
			if(y < h){
	//			g.getCamera().zoom = 1;
				y = 0;
			}
			if(y > h){
				y = y - h; 
	//			g.getCamera().zoom = 2f; 
			}
		
		g.moveCamera(cube.getBodyWorldCenter().x - 1000, y);
	}
	
	protected void createMapEntity1(){
		platformLength = (int)(randomLength.nextDouble() * 1500) + 500;
		globalPosition.x += Gsing.get().holeWidthme1/2;  
		globalPosition.y = 50; 
		MapEntity1 mapPart = new MapEntity1(platformLength, globalPosition, sDeath); 
		globalPosition.x += (Gsing.get().holeWidthme1/2 + platformLength); 
		toDraw.add(mapPart); 
		holes.add(mapPart.hole);
		noMore3 = false; 
		noMore2 = false; 
	}

	protected void createMapEntity2(){
		platformLength = (int)(randomLength.nextDouble() * 400) + 200;
		globalPosition.x += platformLength/2; 
		globalPosition.y = 50; 
		MapEntity2 mapPart = new MapEntity2(platformLength, globalPosition); 
		globalPosition.x += platformLength/2; 
		toDraw.add(mapPart); 
		noMore2 = true; 
		noMore3 = false;
	}

	protected void createMapEntity3(){
		platformLength = (int)(randomLength.nextDouble() * 1000) + 200;
		globalPosition.x += Gsing.get().holeWidthme3/2; 
		globalPosition.y = 50;
		MapEntity3 mapPart = new MapEntity3(platformLength, globalPosition, entity3Sounds, entity3Sprites); 
		globalPosition.x += platformLength/2;
		toDraw.add(mapPart); 
		noMore3 = true; 
		noMore2 = false; 
	}
	

	//	public boolean isInContactWithBox(){
//		Array<Contact> contactList = world.getContactList(); 
//		for(int i = 0; i < contactList.size; i++){
//			Contact contact = contactList.get(i);
//			if(contact.isTouching() && (contact.getFixtureA() == cube1.getBodyFixtureList().first()||
//					contact.getFixtureB() == cube1.getBodyFixtureList().first())) 
//			{				
//				manifold = contact.getWorldManifold();
//				for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
//				}
//				return true; 
//			}
//			else
//			{
//				return false;
//			}
//		}
//			return false; 
//	}

	public static void main(String args[]){
		new GameWindow(); 
	}
}
