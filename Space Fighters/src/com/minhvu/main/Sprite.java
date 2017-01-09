package com.minhvu.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite
{
	private static BufferedImage spriteSheet;
	private static final int TILE_SIZE = 32;

	public static BufferedImage loadSprite(String file)
	{
		BufferedImage sprite = null;

		try
		{
			sprite = ImageIO.read(new File("res/" + file + ".png"));
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return sprite;
	}

	public static BufferedImage getSprite(int x, int y)
	{
		if (spriteSheet == null)
		{
			spriteSheet = loadSprite("AnimationSpriteSheet");
		}

		return spriteSheet.getSubimage(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
}