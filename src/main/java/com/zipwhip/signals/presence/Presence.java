package com.zipwhip.signals.presence;

import com.zipwhip.signals.address.ClientAddress;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Michael
 * Date: 6/28/11
 * Time: 2:43 PM
 */
public class Presence implements Serializable {

    // we control the serialisation version
    static final long serialVersionUID = 10375439476839415L;

    String ip;

    /**
     * A way to uniquely call you
     */
    ClientAddress address;

    /**
     * Tablet, Phone, Browser, none
     */
    PresenceCategory category = PresenceCategory.NONE;

    /**
     * Some user agent string like a browser, that tells all apps installed and versions of apps.
     */
    UserAgent userAgent;

    /**
     * Status - online, busy, away, invisible, offline
     */

    PresenceStatus status;

    /**
     * Connected
     */
    Boolean connected;

    /**
     * The subscriptionId
     */
    String subscriptionId;

    /*
    * Last active Date+Time
    */
    Date lastActive;

    PresenceExtraInfo extraInfo;

    public Presence() {

    }

    public PresenceStatus getStatus() {
        return status;
    }

    public void setStatus(PresenceStatus status) {
        this.status = status;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ClientAddress getAddress() {
        return address;
    }

    public void setAddress(ClientAddress address) {
        this.address = address;
    }

    public PresenceCategory getCategory() {
        return category;
    }

    public void setCategory(PresenceCategory category) {
        this.category = category;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(UserAgent userAgent) {
        this.userAgent = userAgent;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public PresenceExtraInfo getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(PresenceExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * A private constructor for use by the builder.
     * @param builder The builder from which to construct this Presence
     */
    private Presence(Builder builder) {
        ip = builder.ip;
        address = builder.address;
        category = builder.category;
        userAgent = builder.userAgent;
        status = builder.status;
        connected = builder.connected;
        subscriptionId = builder.subscriptionId;
        lastActive = builder.lastActive;
        extraInfo = builder.extraInfo;
    }

    public static class Builder {

        private String ip;
        private ClientAddress address;
        private PresenceCategory category = PresenceCategory.NONE;
        private UserAgent userAgent;
        private PresenceStatus status;
        private Boolean connected;
        private String subscriptionId;
        private Date lastActive;
        private PresenceExtraInfo extraInfo;

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder clientAddressClientId(String clientId) {
            address = new ClientAddress(clientId);
            return this;
        }

        public Builder category(PresenceCategory category) {
            this.category = category;
            return this;
        }

        public Builder userAgentMakeModel(String makeModel) {
            if (userAgent == null) {
                userAgent = new UserAgent();
            }
            userAgent.makeModel = makeModel;
            return this;
        }

        public Builder userAgentBuild(String build) {
            if (userAgent == null) {
                userAgent = new UserAgent();
            }
            userAgent.build = build;
            return this;
        }

        public Builder userAgentProductName(ProductLine name) {
            if (userAgent == null) {
                userAgent = new UserAgent();
                userAgent.product = new Product();
            }
            userAgent.product.name = name;
            return this;
        }

        public Builder userAgentProductVersion(String version) {
            if (userAgent == null) {
                userAgent = new UserAgent();
                userAgent.product = new Product();
            }
            userAgent.product.version = version;
            return this;
        }

        public Builder userAgentProductBuild(String build) {
            if (userAgent == null) {
                userAgent = new UserAgent();
                userAgent.product = new Product();
            }
            userAgent.product.build = build;
            return this;
        }

        public Builder status(PresenceStatus status) {
            this.status = status;
            return this;
        }

        public Builder isConnected(boolean connected) {
            this.connected = connected;
            return this;
        }

        public Builder subscriptionId(String subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Builder lastActive(Date lastActive) {
            this.lastActive = lastActive;
            return this;
        }

        public Builder userExtra(String key, Object value) {
            if (extraInfo == null) {
                extraInfo = new PresenceExtraInfo();
            }
            extraInfo.put(key, value);
            return this;
        }

        public Presence build() {
            return new Presence(this);
        }

    }

}
