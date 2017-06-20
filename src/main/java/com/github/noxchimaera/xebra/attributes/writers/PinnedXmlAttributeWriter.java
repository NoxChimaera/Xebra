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

package com.github.noxchimaera.xebra.attributes.writers;

import org.w3c.dom.Element;

import java.util.function.Function;

/**
 * XML attribute reader pinned to object.
 *
 * @param <TObject> object type
 * @param <TAttr>   attribute type
 * @author Max Balushkin
 */
public class PinnedXmlAttributeWriter<TObject, TAttr> {

    private XmlAttributeWriter<TAttr> writer;
    private Function<TObject, TAttr> getter;

    /**
     * Create new instance of pinned XML attribute writer.
     *
     * @param name   attribute name
     * @param mapper converts attribute value to String
     * @param getter gets attribute value from object
     */
    public PinnedXmlAttributeWriter(String name, Function<TAttr, String> mapper, Function<TObject, TAttr> getter) {
        this(new SimpleXmlAttributeWriter<>(name, mapper), getter);
    }

    /**
     * Creates new instance of pinned XML attribute writer.
     *
     * @param writer free attribute writer
     * @param getter gets attribute value from object
     */
    public PinnedXmlAttributeWriter(XmlAttributeWriter<TAttr> writer, Function<TObject, TAttr> getter) {
        this.writer = writer;
        this.getter = getter;
    }

    /**
     * Writes attribute to specified XML node.
     *
     * @param xmlElement XML node
     * @param source     object
     */
    public void write(Element xmlElement, TObject source) {
        writer.write(xmlElement, getter.apply(source));
    }

    /**
     * Creates unpinned XML attribute writer.
     *
     * @return free attribute writer
     */
    public XmlAttributeWriter<TAttr> unpinned() {
        return writer;
    }

}
