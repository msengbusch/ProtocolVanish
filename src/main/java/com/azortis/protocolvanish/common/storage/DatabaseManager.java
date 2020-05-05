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

package com.azortis.protocolvanish.common.storage;

import com.azortis.protocolvanish.common.storage.drivers.H2Driver;
import com.azortis.protocolvanish.common.storage.drivers.MariaDBDriver;

import java.io.File;

public abstract class DatabaseManager {

    private Driver driver;

    public DatabaseManager(StorageSettings storageSettings, File dataFolder){
        if(storageSettings.getDriver().equalsIgnoreCase("MariaDB")){
            driver = new MariaDBDriver(storageSettings);
        }else if(storageSettings.getDriver().equalsIgnoreCase("H2")){
            driver = new H2Driver(dataFolder);
        }
    }

    public Driver getDriver() {
        return driver;
    }
}