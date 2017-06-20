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

package com.github.noxchimaera.xebra.nodes.writers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author Max Balushkin
 */
public class ListXmlNodeWriter<TNode> implements XmlNodeWriter<List<TNode>> {

    private XmlNodeWriter<TNode> writer;

    public ListXmlNodeWriter(XmlNodeWriter<TNode> writer) {
        this.writer = writer;
    }

    @Override public String tag() {
        return writer.tag();
    }

    @Override
    public void write(Document doc, Element xmlElement, List<TNode> nodes) {
        for (TNode node : nodes) {
            writer.write(doc, xmlElement, node);
        }
    }

}
