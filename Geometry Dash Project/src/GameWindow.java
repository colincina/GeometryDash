import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.audio.SoundSample;
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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;


public class GameWindow extends RenderingScreen{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	WorldManifold manifold;
	
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	Vector<HoleOfTheDamned> holes = new Vector<HoleOfTheDamned>(); 
	Vector<SoundSample> cubeSounds = new Vector<SoundSample>();  
	Vector<SoundSample> entity3Sounds = new Vector<SoundSample>(); 
	
	Vector<Spritesheet> entity3Sprites = new Vector<Spritesheet>(); 
	Spritesheet cubeBirth;
	
	
	Vector2 globalPosition = new Vector2(1000, 50); 
	
	Cube cube1;
	DoubleJumpBox d; 
	DebugRenderer debugRenderer;
	
	public int CREATION_RATE = 1;
	public final int MAX_AGE = 10;
	private int platformLength = 0; 
	
	private float xPos = 0; 
	private float zoom = 0.5f; 
	
	long seed; 

	private boolean debugDraw; 
	private boolean noMore2 = false; 
	private boolean noMore3 = false; 
	
	Spritesheet bStep, sStep; 
	
	Random random; 
	
	SoundSample sBirth, sDeath, sImpact, sLowPulse, sGameMusic; 
	
	
	public void onInit() {
		
		if(Gsing.get().seedBeingUsed){
			seed = Gsing.get().enteredSeed; 
		}
		
		else{
			Gsing.get().mapGenSeed = (long)(Math.random()*10000); 
			seed = Gsing.get().mapGenSeed; 
		}
		
		random = new Random(seed); 
		
		sBirth = new SoundSample("data/sounds/birth.mp3");
		sDeath = new SoundSample("data/sounds/death.mp3");
		sImpact= new SoundSample("data/sounds/impact.mp3");
		sLowPulse = new SoundSample("data/sounds/lowpulse.wav");
		sGameMusic = new SoundSample("data/sounds/Space Travel.mp3"); 
		
		sDeath.setVolume(0.2f); 
		sImpact.setVolume(0.2f); 
		
		cubeSounds.add(sBirth); 
		cubeSounds.add(sImpact); 
		entity3Sounds.add(sDeath); 
		entity3Sounds.add(sLowPulse);
		
		cubeBirth = new Spritesheet("data/Spritesheets/cubeBirthSpriteSheet.png", 170, 170);
		bStep = new Spritesheet("data/Spritesheets/bigME3SpriteSheet.png", 300, 300); 
		sStep = new Spritesheet("data/Spritesheets/smallME3SpriteSheet.png", 150, 150); 
		
		entity3Sprites.add(bStep); 
		entity3Sprites.add(sStep); 
		
		world.setGravity(new Vector2(0, -25f));
		
		//add the background
//		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 600), cubeBirth, cubeSounds); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");
		
		//start platform added 
		toDraw.add(new StartPlatform(2000)); 
		globalPosition.x += 1000; 
//		globalPosition.y = 50; 
//		toDraw.add(new MapEntity4("Air Time", globalPosition, 2000, sDeath)); 
		
//		createMapEntity4(); 
		for(int i = 0; i < 50; i++){
			
			if((int)(random.nextDouble()*10 + 1) < 4){
				createMapEntity1();
			}
			
			else if ((int)(random.nextDouble()*10 + 1) >= 4 && (int)(random.nextDouble()*10 + 1) < 8 && !noMore2){
				 createMapEntity2(); 
			}
			
			else if ((int)(random.nextDouble()*10 + 1) >= 8 && !noMore3){
				
				createMapEntity3(random.nextBoolean()); 
			}
		}
		debugRenderer = new DebugRenderer();
		sGameMusic.loop();
	}
	
	public void onGraphicRender(GdxGraphics g) {
		
		g.clear(); 
		g.setBackgroundColor(Color.WHITE); 
		
		/*
		 * particle trail 
		 */
//		Array<Body> bodies = new Array<Body>();
//		world.getBodies(bodies);
//		Iterator<Body> it = bodies.iterator();
//
//		while (it.hasNext()) {
//			Body p = it.next();
//			if (p.getUserData() instanceof TrailParticle) {
//				TrailParticle particle = (TrailParticle) p.getUserData();
//				particle.step();
//				toDraw.add(particle); 
//
//				if (particle.shouldbeDestroyed()) {
//					toDraw.remove(toDraw.indexOf(particle)); 
//					particle.destroy();
//				}
//			}
//		}
		
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		holeCollisionNotifier(holes); 
		//display some text to see if the cube is grounded 
		if(cube1.isTouching)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube grounded"); 
//			createParticles();
		}
		
		else if (!cube1.isTouching)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube not grounded"); 
		}
		
		moveCamera(g, cube1); 
		debugRenderer.render(world, g.getCamera().combined);

		//Draw the objects
		if (!debugDraw)
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
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
//		if(birthAnim){
//			g.getCamera().zoom = zoom;
//			Vector2 pos = cube.getBodyWorldCenter(); 
//			g.moveCamera(pos.x + g.getScreenWidth()/2, pos.y + g.getScreenHeight()/2); 
//			if(zoom >= 1){
//				birthAnim = false; 
//				g.getCamera().zoom = 1f; 
//			}
//			zoom += 0.005; 
//		}
		
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
		platformLength = (int)(random.nextDouble() * 1500) + 500;
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
		platformLength = (int)(random.nextDouble() * 400) + 200;
		globalPosition.x += platformLength/2; 
		globalPosition.y = 50; 
		MapEntity2 mapPart = new MapEntity2(platformLength, globalPosition); 
		globalPosition.x += platformLength/2; 
		toDraw.add(mapPart); 
		noMore2 = true; 
		noMore3 = false;
	}

	protected void createMapEntity3(boolean bigSize){
		platformLength = (int)(random.nextDouble() * 1000) + 200;
		if(bigSize){
			globalPosition.x += Gsing.get().holeWidthme3/2; 
		}
		else{
			globalPosition.x += (Gsing.get().holeWidthme3*1.5)/2; 
		}
		globalPosition.y = 50;
		MapEntity3 mapPart = new MapEntity3(platformLength, globalPosition, entity3Sounds, entity3Sprites); 
		globalPosition.x += platformLength/2;
		toDraw.add(mapPart); 
		noMore3 = true; 
		noMore2 = false; 
	}
	
	protected void createMapEntity4(){
		platformLength = (int)(random.nextDouble() * 5000) + 3000;
		globalPosition.x += platformLength/2; 
		MapEntity4 mapPart = new MapEntity4(platformLength, globalPosition, sImpact, random); 
		toDraw.add(mapPart);
		globalPosition.x += platformLength/2; 
	}
	
	void createParticles() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Vector2 position = new Vector2(cube1.getBodyWorldCenter()); 
			TrailParticle c = new TrailParticle(position, 10, MAX_AGE + random.nextInt(MAX_AGE / 2));

			// Apply a vertical force with some random horizontal component
			Vector2 force = new Vector2();
			force.x = random.nextFloat() * -0.00095f;
			force.y = random.nextFloat() * 0.00095f * (random.nextBoolean() == true ? -1 : 1);
			c.applyBodyLinearImpulse(force, position, true);
		}
	}

	public static void main(String args[]){
		new GameWindow(); 
	}
}
