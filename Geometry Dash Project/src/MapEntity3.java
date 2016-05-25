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
	PhysicsStaticBox step3;
	PhysicsStaticBox platform; 
	int platformLength; 
	int stepWidth; 
	int stepHeight; 

	public MapEntity3(int stepWidth, int stepHeight, int platformLength, Vector2 firstStepPosition) {
		this.platformLength = platformLength; 
		this.stepWidth = stepWidth; 
		this.stepHeight = stepHeight; 
		this.pos = firstStepPosition; 
		step1 = new PhysicsStaticBox("Box 1", firstStepPosition, stepWidth, stepHeight);
		pos.x += stepWidth; 
		pos.y += stepHeight; 
		step2 = new PhysicsStaticBox("Box 2", pos, stepWidth, stepHeight);
		pos.x += stepWidth; 
		pos.y += stepHeight; 
		step3 = new PhysicsStaticBox("Box 3", pos, stepWidth, stepHeight);
		pos.x += platformLength/2 + stepWidth/2;
		pos.y += stepHeight; 
		platform = new PhysicsStaticBox("platform", pos, platformLength, stepHeight/2 );
	}


	public void draw(GdxGraphics g) {
		
		Vector2 drawStep1 = new Vector2(step1.getBodyWorldCenter());
		Vector2 drawStep2 = new Vector2(step2.getBodyWorldCenter());
		Vector2 drawStep3 = new Vector2(step3.getBodyWorldCenter());
		Vector2 drawPlatform = new Vector2(platform.getBodyWorldCenter());
		
		g.drawFilledRectangle(drawStep1.x, drawStep1.y, stepWidth, stepHeight, 0, Color.BLUE); 
		g.drawFilledRectangle(drawStep2.x, drawStep2.y, stepWidth, stepHeight, 0, Color.BLUE); 
		g.drawFilledRectangle(drawStep3.x, drawStep3.y, stepWidth, stepHeight, 0, Color.BLUE); 
		g.drawFilledRectangle(drawPlatform.x, drawPlatform.y, platformLength, stepHeight/2, 0, Color.BLUE); 

	}
}
