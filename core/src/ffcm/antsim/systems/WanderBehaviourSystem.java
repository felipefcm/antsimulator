
package ffcm.antsim.systems;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import ffcm.antsim.World;
import ffcm.antsim.comps.CVelocity;
import ffcm.antsim.comps.CWander;
import ffcm.antsim.nodes.WanderNode;
import ffcm.antsim.resource.ResourceManager;
import ffcm.ecs.INode;
import ffcm.ecs.ISystem;
import ffcm.ecs.NodeMap;

public class WanderBehaviourSystem implements ISystem
{	
	public static final float AvoidanceTolerance = 2.0f;
	
	private Random random;
	
	public WanderBehaviourSystem()
	{
	}
	
	@Override
	public void Start()
	{
		random = ResourceManager._instance.random;
	}

	@Override
	public void Update(final NodeMap nodeMap)
	{
		LinkedList<INode> nodeList = nodeMap.GetNodes(WanderNode.class);
		
		if(nodeList == null)
			return;
		
		Iterator<INode> it = nodeList.iterator();
		
		TiledMapTileLayer obstaclesLayer = null;
		
		while(it.hasNext())
		{
			WanderNode node = (WanderNode) it.next();
			
			if(obstaclesLayer == null)
				obstaclesLayer = (TiledMapTileLayer) node.terrain.map.getLayers().get("blockers");
			
			Vector2 steerForce = CalculateSteerForce(node);
			
			CVelocity velocity = node.velocity;
			boolean succeeded = true;
			
			if(node.wander.avoidObstacles)
			{	
				//predict next position
				int numTries = 0;
				Vector2 nextPos = null;
				succeeded = false;
				
				while(!succeeded)
				{					
					++numTries;
					
					steerForce = CalculateSteerForce(node);
					nextPos = node.transform.position.cpy().add(velocity.vector.cpy().add(steerForce)).scl(1.01f);
					
					int tileX = (int)(nextPos.x / ((float)obstaclesLayer.getTileWidth() * node.terrain.mapScale));
					int tileY = (int)(nextPos.y / ((float)obstaclesLayer.getTileHeight() * node.terrain.mapScale));
					
					Cell tile = obstaclesLayer.getCell(tileX, tileY);
					
					boolean outsideMap = 
							nextPos.x < AvoidanceTolerance || 
							nextPos.x >= World.WorldSize.x - AvoidanceTolerance || 
							nextPos.y < AvoidanceTolerance || 
							nextPos.y >= World.WorldSize.y - AvoidanceTolerance;
					
					boolean blockedByMap = true;
					
					if(tile == null)
						blockedByMap = false;
					else if(tile.getTile() == null)
						blockedByMap = false;
							
					succeeded = !outsideMap && !blockedByMap;
					
					if(numTries == 2)
						break;
				}
				
				if(numTries < 2)
					succeeded = true;
				else
					velocity.vector.scl(-1.5f + random.nextInt(16) / 10.0f);
			}
			
			if(succeeded)
				velocity.vector.add(steerForce).nor().scl(velocity.maxVelocity); //max velocity
		}
	}
	
	private Vector2 CalculateSteerForce(WanderNode node)
	{
		CWander wander = node.wander;
		CVelocity velocity = node.velocity;
		
		Vector2 circleCenter = velocity.vector.cpy();
		circleCenter.nor().scl(wander.circleDistance);
		
		Vector2 disp = new Vector2(0, 1.0f);
		disp.scl(wander.circleRadius);
		
		//wander.wanderAngle += random.nextFloat() * 15.0f * (random.nextBoolean() ? 1.0f : -1.0f);
		wander.wanderAngle += random.nextFloat() * 30.0f - 30.0f * 0.5f;
		wander.wanderAngle = wander.wanderAngle % 360;
		
		disp.setAngle(wander.wanderAngle);
		
		Vector2 wanderForce = circleCenter.cpy().add(disp);
		
		return wanderForce.sub(velocity.vector);
	}

	@Override
	public void End()
	{
	}

	@Override
	public int GetPriority()
	{
		return HighPriority;
	}
}
