package com.cosmocode.image;

/**
 *
 * @version $Id: ImageAnalyzer.java,v 1.1.1.1 2002/09/27 15:45:05 huettemann Exp $
 *
 * Written by CosmoCode GmbH 2001; contact: info@cosmocode.de
 */

public interface ImageAnalyzer  {
 
	public static final String cvsID = "$Id: ImageAnalyzer.java,v 1.1.1.1 2002/09/27 15:45:05 huettemann Exp $";

	public ImageInfo analyse( String url ) throws Exception ;
 
} // end class ImageAnalyzer
 
 
 
//////////////////////////////////////////////////////////////////////////////////
//
// $Log: ImageAnalyzer.java,v $
// Revision 1.1.1.1  2002/09/27 15:45:05  huettemann
//
//
// Revision 1.1.1.1  2002/05/14 14:26:28  rademacher
// first version
//
// 
//
