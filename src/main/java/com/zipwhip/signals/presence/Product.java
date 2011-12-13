package com.zipwhip.signals.presence;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 6/28/11
 * Time: 3:40 PM
 */
public class Product implements Serializable {

	private static final long serialVersionUID = -2735403011556045497L;

	/**
	 * This is the friendly name for the software on the phone/browser
	 * e.g.  ZIPGROUPS, DEVICE_CARBON, TABLET_TEXTER, PEEPS
	 */
	private ProductLine name;

	/**
	 * Version of the software
	 */
	private String version;

	/**
	 * Aka the build of the software that will
	 * correlate to a set of packages
	 */
	private String build;

	public ProductLine getName() {
		return name;
	}

	public void setName(ProductLine name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}
}
