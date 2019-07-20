/*
 * Hides you completely from players on your servers by using packets!
 *     Copyright (C) 2019  Azortis
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.azortis.protocolvanish.settings;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class VisibilitySettingsWrapper {

    private SettingsManager parent;
    private Map<String, Object> settingsMap;

    public VisibilitySettingsWrapper(SettingsManager parent, Object settingsMap){
        this.parent = parent;
        this.settingsMap = (Map<String, Object>) settingsMap;
    }

    public List<String> getEnabledPacketListeners(){
        return (List<String>) settingsMap.get("enabledPacketListeners");
    }

    public void setEnabledPacketListeners(List<String> enabledPacketListeners){
        settingsMap.remove("enabledPacketListeners");
        settingsMap.put("enabledPacketListeners", enabledPacketListeners);
    }

    public boolean getAdjustOnlinePlayerCount(){
        Map<String, Object> externalVisibility = (Map<String, Object>) settingsMap.get("externalVisibility");
        return (Boolean) externalVisibility.get("adjustOnlinePlayerCount");
    }

    public void setAdjustOnlinePlayerCount(boolean adjustOnlinePlayerCount){
        Map<String, Object> externalVisibility = (Map<String, Object>) settingsMap.get("externalVisibility");
        externalVisibility.remove("adjustOnlinePlayerCount");
        externalVisibility.put("adjustOnlinePlayerCount", adjustOnlinePlayerCount);
        settingsMap.remove("externalVisibilty");
        settingsMap.put("externalVisibilty", externalVisibility);
    }

    public boolean getAdjustOnlinePlayerList(){
        Map<String, Object> externalVisibility = (Map<String, Object>) settingsMap.get("externalVisibility");
        return (Boolean) externalVisibility.get("adjustOnlinePlayerCount");
    }

    public void setAdjustOnlinePlayerList(boolean adjustOnlinePlayerList){
        Map<String, Object> externalVisibility = (Map<String, Object>) settingsMap.get("externalVisibility");
        externalVisibility.remove("adjustOnlinePlayerList");
        externalVisibility.put("adjustOnlinePlayerList", adjustOnlinePlayerList);
        settingsMap.remove("externalVisibilty");
        settingsMap.put("externalVisibilty", externalVisibility);
    }

    public void save(){
        Map<String, Object> parrentSettingsMap = parent.getSettingsMap();
        parrentSettingsMap.remove("visibilitySettings");
        parrentSettingsMap.put("visibilitySettings", settingsMap);
    }

}