import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.InputMultiplexer;


public class ScreenHub extends PortableApplication{
	
		static ScreenManager s = new ScreenManager();

		ScreenHub(){
			super(2000, 1000);
		}
	
	    public void onInit() {
	        setTitle("Multiple screens and transitions");
	        Logger.log("Press enter/space to show the next screen, 1/2/3 to transition to them");
	        s.registerScreen(MenuWindow.class);
	        s.registerScreen(GameWindow.class);
	        s.registerScreen(GameOverWindow.class);
	        s.registerScreen(ControlMenu.class);
	    }
	    
	    public void onGraphicRender(GdxGraphics g) {
	        s.render(g);
	    }
	
	    public void onClick(int x, int y, int button) {
	    	// Delegate the click to the child class
	        s.getActiveScreen().onClick(x, y, button);
	    }
	 
	  
	    public void onKeyDown(int keycode) {
	    	// Delegate the onKeyDown to the child class
	    	s.getActiveScreen().onKeyDown(keycode);
	    }
	    
	    public static void main(String[] args) {
	        new ScreenHub();
	    }
}
