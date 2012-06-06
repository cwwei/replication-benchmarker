/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crdt.tree.orderedtree;

import collect.OrderedNode;
import java.util.List;
import jbenchmarker.logoot.LogootDocument;
import jbenchmarker.logoot.LogootIdentifier;
import jbenchmarker.logoot.LogootStrategy;

/**
 *
 * @author urso
 */
class LogootNode<T> extends LogootDocument<LogootNode<T>> implements OrderedNode<T> {
    private final T value;
    
    public LogootNode(T value, int r, int nbBit, LogootStrategy strategy) {
        super(r, nbBit, strategy);
        this.value = value;
    }
    
    @Override
    public T getValue() {
        return value;
    }
    
    @Override
    public int childrenNumber() {
        return document.size()-2; 
    }

    @Override
    public OrderedNode<T> getChild(int p) {
        return document.get(p+1);
    }

    @Override
    public OrderedNode<T> getChild(Positioned<T> p) {
        return document.get(dicho((LogootIdentifier)p.getPi()));
    }

    @Override
    public Positioned<T> getPositioned(int p) {
        return new Positioned<T>(idTable.get(p+1), document.get(p+1).getValue());
    }
    
    @Override
    public PositionIdentifier getNewPosition(int p, T element) {
        return getNewId(p);
    }

    @Override
    public void add(PositionIdentifier id, T elem) {
         int pos = dicho((LogootIdentifier)id);
         idTable.add(pos, (LogootIdentifier)id);
         document.add(pos, createNode(elem));       
    }
    
    @Override
    public void remove(PositionIdentifier id, T elem) {
         int pos = dicho((LogootIdentifier)id);
         idTable.remove(pos);
         document.remove(pos);       
    }

    @Override
    public List<LogootNode<T>> getElements() {
        return document.subList(1, document.size()-1);
    }

    @Override
    public LogootNode<T> createNode(T elem) {
        return new LogootNode<T>(elem, replicaNumber, nbBit, strategy);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogootNode<T> other = (LogootNode<T>) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }
}