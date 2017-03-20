package net.redstoneore.rcamerastudio.rtext;

public abstract class RText {

	// TODO: add impl via reflection
	private static RText impl;
	public static void impl(RText newImpl) { impl = newImpl; }
	
	public static RText of(Object... str) {
		assert impl != null;
		return impl.startOf(str);
	}
		
	protected abstract RText startOf(Object... str);
	
	public abstract RText colour(Object colour);
	public abstract RText then(RText rtext);

}
