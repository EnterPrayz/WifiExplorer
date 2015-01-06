package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;

/**
 * Apn user state interface.
 * */
public interface OnAPNUsersChangeStateListener {
    /**
     * On APN user state was change. Return  <{@link ClientScanResultItem }> and flag after change state of APN user.

     * @param  userItem <{@link ClientScanResultItem }>
     *  @param  flag 1 connect, 2 disconnect
     * */
    public void onAPNUsersChangeState(ClientScanResultItem userItem, int flag);
}
