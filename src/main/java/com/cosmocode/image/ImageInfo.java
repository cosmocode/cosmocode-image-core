package com.cosmocode.image;

/**
 *
 * @version $Id: ImageInfo.java,v 1.1.1.1 2002/09/27 15:45:05 huettemann Exp $
 *
 * Written by CosmoCode GmbH 2001; contact: info@cosmocode.de
 */

public class ImageInfo  {
 
	private int width;
	private int height;
	private long size;
	private String url;
	private String altTag;
	private String name;

	/** Erzeugt ein neues ImageInfo-Object.
	 */
	public ImageInfo ( String url, int width, int height, long size ) {
		this.url = url;
		this.width = width;
		this.height = height;
		this.size = size;
		this.altTag = "";
		this.name = "";
	}
	
	/** gibt die Breite des Image zurueck.
	 */
	public int getWidth() {
		return width;
	}

	/** gibt die Hoehe des Image zurueck.
	 */
	public int getHeight() {
		return height;
	}

	/** setzt die Breite des Image.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/** setzt die Hoehe des Image.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/** gibt die Dateigroesse des Image zurueck.
	 */
	public long getSize() {
		return size;
	}

	/** Gibt die url des Image zurueck.
	 */
	public String getUrl() {
		return url;
	}

	/** setzt das ALT-Attribute des image-tag.
	 */
	public void setAltAttribute(String n) {
		this.altTag = n;
	}

	/** gibt das ALT-Attribute des image-tag zurueck.
	 */
	public String getAltAttribute() {
		return altTag;
	}

	/** setzt den Namen des image.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** gbit den Namen des images zurueck.
	 */
	public String getName() {
		return name;
	}

	/** gibt ein image-tag als Strinng zurueck. der tag
	 * enthaelt alle benoetigten attribute, wie breite, hoehe, border
	 * etc. 
	 */
	public String toString() {
		return "<img src=\"" + url + 
		    "\" name=\"" + name + 
		    "\" alt=\"" + altTag + 
		    "\" border=\"0\" width=\"" + width + 
		    "\" height=\""+ height + 
		    "\">";
	}
 
 
} // end class ImageInfo
 
 
 
//////////////////////////////////////////////////////////////////////////////////
//
// $Log: ImageInfo.java,v $
// Revision 1.1.1.1  2002/09/27 15:45:05  huettemann
//
//
// Revision 1.1.1.1  2002/05/14 14:26:28  rademacher
// first version
//
// 
//
