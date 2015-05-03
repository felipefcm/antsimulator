package ffcm.antsim.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	
	public BitmapFont font;
	
	private TextureAtlas mainSpritesAtlas;
	
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
		font.setColor(Color.WHITE);

		MathUtils.random.setSeed(new Date().getTime());

        mainSpritesAtlas = new TextureAtlas(Gdx.files.internal("gfx/mainSprites.atlas"));
    }

    public TextureRegion GetMainSpritesRegion(String name)
	{
		return mainSpritesAtlas.findRegion(name);
	}

	public void Dispose()
    {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        mainSpritesAtlas.dispose();
        font.dispose();
    }
}
