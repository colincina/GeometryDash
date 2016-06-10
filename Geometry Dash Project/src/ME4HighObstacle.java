import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class ME4HighObstacle extends ME4Obstacle{

	Sensor sensor; 
	Platform platform; 
    	
	public ME4HighObstacle(Vector2 position){
		sensor  = new Sensor(position, Gsing.get().obsW1, Gsing.get().obsH);
		pos = position;
		pos.y -= height; 
		platform = new Platform(position, width1, height); 
	}
	
	public void draw(GdxGraphics g) {
		sensor.draw(g); 
		platform.draw(g); 
	}

}
