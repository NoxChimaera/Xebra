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

package com.github.noxchimaera.nodes.writers;

import com.github.noxchimaera.attributes.writers.PinnedXmlAttributeWriter;
import com.github.noxchimaera.attributes.writers.XmlAttributeWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Writes object to XML node.
 * Default implementation of {@link XmlNodeWriter}.
 *
 * @author Max Balushkin
 */
public class SimpleXmlNodeWriter<TNode> implements XmlNodeWriter<TNode> {

    private String tag;
    private List<PinnedXmlAttributeWriter<TNode, ?>> attributes;
    private List<PinnedXmlNodeWriter<TNode, ?>> children;

    /**
     * Creates new instance of XML node writer.
     *
     * @param tag XML node tag
     */
    public SimpleXmlNodeWriter(String tag) {
        this.tag = tag;
        attributes = new ArrayList<>();
        children = new ArrayList<>();
    }

    /**
     * Composes this writer with specified attribute writer.
     *
     * @param writer  attribute writer
     * @param getter  gets attribute value from object
     * @param <TAttr> attribute type
     * @return self
     */
    public <TAttr>
    SimpleXmlNodeWriter<TNode> attribute(XmlAttributeWriter<TAttr> writer, Function<TNode, TAttr> getter) {
        return attribute(writer.pinned(getter));
    }

    /**
     * Composes this writer with specified attribute writer.
     *
     * @param writer  pinned attribute writer
     * @param <TAttr> attribute type
     * @return self
     */
    public <TAttr>
    SimpleXmlNodeWriter<TNode> attribute(PinnedXmlAttributeWriter<TNode, TAttr> writer) {
        attributes.add(writer);
        return this;
    }

    /**
     * Composes this node writer with specified node writer (as child).
     *
     * @param writer   node writer
     * @param getter   gets child object from object
     * @param <TChild> child object type
     * @return self
     */
    public <TChild>
    SimpleXmlNodeWriter<TNode> child(XmlNodeWriter<TChild> writer, Function<TNode, TChild> getter) {
        return child(writer.pinned(getter));
    }

    /**
     * Composes this node writer with specified node writer (as child).
     *
     * @param writer   pinned node writer
     * @param <TChild> child object type
     * @return self
     */
    public <TChild>
    SimpleXmlNodeWriter<TNode> child(PinnedXmlNodeWriter<TNode, TChild> writer) {
        children.add(writer);
        return this;
    }

    @Override public String tag() {
        return tag;
    }

    @Override
    public void write(Document doc, Element xmlElement, TNode node) {
        Element element = doc.createElement(tag);
        for (PinnedXmlAttributeWriter<TNode, ?> attribute : attributes) {
            attribute.write(element, node);
        }
        for (PinnedXmlNodeWriter<TNode, ?> child : children) {
            child.write(doc, element, node);
        }
        xmlElement.appendChild(element);
    }

}
