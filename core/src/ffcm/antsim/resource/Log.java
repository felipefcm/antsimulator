
package ffcm.antsim.resource;

import com.badlogic.gdx.Gdx;

public class Log
{
	public static void Info(String msg)
	{
		Gdx.app.log("AntSim", msg);
	}
	
	public static void Debug(String msg)
	{
		Gdx.app.debug("AntSim", msg);
	}
	
	public static void Error(String msg)
	{
		Gdx.app.error("AntSim", msg);
	}
}
