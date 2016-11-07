package me.roryclaasen.game.logic;

public class TileAttributes {

	public static final TileAttributes DEFAULT;
	static {
		DEFAULT = new TileAttributes();
		DEFAULT.setSize(Tile.SIZE);
		DEFAULT.setxOffset(0);
		DEFAULT.setyOffset(0);
	}

	private int size, xOffset, yOffset;

	public int getSize() {
		return size;
	}

	public int getTileCurve() {
		return getSize() / 8;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public TileAttributes clone() {
		TileAttributes attribs = new TileAttributes();
		attribs.setSize(this.size);
		attribs.setxOffset(this.xOffset);
		attribs.setyOffset(this.yOffset);
		return attribs;
	}
}
