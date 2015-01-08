/*
 * Copyright 2013 WhiteByte (Nick Russler, Ahmet Yueksektepe).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enterprayz.urec.wifiexplorerlib.items;

public class ClientScanResultItem {

	private String IpAddr;
	private String HWAddr;
	private String DeviceVendorName;
	private boolean isReachable;

	public ClientScanResultItem(String ipAddr, String hWAddr, String deviceVendorName, boolean isReachable) {
		super();
		this.IpAddr = ipAddr;
		this.HWAddr = hWAddr;
		this.DeviceVendorName = deviceVendorName;
		this.isReachable = isReachable;
	}

	public String getIpAddr() {
		return IpAddr;
	}
	public void setIpAddr(String ipAddr) {
		IpAddr = ipAddr;
	}


	public String getHWAddr() {
		return HWAddr;
	}
	public void setHWAddr(String hWAddr) {
		HWAddr = hWAddr;
	}

	
	public String getDeviceVendorName() {
		return DeviceVendorName;
	}
	public void setDeviceVendorName(String deviceVendorName) {
		DeviceVendorName = deviceVendorName;
	}

	
	public boolean isReachable() {
		return isReachable;
	}
	public void setReachable(boolean isReachable) {
		this.isReachable = isReachable;
	}
	
}