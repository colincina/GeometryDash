import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class MapEntity1 implements DrawableObject {

	HoleOfTheDamned hole;
	Obstacle obs1 = null;
	Obstacle obs2 = null; 
	PhysicsStaticBox platform; 
	Vector2 pos = new Vector2(0, 50); 
	int platformLength;
	int holeWidth; 

	public MapEntity1(int platformLength, int holeWidth, Vector2 holePosition) {
		
		this.platformLength = platformLength; 
		this.holeWidth = holeWidth; 
		hole = new HoleOfTheDamned("hole", holePosition, holeWidth, 25); 
		hole.setSensor(true); 
		hole.enableCollisionListener();
		pos.x += (holePosition.x + holeWidth/2 + platformLength/2); 
		platform = new PhysicsStaticBox("platform", pos, platformLength, 100);
		addObstacles(); 
	}


	public void draw(GdxGraphics g) {
		Vector2 drawPos = new Vector2(platform.getBodyWorldCenter());
		g.drawFilledRectangle(drawPos.x, drawPos.y, platformLength, 100, 0, Color.BLACK); 
		hole.draw(g); 
		
		if(obs1 != null)
		{
			obs1.draw(g); 
		}
		
		if(obs2 != null)
		{
			obs2.draw(g);
		}
	}

	public void addObstacles(){
		
		if(platformLength > 750)
		{
			if((int)(Math.random()*11) > 5)
			{
				float xPos = pos.x + (float)(Math.random()*200 - 100); 
				Vector2 obsPosition = new Vector2(xPos, 135); 
				obs1 = new Obstacle(40, 200, obsPosition);  
			}
		}
	}

}
