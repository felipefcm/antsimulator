
package ffcm.antsim;

import com.badlogic.gdx.Gdx;

public class Log
{
	public static void Debug(String msg)
	{
		Gdx.app.debug("AntSim", msg);
	}
	
	public static void Error(String msg)
	{
		Gdx.app.error("AntSim", msg);
	}
}
