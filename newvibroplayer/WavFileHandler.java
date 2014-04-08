/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author imdc
 */
public class WavFileHandler {
    
    /* header information */
        private String chunkID;
        private int chunkSize;
        private String format;
        private String subchunk1ID;
        private int subchunk1Size;
        private int audioFormat;
        private int numChannels;
        private int sampleRate;
        private int byteRate;
        private int blockAlign;
        private int bitsPerSample;
        private ArrayList<Byte> options;
        private String subChunk2ID;
        private int subchunk2Size;
        
        int aux;
    /* bytes of the header (header size) */
    private int headerSize;
    
    private int frameSize;
    
    /* data */
    private ArrayList<byte[]> samples;
        
    private File file;
    //private AudioFormat audioFormat;
    
    WavFileHandler ( File file ) throws IOException, UnsupportedAudioFileException {
        this.file = file;
        
        this.chunkID = "";
        this.chunkSize = 0;
        this.format = "";
        this.subchunk1ID = "";
        this.subchunk1Size = 0;
        this.audioFormat = 0;
        this.numChannels = 0;
        this.sampleRate = 0;
        this.byteRate = 0;
        this.blockAlign = 0;
        this.bitsPerSample = 0;
        this.subChunk2ID = "";
        this.options = new ArrayList<>();
        this.subchunk2Size = 0;
        this.headerSize = 0;
        this.frameSize = 0;
        
        this.samples = new ArrayList<>();
        
        if ( !getAudioFormat() ) {
            /* Is it possible to get where is the error?! */
            throw new IOException ( "File format is invalid." );
        }
    }
    
    public boolean getAudioFormat() throws IOException {
        
        byte[] shortSample = new byte[2];
        byte[] bigSample = new byte[4];
        FileInputStream inputStream = new FileInputStream ( this.file );
        
        /*
         * To more information about the WAVE file, please check:
         * https://ccrma.stanford.edu/courses/422/projects/WaveFormat/
         */
        
        /* read ChunkID (big endian, from byte 0 to 4) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                chunkID += (char) bigSample[i];
                headerSize++;
            }

            /* check if the ChunkID field is correct */
            if ( !chunkID.equals ( "RIFF" ) ) {
                /* Is it better to create our own exception? */
                throw new IOException ( "File Header modified.\nPlease, check the \"ChunkID\" field of the header and try to submit again." );
            }
        
        /* read ChunkSize (little endian, from byte 4 to 8 */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                headerSize++;
            }
            chunkSize = ByteBuffer.wrap ( bigSample ).order ( ByteOrder.LITTLE_ENDIAN ).getInt();
        
        /* read Format (big endian, from byte 8 to 12) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                format += (char) bigSample[i];
                headerSize++;
            }
            
            /* check if the Format field is correct */
            if ( !format.equals ( "WAVE" ) ) {
                /* Is it better to create our own exception? */
                throw new IOException ( "File Header wrong.\nPlease, check the \"Format\" field of the header and try to submit again." );
            }
           
        /* read Subchunk1ID (big endian, from byte 12 to 16) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                subchunk1ID += (char) bigSample[i];
                headerSize++;
            }
            
            /* check if the Subchunk1ID field is correct */
            if ( !subchunk1ID.equals ( "fmt " ) ) {
                /* Is it better to create our own exception? */
                throw new IOException ( "File Header wrong.\nPlease, check the \"Subchunk1ID\" field of the header and try to submit again." );
            }
        
        /* read Subchunk1Size (little endian, from byte 16 to 20) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                headerSize++;
            }
            subchunk1Size = ByteBuffer.wrap ( bigSample ).order ( ByteOrder.LITTLE_ENDIAN ).getInt();
            aux = 0;
        
        /* read AudioFormat (little endian, from byte 20 to 22 ) */
            for ( int i = 0; i < 2; i++ ) {
                shortSample[i] = (byte) inputStream.read();
                aux++;
            }
            audioFormat = ByteBuffer.wrap( shortSample ).order ( ByteOrder.LITTLE_ENDIAN ).getShort();
        
        /* read NumChannels (little endian, from byte 22 to 24) */
            for ( int i = 0; i < 2; i++ ) {
                shortSample[i] = (byte) inputStream.read();
                aux++;
            }
            numChannels = ByteBuffer.wrap( shortSample ).order ( ByteOrder.LITTLE_ENDIAN ).getShort();
            
        /* read SampleRate (little endian, from byte 24 to 28) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                aux++;
            }
            sampleRate = ByteBuffer.wrap( bigSample ).order ( ByteOrder.LITTLE_ENDIAN ).getInt();
            
        /* read ByteRate (little endian, from byte 28 to 32) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                aux++;
            }
            byteRate = ByteBuffer.wrap( bigSample ).order ( ByteOrder.LITTLE_ENDIAN ).getInt();
            
        /* read BlockAlign (little endian, from byte 32 to 34) */
            for ( int i = 0; i < 2; i++ ) {
                shortSample[i] = (byte) inputStream.read();
                aux++;
            }
            blockAlign = ByteBuffer.wrap( shortSample ).order ( ByteOrder.LITTLE_ENDIAN ).getShort();
            
        /* read BitsPerSample (little endian, from byte 34 to 36) */
            for ( int i = 0; i < 2; i++ ) {
                shortSample[i] = (byte) inputStream.read();
                aux++;
            }
            bitsPerSample = ByteBuffer.wrap( shortSample ).order ( ByteOrder.LITTLE_ENDIAN ).getShort();
            
            /* check if the information read is valid. The field "ByteRate" has to be equal to SampleRate * NumChannels * BitsPerSample / 8 */
            int verifier = sampleRate * numChannels * bitsPerSample / 8;
            if ( verifier != byteRate ) {
                throw new IOException ( "File Header wrong.\nPlease, check the \"SampleRate\", \"NumChannel\", \"BitsPerSample\" and/or \"ByteRate\" field of the header and try to submit again." );
            }
            
        /* the next bytes are specifc options of the file. It doesn't matter in our case, so I */
        for ( ; aux < subchunk1Size; aux++ ) {
            options.add( (byte) inputStream.read() );
        }
        
        headerSize += aux;
            
        /* set frame size */
            this.frameSize = numChannels * bitsPerSample / 8;
        
        /* read Subchunk2ID (big endian, from byte 36 to 40) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                subChunk2ID += (char) bigSample[i];
                headerSize++;
            }
            
            /* check if the Subchunk1ID field is correct */
            if ( !subChunk2ID.equals ( "data" ) ) {
                /* Is it better to create our own exception? */
                throw new IOException ( "File Header wrong.\nPlease, check the \"Subchunk1ID\" field of the header and try to submit again." );
            }
            
        /* read Subchunk2Size (little endian, from byte 40 to 44) */
            for ( int i = 0; i < 4; i++ ) {
                bigSample[i] = (byte) inputStream.read();
                headerSize++;
            }
            subchunk2Size = ByteBuffer.wrap ( bigSample ).order ( ByteOrder.LITTLE_ENDIAN ).getInt();
            
        return true;
    }
    
    public int getFrameSize() {
        return this.frameSize;
    }
    
    public ArrayList<byte[]> getSamples() {
        return this.samples;
    }
    
    public void read() {
        try {
            //byte[] sample = new byte[audioFormat.getFrameSize()];
            int frameSize = this.getFrameSize();
            byte[] sample = new byte[frameSize];
            
            FileInputStream inputStream = new FileInputStream ( this.file );
            
            /* Throw away header bytes */
                for ( int i = 0; i < headerSize; i++ ) {
                    sample[0] = (byte) inputStream.read();
                }
            
            int dataBytes = headerSize;
            
            /* get all the samples of the file */
            for ( ; dataBytes < chunkSize; dataBytes++ ) {
                /* group the samples in frames */
                for ( int i = 0; i < frameSize; i++, dataBytes++ ) {
                    sample[i] = (byte) inputStream.read();;
                }
                
                samples.add ( sample );
                
                /*System.out.println ( Math.round ( dataBytes * 100 / chunkSize ) );
                
                if ( Math.round ( dataBytes * 100 / chunkSize ) == 99 ) {
                    System.out.println ( Math.round ( dataBytes * 100 / chunkSize ) );
                }*/
            }
        }
        catch ( IOException ex ) {
            System.out.println ( ex );
        }
    }
}
