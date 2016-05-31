import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class MapEntity3 implements DrawableObject{

	//to make the length of the platform random
	Vector2 pos; 
	
	
	PhysicsStaticBox step1; 
	PhysicsStaticBox step2;
	PhysicsStaticBox platform; 
	
	HoleOfTheDamned hole1;
	HoleOfTheDamned hole2;
	HoleOfTheDamned hole3;
	
	int platformLength; 
	int stepWidth; 
	int stepHeight; 
	int holeWidth; 
	
	boolean bigSize = true; 

	public MapEntity3(int sWidth, int sHeight, int platformLength, int hWidth, Vector2 firstStepPosition) {
		
		this.platformLength = platformLength; 
		this.stepWidth = sWidth; 
		this.stepHeight = sHeight; 
		this.holeWidth = hWidth; 
		this.pos = firstStepPosition; 
		
		if(Math.random()*2 > 1){
			this.stepWidth = stepWidth/2; 
			this.stepHeight = stepHeight/2; 
			buildSmallEntity();
		}
		else{
			buildBigEntity(); 
		}
	}

	public void buildBigEntity(){
		hole1 = new HoleOfTheDamned("Hole 1", pos, holeWidth, 25);
		hole1.setSensor(true); 
		hole1.enableCollisionListener();
		pos.x += stepWidth/2 + holeWidth/2; 
		pos.y += stepHeight/2; 
		
		step1 = new PhysicsStaticBox("Box 1", pos, stepWidth, stepHeight);
		pos.x += stepWidth/2 + holeWidth/2; 
		
		hole2 = new HoleOfTheDamned("Hole 2", pos, holeWidth, 25);
		hole2.setSensor(true); 
		hole2.enableCollisionListener();
		pos.x += stepWidth/2 + holeWidth/2; 
		pos.y += stepHeight/2; 
		
		step2 = new PhysicsStaticBox("Box 2", pos, stepWidth, stepHeight);
		pos.x += stepWidth/2 + holeWidth/2; 
		
		hole3 = new HoleOfTheDamned("Hole 3", pos, holeWidth, 25);
		hole3.setSensor(true); 
		hole3.enableCollisionListener();
		pos.x += platformLength/2 + holeWidth/2; 
		pos.y += stepHeight; 
		
		platform = new PhysicsStaticBox("platform", pos, platformLength, stepHeight/2 );
	}
	
	public void buildSmallEntity(){
		hole1 = new HoleOfTheDamned("Hole 1", pos, holeWidth, 25);
		hole1.setSensor(true); 
		hole1.enableCollisionListener();
		pos.x += stepWidth/2 + holeWidth/2; 
		pos.y += stepHeight*2; 
		
		step1 = new PhysicsStaticBox("Box 1", pos, stepWidth, stepHeight);
		pos.x += stepWidth/2 + holeWidth/2; 
		
		hole2 = new HoleOfTheDamned("Hole 2", pos, holeWidth, 25);
		hole2.setSensor(true); 
		hole2.enableCollisionListener();
		pos.x += stepWidth/2 + holeWidth/2; 
		pos.y += stepHeight*2; 
		
		step2 = new PhysicsStaticBox("Box 2", pos, stepWidth, stepHeight);
		pos.x += stepWidth/2 + holeWidth/2; 
		
		hole3 = new HoleOfTheDamned("Hole 3", pos, holeWidth, 25);
		hole3.setSensor(true); 
		hole3.enableCollisionListener();
		pos.x += platformLength/2 + holeWidth/2; 
		pos.y += stepHeight*2; 
		
		platform = new PhysicsStaticBox("platform", pos, platformLength, stepHeight/2 );
	}
	public void draw(GdxGraphics g) {
		
		Vector2 drawStep1 = new Vector2(step1.getBodyWorldCenter());
		Vector2 drawStep2 = new Vector2(step2.getBodyWorldCenter());
		Vector2 drawPlatform = new Vector2(platform.getBodyWorldCenter());
		
		g.drawFilledRectangle(drawStep1.x, drawStep1.y, stepWidth, stepHeight, 0, Color.BLACK); 
		g.drawFilledRectangle(drawStep2.x, drawStep2.y, stepWidth, stepHeight, 0, Color.BLACK); 
		g.drawFilledRectangle(drawPlatform.x, drawPlatform.y, platformLength, stepHeight/2, 0, Color.BLACK); 
		hole1.draw(g); 
		hole2.draw(g); 
		hole3.draw(g); 
	}
}
