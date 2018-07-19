/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Initializing starting point for X and Y for the bricks*/
	private double startingX = 0;
	private double startingY = 0;
	
/** Initializing GRect for paddle and  */
	private GRect paddle;
	private double lastX;
	
/** Animation cycle delay */
	private static final int DELAY = 6;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setupBricks();
		drawPaddle();
		bouncingBall();
		while (bouncingBall != null) {
			moveBouncingBall();
			pause(DELAY);
		}
	}
	
	private void setupBricks() {
		
		/** Setting the x and y-coordinate to center the bricks in the middle of display */
		startingX = APPLICATION_WIDTH / 2 - (BRICK_WIDTH * NBRICKS_PER_ROW) / 2;
		startingY = BRICK_Y_OFFSET;
		
		for(int row = 0; row < NBRICK_ROWS; row++) {
			
			for(int col = 0; col < NBRICKS_PER_ROW; col++) {
				
				GRect brick = new GRect(startingX + (col * BRICK_WIDTH) + (col * BRICK_SEP), startingY + (row * BRICK_HEIGHT) + (row * BRICK_SEP), BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				
				/** Setting color of the bricks based on position of the row */
				if(row <= 1) {
					brick.setFillColor(Color.red);
				} else if(row <= 3) {
					brick.setFillColor(Color.orange);
				} else if(row <= 5) {
					brick.setFillColor(Color.yellow);
				} else if(row <= 7) {
					brick.setFillColor(Color.green);
				} else if(row <= 9) {
					brick.setFillColor(Color.cyan);
				}
				add(brick);
			}
		}
	}
	
	public void drawPaddle() {
		
		/** Setting the Paddle in the middle of the display*/
		double paddleX = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2;
		double paddleY = APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		
		paddle = new GRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setFillColor(Color.black);
		add(paddle);
		addMouseListeners();
	}
	
	/** Makes paddle tracking the mouse */
	public void mouseMoved(MouseEvent e) {
		lastX = e.getX(); 
		
		/** prevents the paddle to go off screen */
		
		if ((e.getX() < APPLICATION_WIDTH - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2) ) {
			paddle.setLocation(lastX - PADDLE_WIDTH / 2, APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	/** Creating the ball and making it bouncing off the walls */
	public void bouncingBall() {
		
		/** Getting the diameter of the ball */
		int ballHeight = BALL_RADIUS * 2;
		int ballWidth = BALL_RADIUS * 2;
		
		/** Setting the x and y coordinates of ball to the middle of the display */
		double ballX = (APPLICATION_WIDTH - ballWidth) / 2;
		double ballY = (APPLICATION_HEIGHT - ballHeight) / 2;
		
		bouncingBall = new GOval(ballX, ballY, ballWidth, ballHeight);
		bouncingBall.setFilled(true);
		add(bouncingBall);
		
	}
	
	/** Lets the bouncing ball move inside the display */
	private void moveBouncingBall() {
		
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		
		vy = 3.0;
				
		if(ballDown) {
			bouncingBall.move(vx, vy);
			
			if (bouncingBall.getY() >= APPLICATION_HEIGHT - BALL_RADIUS * 2) {
				ballDown = false;
			}
		} 
	}
	
	/** Velocity of the ball */
	private double vx, vy;
	
	/** Random number generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private GOval bouncingBall;
	private boolean ballUp;
	private boolean ballDown = true;
	private boolean ballLeft;
	private boolean ballRight;
}
