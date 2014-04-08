

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Class to filter files. This class filter all the files that are exhibited in the
 * dialog page.
 *
 * @author Rener Baffa da Silva
 */
public class SoundsFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if ( !file.isDirectory() ) {
            String extension = "";
            
            /* Split the file path and get the file extension. */
                int i = file.getAbsolutePath().lastIndexOf ( "." );
                int p = Math.max ( file.getAbsolutePath().lastIndexOf ( "\\" ), file.getAbsolutePath().lastIndexOf ( "/" ) );

                if ( i > p ) {
                    extension = file.getAbsolutePath().substring ( i + 1 );
                }
            
            /* Check if extension is expected. If so returns true and if not returns false */
                if ( extension != null ) {
                    /* If you want to add more extensions, just change the "if" below and add the new extension. */
                    if ( extension.equals ( "wav" ) ) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return false;
                }
        }
        else {
            return true;
        }
    }

    @Override
    public String getDescription() {
        return "WAV files";
    }
    
}
