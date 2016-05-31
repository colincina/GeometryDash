import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.utils.Logger;

import com.badlogic.gdx.Input;


public class ScreenHub extends PortableApplication{
	 private ScreenManager s = new ScreenManager();
	    private int transactionTypeId;

	 
	    public void onInit() {
	        setTitle("Multiple screens and transitions");
	        Logger.log("Press enter/space to show the next screen, 1/2/3 to transition to them");
	        s.registerScreen(GameWindow.class);
	        s.registerScreen(MenuWindow.class);
	        s.registerScreen(GameOverWindow.class);
	    }
	  
	    
	    public void onGraphicRender(GdxGraphics g) {
	        s.render(g);
	    }

	
	    public void onClick(int x, int y, int button) {
	       
	    	// Delegate the click to the child class
	        s.getActiveScreen().onClick(x, y, button);
	    }

	 
	    public void onKeyDown(int keycode) {
	        super.onKeyDown(keycode);

	        // Display the next screen without transition
	        if (keycode == Input.Keys.ENTER)
	            s.activateNextScreen();

	        // Switch to next screen using all available transitions effects
	        if (keycode == Input.Keys.SPACE) {
	            s.transitionToNext(ScreenManager.TransactionType.values()[transactionTypeId]);

	            // Switch to the next transition effect
	            transactionTypeId = (transactionTypeId + 1) % ScreenManager.TransactionType.values().length;
	        }

	        if (keycode == Input.Keys.NUM_1)
	            s.transitionTo(0, ScreenManager.TransactionType.SLICE); // s.activateScreen(0);

	        if (keycode == Input.Keys.NUM_2)
	            s.transitionTo(1, ScreenManager.TransactionType.SLIDE); // s.activateScreen(1);

	        if (keycode == Input.Keys.NUM_3)
	            s.transitionTo(2, ScreenManager.TransactionType.SMOOTH); // s.activateScreen(2);
	    }

	    public static void main(String[] args) {
	        new ScreenHub();
	    }
}
