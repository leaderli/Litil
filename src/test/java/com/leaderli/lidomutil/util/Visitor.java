package com.leaderli.lidomutil.util;

import org.dom4j.dom.DOMElement;

public interface Visitor {

    void visit(DOMElement dom);
}
