import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.math.Vector2;

public class ME4LowObstacle extends ME4Obstacle{
	DamageSensor sensor; 
    	
	public ME4LowObstacle(Vector2 position){
		sensor  = new DamageSensor(position, Gsing.get().obsW2, Gsing.get().obsH);
	}
	
	public void draw(GdxGraphics g) {
		sensor.draw(g); 
	}
}
