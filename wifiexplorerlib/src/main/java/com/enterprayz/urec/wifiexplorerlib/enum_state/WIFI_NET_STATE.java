package com.enterprayz.urec.wifiexplorerlib.enum_state;


/**
 * Created by con on 24.12.14.
 */
     public enum WIFI_NET_STATE {
        /** Ready to start data connection setup. */
        IDLE,
        /** Searching for an available access point. */
        SCANNING,
        /** Currently setting up data connection. */
        CONNECTING,
        /** Network link established, performing authentication. */
        AUTHENTICATING,
        /** Awaiting response from DHCP server in order to assign IP address information. */
        OBTAINING_IPADDR,
        /** IP traffic should be available. */
        CONNECTED,
        /** IP traffic is suspended */
        SUSPENDED,
        /** Currently tearing down data connection. */
        DISCONNECTING,
        /** IP traffic not available. */
        DISCONNECTED,
        /** Attempt to connect failed. */
        FAILED,
        /** Access to this network is blocked. */
        BLOCKED,
        /** Link has poor connectivity. */
        VERIFYING_POOR_LINK,
        /** Checking if network is a captive portal */
        CAPTIVE_PORTAL_CHECK
    }

