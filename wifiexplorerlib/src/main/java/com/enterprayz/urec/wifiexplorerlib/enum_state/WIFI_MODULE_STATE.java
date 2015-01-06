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

package com.enterprayz.urec.wifiexplorerlib.enum_state;

public enum WIFI_MODULE_STATE  {
    WIFI_MODULE_STATE_DISABLING(0),
    WIFI_MODULE_STATE_DISABLED(1),
    WIFI_MODULE_STATE_ENABLING(2),
    WIFI_MODULE_STATE_ENABLED(3),
    WIFI_MODULE_STATE_FAILED(4);

    private int index;

    private WIFI_MODULE_STATE(int index) {
        this.index = index;
    }

    public static WIFI_MODULE_STATE getStateByIndex(int index) {
        for (WIFI_MODULE_STATE l : WIFI_MODULE_STATE.values()) {
            if (l.index == index)
                return l;
        }
        throw new IllegalArgumentException("State not found");
    }
}