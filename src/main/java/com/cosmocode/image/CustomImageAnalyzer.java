package com.cosmocode.image;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CustomImageAnalyzer implements ImageAnalyzer  {
 
    // Liste der Segment-Typen
    private static final int M_SOF0 = 0xC0;
    private static final int M_SOF1 = 0xC1;
    private static final int M_SOF2 = 0xC2;
    private static final int M_SOF3 = 0xC3;
    private static final int M_SOF5 = 0xC5;
    private static final int M_SOF6 = 0xC6;
    private static final int M_SOF7 = 0xC7;
    private static final int M_SOF9 = 0xC9;
    private static final int M_SOF10 = 0xCA;
    private static final int M_SOF11 = 0xCB;
    private static final int M_SOF13 = 0xCD;
    private static final int M_SOF14 = 0xCE;
    private static final int M_SOF15 = 0xCF;

    private final String docroot;
    
    /** Erzeugt ein neues Objekt vom CustomImageAnalyzer.
     * Bei diesem ImageAnalyzer werden eigene Methoden zum Anladen eines
     * images benutzt.
     */
    public CustomImageAnalyzer ( ) {
        docroot = "";
    }

    /** Erzeugt ein neues Objekt vom CustomImageAnalyzer.
     * Bei diesem ImageAnalyzer werden eigene Methoden zum Anladen eines
     * images benutzt. Ebenso wird beim Anladen des Bildes noch ein Pfad
     * vorangesetzt.
     */
    public CustomImageAnalyzer(String docroot) {
        this.docroot = docroot;
    }

    /** laed eine datei mit dem CustomAnalyzer an, erzeugt anhand der datei ein
     * neues ImageInfo-Objekt und gibt dies zurueck.
     * @param url Dateianme
     */
    public ImageInfo analyse( String url ) throws Exception {
        ImageInfo ii = gifImageHeader( docroot+url );
        if (ii==null) ii = jpegImageHeader( docroot+url );

        if (ii==null) ii = unknownImageHeader( docroot+url) ;

        return ii;
    }

    /** laed eine datei mit dem CustomAnalyzer an, erzeugt anhand der datei ein
     * neues ImageInfo-Objekt und gibt dies zurueck.
     * @param in InputStream
     */
    public ImageInfo analyse( InputStream in ) throws Exception {
        ImageInfo ii = gifImageHeader( in );
        if (ii==null) ii = jpegmageHeader( in );

        return ii;
    }

    private ImageInfo getImageHeader( String url, long size, DataInputStream dis ) throws Exception {
        final byte[] headerinfo = new byte[6];
        final byte[] widthinfo = new byte[2];
        final byte[] heightinfo = new byte[2];

        dis.read( headerinfo,0,6 );
        final String signatur = new String(headerinfo,0,3);
        final String version = new String(headerinfo,3,3);

        if ( (!signatur.equals("GIF")) && ( (!version.equals("89a")) || (!version.equals("87a")) ) )
            return null;

        dis.read( widthinfo, 0, 2);
        dis.read( heightinfo, 0, 2);
        
        final int width = ( widthinfo[0] & 0xFF) | ( widthinfo[1] << 8);
        final int height = ( heightinfo[0] & 0xFF) | ( heightinfo[1] << 8);

        System.err.println( "IMAGE-BREITE: " + width );
        System.err.println( "IMAGE-H�HE: " + height );

        return new ImageInfo(url, width, height, size );
    }
    private ImageInfo getJPEGHeader( String url, long size, DataInputStream dis ) throws Exception {
        boolean weiter = true;
        int b;
        int width=0;
        int height=0;

        final int header1 = dis.readUnsignedByte();    // header: 0xFF
        final int header2 = dis.readUnsignedByte();    // header: 0xD8

        if ((header1 != 0xFF) && (header2 != 0xD8)) return null;

        do {
            b = dis.readUnsignedByte(); // Segment-Start: 0xFF
            b = dis.readUnsignedByte(); // Segment-Type (siehe oben)

            switch ( b ) {
                case M_SOF0:
                case M_SOF1:
                case M_SOF2:
                case M_SOF3:
                case M_SOF5:
                case M_SOF6:
                case M_SOF7:
                case M_SOF9:
                case M_SOF10:
                case M_SOF11:
                case M_SOF13:
                case M_SOF14:
                case M_SOF15:
                    b = dis.readUnsignedShort() - 2;    // Segment-Length
                    dis.readUnsignedByte();             // data precision
                    height = dis.readUnsignedShort();
                    width = dis.readUnsignedShort();
                    weiter = false;
                    break;
                default:
                    b = dis.readUnsignedShort() - 2;    // Segment-Length
                    dis.skipBytes(b);
                    break;
            }
        } while (weiter);

        return new ImageInfo(url, width, height, size );
    }
    private ImageInfo gifImageHeader( InputStream in ) throws Exception {
        final DataInputStream dis = new DataInputStream( in );
        return getImageHeader( "", 0L, dis ) ;
    }


    /** gibt ein ImageInfo-Objekt von einer GIF-datei zurueck. sollte es
     * sich nicht um eine GIF-Datei handeln, wird ein NULL zurueck
     * gegeben.
     */
    private ImageInfo gifImageHeader( String url  ) throws Exception {
        final File f = new File( url );
        final FileInputStream in = new FileInputStream( url );
        final DataInputStream dis = new DataInputStream( in );
        return getImageHeader( url, f.length() , dis ) ;
    }

    /** gibt ein ImageInfo-Objekt von einer JPEG-datei zurueck. sollte es
     * sich nicht um eine JPEG-Datei handeln, wird ein NULL zurueck
     * gegeben.
     */
    private ImageInfo jpegImageHeader( String url ) throws Exception {
        final File f = new File( url );
        final FileInputStream in = new FileInputStream( url );
        final DataInputStream dis = new DataInputStream( in );
        return getJPEGHeader( url, f.length() , dis ) ;
    }
    private ImageInfo unknownImageHeader( String url  ) throws Exception {
        final File f = new File( url );
        return new ImageInfo( url, -1, -1, f.length() ) ;
    }
    private ImageInfo jpegmageHeader( InputStream in ) throws Exception {
        final DataInputStream dis = new DataInputStream( in );
        return getJPEGHeader( "", 0L , dis ) ;
    }
 
 
} // end class CustomImageAnalyzer
 
 
 
//////////////////////////////////////////////////////////////////////////////////
//
// $Log: CustomImageAnalyzer.java,v $
// Revision 1.7  2003/11/19 11:06:06  huettemann
// 1.4.2.0
//
// Revision 1.6  2003/07/09 11:36:07  rademacher
// 1.4.1.0
//
// Revision 1.5  2003/03/03 15:01:37  rademacher
// 1.4.0.0
//
// Revision 1.4  2003/03/03 14:58:37  rademacher
// 1.3.0.0
//
// Revision 1.3  2003/03/03 14:56:19  rademacher
// 1.2.0.0
//
// Revision 1.2  2003/03/03 14:27:29  rademacher
// 1.1.0.0
//
// Revision 1.1.1.1  2002/09/27 15:45:05  huettemann
//
//
// Revision 1.2  2002/05/16 15:12:54  rademacher
// erste version
//
// Revision 1.1.1.1  2002/05/14 14:26:28  rademacher
// first version
//
// 
//
