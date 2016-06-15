import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


public class EndPlatform implements DrawableObject{

	Platform platform; 
	WinSensor sensorOfTheWin; 
	Vector2 tempPos; 
	
	public EndPlatform(Vector2 position, int width, int height){
		tempPos = position; 
		platform = new Platform(tempPos, width, height); 
		tempPos.x -= width/4; 
		tempPos.y = Gdx.graphics.getHeight()/2; 
		sensorOfTheWin = new WinSensor(tempPos, 20, Gdx.graphics.getHeight()); 
	}

	public void draw(GdxGraphics g) {
		sensorOfTheWin.draw(g); 
		platform.draw(g); 
	}
}
