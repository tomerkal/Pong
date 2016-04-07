package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Render extends JPanel{
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Imperial", Font.BOLD, 20));
		
		g.setColor(new Color(2039572));
		g.fillRect(0, 0, Pong.WID, Pong.HIGH);
		
		g.setColor(new Color(10040166));
		g.fillRect(Pong.ball.x, Pong.ball.y, Pong.SCALE, Pong.SCALE);
		
		g.setColor(new Color(10066278));
		g.drawLine(Pong.WID/2, 0, Pong.WID/2, Pong.HIGH-1);
		g.drawArc(Pong.WID/2-60, Pong.HIGH/2-60, 120, 120, 0, 360);
		
		for (int i=0 ; i<Pong.PONG_SIZE ; i++){
			g.fillRect(Pong.pong_left.get(i).x, Pong.pong_left.get(i).y, Pong.SCALE, Pong.SCALE);
			g.fillRect(Pong.pong_right.get(i).x, Pong.pong_right.get(i).y, Pong.SCALE, Pong.SCALE);
		}
		
		g.setColor(new Color(26367));
		g.drawString("Goals: "+Pong.goals_left, Pong.WID/4, 30);
		g.drawString("Goals: "+Pong.goals_right, 3*Pong.WID/4, 30);
		if (Pong.paused)
			g.drawString("Press Space to start", Pong.WID/2-100, Pong.HIGH/2);
	}
}
