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
        
        private ArrayList<Float>[] channels;
        
        private ArrayList<Integer> channel0;
        private ArrayList<Integer> channel1;
        private ArrayList<Integer> channel2;
        private ArrayList<Integer> channel3;
        private ArrayList<Integer> channel4;
        private ArrayList<Integer> channel5;
        private ArrayList<Integer> channel6;
        private ArrayList<Integer> channel7;
        
        private ArrayList<Byte> aux0;
        private ArrayList<Byte> aux1;
        private ArrayList<Byte> aux2;
        private ArrayList<Byte> aux3;
        private ArrayList<Byte> aux4;
        private ArrayList<Byte> aux5;
        private ArrayList<Byte> aux6;
        private ArrayList<Byte> aux7;
        
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
        
        this.channel0 = new ArrayList<>();
        this.channel1 = new ArrayList<>();
        this.channel2 = new ArrayList<>();
        this.channel3 = new ArrayList<>();
        this.channel4 = new ArrayList<>();
        this.channel5 = new ArrayList<>();
        this.channel6 = new ArrayList<>();
        this.channel7 = new ArrayList<>();
        
        this.aux0 = new ArrayList<>();
        this.aux1 = new ArrayList<>();
        this.aux2 = new ArrayList<>();
        this.aux3 = new ArrayList<>();
        this.aux4 = new ArrayList<>();
        this.aux5 = new ArrayList<>();
        this.aux6 = new ArrayList<>();
        this.aux7 = new ArrayList<>();
        
        if ( !getAudioFormat() ) {
            /* Is it possible to get where is the error?! */
            throw new IOException ( "File format is invalid." );
        }
        
        this.channels = (ArrayList<Float>[]) new ArrayList[this.numChannels];
        
        for ( int i = 0; i < this.channels.length; i++ ) {
            this.channels[i] = new ArrayList<>();
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
    
    public int getNumChannels() {
        return this.numChannels;
    }
    
    public ArrayList<byte[]> getSamples() {
        return this.samples;
    }
    
    public void read() {
        try {
            //byte[] sample = new byte[audioFormat.getFrameSize()];
            int frameSize = this.getFrameSize();
            
            //System.out.println ( "AAAAAAAA| " + Interface.progressBar.getValue() );
            
            FileInputStream inputStream = new FileInputStream ( this.file );
            
            /* Throw away header bytes */
                byte[] sample = new byte[frameSize];
                for ( int i = 0; i < headerSize; i++ ) {
                    sample[0] = (byte) inputStream.read();
                }
            
            int dataBytes = headerSize;
            
            int channelBytesPerSample = this.frameSize / this.numChannels;
            
            /* get all the samples of the file */
            for ( ; dataBytes < chunkSize; dataBytes++ ) {
                /* group the samples in frames */
                byte[] sample2 = new byte[frameSize];
                for ( int i = 0; i < frameSize; i++, dataBytes++ ) {
                    sample2[i] = (byte) inputStream.read();
                }
                
                byte[][] channel = new byte[this.numChannels][channelBytesPerSample];
                
                for ( int counter = 0; counter < channelBytesPerSample; counter++ ) {
                    int aux = counter;
                    for ( int k = 0; k < this.numChannels; k++ ) {
                             if ( k == 0 ) { aux0.add ( sample2[aux] ); }
                        else if ( k == 1 ) { aux1.add ( sample2[aux] ); }
                        else if ( k == 2 ) { aux2.add ( sample2[aux] ); }
                        else if ( k == 3 ) { aux3.add ( sample2[aux] ); }
                        else if ( k == 4 ) { aux4.add ( sample2[aux] ); }
                        else if ( k == 5 ) { aux5.add ( sample2[aux] ); }
                        else if ( k == 6 ) { aux6.add ( sample2[aux] ); }
                        else if ( k == 7 ) { aux7.add ( sample2[aux] ); }
                        
                        aux += channelBytesPerSample;
                    }
                }
                
                for ( int i = 0; i < this.numChannels; i++ ) {
                    if ( i == 0 ) {
                        byte[] toConvert = new byte[aux0.size()];
                        
                        for ( int count = 0; count < aux0.size(); count++ ) {
                            toConvert[count] = aux0.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel0.add ( converted );
                        
                        aux0.clear();
                    }
                    else if ( i == 1 ) {
                        byte[] toConvert = new byte[aux1.size()];
                        
                        for ( int count = 0; count < aux1.size(); count++ ) {
                            toConvert[count] = aux1.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel1.add ( converted );
                        
                        aux1.clear();
                    }
                    else if ( i == 2 ) {
                        byte[] toConvert = new byte[aux2.size()];
                        
                        for ( int count = 0; count < aux2.size(); count++ ) {
                            toConvert[count] = aux2.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel2.add ( converted );
                        
                        aux2.clear();
                    }
                    else if ( i == 3 ) {
                        byte[] toConvert = new byte[aux3.size()];
                        
                        for ( int count = 0; count < aux3.size(); count++ ) {
                            toConvert[count] = aux3.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel3.add ( converted );
                        
                        aux3.clear();
                    }
                    else if ( i == 4 ) {
                        byte[] toConvert = new byte[aux4.size()];
                        
                        for ( int count = 0; count < aux4.size(); count++ ) {
                            toConvert[count] = aux4.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel4.add ( converted );
                        
                        aux4.clear();
                    }
                    else if ( i == 5 ) {
                        byte[] toConvert = new byte[aux5.size()];
                        
                        for ( int count = 0; count < aux5.size(); count++ ) {
                            toConvert[count] = aux5.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel5.add ( converted );
                        
                        aux5.clear();
                    }
                    else if ( i == 6 ) {
                        byte[] toConvert = new byte[aux6.size()];
                        
                        for ( int count = 0; count < aux6.size(); count++ ) {
                            toConvert[count] = aux6.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel6.add ( converted );
                        
                        aux6.clear();
                    }
                    else if ( i == 7 ) {
                        byte[] toConvert = new byte[aux7.size()];
                        
                        for ( int count = 0; count < aux7.size(); count++ ) {
                            toConvert[count] = aux7.get ( count );
                        }
                        
                        int converted = 0;
                        
                        if ( toConvert.length == 2 ) {
                            converted = ( (toConvert[0] & 0xF) << 8 ) | ( (toConvert[1] & 0xFF) );
                        }
                        else if ( toConvert.length == 3 ) {
                            converted = ( (toConvert[0] & 0xF) << 16 ) | ( (toConvert[1] & 0xFF) << 8 ) | ( (toConvert[2] & 0xFF) << 16 );
                        }
                        else if ( toConvert.length > 3 ) {
                            ByteBuffer buffer = ByteBuffer.wrap ( toConvert );
                            buffer.order(ByteOrder.LITTLE_ENDIAN);
                            converted = buffer.getInt();
                        }
                        
                        channel7.add ( converted );
                        
                        aux7.clear();
                    }
                }
                
                String res = "" +  ( ( dataBytes / 100 ) * 100 / ( chunkSize / 100 ) );
                double r = new Double ( res );
                
                //System.out.println ( res );
                
                //Interface.progressBar.setValue ( Math.round ( dataBytes * 100 / chunkSize ) );
                Interface.progressBar.setValue ( (int) r );
                
                //System.out.println ( Interface.progressBar.getValue() + " | " + dataBytes + " * 100 / " + chunkSize + " = " + Math.round ( dataBytes * 100 / chunkSize ) );
                
                /*if ( Math.round ( dataBytes * 100 / chunkSize ) == 99 ) {
                    System.out.println ( Math.round ( dataBytes * 100 / chunkSize ) );
                }*/
            }
        }
        catch ( IOException ex ) {
            System.out.println ( ex );
        }
    }
    
    public int getSampleFromChannel ( int channel, int samplePosition ) {
             if ( channel == 0 ) { return channel0.get ( samplePosition ); }
        else if ( channel == 1 ) { return channel1.get ( samplePosition ); }
        else if ( channel == 2 ) { return channel2.get ( samplePosition ); }
        else if ( channel == 3 ) { return channel3.get ( samplePosition ); }
        else if ( channel == 4 ) { return channel4.get ( samplePosition ); }
        else if ( channel == 5 ) { return channel5.get ( samplePosition ); }
        else if ( channel == 6 ) { return channel6.get ( samplePosition ); }
        else { return channel7.get ( samplePosition ); }
    }
}
