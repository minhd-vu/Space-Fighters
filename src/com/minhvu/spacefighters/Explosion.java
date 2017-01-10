package com.minhvu.spacefighters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Explosion
{
	private Point location;

	private static BufferedImage[] explosion = 
	{
		Sprite.getSprite(0, 0, 128),
		Sprite.getSprite(2, 0, 128),
		Sprite.getSprite(4, 0, 128),
		Sprite.getSprite(6, 0, 128),
		Sprite.getSprite(0, 1, 128),
		Sprite.getSprite(2, 1, 128),
		Sprite.getSprite(4, 1, 128),
		Sprite.getSprite(6, 1, 128),
		Sprite.getSprite(0, 2, 128),
		Sprite.getSprite(2, 2, 128),
		Sprite.getSprite(4, 2, 128),
		Sprite.getSprite(6, 2, 128),
		Sprite.getSprite(0, 3, 128),
		Sprite.getSprite(2, 3, 128),
		Sprite.getSprite(4, 3, 128),
		Sprite.getSprite(6, 3, 128),
		Sprite.getSprite(0, 4, 128),
		Sprite.getSprite(2, 4, 128),
		Sprite.getSprite(4, 4, 128),
		Sprite.getSprite(6, 4, 128),
		Sprite.getSprite(0, 5, 128),
		Sprite.getSprite(2, 5, 128),
		Sprite.getSprite(4, 5, 128),
		Sprite.getSprite(6, 5, 128)
	};
	private Animation explode = new Animation(explosion, 1);
	
	public Explosion(Point location)
	{
		this.location = location;
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(explode.getSprite(), location.x, location.y, Game.getInstance());
	}
	
	public Animation getAnimation()
	{
		return explode;
	}
}
