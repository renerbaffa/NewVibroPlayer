/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.synthbot.jasiohost.AsioDriver;

/**
 *
 * @author imdc
 */
public class NewVibroPlayer {

    AsioDriver driver;
    AsioSoundHost listener;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            Interface mainScreen = new Interface();
            mainScreen.setVisible ( true );
            
            /*Server server = new Server ( 7400 );
            
            /* can we do that in a thread? 
            server.start();*/
            
            //File file = new File ( "C:\\Users\\imdc\\Desktop\\test.wav" );

            //WavFileHandler wavFile = new WavFileHandler ( file );
            
            //wavFile.read();
        }
        catch ( Exception ex ) {
            System.out.println ( ex );
        }
    }
    
}
