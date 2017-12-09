/*
 * Copyright 2017 Aurélien Gâteau <mail@agateau.com>
 *
 * This file is part of Tiny Wheels.
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

import com.agateau.ui.anchor.AnchorGroup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Hud showing player info during race
 */
class Hud {
    private final static float BUTTON_SIZE_CM = 1.5f;

    private final float BUTTON_SIZE_PX;

    private AnchorGroup mRoot;
    private float mZoom;

    public Hud(Assets assets, Stage stage) {
        mRoot = new AnchorGroup();

        BUTTON_SIZE_PX = assets.findRegion("hud-action").getRegionWidth();
        stage.addActor(mRoot);
    }

    public AnchorGroup getRoot() {
        return mRoot;
    }

    @SuppressWarnings("UnusedParameters")
    public void act(float delta) {
        updateZoom();
    }

    public void setScreenRect(int x, int y, int width, int height) {
        mRoot.setBounds(x, y, width, height);
    }

    public float getZoom() {
        return mZoom;
    }

    private void updateZoom() {
        float ppc = (Gdx.graphics.getPpcX() + Gdx.graphics.getPpcY()) / 2;
        float pxSize = BUTTON_SIZE_CM * ppc;
        mZoom = MathUtils.floor(Math.max(pxSize / BUTTON_SIZE_PX, 1));
    }
}
