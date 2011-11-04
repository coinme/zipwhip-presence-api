package com.zipwhip.signals.presence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.zipwhip.cache.CacheProvider;
import com.zipwhip.signals.address.Address;
import com.zipwhip.signals.address.ChannelAddress;
import com.zipwhip.signals.mail.ClientInfo;
import com.zipwhip.signals.mail.ClientInfoProvider;
import com.zipwhip.util.StringUtil;

/**
 * Created by IntelliJ IDEA.
 * User: David Davis
 * Date: 7/5/11
 * Time: 5:13 PM
 *
 */
public class DefaultPresenceProvider extends PresenceProviderBase {

	private static final Logger LOG = Logger.getLogger(DefaultPresenceProvider.class);

	private static final String PRESENCE_PREFIX = "presence:";
	private static final String MEMBER_LIST_PREFIX = "members:";
	private static final String CLIENT_INFO_PREFIX = "clientinfo:";

	private CacheProvider cacheProvider;
	private ClientInfoProvider clientInfoProvider;

	public DefaultPresenceProvider() {

	}

	public DefaultPresenceProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	/**
	 * Save a presence obj in cache, preserving an existing PresenceGroup
	 * @param presence
	 */
	public void put(Presence presence) {
		String clientId = presence.getAddress().getClientId();
		if (clientId == null) {
			return;
		}

		PresenceGroup presenceGroup = get(clientId);
		if (presenceGroup == null) {
			presenceGroup = new PresenceGroup();
			presenceGroup.add(presence);
		} else {
			/* probably can't do this
            if (!presenceGroup.contains(presence)) {
                presenceGroup.add(presence);
            }
			 */
			boolean found = false;
			for (Presence _presence : presenceGroup) {
				String _subId = _presence.getSubscriptionId();
				String subId = presence.getSubscriptionId();

				if (_subId == null) {
					_subId = "";
				}
				if (subId == null) {
					subId = "";
				}

				if (_subId.equals(subId)) {
					found = true;
					break;
				}
			}

			if (!found) {
				presenceGroup.add(presence);
			}
		}

		put(presenceGroup);
	}


	/**
	 * Save a presence group in the cache
	 * @param presenceGroup
	 */
	public void put(PresenceGroup presenceGroup) {
		String clientId = null;
		ClientInfo clientInfo = null;

		for (Presence presence : presenceGroup) {
			clientId = presence.getAddress().getClientId();

			if (clientId != null) {
				clientInfo = clientInfoProvider.get(clientId);
				break;
			}
		}

		if ((clientInfo == null) || (clientInfo.getSubscriptions() == null)) {
			return;
		}

		for (String subscriptionId : clientInfo.getSubscriptions().keySet()) {
			if (subscriptionId == null) {
				subscriptionId = "";
			}

			Collection<Address> addresses = clientInfo.getSubscriptions().get(subscriptionId);
			if (addresses == null) {
				continue;
			}

			for (Address address : addresses) {
				String cacheKey = PRESENCE_PREFIX + MEMBER_LIST_PREFIX + address;
				List<String> keylist = (List<String>)cacheProvider.get(cacheKey);
				if (keylist == null) {
					keylist = new ArrayList<String>();
				}

				if (keylist.contains(clientId)) {
					continue;
				}

				keylist.add(clientId);

				cacheProvider.put(cacheKey, keylist);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("presenceProvider is doing a PUT on " + clientId);
		}

		cacheProvider.put(PRESENCE_PREFIX + clientId, presenceGroup);
	}

	public void removeFromChannels(PresenceGroup presenceGroup, List<ChannelAddress> channels) {
		String clientId = null;

		for (Presence presence : presenceGroup) {
			clientId = presence.getAddress().getClientId();

			if (clientId != null) {
				break;
			}
		}

		if (clientId == null) {
			return;
		}

		for (Address address : channels) {
			List<String> keylist = (List<String>)cacheProvider.get(PRESENCE_PREFIX + MEMBER_LIST_PREFIX + address);
			if (keylist == null) {
				continue;
			}

			if (keylist.contains(clientId)) {
				keylist.remove(clientId);
				continue;
			}

			cacheProvider.put(PRESENCE_PREFIX + MEMBER_LIST_PREFIX + address, keylist);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("presenceProvider is doing a PUT on " + clientId);
		}

		cacheProvider.put(PRESENCE_PREFIX + clientId, presenceGroup);
	}

	/**
	 * get a presence obj by clientId
	 * @param clientId
	 * @return presence
	 */
	public PresenceGroup get(String clientId) {
		if (StringUtil.isNullOrEmpty(clientId)) {
			return null;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("presenceProvider is doing a GET on clientId: " + clientId);
		}

		return (PresenceGroup)cacheProvider.get(PRESENCE_PREFIX + clientId);
	}

	/**
	 * list the presence objects that are on this address (channel)
	 * @param address The address that people can listen to
	 * @return List<Presence>
	 */
	@Override
	public List<Presence> listByAddress(Address address) {
		List<Presence> plist = new ArrayList<Presence>();
		List<String> keylist = (List<String>)cacheProvider.get(PRESENCE_PREFIX + MEMBER_LIST_PREFIX + address);
		for (String key : keylist) {
			PresenceGroup presenceGroup = (PresenceGroup)cacheProvider.get(PRESENCE_PREFIX + key);
			if (presenceGroup == null) {
				continue;
			}
			plist.addAll(presenceGroup);
		}
		return plist;
	}

	/**
	 * get a clientInfo object by presence
	 * @return clientInfo
	 */
	@Override
	public ClientInfo get(Presence presence) {
		String clientId = presence.getAddress().getClientId();
		if (StringUtil.isNullOrEmpty(clientId)) {
			return null;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("presenceProvider is doing a GET using presence obj.  clientId: " + clientId);
		}

		return (ClientInfo)cacheProvider.get(CLIENT_INFO_PREFIX + clientId);
	}

	public void setClientInfoProvider(ClientInfoProvider clientInfoProvider) {
		this.clientInfoProvider = clientInfoProvider;
	}

	public ClientInfoProvider getClientInfoProvider() {
		return clientInfoProvider;
	}

	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
}
