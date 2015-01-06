package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;

import java.util.ArrayList;


public interface OnFinishAPNScanUsersListener {
    /**
     * Return ArrayList <{@link ClientScanResultItem }> after set method getAPNUsers.
     * */
    public void onGetAPNUsers(ArrayList<ClientScanResultItem> clients);


}
