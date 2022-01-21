package io.leaderli.litil.dom;

import org.dom4j.dom.DOMElement;

public interface Visitor {

    void visit(DOMElement dom);
}
