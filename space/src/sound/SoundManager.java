package sound;

import java.util.HashMap;
import java.util.Map;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

public class SoundManager
{

	private static Map<String, Sound>	sounds;
	private static Map<String, Music>	music;
	private static boolean				sound			= true;
	private static Music				currentMusic	= null;
	private static float				GLOBAL_VOLUME	= 1;

	static
	{
		sounds = new HashMap<String, Sound>();
		music = new HashMap<String, Music>();
		TinySound.init();
	}

	public synchronized static void loadSound(String path, String name)
	{
		sounds.put(name, TinySound.loadSound(path, true));
		System.out.println("loaded: " + path + " to sounds with the name: " + name);
	}

	public synchronized static void loadMusic(String path, String name)
	{
		music.put(name, TinySound.loadMusic(path, true));
		System.out.println("loaded: " + path + " to sounds with the name: " + name);
	}

	public static void setGlobalVolume(float f)
	{
		TinySound.setGlobalVolume(f);
		GLOBAL_VOLUME = f;
	}

	public static float getGlobalVolume()
	{
		return GLOBAL_VOLUME;
	}

	public static void setSound(boolean c)
	{
		sound = c;
		if (!c)
			setGlobalVolume(0);
		else
			setGlobalVolume(1);
	}

	public static boolean getSound()
	{
		return sound;
	}

	public static void playSound(String name)
	{
		if (!sound)
			return;
		if (sounds.containsKey(name))
		{
			sounds.get(name).play();
		}
		else
		{
			System.out.println("" + name + " was not found in loaded sounds.");
		}
	}

	public static void playSound(String name, float vol)
	{
		if (!sound)
			return;
		if (sounds.containsKey(name))
		{
			sounds.get(name).play(vol);
		}
		else
		{
			System.out.println("" + name + " was not found in loaded sounds.");
		}
	}

	public static void loopMusic(String name)
	{
		if (!sound)
			return;
		if (music.containsKey(name))
		{
			if (!music.get(name).done())
				music.get(name).stop();
			music.get(name).play(true);
		}
		else
		{
			System.out.println("" + name + " was not found in loaded sounds.");
		}
	}

	public static void stopSound(String name)
	{
		if (!sound)
			return;
		if (sounds.containsKey(name))
		{
			sounds.get(name).stop();
		}
		else
		{
			System.out.println("" + name + " was not found in loaded sounds.");
		}
	}

	public static void stopMusic(String name)
	{
		if (!sound)
			return;
		if (music.containsKey(name))
		{
			music.get(name).stop();
		}
		else
		{
			System.out.println("" + name + " was not found in loaded sounds.");
		}
	}

	public static void playMusic(String name)
	{
		if (music.containsKey(name))
		{
			if (currentMusic != null)
				currentMusic.stop();
			currentMusic = music.get(name);
			currentMusic.play(true);
		}
		else
		{
			System.out.println("" + name + " was not found in loaded music.");
		}
	}

	public static boolean isMusicLoaded(String name)
	{
		return music.containsKey(name);
	}

	public static boolean isMusicPlaying(String name)
	{
		if (!sound)
			return true;
		if (music.containsKey(name))
		{
			return music.get(name).playing();
		}
		return false;
	}

	public static void stopAllMusic()
	{
		for (String key : music.keySet())
		{
			music.get(key).stop();
		}
	}

	public static void cleanUp()
	{
		TinySound.shutdown();
	}

}
