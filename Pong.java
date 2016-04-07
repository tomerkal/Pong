package pong;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener, KeyListener{
	
	public static final int WID=1000, HIGH = 600, LEFT = 0, RIGHT = 1, UP = 0, DOWN = 1, SCALE = 10, PONG_SIZE=80;
	
	private static int vertical;
	private static int direction;
	private static Render render = new Render();
	public static Point ball;
	public static ArrayList<Point> pong_left = new ArrayList<Point>();
	public static ArrayList<Point> pong_right = new ArrayList<Point>();
	public static int goals_left = 0;
	public static int goals_right = 0;
	public static boolean paused = true;
	private static boolean over;
	private static int angle;
	private static Random random = new Random();
	private Timer timer = new Timer(50, this);
	private JFrame jframe = new JFrame("Pong");
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Pong(){
	
		jframe.setVisible(true);
		jframe.setSize(WID, HIGH);
		jframe.setLocation(dim.width/2-WID/2, dim.height/2-HIGH/2);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(render);
		jframe.addKeyListener(this);

	}
	
	private void startGame(){
		timer.start();
		ball = new Point(WID/2-SCALE/2, HIGH/2-SCALE/2);
		fillPong(pong_left, LEFT);
		fillPong(pong_right, RIGHT);
		goals_right = 0;
		goals_left = 0;
		paused = true;
		over = false;
		angle = random.nextInt(4);
		direction = RIGHT;
		vertical = UP;
		
	}
	
	private static void newRound(){
		ball = new Point(WID/2-SCALE/2, HIGH/2-SCALE/2);
		fillPong(pong_left, LEFT);
		fillPong(pong_right, RIGHT);
		paused = true;
		angle = 1;
		direction = 1-direction;
		vertical = 1-vertical;
		angle = random.nextInt(4);
	}
	
	private static void fillPong(ArrayList<Point> pong, int side) {
		pong.clear();
		for (int i=0 ; i < PONG_SIZE ; i++){
			if (side == LEFT)
				pong.add(new Point(5, HIGH/2-PONG_SIZE/2+i));
			if (side == RIGHT)
				pong.add(new Point(WID-30, HIGH/2-PONG_SIZE/2+i));
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			movePong(pong_left, UP);
		if (key == KeyEvent.VK_S)
			movePong(pong_left, DOWN);
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP)
			movePong(pong_right, UP);
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN)
			movePong(pong_right, DOWN);
		if (key == KeyEvent.VK_SPACE)
			paused = !paused;
		
	}

	private static void movePong(ArrayList<Point> pong, int direction) {
		
		for (int i=0 ; i<15 ; i++){
			if (direction == DOWN){
				if (pong.get(pong.size()-1).y+1<HIGH-50){
					pong.add(new Point(pong.get(0).x, pong.get(PONG_SIZE-1).y+1));
					pong.remove(0);}
			}
			if (direction == UP){
				if (pong.get(0).y-1>0){
					pong.add(0, new Point(pong.get(0).x, pong.get(0).y-1));
					pong.remove(pong.size()-1);}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		render.repaint();
		if (!over && !paused){
			if (direction == RIGHT){
				ball.x += 5;
				if (vertical == UP)
					ball.y -= 5*angle;
				if (vertical == DOWN)
					ball.y += 5*angle;
			}
			if (direction == LEFT){
				ball.x -= 5;
				if (vertical == UP)
					ball.y -= 5*angle;
				if (vertical == DOWN)
					ball.y += 5*angle;
			}
			if (ball.x == WID-30){
				goals_left++;
				newRound();
			}

			if (ball.x == 10){
				goals_right++;
				newRound();
			}
			
			if (ball.y-SCALE <= 0 || ball.y+SCALE >= HIGH-40){
				vertical = 1-vertical;
			}
			
			Point ball_right = new Point(ball.x+SCALE, ball.y);
			Point ball_left = new Point(ball.x-SCALE, ball.y);
			if (myContains(pong_right, ball_right) || myContains(pong_left, ball_left)){
				if (direction == RIGHT)
					changeSpeed(pong_right, ball_right);
		
				if (direction == LEFT)
					changeSpeed(pong_left, ball_left);
				direction = 1-direction;
			}
		}
	}
	
	private boolean myContains(ArrayList<Point> pong, Point ball) {
		return pong.contains(ball) || pong.contains(new Point(ball.x, ball.y-SCALE)) || pong.contains(new Point(ball.x, ball.y+SCALE));
	}

	public static void changeSpeed(ArrayList<Point> pong, Point ball){
		if (pong.indexOf(ball) <= 11){
			vertical = UP;
			angle = 3;
		}
		else if (pong.indexOf(ball) <= 22){
			vertical = UP;
			angle = 2;
		}
		else if (pong.indexOf(ball) <= 33){
			vertical = UP;
			angle = 1;
		}
		else if (pong.indexOf(ball) >= 68){
			vertical = DOWN;
			angle = 3;
		}
		else if (pong.indexOf(ball) >= 57){
			vertical = DOWN;
			angle = 2;
		}
		else if (pong.indexOf(ball) >= 46){
			vertical = DOWN;
			angle = 1;
		}
	}
	
	public static void main(String[] args){
		Pong game = new Pong();
		game.startGame();
	}
}
