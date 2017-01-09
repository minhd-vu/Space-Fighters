package com.minhvu.main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy
{
	private Point location;
	private int speed;

	private static ImageIcon imageicon = new ImageIcon("res/enemies.png");
	private static Image image = imageicon.getImage();
	
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
}
