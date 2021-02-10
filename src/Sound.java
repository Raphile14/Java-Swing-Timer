import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JTextField;

public class Sound {
		
		public void PlaySound(String soundName, Clip clip, JTextField inputField) {
			try {			
				InputStream path = getClass().getResourceAsStream(soundName);
				InputStream buffer = new BufferedInputStream(path);
				clip = AudioSystem.getClip();
				AudioInputStream ais = AudioSystem.getAudioInputStream(buffer);
				clip.open(ais);				
				clip.start();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}