import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;


public class Background implements DrawableObject {

	BitmapImage bckgrnd;
	public Background(){
		bckgrnd = new BitmapImage("data/images/CrownNebula.bmp/");; 
	}
	public void draw(GdxGraphics g) {
		g.drawBackground(bckgrnd, 0, 0); 
	}

}
