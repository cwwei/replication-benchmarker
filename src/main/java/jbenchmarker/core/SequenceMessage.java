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
package jbenchmarker.core;

import crdt.CommutativeMessage;
import java.io.Serializable;

/**
 * An operation for a replication algorithm. C'est quoi le différence avec sequence operation ?
 * @author urso
 */
public abstract class SequenceMessage extends CommutativeMessage implements Serializable,Message {
    final private SequenceOperation originalOp;       // Trace operation issuing this one

    public SequenceOperation getOriginalOp() {
        return originalOp;
    }
    
    public SequenceMessage(SequenceOperation o) {
        this.originalOp = o;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SequenceMessage other = (SequenceMessage) obj;
        if (this.originalOp != other.originalOp && (this.originalOp == null || !this.originalOp.equals(other.originalOp))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.originalOp != null ? this.originalOp.hashCode() : 0);
        return hash;
    }
    
    @Override
    abstract public SequenceMessage copy();

    /*@Override
    public SequenceMessage clone(){
        return copy();
    }*/

    
    @Override
    protected String visu() {
        return originalOp.toString();
    }
}
