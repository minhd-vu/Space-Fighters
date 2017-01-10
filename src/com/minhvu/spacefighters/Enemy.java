package com.minhvu.spacefighters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy
{
	private Point location;
	private int speed;
	
	private static BufferedImage image = Sprite.getSprite(1024, 134, 112, 104);
	
	public Enemy()
	{
		respawn();
		
		for (Enemy enemy : Game.getInstance().getEnemies())
		{
			while (getBounds().intersects(enemy.getBounds()))
			{
				respawn();
			}
		}
		
		speed = (int) ((Math.random() * 15) + 5);
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image, location.x, location.y, Game.getInstance());
	}
	
	public void move()
	{
		location.y += speed;
		
		if (getBounds().intersects(Game.getInstance().getPlayer().getBounds()))
		{
			Game.getInstance().end();
		}
		
		if (location.y > Game.getInstance().getHeight())
		{
			respawn();
		}
	}
	
	public void respawn()
	{
		location = new Point((int) (Math.random() * (Game.getInstance().getWidth() - image.getWidth(Game.getInstance()))), (int) (Math.random() * -1000) - image.getWidth(Game.getInstance()));
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(location.x, location.y, image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
	}
	
	public Point getLocation()
	{
		return location;
	}
}
