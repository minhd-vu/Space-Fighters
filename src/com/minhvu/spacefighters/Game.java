package com.minhvu.spacefighters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable
{
	// Entry Point.
	public static void main(String[] args)
	{
		new Game();
	}
	
	// Used For Accessing JPanel Method.
	private static Game instance;

	private boolean running = false;
	private Thread thread;
	
	// State Of The Game.
	public static enum STATE
	{
		MENU,
		GAME,
		HELP,
		END
	};
	
	private STATE state;
	
	// Menu For The Game.
	private Menu menu;
	
	// For The End Game.
	private End end;
	
	// Keeping Score.
	private Score score;
	
	// Objects Used In The Game.
	private Player player;
	private List<Star> stars = new ArrayList<Star>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<Missile> missiles = new ArrayList<Missile>();
	private List<Explosion> explosions = new ArrayList<Explosion>();
	
	// Used For Keeping Count Of Objects.
	private final int starcount = 50;
	private final int enemycount = 20;
	private final int firerate = 250;
	private long timer = System.currentTimeMillis();
	
	// Constructor.
	public Game()
	{
		instance = this;
		
		state = STATE.MENU;
		
		// Anonymous Use Of Keyboard Input.
		KeyListener keylistener = new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				if (state.equals(STATE.GAME))
				{
					player.keyPressed(e);
					
					if (e.getKeyCode() == KeyEvent.VK_SPACE)
					{
						if (System.currentTimeMillis() - timer > firerate)
						{
							missiles.add(new Missile(player));
							timer = System.currentTimeMillis();
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (state.equals(STATE.GAME))
				{
					player.keyReleased(e);
				}
			}
		};
		
		// Anonymouse Use Of Mouse Input.
		MouseListener mouselistener = new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				if (state.equals(STATE.MENU) || state.equals(STATE.HELP))
				{
					menu.mousePressed(e);
				}
				
				if (state.equals(STATE.END))
				{
					end.mousePressed(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
		};

		addKeyListener(keylistener);
		addMouseListener(mouselistener);
		setFocusable(true);

		// Load In The Sprite Sheet.
		Sprite.loadSprite("spritesheet");

		//Sound.BACKGROUND.loop();
		
		// Create The Frame.
		JFrame frame = new JFrame("Space Fighters");
		frame.add(this);
		frame.setSize(1920, 1080);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initialize Everyhing.
		menu = new Menu();
		end = new End();
		score = new Score();
		player = new Player();
		
		for (int i = 0; i < starcount; ++i)
		{
			stars.add(new Star());
		}
		
		for (int i = 0; i < enemycount; ++i)
		{
			enemies.add(new Enemy());
		}
		
		// Begins The Thread.
		start();
	}
	
	// Starts The Thread.
	private synchronized void start()
	{
		if (running)
		{
			return;
		}
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	// Stops The Thread.
	private synchronized void stop()
	{
		if (!running)
		{
			return;
		}
		
		running = false;
		
		try
		{
			thread.join();
		}
		
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		System.exit(1);
	}
	
	// The Heart Of The Game: The Game Loop.
	@Override
	public void run()
	{
		long lasttime = System.nanoTime();
		final double ticks = 60.0;
		double nanoseconds = 1000000000 / ticks;
		double delta = 0;
		
		while (running)
		{
			long time = System.nanoTime();
			delta += (time - lasttime) / nanoseconds;
			lasttime = time;
			
			if (delta >= 1)
			{
				update();
				delta--;
			}
		}
		
		stop();
	}
	
	// Updates The Objects.
	private void update()
	{
		this.setBackground(Color.BLACK);
		
		if (state.equals(STATE.GAME))
		{
			// Movement Of All Objects.
			player.move();
			
			for (Enemy enemy : enemies)
			{
				enemy.move();
			}
			
			for (int i = 0; i < missiles.size(); ++i)
			{
				missiles.get(i).move();
				
				if (missiles.get(i).hasExploded())
				{
					missiles.remove(missiles.get(i));
				}
			}
			
			for (int i = 0; i < explosions.size(); ++i)
			{
				explosions.get(i).getAnimation().update();
				
				if (explosions.get(i).getAnimation().isStopped())
				{
					explosions.remove(explosions.get(i));
				}
			}
			
			for (Star star : stars)
			{
				star.move();
			}
		}
		
		repaint();
	}

	// Used For Painting/Rendering Images.
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		
		super.paint(g2d);
		
		if (state.equals(STATE.GAME) || state.equals(STATE.END))
		{
			for (Star star : stars)
			{
				star.paint(g2d);
			}
			
			player.paint(g2d);
			
			for (Enemy enemy : enemies)
			{
				enemy.paint(g2d);
			}

			for (Missile missile : missiles)
			{
				missile.paint(g2d);
			}
			
			for (Explosion explosion : explosions)
			{
				explosion.paint(g2d);
			}
			
			if (state.equals(STATE.END))
			{
				end.paint(g2d);
			}
			
			else
			{
				score.paint(g2d);
			}
		}
		
		else if (state.equals(STATE.MENU) || state.equals(STATE.HELP))
		{
			menu.paint(g2d);
		}
		
	}

	// Used When The Game Is Over.
	public void end()
	{
		//Sound.BACKGROUND.stop();
		Sound.GAMEOVER.play();
		//JOptionPane.showMessageDialog(this, "Final Score: " + score.getScore(), "Game Over", JOptionPane.YES_NO_OPTION);
		state = STATE.END;
		
		//System.exit(ABORT);
	}
	
	public void restart()
	{
		state = STATE.GAME;
		
		score = new Score();
		player = new Player();
		
		stars.removeAll(stars);
		enemies.removeAll(enemies);
		missiles.removeAll(missiles);
		explosions.removeAll(explosions);
		
		for (int i = 0; i < starcount; ++i)
		{
			stars.add(new Star());
		}
		
		for (int i = 0; i < enemycount; ++i)
		{
			enemies.add(new Enemy());
		}
	}

	// Getters For The Class Objects.
	
	public static Game getInstance()
	{
		return instance;
	}
	
	public STATE getState()
	{
		return state;
	}
	
	public Score getScore()
	{
		return score;
	}

	public void setState(STATE state)
	{
		this.state = state;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public List<Star> getStars()
	{
		return stars;
	}
	
	public List<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public List<Missile> getMissiles()
	{
		return missiles;
	}
	
	public List<Explosion> getExplosions()
	{
		return explosions;
	}
}
