import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class ME4HighObstacle extends ME4Obstacle{

	DamageSensor sensor; 
	Platform platform;
	HorizontalCollisionSensor hzSensor; 
    	
	public ME4HighObstacle(Vector2 position){
		sensor  = new DamageSensor(position, width1, height);
		pos = position;
		pos.y -= height; 
		platform = new Platform(position, width1, height); 
		pos.x -= width1/2; 
		hzSensor = new HorizontalCollisionSensor(pos, 10, height - 10);
	}
	
	public void draw(GdxGraphics g) {
		sensor.draw(g); 
		hzSensor.draw(g); 
		platform.draw(g); 
	}

}
