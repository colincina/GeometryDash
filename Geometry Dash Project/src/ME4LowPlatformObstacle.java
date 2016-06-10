import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.math.Vector2;


public class ME4LowPlatformObstacle extends ME4Obstacle{

	Sensor highSensor, LowSensor; 
	Platform platform; 
	
	public ME4LowPlatformObstacle(Vector2 position){
		
		platform = new Platform(position, width1, height); 
		pos = position; 
		pos.y = 125; 
		LowSensor = new Sensor(position, Gsing.get().obsW1, Gsing.get().obsH); 
	}
	
	public void draw(GdxGraphics g) {
		
		LowSensor.draw(g); 
		platform.draw(g); 
	}

}
