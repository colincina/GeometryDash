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
import com.badlogic.gdx.physics.box2d.World;


public class GameWindow extends PortableApplication{

	DebugRenderer dbgRenderer;
	World world = PhysicsWorld.getInstance();
	Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	Vector<HoleOfTheDamned> holes = new Vector<HoleOfTheDamned>(); 
	Vector2 globalPosition = new Vector2(1000, 50); 
	Cube cube1;
	DoubleJumpBox d; 
	DebugRenderer debugRenderer;
	private boolean debugDraw; 
	private int platformLength = 0; 
	private int holeWidth = 500; 
	private int stepWidth = 250; 
	private int stepHeight = 250; 
	private int holeWidth2 = 300; 
	private boolean noMore2 = false; 
	private boolean noMore3 = false; 
	Random randomLength; 
	
	//Constructor
	public GameWindow(){
		super(2000,1000);	
	}
	
	public void onInit() {
		
		long seed = (long)(Math.random()*10000); 
		randomLength = new Random(seed); 
		BitmapImage cubeSkin = new BitmapImage("data/images/qr3.png");
		
		world.setGravity(new Vector2(0, -25f));
		
		//add the background
//		toDraw.add(new Background()); 
		
		//add the cube
		cube1 = new Cube(new Vector2(100, 1000), 75, cubeSkin); 
		toDraw.add(cube1); 
		Logger.log("Cube has been created");
		
		//start platform added 
		toDraw.add(new StartPlatform(1000)); 
		
		//double jump  block created and added 
		d = new DoubleJumpBox("Double jump test", new Vector2(1000, 500), 100, 100, 0);
		toDraw.add(d); 
		
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
		cube1.update();
		moveCamera(g, cube1); 
		debugRenderer.render(world, g.getCamera().combined);

		//		Draw the objects
		if (!debugDraw)
		for (DrawableObject obj : toDraw) {
			obj.draw(g); 
		}
		
		//display some text to see if the cube is grounded 
		if(cube1.grounded)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube grounded"); 
		}
		else if (!cube1.grounded)
		{
			g.setColor(Color.BLACK); 
			g.drawString(cube1.getBodyPosition().x, cube1.getBodyPosition().y + 100, "Cube not grounded"); 
		}
		
		//check if the cube has encountered an obstacle
		if(cube1.isHurt)
		{
			cube1.emitParticle(); 
		}
		
		//check if the cube has touched a DoubleJumpBox
		if(d.cubeReJump){
			d.cubeReJump = false; 
			cube1.grounded = true; 
		}
		
		g.drawFPS(Color.WHITE);
	}
	
//	@Override
//	public void dispose() {
//		PhysicsWorld.dispose();
//		super.dispose();
//	}

	@Override
	public void onKeyDown(int keycode) {
		if(keycode == Keys.SPACE && cube1.grounded){
				cube1.jump = true; 
			}
		
		if (keycode == Keys.D){
			debugDraw = !debugDraw;
		}
		
		if (keycode == Keys.UP){
			cube1.speed += 5; 
		}
		if (keycode == Keys.DOWN){
			cube1.speed -= 5; 
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
		globalPosition.x += holeWidth/2;  
		globalPosition.y = 50; 
		MapEntity1 mapPart = new MapEntity1(platformLength, holeWidth, globalPosition);
		globalPosition.x += (holeWidth/2 + platformLength); 
		toDraw.add(mapPart); 
		holes.add(mapPart.hole);
		noMore3 = false; 
		noMore2 = false; 
}

	protected void createMapEntity2(){
	platformLength = (int)(randomLength.nextDouble() * 400) + 200;
	globalPosition.x += platformLength/2; 
	globalPosition.y = 50; 
	MapEntity2 mapPart = new MapEntity2(platformLength, 500, globalPosition); 
	globalPosition.x += platformLength/2; 
	toDraw.add(mapPart); 
	noMore2 = true; 
	noMore3 = false;
}

	protected void createMapEntity3(){
	platformLength = (int)(randomLength.nextDouble() * 1000) + 200;
	globalPosition.x += holeWidth2/2; 
	globalPosition.y = 50;
	MapEntity3 mapPart = new MapEntity3(stepWidth, stepHeight, platformLength, holeWidth2, globalPosition); 
	globalPosition.x += platformLength/2;
	toDraw.add(mapPart); 
	noMore3 = true; 
	noMore2 = false; 
}

	public static void main(String args[]){
		new GameWindow(); 
	}
}
