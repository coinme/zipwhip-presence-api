package com.zipwhip.signals.presence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.zipwhip.signals.address.ClientAddress;

/**
 * Created by IntelliJ IDEA.
 * User: David Davis
 * Date: 7/27/11
 * Time: 2:40 PM
 */
public class PresenceGroup extends ArrayList<Presence> implements Serializable {

	// we control the serialisation version
	private static final long serialVersionUID = 30936826296139318L;

	public void setData(ClientAddress address, String ip, Date date, boolean connected) {
		for (Presence presence : this) {
			presence.setAddress(address);
			presence.setIp(ip);
			presence.setLastActive(date);
			presence.setConnected(Boolean.valueOf(connected));
		}
	}

	public void setIp(String ip) {
		for (Presence presence : this) {
			presence.setIp(ip);
		}
	}

	public void setLastActive(Date date) {
		for (Presence presence : this) {
			presence.setLastActive(date);
		}
	}

	public void setConnected(boolean connected) {
		for (Presence presence : this) {
			presence.setConnected(Boolean.valueOf(connected));
		}
	}

	public void setAddress(ClientAddress address) {
		for (Presence presence : this) {
			presence.setAddress(address);
		}
	}
}
