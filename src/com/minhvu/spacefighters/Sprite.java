package com.minhvu.spacefighters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite
{
	private static BufferedImage spritesheet;

	public static void loadSprite(String file)
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

		spritesheet = sprite;
	}

	public static BufferedImage getSprite(int x, int y, int width, int height)
	{
		return spritesheet.getSubimage(x, y, width, height);
	}
	
	public static BufferedImage getSprite(int x, int y, int size)
	{
		return spritesheet.getSubimage(x * size, y * size, size, size);
	}
}