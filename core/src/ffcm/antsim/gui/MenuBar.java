
package ffcm.antsim.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ffcm.antsim.AntSim;
import ffcm.antsim.AntWorld;
import ffcm.antsim.resource.Resources;
import ffcm.antsim.screen.SimulationScreen;

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

    private AntWorld world;
	
	public MenuBar()
	{
	}
	
	public void Init()
	{
		spriteBatch = Resources.instance.spriteBatch;
		shapeRenderer = Resources.instance.shapeRenderer;
		guiCamera = Resources.instance.guiCamera;

		world = ((SimulationScreen) AntSim.instance.getScreen()).world;

		atlas = Resources.instance.assetManager.get("ui/uiSprites.atlas", TextureAtlas.class);
		
		skin = new Skin(Gdx.files.internal("ui/ui_skin.json"), atlas);
		
		table = new Table(skin);
		//table.debug();

		table.setPosition(AntSim.V_WIDTH * 0.03f, AntSim.V_HEIGHT * 0.5f);

		stage = new Stage(Resources.instance.guiViewport, spriteBatch);
		stage.addActor(table);

		createAntButton = new ImageButton(skin, "createAntButton");

		table.add(createAntButton).size(AntSim.V_HEIGHT * 0.1f);//.row();

		createAntButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                world.SpawnAntsFromNest(1);
            }
        });
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











