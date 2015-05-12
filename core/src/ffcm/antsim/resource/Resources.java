package ffcm.antsim.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Date;

import ffcm.antsim.AntSim;

public class Resources
{
	public static Resources instance = new Resources();

	public OrthographicCamera mainCamera;
	public OrthographicCamera guiCamera;

	public FitViewport viewport;
	public FitViewport guiViewport;

	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;

	public AssetManager assetManager;

	public BitmapFont font;
	
	public Resources()
	{
	}

	public void Init()
    {
        mainCamera = new OrthographicCamera();
		viewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, mainCamera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		guiCamera = new OrthographicCamera();
		guiViewport = new FitViewport(AntSim.V_WIDTH, AntSim.V_HEIGHT, guiCamera);
		guiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

		spriteBatch = new SpriteBatch(200);
		shapeRenderer = new ShapeRenderer(200);

		font = new BitmapFont();

		//load some assets and wait until completion
		assetManager = new AssetManager();

		MathUtils.random.setSeed(new Date().getTime());
    }

	public void Dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
