
package ffcm.antsim.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import ffcm.antsim.resource.Resources;

public class MenuBar
{
	private SpriteBatch spriteBatch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera guiCamera;
	
	public Stage stage;
	private Table table;
	private Skin skin;
	
	private ImageButton createAntButton;
	private ImageButton createFoodButton;
	
	private TextureAtlas atlas;
	
	public MenuBar()
	{
	}
	
	public void Init()
	{
		spriteBatch = Resources.instance.spriteBatch;
		shapeRenderer = Resources.instance.shapeRenderer;
		guiCamera = Resources.instance.guiCamera;

		atlas = Resources.instance.assetManager.get("ui/uiSprites.atlas", TextureAtlas.class);
		
		skin = new Skin(Gdx.files.internal("ui/ui_skin.json"), atlas);
		
		table = new Table(skin);
		//table.debug();
		
		table.setBounds
				(
					0, guiCamera.viewportHeight * 0.1f, 
					guiCamera.viewportWidth * 0.05f, guiCamera.viewportHeight * 0.8f
				);

		stage = new Stage(Resources.instance.guiViewport, spriteBatch);
		stage.addActor(table);
		
		createAntButton = new ImageButton(skin, "createAntButton");
		
		table.add(createAntButton);
		
		
	}
	
	public void Draw()
	{
		/*
		shapeRenderer.setProjectionMatrix(guiCamera.combined);
		shapeRenderer.setColor(Color.BLACK);
		
		shapeRenderer.begin(ShapeType.Line);
		{
			table.drawDebug(shapeRenderer);
		}
		shapeRenderer.end();
		*/
		
		stage.act();
		stage.draw();
	}
	
	public void Dispose()
	{
		if(stage != null)
			stage.dispose();
		
		if(skin != null)
			skin.dispose();
	}
}











