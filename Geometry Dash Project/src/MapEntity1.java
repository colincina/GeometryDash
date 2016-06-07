import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class MapEntity1 implements DrawableObject {

	HoleOfTheDamned hole;
	VarObstacle obs1 = null;
	Platform platform; 
	Vector2 pos = new Vector2(0, 50); 
	int platformLength;

	public MapEntity1(int platformLength, Vector2 holePosition, SoundSample death) {
		
		this.platformLength = platformLength; 
		
		hole = new HoleOfTheDamned(Gsing.get().holeWidthme1, holePosition, death); 
		hole.setSensor(true); 
		hole.enableCollisionListener();
		
		pos.x += (holePosition.x + Gsing.get().holeWidthme1/2 + platformLength/2); 
		platform = new Platform(pos, platformLength, 100);
		platform.enableCollisionListener(); 
		addObstacles(); 
	}


	public void draw(GdxGraphics g) {
		
		hole.draw(g); 
		platform.draw(g); 
		if(obs1 != null)
		{
			obs1.draw(g); 
		}
	}

	public void addObstacles(){
		
		if(platformLength > 750)
		{
			if((int)(Math.random()*11) > 5)
			{
				float xPos = pos.x + (float)(Math.random()*200 - 100); 
				Vector2 obsPosition = new Vector2(xPos, 200); 
				obs1 = new VarObstacle(40, 200, obsPosition);  
			}
		}
	}

}
