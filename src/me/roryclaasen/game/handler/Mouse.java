package me.roryclaasen.game.handler;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	public static final int BTN_RELEASED = MouseEvent.NOBUTTON;
	public static final int BTN_LEFT = MouseEvent.BUTTON1;
	public static final int BTN_WHEEL = MouseEvent.BUTTON2;
	public static final int BTN_RIGHT = MouseEvent.BUTTON3;

	private int x, y;
	private int button;

	protected Mouse(Canvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// update(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		update(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		update(e);
		button = BTN_RELEASED;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		update(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		update(e);
	}

	private void update(MouseEvent e) {
		button = e.getButton();
		x = e.getX();
		y = e.getY();
	}

	public Point getPos() {
		return new Point(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getButton() {
		return button;
	}

}
