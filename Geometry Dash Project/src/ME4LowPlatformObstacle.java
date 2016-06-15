import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.math.Vector2;


public class ME4LowPlatformObstacle extends ME4Obstacle{

	DamageSensor highSensor, LowSensor; 
	Platform platform; 
	HorizontalCollisionSensor hzSensor; 
	JumpDetector jumpSensor; 
	
	public ME4LowPlatformObstacle(Vector2 position){
		
		pos = position; 
		platform = new Platform(position, width1, height); 
		pos.x -= width1/2; 
		hzSensor = new HorizontalCollisionSensor(pos, 10, height - 10); 
		pos.x += width1; 
		jumpSensor = new JumpDetector(position); 
		pos.x -= width1/2; 
		pos.y = 125; 
		LowSensor = new DamageSensor(position, width1, height); 
	}
	
	public void draw(GdxGraphics g) {
		
		LowSensor.draw(g); 
		hzSensor.draw(g); 
		platform.draw(g); 
	}

}
