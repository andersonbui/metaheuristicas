/*
 * Copyright (C) 2018 debian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package metaheuristicas;

import java.util.Random;

/**
 *
 * @author debian
 */
public class Aleatorio {

    private static Random rand;

    private Aleatorio() {
    }

    static {
        rand = new Random(3);
    }

    public static double nextDouble() {
        return rand.nextDouble();
    }

    public static int nextInt() {
        return rand.nextInt();
    }

    public static int nextInt(int bound) {
        return rand.nextInt(bound);
    }

}
