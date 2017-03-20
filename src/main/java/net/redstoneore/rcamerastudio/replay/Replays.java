package net.redstoneore.rcamerastudio.replay;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.redstoneore.rcamerastudio.config.Config;

public class Replays {

	// -------------------------------------------------- //
	// FIELDS
	// -------------------------------------------------- //
	
	private static Map<String, Replay> replays = null;
	private static Path dir = Paths.get(Config.configPath.getParent().toString(), "replays");

	// -------------------------------------------------- //
	// METHODS
	// -------------------------------------------------- //
	
	public static Optional<Replay> get(String name) {
		load();
		if (!replays.containsKey(name)) return Optional.empty();
		return Optional.of(replays.get(name));
	}
	
	public static Replay create(String name) {
		try {
			Files.createDirectories(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Replay replay = new Replay();
		replay.setup(Paths.get(dir.toString(), name + ".json"), Charset.defaultCharset());

		replay.name = name;
		replays.put(name, replay);
		return replay;
	}
	
	public static Collection<Replay> all() {
		load();
		return replays.values();
	}
	
	public static void load() {
		load(false);
	}
	
	public static void load(boolean force) {
		if (replays != null && !force) return;
		
		replays = new HashMap<String, Replay>();
		
		
		if (!Files.isDirectory(dir)) return;
		
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
			for (Path path : directoryStream) {
				
				String fileName = path.getFileName().toString();
				if (!fileName.endsWith(".json")) continue;
				
				// this is kind of lazy
				String name = fileName.replace(".json", "");
				
				Replay replay = new Replay();
				replay.setup(path, Charset.defaultCharset());
				replay.load();
				replays.put(name, replay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void save() {
		for (Replay replay : replays.values()) {
			try {
				replay.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
