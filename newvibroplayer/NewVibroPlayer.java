/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;

/**
 *
 * @author imdc
 */
public class NewVibroPlayer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            //File file = new File ( "C:\\Users\\imdc\\Desktop\\sample_file.wav" );
            File file = new File ( "C:\\Users\\imdc\\Desktop\\test.wav" );

            WavFileHandler wavFile = new WavFileHandler ( file );
            
            wavFile.read();
        }
        catch ( Exception ex ) {
            System.out.println ( ex );
        }
    }
    
}
