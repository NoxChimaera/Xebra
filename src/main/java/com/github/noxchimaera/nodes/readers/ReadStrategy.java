/*
 * Copyright 2016 Max Balushkin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.noxchimaera.nodes.readers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Specifies read strategy. E.g. first element in children list, sequential read, etc.
 *
 * @author Max Balushkin
 */
@FunctionalInterface
public interface ReadStrategy {

    /**
     * Returns current XML node.
     *
     * @param tag        node tag
     * @param xmlElement parent XML node
     * @return current XML node
     */
    Element getElement(String tag, Element xmlElement);

    /**
     * Returns argument node.
     */
    ReadStrategy Self = (tag, xmlElement) -> {
        return xmlElement;
    };

    /**
     * Returns first node in child list or null if node with such tag doesn't exists.
     */
    ReadStrategy Auto = (tag, xmlElement) -> {
        NodeList xmlChildren = xmlElement.getChildNodes();
        final int n = xmlChildren.getLength();
        for (int i = 0; i < n; ++i) {
            if (xmlChildren.item(i).getNodeName().equals(tag)) {
                return (Element)xmlChildren.item(i);
            }
        }
        return null;
    };

}
