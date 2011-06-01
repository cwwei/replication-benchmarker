/**
 * Replication Benchmarker
 * https://github.com/score-team/replication-benchmarker/
 * Copyright (C) 2011 INRIA / LORIA / SCORE Team
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
package jbenchmarker.ot;

/**
 *
 * @author oster
 */
public class TTFChar {

    private char character;
    private boolean visible;

    public TTFChar(char c) {
        this.character = c;
        this.visible = true;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void hide() {
        this.visible = false;
    }

    public char getChar() {
        return this.character;
    }

    @Override
    public String toString() {
        if (this.visible) {
            return "" + this.character;
        } else {
            return "{" + this.character + "}";
        }
    }
}