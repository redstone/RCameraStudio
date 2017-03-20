package net.redstoneore.rcamerastudio.config;

import java.nio.charset.Charset;
import java.nio.file.Path;

import net.redstoneore.rson.Rson;

public class ConfigBuilder extends Rson<Config> {

	public static transient Path configPath = null;
	
	private static transient Config instance = null;
	
	public static Config get() {
		if (instance == null)  {
			try {
				instance = ConfigBuilder.build()
					.setup(configPath, Charset.defaultCharset())
					.load()
					.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	protected static Config build() {
		return new Config();
	};
	
}
