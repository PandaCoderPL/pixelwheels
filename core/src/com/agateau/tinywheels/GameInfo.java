/*
 * Copyright 2017 Aurélien Gâteau <mail@agateau.com>
 *
 * This file is part of Pixel Wheels.
 *
 * Tiny Wheels is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.agateau.tinywheels;

import com.agateau.tinywheels.gameinput.GameInputHandler;
import com.agateau.tinywheels.map.Track;
import com.agateau.tinywheels.vehicledef.VehicleDef;
import com.badlogic.gdx.utils.Array;

/**
 * Details about the game to start
 */
public abstract class GameInfo {
    private final Array<VehicleDef> mVehicleDefs;
    protected final GameInfoConfig mGameInfoConfig;
    private final Array<Entrant> mEntrants = new Array<Entrant>();

    public static class Entrant {
        String vehicleId;
    }

    public static class Player extends Entrant {
        GameInputHandler inputHandler;

        public Player(String vehicleId, GameInputHandler inputHandler) {
            this.vehicleId = vehicleId;
            this.inputHandler = inputHandler;
        }
    }

    public GameInfo(Array<VehicleDef> vehicleDefs, GameInfoConfig gameInfoConfig) {
        mVehicleDefs = vehicleDefs;
        mGameInfoConfig = gameInfoConfig;
    }

    public abstract Track getTrack();

    public Array<Entrant> getEntrants() {
        return mEntrants;
    }

    public void setPlayers(Array<Player> players) {
        Array<String> vehicleIds = new Array<String>();
        for (VehicleDef vehicleDef : mVehicleDefs) {
            vehicleIds.add(vehicleDef.id);
        }
        for (GameInfo.Player player : players) {
            vehicleIds.removeValue(player.vehicleId, /* identity= */ false);
        }
        vehicleIds.shuffle();
        int aiCount = GamePlay.instance.racerCount - players.size;

        mEntrants.clear();
        for (int idx = 0; idx < aiCount; ++idx) {
            Entrant entrant = new Entrant();
            entrant.vehicleId = vehicleIds.get(idx % vehicleIds.size);
            mEntrants.add(entrant);
        }
        mEntrants.addAll(players);

        storePlayersInConfig(players);
        flush();
    }

    private void storePlayersInConfig(Array<Player> players) {
        for (int idx = 0; idx < mGameInfoConfig.vehicles.length; ++idx) {
            String vehicleId = idx < players.size ? players.get(idx).vehicleId : "";
            mGameInfoConfig.vehicles[idx] = vehicleId;
        }
    }

    protected void flush() {
        mGameInfoConfig.flush();
    }
}
