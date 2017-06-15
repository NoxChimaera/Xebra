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

import com.github.noxchimaera.attributes.readers.PinnedXmlAttributeReader;
import com.github.noxchimaera.attributes.readers.XmlAttributeReader;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author Max Balushkin
 */
public class SimpleXmlNodeReader<TNode> implements XmlNodeReader<TNode> {

    private String tag;
    private Supplier<TNode> supplier;
    private List<PinnedXmlAttributeReader<TNode, ?>> attributes;
    private List<PinnedXmlNodeReader<TNode, ?>> children;

    public SimpleXmlNodeReader(String tag, Supplier<TNode> supplier) {
        this.tag = tag;
        this.supplier = supplier;
        attributes = new ArrayList<>();
        children = new ArrayList<>();
    }

    public <TAttr>
    SimpleXmlNodeReader<TNode> attribute(XmlAttributeReader<TAttr> reader, BiConsumer<TNode, TAttr> setter) {
        return attribute(reader.pinned(setter));
    }

    public <TAttr>
    SimpleXmlNodeReader<TNode> attribute(PinnedXmlAttributeReader<TNode, TAttr> reader) {
        attributes.add(reader);
        return this;
    }

    public <TChild>
    SimpleXmlNodeReader<TNode> child(XmlNodeReader<TChild> reader, BiConsumer<TNode, TChild> setter) {
        return child(reader.pinned(setter));
    }

    public <TChild>
    SimpleXmlNodeReader<TNode> child(PinnedXmlNodeReader<TNode, TChild> reader) {
        children.add(reader);
        return this;
    }

    @Override public String tag() {
        return tag;
    }

    @Override
    public TNode read(Element xmlElement, ReadStrategy strategy) {
        TNode target = supplier.get();
        Element element = strategy.getElement(tag, xmlElement);
        if (element == null) {
            return null;
        }
        for (PinnedXmlAttributeReader<TNode, ?> attribute : attributes) {
            attribute.read(element, target);
        }
        for (PinnedXmlNodeReader<TNode, ?> child : children) {
            child.read(element, target);
        }
        return target;
    }

}
