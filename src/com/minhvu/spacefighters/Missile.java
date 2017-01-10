package com.minhvu.spacefighters;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Missile
{
	private static BufferedImage image = Sprite.getSprite(1024, 238, 32, 64);
	
	private Point location;
	private int speed;
	
	private boolean exploded;
	
	public Missile(Player player)
	{
		location = new Point(player.getLocation().x + (player.getDimensions().width - image.getWidth(Game.getInstance())) / 2, player.getLocation().y - 40);
		
		speed = 15;
		
		exploded = false;
	}
	
	public void move()
	{
		location.y -= speed;
		
		if (location.y + image.getHeight(Game.getInstance()) < 0)
		{
			exploded = true;
		}
		
		else if (!exploded)
		{
			for (Enemy enemy : Game.getInstance().getEnemies())
			{
				if (getBounds().intersects(enemy.getBounds()))
				{
					Explosion explosion = new Explosion(new Point(
							(int) (enemy.getLocation().x - (128 - enemy.getBounds().getWidth()) / 2),
							(int) (enemy.getLocation().y - (128 - enemy.getBounds().getHeight()) / 2)));
					
					explosion.getAnimation().start();
					Game.getInstance().getExplosions().add(explosion);

					enemy.respawn();
					
					Sound.HIT.stop();
					Sound.HIT.play();
					
					Game.getInstance().getScore().increment();
					
					exploded = true;
					
					break;
				}
			}
		}
	}
	
	public void paint(Graphics2D g2d)
	{
		g2d.drawImage(image, location.x, location.y, Game.getInstance());
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(location.x, location.y, image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
	}
	
	public boolean hasExploded()
	{
		return exploded;
	}
	
	public Point getLocation()
	{
		return location;
	}
}
