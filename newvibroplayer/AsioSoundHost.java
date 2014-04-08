
import com.synthbot.jasiohost.AsioChannel;
import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioDriverListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the AsioSoundHost class. It implements AsioDriverListener. That is the
 * class responsible to play a sound to a specific channel and control the driver
 * at all.
 * 
 * @author Rener Baffa da Silva
 */
public class AsioSoundHost implements AsioDriverListener {
    
    /**
     * @param driver            Current driver.
     * @param buferSize         Buffer size.
     * @param sampleRate        Sample Rate.
     * @param activeChannels    List of channels.
     * @param output            Sound signal to be played.
     * @param channel           List of active channels.
     * @param startTime         Time that the driver was started.
     * 
     * @param aBuffer           Bytes read.
     * @param inputStream       File source from where the information is got.
     * @param frameSize         Frame size.
     */
    AsioDriver driver;
    private int bufferSize;
    private double sampleRate;
    private Set<AsioChannel> activeChannels;
    
    private float[] output;
    private float[][] out;
    
    private boolean[] channel;
    private long startTime;
    
    /* Can we throw it out when move the file load? */
        FileInputStream inputStream;
        
        int frameSize;
        
        int currentCounter = 0;
    
    /**
     * Class constructor. This is the constructor of the class that initializes
     * everything needed such as buffer size, list of active channels and output variable.
     * 
     * @param driver Receive the current driver to be used.
     */
    AsioSoundHost ( AsioDriver driver ) {
        if ( driver != null ) {
            /* Get and instanciate the driver and its listener */
                this.driver = driver;
                this.driver.addAsioDriverListener ( this );
            
            /* Get the the buffer preferred size and the sample rate from current driver */
                bufferSize = this.driver.getBufferPreferredSize();
                sampleRate = this.driver.getSampleRate();
          
            /* Initializing the variable that contains the sound information o be played */
                output = new float[bufferSize];
                out = new float[9][bufferSize];
            
            /* Add all the 7 channels of the chair to a list and then to the driver */
                activeChannels = new HashSet<>();
                for ( int i = 0; i < 8; i++ ) {
                    activeChannels.add ( this.driver.getChannelOutput ( i ) );
                }
                this.driver.createBuffers ( activeChannels );
            
            /* Create a list with all active channels */
                channel = new boolean[8];
                for ( int i = 0; i < channel.length; i++ ) {
                    channel[i] = false;
                }
        }
    }
    
    public void restart() {
        this.currentCounter = 0;
    }
    
    /**
     * This method add a new channel to the list of active channels.
     * 
     * @param channel This is the channel to be added into the active list.
     */
    public void addChannel ( int channel ) {
        this.channel[channel] = true;
    }
    
    /**
     * This method remove a channel to the list of active channels.
     * 
     * @param channel This is the channel to be removed of the active list.
     */
    public void removeChannel ( int channel ) {
        this.channel[channel] = false;
    }
    
    @Override
    public void bufferSwitch(long sampleTime, long samplePosition, Set<AsioChannel> activeChannels) {

        /* Get the elapsed time (difference between the current time and the start time and convert it to seconds. */
            long elapsedTime = ((sampleTime - startTime) / 1000000);
        
        /* Load the samples to play */
            for ( int j = 1; j < 9; j++ ) {
                /*Music m = Player.musics.get ( j );
                
                if ( m != null ) {
                    for ( int i = 0; i < bufferSize; i++, currentCounter++ ) {
                        out[j][i] = m.getFloat ( currentCounter );
                    }
                }*/
            }
            
            /*for( int i = 0; i < output.length; i++, currentCounter++) {
                output[i] = Player.musics.get ( 0 ).getFloat ( currentCounter );
                //System.out.println ( currentCounter );
            }*/

        /* Runs all the channel in the active list */
            for ( AsioChannel channelInfo : activeChannels ) {
                /* Check if the current channel is active */
                if ( channel[channelInfo.getChannelIndex()] ) {
                    /* play the information in the current channel */
                    channelInfo.write ( out[channelInfo.getChannelIndex() + 1] );
                }
            }
    }
    
    @Override
    public void sampleRateDidChange(double sampleRate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resyncRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bufferSizeChanged(int bufferSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void latenciesChanged(int inputLatency, int outputLatency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
