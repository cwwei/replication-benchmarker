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
package jbenchmarker.trace;

import jbenchmarker.core.SequenceOperation;
import crdt.simulator.IncorrectTraceException;
import crdt.simulator.Trace;
import crdt.simulator.TraceOperation;
import java.util.Enumeration;
import java.util.HashMap;
import collect.VectorClock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author urso
 */
public class TraceGeneratorTest {


    private static List<SequenceOperation> it2list(Trace t) {
        List<SequenceOperation> l = new ArrayList<SequenceOperation>();
        Enumeration<TraceOperation> rn = t.enumeration();
        while (rn.hasMoreElements()) l.add((SequenceOperation) rn.nextElement());
        return l;
    }
    
    /**
     * Helper to construct a 3 entries VC
     */
    public static VectorClock vc(int a, int b, int c) {
        VectorClock v = new VectorClock();
        v.put(1,a); v.put(2,b); v.put(3,c);
        return v;
    } 
    
    /**
     * Helper to construct an op with a 3 entries VC
     */
    public static SequenceOperation op(int r, int a, int b, int c) {
        return SequenceOperation.insert(r, 0, "", vc(a, b, c));
    } 

    /**
     * Tests of causalHistoryPerReplica method, of class TraceGenerator.
     * tests only exceptions.
     */
    @Test(expected=IncorrectTraceException.class)
    public void testOrderMissing() throws Exception {
        System.out.println("causalHistoryPerReplica");
        Map<Integer, VectorClock> vcs = new HashMap<Integer, VectorClock>();
        vcs.put(1, vc(3, 1, 0)); vcs.put(2, vc(0, 1, 0)); vcs.put(3, vc(0, 0, 0));
        TraceGenerator.causalCheck(op(2,0,3,0), vcs);
        fail("Missing operation not detected.");
    }
    
    @Test(expected=IncorrectTraceException.class)
    public void testOrderCausalMissing() throws Exception {
        Map<Integer, VectorClock> vcs = new HashMap<Integer, VectorClock>();
        vcs.put(1, vc(3, 1, 0)); vcs.put(2, vc(0, 1, 0)); vcs.put(3, vc(0, 0, 0));
        TraceGenerator.causalCheck(op(2,4,2,0), vcs);
        fail("Missing causal operation not detected.");
    }
    
    @Test
    public void testOrderOK() throws Exception {
        Map<Integer, VectorClock> vcs = new HashMap<Integer, VectorClock>();
        vcs.put(1, vc(3, 1, 0)); vcs.put(2, vc(0, 1, 0));  vcs.put(3, vc(0, 0, 0));
        TraceGenerator.causalCheck(op(2,2,2,0), vcs);
    }
    
    @Test
    public void testTraceFromXML() throws Exception {
        System.out.println("traceFromXML");
        List<SequenceOperation> trace = it2list(TraceGenerator.traceFromXML("../../traces/xml/exemple.xml", 1));
        
        assertEquals(5, trace.size());
        assertEquals(SequenceOperation.OpType.ins, trace.get(0).getType());
        assertEquals(14, trace.get(1).getPosition());
        assertEquals(" Mehdi", trace.get(2).getContentAsString());
        assertEquals(2, (long)trace.get(2).getVectorClock().get(2));
        assertEquals(SequenceOperation.OpType.del, trace.get(3).getType());
        assertEquals(4, trace.get(3).getReplica());
        assertEquals(3, trace.get(3).getNumberOf());        
        assertEquals(1, (long)trace.get(3).getVectorClock().get(4));
    }
    
    @Test
    public void testTraceFromJson() throws Exception {        
        List<SequenceOperation> trace = it2list(TraceGenerator.traceFromJson("../../traces/json/dirtyCSGerald3.db","notes003"));
//        assertEquals(11, trace.size());

//        assertEquals(SequenceOperation.OpType.ins, trace.get(0).getType());
//        assertEquals(25, trace.get(0).getPosition());        
//        assertEquals("z", trace.get(0).getContentAsString());
//        assertEquals(1, (long)trace.get(0).getVectorClock().get(1));
//        assertEquals(1, trace.get(0).getReplica());
//        assertEquals(0,trace.get(0).getNumberOf());
//        
//        assertEquals(SequenceOperation.OpType.del, trace.get(3).getType());
//        assertEquals(40, trace.get(3).getPosition());        
//        //assertEquals("", trace.get(3).getContentAsString());
//        assertEquals(4, (long)trace.get(3).getVectorClock().get(1));        
//        assertEquals(1, trace.get(3).getReplica());
//        assertEquals(137,trace.get(3).getNumberOf());
//        
//        assertEquals(SequenceOperation.OpType.up, trace.get(7).getType());
//        assertEquals(86, trace.get(7).getPosition());        
//        assertEquals("\nd", trace.get(7).getContentAsString());
//        assertEquals(8, (long)trace.get(7).getVectorClock().get(1));
//        assertEquals(1, trace.get(7).getReplica());
//        assertEquals(2,trace.get(7).getNumberOf());        
    }
    @Test
    public void testTraceFromJson2() throws Exception {        
        //List<SequenceOperation> trace = it2list(TraceGenerator.traceFromJson("../../traces/json/dirtyCSGerald.db","corrections001"));
        List<SequenceOperation> trace = it2list(TraceGenerator.traceFromJson("/home/damien/etherpad-lite/var/dirtyCS.db"));
        //assertEquals(21, trace.size());

        assertEquals(SequenceOperation.OpType.ins, trace.get(0).getType());
        assertEquals(0, trace.get(0).getPosition());        
        assertEquals("f", trace.get(0).getContentAsString());
        assertEquals(1, (long)trace.get(0).getVectorClock().get(1));
        assertEquals(1, trace.get(0).getReplica());
        assertEquals(0,trace.get(0).getNumberOf());
    }
}
