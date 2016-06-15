import java.util.Random;
import java.util.Vector;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class AirTime implements DrawableObject{
	
	int length; 
	int obsWidth; 
	int obs1Height; 
	int obs2Height;
	int platformWidth; 
	Platform beginPlat, endPlat;
	ModeSelector mod1, mod2; 
	Vector2 tempPos; 
	Random r; 
	Vector<AirTimeObstacle> obstacles = new Vector<AirTimeObstacle>(); 
	
	
	public AirTime(int length, int obsWidth, Vector2 position, Random rand){
		
		this.length = length; 
		this.tempPos = position; 
		this.obsWidth = obsWidth; 
		this.platformWidth = Gsing.get().AirPlatformWidth; 
		this.r = rand; 
		createStructure(); 
	}


	public void draw(GdxGraphics g) {
		
		mod2.draw(g); 
		mod1.draw(g); 
		beginPlat.draw(g); 
		endPlat.draw(g); 
		
		for (AirTimeObstacle obs : obstacles) {
			obs.draw(g); 
		}
	}
	
	public void createStructure(){
		
		tempPos.x -= (this.length/2 + (platformWidth - 10)); 
		tempPos.y = 1100; 
		mod1 = new ModeSelector(tempPos, 20, 2000); 
		tempPos.y = 50; 
		tempPos.x += (platformWidth/2 - 10); 
		beginPlat = new Platform(tempPos, platformWidth, 100);
		tempPos.x += platformWidth/2; 

		
		for(int i = 0; i < length/obsWidth; i++){
			tempPos.x += obsWidth/2; 
			obs1Height = r.nextInt(Gdx.graphics.getHeight()/2 - 350); 
			tempPos.y = obs1Height/2; 
			AirTimeObstacle obst1 = new AirTimeObstacle(tempPos, obsWidth, obs1Height);
			obstacles.add(obst1); 
			obs2Height = r.nextInt(Gdx.graphics.getHeight()/2 - obs1Height); 
			tempPos.y = Gdx.graphics.getHeight() - obs2Height/2;
			AirTimeObstacle obst2 = new AirTimeObstacle(tempPos, obsWidth, obs2Height);
			obstacles.add(obst2); 
			tempPos.x += obsWidth/2; 
		}
	
		tempPos.x += 10; 
		tempPos.y = 1100; 
		mod2 = new ModeSelector(tempPos, 20, 2000); 
		tempPos.x += (platformWidth/2 - 10);
		tempPos.y = 50; 
		endPlat = new Platform(tempPos, platformWidth, 100);
		tempPos.x += platformWidth/2; 
	}
}
