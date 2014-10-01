
package ffcm.antsim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ffcm.ecs.Entity;
import ffcm.ecs.comps.CDrawable;
import ffcm.ecs.comps.CPosition;

public class Ant extends Entity
{
	float timeAccum;
	
	CPosition position;
	CDrawable drawable;
	
	public static Ant CreateAnt()
	{
		Ant ant = new Ant();
		
		
	}
	
	public Ant()
	{
		timeAccum = 0;
	}
	
	public void Render(SpriteBatch batch)
	{
		//batch already began
		
		//sprite.draw(batch);
	}
}
