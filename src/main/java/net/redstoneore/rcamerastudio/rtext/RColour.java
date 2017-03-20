package net.redstoneore.rcamerastudio.rtext;

public abstract class RColour {

	private static RColour impl;
	public static void impl(RColour newImpl) { impl = newImpl.build(); }
	public static RColour impl() { return impl; }
	
	protected abstract RColour build();
	
	public Object BLACK;// = new String(new char[] {'\u00A7', '0'});
	public Object DARK_GRAY;// = new String(new char[] {'\u00A7', '8'});
	public Object GRAY;// = new String(new char[] {'\u00A7', '7'});
	public Object WHITE;// = new String(new char[] {'\u00A7', 'f'});

	public Object DARK_RED;// = new String(new char[] {'\u00A7', '4'});
	public Object RED;// = new String(new char[] {'\u00A7', 'c'});

	public Object DARK_GREEN;// = new String(new char[] {'\u00A7', '2'});
	public Object GREEN;// = new String(new char[] {'\u00A7', 'a'});

	public Object BLUE;// = new String(new char[] {'\u00A7', '9'});
	public Object DARK_BLUE;// = new String(new char[] {'\u00A7', '1'});

	public Object AQUA;// = new String(new char[] {'\u00A7', 'b'});
	public Object DARK_AQUA;// = new String(new char[] {'\u00A7', '3'});

	public Object DARK_PURPLE;// = new String(new char[] {'\u00A7', '5'});
	public Object LIGHT_PURPLE;// = new String(new char[] {'\u00A7', 'd'});
	
	public Object GOLD;// = new String(new char[] {'\u00A7', '6'});
	public Object YELLOW;// = new String(new char[] {'\u00A7', 'e'});
	
}
