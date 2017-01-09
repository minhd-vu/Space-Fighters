package com.minhvu.main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Star
{
	private Point location;
	private final int speed = 20;

	private static ImageIcon imageicon = new ImageIcon("res/star.png");
	private static Image image = imageicon.getImage();
	
	public Star()
	{
		location = new Point((int) (Math.random() * (Game.getInstance().getWidth() - image.getWidth(Game.getInstance()))), (int) (Math.random() * (Game.getInstance().getHeight() - image.getHeight(Game.getInstance()))));
	
		for (Star star : Game.getInstance().getStars())
		{
			while (getBounds().intersects(star.getBounds()))
			{
				location = new Point((int) (Math.random() * (Game.getInstance().getWidth() - image.getWidth(Game.getInstance()))), (int) (Math.random() * (Game.getInstance().getHeight() - image.getHeight(Game.getInstance()))));
			}
		}
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image, location.x, location.y, Game.getInstance());
	}
	
	public void move()
	{
		location.y += speed;
		
		if (location.y > Game.getInstance().getHeight())
		{
			location.x = (int) (Math.random() * (Game.getInstance().getWidth() - image.getWidth(Game.getInstance())));
			location.y = (int) (Math.random() * -1000) - image.getWidth(Game.getInstance());
		}
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(location.x, location.y, image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
	}
}
