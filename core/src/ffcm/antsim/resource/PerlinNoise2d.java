
package ffcm.antsim.resource;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class PerlinNoise2d
{
	Vector2[][] gradientMatrix;
	int matrixSize;
	
	Random random;
	
	public PerlinNoise2d(long seed, int matrixSize)
	{
		this.matrixSize = matrixSize;
		random = new Random(seed);
		
		InitMatrix();
	}
	
	public PerlinNoise2d(Random random, int matrixSize)
	{
		this.matrixSize = matrixSize;
		this.random = random;
		
		InitMatrix();
	}
	
	private void InitMatrix()
	{
		gradientMatrix = new Vector2[matrixSize][matrixSize];
		
		for(int r = 0; r < matrixSize; ++r)
		{
			for(int c = 0; c < matrixSize; ++c)		
			{				
				gradientMatrix[r][c] = new Vector2
				(
					random.nextInt(), 
					random.nextInt()
				).nor();
			}
		}
	}
	
	private float LinearInterpolate(float x, float y, float a)
	{ 
		return ((1.0f - a) * x + a * y);
	}
	
	private float NonLinearInterpolate(float x, float y, float a)
	{	
		float value = 1.0f - (a*a * (3.0f - 2.0f * a));
		//float value = 1.0f - (6.0f*a*a*a*a*a - 15.0f*a*a*a*a + 10.0f*a*a*a);
		
		return (value * x + (1.0f - value) * y);
	}
	
	private float Interpolate(float x, float y, float a)
	{
		//return LinearInterpolate(x, y, a);
		return NonLinearInterpolate(x, y, a);
	}
	
	public float GetNoiseValue(final float x, final float y)
	{
		int xI = (int) x;
		int yI = (int) y;
		
		float xF = x - xI;
		float yF = y - yI;
		
		xI = xI % matrixSize;
		yI = yI % matrixSize;
		
		final Vector2 p = new Vector2(xI + xF, yI + yF);
		
		//displacement vectors
		Vector2 pTopLeft = new Vector2(xI, yI).sub(p);
		Vector2 pTopRight = new Vector2(xI + 1, yI).sub(p);
		Vector2 pBottomLeft = new Vector2(xI, yI + 1).sub(p);
		Vector2 pBottomRight = new Vector2(xI + 1, yI + 1).sub(p);
		
		final int wrappedX = (xI + 1) % matrixSize;
		final int wrappedY = (yI + 1) % matrixSize;
		
		//gradients
		final Vector2 gTopLeft = gradientMatrix[yI][xI];
		final Vector2 gTopRight = gradientMatrix[yI][wrappedX];
		final Vector2 gBottomLeft = gradientMatrix[wrappedY][xI];
		final Vector2 gBottomRight = gradientMatrix[wrappedY][wrappedX];
		
		//weights
		float valTopLeft = gTopLeft.dot(pTopLeft);
		float valTopRight = gTopRight.dot(pTopRight);
		float valBottomLeft = gBottomLeft.dot(pBottomLeft);
		float valBottomRight = gBottomRight.dot(pBottomRight);
		
		float xTop = Interpolate(valTopLeft, valTopRight, xF);
		float xBottom = Interpolate(valBottomLeft, valBottomRight, xF);
		
		return Interpolate(xTop, xBottom, yF);
	}
}
