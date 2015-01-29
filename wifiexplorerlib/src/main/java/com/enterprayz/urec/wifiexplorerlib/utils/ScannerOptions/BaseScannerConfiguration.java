package com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions;

import android.content.Context;

/**
 * Created by con on 11.01.15.
 */
public  class BaseScannerConfiguration {
    private int[] DPORTS;//default port to scan
    private int TIMEOUT_SCAN; // seconds
    private int TIMEOUT_SHUTDOWN; // seconds
    private int THREADS;// alive threads
    private int mRateMult; // Number of alive hosts between Rate
    private int pt_move; // 1=backward 2=forward
    private long start;// from ip
    private long end;//to ip
    private String gateIp;
    private boolean doRateControl;

    public int[] getDPORTS() {
        return DPORTS;
    }

    public void setDPORTS(int[] DPORTS) {
        this.DPORTS = DPORTS;
    }

    public int getTIMEOUT_SCAN() {
        return TIMEOUT_SCAN;
    }

    public void setTIMEOUT_SCAN(int TIMEOUT_SCAN) {
        this.TIMEOUT_SCAN = TIMEOUT_SCAN;
    }

    public int getmRateMult() {
        return mRateMult;
    }

    public void setmRateMult(int mRateMult) {
        this.mRateMult = mRateMult;
    }

    public int getPtmove() {
        return pt_move;
    }

    public void setPt_move(int pt_move) {
        this.pt_move = pt_move;
    }

    public int getTHREADS() {
        return THREADS;
    }

    public void setTHREADS(int THREADS) {
        this.THREADS = THREADS;
    }

    public int getTIMEOUT_SHUTDOWN() {
        return TIMEOUT_SHUTDOWN;
    }

    public void setTIMEOUT_SHUTDOWN(int TIMEOUT_SHUTDOWN) {
        this.TIMEOUT_SHUTDOWN = TIMEOUT_SHUTDOWN;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getGateIp() {
        return gateIp;
    }

    public void setGateIp(String gateIp) {
        this.gateIp = gateIp;
    }

    public boolean isRateControl() {
        return doRateControl;
    }

    public void setDoRateControl(boolean doRateControl) {
        this.doRateControl = doRateControl;
    }
}
