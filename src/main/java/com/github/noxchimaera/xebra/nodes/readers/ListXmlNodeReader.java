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

package com.github.noxchimaera.xebra.nodes.readers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Balushkin
 */
public class ListXmlNodeReader<TNode> implements XmlNodeReader<List<TNode>> {

    private XmlNodeReader<TNode> reader;

    public ListXmlNodeReader(XmlNodeReader<TNode> reader) {
        this.reader = reader;
    }

    @Override public String tag() {
        return reader.tag();
    }

    @Override
    public List<TNode> read(Element xmlElement, ReadStrategy strategy) {
        Element element = xmlElement;
        // Element element = strategy.getElement(tag(), xmlElement);
        // if (element == null) {
        //     return null;
        // }
        List<TNode> result = new ArrayList<>();
        NodeList lst = element.getChildNodes();
        for (int i = 0, n = lst.getLength(); i < n; ++i) {
            if (lst.item(i).getNodeName().equals(tag())) {
                Element xmlChild = (Element)lst.item(i);
                TNode child = reader.read(xmlChild, ReadStrategy.Self);
                if (child != null) {
                    result.add(child);
                }
            }
        }
        return result;
    }

}
