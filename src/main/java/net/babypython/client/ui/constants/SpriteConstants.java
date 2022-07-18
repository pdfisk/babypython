/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.ui.constants;

public class SpriteConstants {
    public static final int BalloonOffset = 15;
    public static final String BalloonStyle = "balloonPanel";
    public static final String CatLeft = "cat_left";
    public static final String CatRight = "cat_right";
    public static final String ChickLeft = "chick_left";
    public static final String ChickRight = "chick_right";
    public static final String DogAtHome = "dog_at_home";
    public static final String DogHouse = "doghouse";
    public static final String DuckFront = "duck_front";
    public static final String DuckLeft = "duck_left";
    public static final String DuckRight = "duck_right";
    public static final String DucklingInDogHouse = "duckling_in_dog_house";
    public static final String Fence = "fence";
    public static final String HenWithChick = "hen_with_chick";
    public static final String LightHenLeft = "light_hen_chick";
    public static final String LightHenRight = "light_hen_right";
    public static final String OrangeDucklingLeft = "orange_duckling_left";
    public static final String OrangeDucklingRight = "orange_duckling_right";
    public static final String Pump = "pump";
    public static final String PumpWithDog = "pump_with_dog";
    public static final String PumpWithDogAndDuckling = "pump_with_dog_and_duckling";

    public static String[] getAllSprites() {
        return new String[]{
                CatLeft,
                CatRight,
                ChickLeft,
                ChickRight,
                HenWithChick,
                DogAtHome,
                DogHouse,
                DuckFront,
                DuckLeft,
                DuckRight,
                DucklingInDogHouse,
                Fence,
                LightHenLeft,
                LightHenRight,
                OrangeDucklingLeft,
                OrangeDucklingRight,
                Pump,
                PumpWithDog,
                PumpWithDogAndDuckling
        };
    }
}
