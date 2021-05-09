package attilathehun.videoconverter;

import javax.sound.sampled.*;
import java.net.URL;

public class AudioRickrollPlayer extends Thread{

    private boolean isPlaying;

    @Override
    public void run() {
        this.startPlaying();
        while(this.isPlaying) {
            this.play();
        }
    }

    private void play(){
        try {
            String filename = "/lib/WAVConversionLibrary";
            URL url = AudioRickrollPlayer.class.getResource(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength()); // Duration should match length of audio file.
            clip.addLineListener(event -> {
                if (LineEvent.Type.STOP.equals(event.getType())) {
                    clip.close();
                }
            });
        /*
        * @catch UnsupportedAudioFileTypeException
        * @catch IOException
        * @catch LineUnavailableException
        * @catch InterruptedException
        */
        }catch(Exception e){
            //dead, idk what to do, this should never happen
            e.printStackTrace(System.out);
            this.stopPlaying();
        }
    }
    public void startPlaying(){
        this.isPlaying = true;
    }

    public void stopPlaying(){
        this.isPlaying = false;
    }
}
