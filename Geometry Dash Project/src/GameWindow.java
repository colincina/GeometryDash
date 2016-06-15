import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.Spritesheet;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.desktop.physics.DebugRenderer;
import ch.hevs.gdx2d.lib.GdxGraphics;
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
	Vector<TrailParticle> trailParticles = new Vector<TrailParticle>(); 
	
	Vector2 globalPosition = new Vector2(0, 50); 
	
	Cube cube1;
	Platform startPlatform; 
	DoubleJumpBox d; 
	DebugRenderer debugRenderer;
	
	private float xPos = 0; 
	private float zoom = 0.5f; 
	
	private int platformLength = 0; 

	long seed; 

	private boolean debugDraw; 
	private boolean noMore2 = false; 
	private boolean noMore3 = false; 
	private boolean noMore4 = false; 
	
	Spritesheet bStep, sStep; 
	
	Random random; 
	
	SoundSample sBirth, sDeath, sImpact, sLowPulse;
	static SoundSample sGameMusic; 
	
	
	public void onInit() {
		
		if(Gsing.get().seedBeingUsed){
			seed = Gsing.get().enteredSeed; 
		}
		
		else if(Gsing.get().retryMap){
			seed = Gsing.get().mapGenSeed; 
			Vector2 position = new Vector2(Gsing.get().totalDistance, 400);
			VarObstacle beacon = new VarObstacle(position); 
			toDraw.add(beacon); 
			Gsing.get().retryMap = false; 
		}
		
		else{
			Gsing.get().mapGenSeed = (long)(Math.random()*10000); 
			seed = Gsing.get().mapGenSeed; 
		}
		
		random = new Random(seed); 
		sLowPulse = new SoundSample("data/sounds/lowpulse.wav");
		sGameMusic = new SoundSample("data/sounds/Space Travel.mp3"); 
		world.setGravity(new Vector2(0, -25f));
		generateWorld(); 
		debugRenderer = new DebugRenderer();
//		sGameMusic.loop();
	}
	
	public void onGraphicRender(GdxGraphics g) {
		
		g.clear(); 
		g.setBackgroundColor(Color.WHITE); 
		
		//display some text to see if the cube is grounded 
			if(cube1.isTouching && !cube1.itsTheEnd)
			{
				g.setColor(Color.BLACK); 
				g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube grounded"); 
				cube1.createTrailParticles();
			}
			
			else if (!cube1.isTouching){
				g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube not grounded"); 
			}
		
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
		moveCamera(g, cube1); 
		
		debugRenderer.render(world, g.getCamera().combined);
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		Iterator<Body> it = bodies.iterator();


		while (it.hasNext()) {
			Body p = it.next();

			if (p.getUserData() instanceof TrailParticle) {
				TrailParticle particle = (TrailParticle) p.getUserData();
				particle.step();
				particle.draw(g); 

				if(!cube1.itsTheEnd){
					if (particle.shouldbeDestroyed()) {
						particle.destroy();
					}
				}
			}
		}
		
		//Draw the objects
		if (!debugDraw){
			for (DrawableObject obj : toDraw) {
				obj.draw(g); 
			}
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

		if(keycode == Keys.SPACE && cube1.isTouching && cube1.jumpMode){
			cube1.jump = true;
		}
		
		if(keycode == Keys.SPACE && cube1.isTouching && cube1.flyMode){
			cube1.fly = true;
			System.out.println("the cube can fly !!!!!");
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
		platformLength = (int)(random.nextDouble() * 1500) + 500;
		globalPosition.x += Gsing.get().holeWidthme1/2;  
		globalPosition.y = 50; 
		MapEntity1 mapPart = new MapEntity1(platformLength, globalPosition, sDeath); 
		globalPosition.x += (Gsing.get().holeWidthme1/2 + platformLength); 
		toDraw.add(mapPart); 
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
		noMore4 = false; 
	}

	protected void createMapEntity3(boolean bigSize){
		platformLength = (int)(random.nextDouble() * 1000) + 200;
		if(random.nextBoolean()){
			Gsing.get().bigSize = true; 
			globalPosition.x += Gsing.get().holeWidthme3/2; 
		}
		else{
			Gsing.get().bigSize = false; 
			globalPosition.x += Gsing.get().holeWidthme3Big/2; 
		}
		globalPosition.y = 50;
		MapEntity3 mapPart = new MapEntity3(platformLength, globalPosition); 
		globalPosition.x += platformLength/2;
		toDraw.add(mapPart); 
		noMore3 = true; 
		noMore2 = false; 
		noMore4 = false; 
	}
	
	protected void createMapEntity4(){
		platformLength = (int)(random.nextDouble() * 10000) + 5000;
		globalPosition.x += platformLength/2; 
		globalPosition.y = 50;
		MapEntity4 mapPart = new MapEntity4(platformLength, globalPosition, sImpact, random); 
		toDraw.add(mapPart);
		globalPosition.x += platformLength/2; 
		noMore4 = true; 
		noMore2 = false; 
		noMore3 = false; 
	}
	
	protected void generateWorld(){
		//add the background
//		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(-500, 600), random); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");
		
		//start platform added
		startPlatform = new Platform(globalPosition, 2000, 100); 
		startPlatform.enableCollisionListener(); 
		toDraw.add(startPlatform); 
		globalPosition.x += 1000; 
		
			for(int i = 0; i < 5; i++){
				int rand = random.nextInt(4); 
				switch (rand) {
				case 0:
						createMapEntity1();
							break;
	
				case 1: 
						System.out.println(rand);
						if(noMore2){
							break; 
						}
						createMapEntity2(); 
						break; 
					
				case 2: 
						if(noMore3){
							break; 
						}
						createMapEntity3(random.nextBoolean()); 
						break; 
					
				case 3: 
						if(noMore4){
							break; 
						}
						createMapEntity4(); 
						break; 
				default:
						System.out.println("Numbers are shit");
						break;
				}
			}
			
		globalPosition.x += 9500; 
		AirTime yolo = new AirTime(15000, 200, globalPosition, random); 
		toDraw.add(yolo); 
			
			for(int i = 0; i < 5; i++){
				int rand = random.nextInt(4); 
				switch (rand) {
				case 0:
						createMapEntity1();
							break;
	
				case 1: 
						System.out.println(rand);
						if(noMore2){
							break; 
						}
						createMapEntity2(); 
						break; 
					
				case 2: 
						if(noMore3){
							break; 
						}
						createMapEntity3(random.nextBoolean()); 
						break; 
					
				case 3: 
						if(noMore4){
							break; 
						}
						createMapEntity4(); 
						break; 
				default:
						System.out.println("Numbers are shit");
						break;
				}
			}
		globalPosition.x += 1000;
		globalPosition.y = 50;
		EndPlatform theEnd = new EndPlatform(globalPosition, 2000, 100); 
		toDraw.add(theEnd); 
	}

	public static void main(String args[]){
		new GameWindow(); 
	}
}
