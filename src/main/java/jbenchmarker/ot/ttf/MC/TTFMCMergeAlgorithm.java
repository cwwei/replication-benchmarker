/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbenchmarker.ot.ttf.MC;

import crdt.Factory;
import jbenchmarker.core.SequenceOperation;
import jbenchmarker.ot.soct2.OTAlgorithm;
import jbenchmarker.ot.soct2.SOCT2;
import jbenchmarker.ot.ttf.TTFMergeAlgorithm;
import jbenchmarker.ot.ttf.TTFOperation;
import jbenchmarker.ot.ttf.TTFVisibilityChar;

/**
 *
 * @author score
 */

public class TTFMCMergeAlgorithm extends TTFMergeAlgorithm<TTFOperation> {

    public TTFMCMergeAlgorithm(TTFMCDocument doc, int siteId, Factory<OTAlgorithm<TTFOperation>> otAlgo) {
        super(doc, siteId, otAlgo);
    }

    public TTFMCMergeAlgorithm(int siteId) {
        this(new TTFMCDocument(), siteId, new SOCT2<TTFOperation>(new TTFMCTransformations(), siteId, null));
    }

    public TTFMCMergeAlgorithm(Factory<OTAlgorithm<TTFOperation>> otAlgo) {
        this(new TTFMCDocument(), 0, otAlgo);
    }

    @Override
    public TTFMCDocument getDoc() {
        return (TTFMCDocument) super.getDoc();
    }

    @Override
    protected TTFOperation deleteOperation(int pos) {
        return new TTFOperation(SequenceOperation.OpType.delete, pos, null);
    }

    @Override
    protected TTFOperation insertOperation(int pos, Object content) {
        //Debug =============================
        int i = pos;
        boolean exit = false;
        if (this.getDoc().getModel().size() > i) {
            TTFVisibilityChar o = (TTFVisibilityChar) this.getDoc().getChar(i);
            while (!o.isVisible() && !exit) {
                if (o.getContent().toString().equals(content.toString())) {
                    nbrRedo++;
                    exit = true;

                }
                i++;
                o = (TTFVisibilityChar) this.getDoc().getChar(i);
            }
        }
        //=============================
        return new TTFOperation(SequenceOperation.OpType.insert, pos, content);
    }
}
