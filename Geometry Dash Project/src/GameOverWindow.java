import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lib.utils.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class GameOverWindow extends RenderingScreen{
	Skin skin;
	Stage stage;
	TextButton newGameButton, quitGameButton, retryButton;
	TextField textArea;
	int stateCounter = 0; 
	String state; 
	BitmapImage bckgrnd; 

	public static void main(String[] args) {
		new GameOverWindow();
	}

	
	public void onInit(){
		
		int buttonWidth = 180;
		int buttonHeight = 30;
		bckgrnd = new BitmapImage("data/images/CrownNebula.bmp/");
//		setTitle("Jumping Cube");

		stage = new Stage();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(Gdx.input.getInputProcessor());
		Gdx.input.setInputProcessor(multiplexer);

		// Load the default skin (which can be configured in the JSON file)
		skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));

		newGameButton = new TextButton("Return to Menu", skin); // Use the initialized skin
		newGameButton.setWidth(buttonWidth);
		newGameButton.setHeight(buttonHeight);

		quitGameButton = new TextButton("Quit", skin); // Use the initialized skin
		quitGameButton.setWidth(buttonWidth);
		quitGameButton.setHeight(buttonHeight);
		
		retryButton = new TextButton("Retry the same map", skin); 
		retryButton.setWidth(buttonWidth);
		retryButton.setHeight(buttonHeight);

		newGameButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .6));
		quitGameButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, (int) (Gdx.graphics.getHeight() * .7));
		retryButton.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth/2, (int) (Gdx.graphics.getHeight() * .8)); 
		

		/**
		 * Adds the buttons to the stage
		 */
		stage.addActor(newGameButton);
		stage.addActor(quitGameButton);
		stage.addActor(retryButton); 

		/**
		 * Register listener
		 */
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				ScreenHub.s.transitionTo(0, ScreenManager.TransactionType.SMOOTH); 
			}
		});
	
		quitGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				System.exit(0); 
			}
		});
		
		retryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gsing.get().retryMap = true; 
				ScreenHub.s.transitionTo(1, ScreenManager.TransactionType.SMOOTH); 
			}
		});
	}
		
	public void onGraphicRender(GdxGraphics g) {
		g.clear(Color.BLACK);
		g.drawBackground(bckgrnd, 0, 0); 

		// This is required for having the GUI work properly
		stage.act();
		stage.draw();
		g.setColor(Color.GREEN); 
		g.drawStringCentered(Gdx.graphics.getHeight()/2, "Congratulation! Your journey was " + Gsing.get().totalDistance + "Long!");
		g.drawStringCentered(700, "Seed : " + Gsing.get().mapGenSeed);
//		g.drawStringCentered(getWindowHeight() / 4, "Colin Cina & Martin Juon");
		g.drawSchoolLogo();
		g.drawFPS();
	}

	@Override
	public void dispose() {
		PhysicsWorld.dispose();
		super.dispose();
		GameWindow.sGameMusic.stop(); 
		GameWindow.sGameMusic.dispose(); 
	}
}

