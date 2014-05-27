
import com.synthbot.jasiohost.AsioDriver;
import com.synthbot.jasiohost.AsioDriverState;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author imdc
 */
public class Server {
    
    AsioDriver driver;
    AsioSoundHost listener;
    
    private int port;
    public static String musicName;
    
    public Server ( int port ) {
        
        this.musicName = "";
        
        this.port = port;
        
        /* automaticly loads the firepod driver */
        this.driver = AsioDriver.getDriver ( "ASIO PreSonus FireStudio" );
        
    }
    
    public void start() throws SocketException, IOException {
        DatagramSocket serverSocket = new DatagramSocket ( 7400 );
        
        byte[] receivedData = new byte[1024];
        
        while ( true ) {
            DatagramPacket receivedPacket = new DatagramPacket ( receivedData, receivedData.length );
            serverSocket.receive ( receivedPacket );
            
            String sentence = new String ( receivedPacket.getData() );
            
            String[] message = sentence.split ( "" );
            musicName = message[0];
            
            switch ( musicName ) {
                case "stop":
                    System.out.println("Driver stopped.");
                    listener.restart();
                    driver.returnToState ( AsioDriverState.INITIALIZED );
                    break;

                case "stand":
                    System.out.println("Driver stopped.");
                    listener.restart();
                    driver.returnToState ( AsioDriverState.INITIALIZED );
                    break;

                default:
                    if ( driver.getCurrentState() != AsioDriverState.RUNNING ) {
                        listener = new AsioSoundHost ( driver );
                        driver.start();
                        System.out.println ( "Playing sound: " + musicName );
                    }
                    else {
                        listener.restart();
                        driver.returnToState ( AsioDriverState.INITIALIZED );
                        listener = new AsioSoundHost ( driver );
                        driver.start();
                        System.out.println ( "Playing sound: " + musicName );
                    }

                    break;
                }
            
            System.out.println ( sentence );
        }
        
    }
    
}
