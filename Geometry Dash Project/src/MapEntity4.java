import java.util.Random;
import java.util.Vector;
import com.badlogic.gdx.math.Vector2;
import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class MapEntity4 implements DrawableObject {
	
	Vector<ME4Obstacle>  obstacles = new Vector<ME4Obstacle>(); 
	Platform platform; 
	SoundSample loveHurts; 
	int height = Gsing.get().platformHeight; 
	int width; 
	Random r; 
	Vector2 obsPos = new Vector2(0,0); 
	
	//Same sound sample as if the cube had touched a VarObstacle
	
	/*
	 * have to make a method to make the cube bounce when it touches an obstacle 
	 */
	
	public MapEntity4(int width, Vector2 position, SoundSample sound, Random rand) {
		platform = new Platform(position, width, Gsing.get().platformHeight);
		this.loveHurts = sound; 
		this.width = width; 
		this.r = rand; 
		obsPos.x = position.x - width/2; 
		obsPos.y = position.y + height + Gsing.get().obsH/2; 
		generateRandomObstacles(); 
	}

	public void draw(GdxGraphics g) {
		platform.draw(g); 
		for (ME4Obstacle obs : obstacles) {
			obs.draw(g); 
		}
	}
	
	public void generateRandomObstacles(){
		
		int possibleObstacles = width/(Gsing.get().obsW2 + Gsing.get().obsOffset); 
		System.out.println(possibleObstacles);
		obsPos.x += Gsing.get().obsOffset/2;
		for(int i = 0; i <= possibleObstacles ; i++){
				obsPos.y = 125; 
				obsPos.x += Gsing.get().obsOffset; 
				
				//logic to decide which type of obstacle will be instanciated
				if(r.nextDouble()*10 > 5)
				{
					obsPos.y = 350; 
					ME4HighObstacle obstacle = new ME4HighObstacle(obsPos); 
					obstacles.add(obstacle);
				}
				
				else if(r.nextDouble()*11 <= 5){
					obsPos.y  = 300; 
					ME4LowPlatformObstacle obstacle = new ME4LowPlatformObstacle(obsPos); 
					obstacles.add(obstacle); 
				}
				else
				{
					ME4LowObstacle obstacle = new ME4LowObstacle(obsPos); 
					obstacles.add(obstacle);
				}	
		}
	
	}
}
