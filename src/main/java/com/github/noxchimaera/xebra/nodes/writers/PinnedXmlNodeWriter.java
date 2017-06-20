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

import java.util.function.Function;

/**
 * Pinned XML node writer.
 *
 * @author Max Balushkin
 */
public class PinnedXmlNodeWriter<TParent, TNode> {

    private XmlNodeWriter<TNode> writer;
    private Function<TParent, TNode> getter;

    /**
     * Creates new instance of pinned XML node writer.
     *
     * @param writer free node writer
     * @param getter gets child object from source
     */
    public PinnedXmlNodeWriter(XmlNodeWriter<TNode> writer, Function<TParent, TNode> getter) {
        this.writer = writer;
        this.getter = getter;
    }

    /**
     * Writes object to XML node.
     *
     * @param doc        XML document
     * @param xmlElement XML node
     * @param parent     parent object
     */
    public void write(Document doc, Element xmlElement, TParent parent) {
        TNode child = getter.apply(parent);
        writer.write(doc, xmlElement, child);
    }

}
