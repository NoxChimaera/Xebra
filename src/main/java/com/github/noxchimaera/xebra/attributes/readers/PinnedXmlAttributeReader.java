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

package com.github.noxchimaera.xebra.attributes.readers;

import org.w3c.dom.Element;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * XML attribute reader pinned to object.
 *
 * @param <TObject> object type
 * @param <TAttr>   attribute type
 * @author Max Balushkin
 */
public class PinnedXmlAttributeReader<TObject, TAttr> {

    private XmlAttributeReader<TAttr> reader;
    private BiConsumer<TObject, TAttr> setter;

    /**
     * Create new instance of pinned XML attribute reader.
     *
     * @param name   attribute name
     * @param mapper converts from String to specified type
     * @param setter sets attribute of object
     */
    public PinnedXmlAttributeReader(String name, Function<String, TAttr> mapper, BiConsumer<TObject, TAttr> setter) {
        this(new SimpleXmlAttributeReader<>(name, mapper), setter);
    }

    /**
     * Creates new instance of pinned XML attribute reader.
     *
     * @param reader free attribute reader
     * @param setter sets attribute of object
     */
    public PinnedXmlAttributeReader(XmlAttributeReader<TAttr> reader, BiConsumer<TObject, TAttr> setter) {
        this.reader = reader;
        this.setter = setter;
    }

    /**
     * Reads attribute of specified XML node.
     *
     * @param xmlElement XML node
     * @param target     object
     */
    public void read(Element xmlElement, TObject target) {
        TAttr attr = reader.read(xmlElement);
        setter.accept(target, attr);
    }

    /**
     * Returns unpinned attribute reader.
     *
     * @return free attribute reader
     */
    public XmlAttributeReader<TAttr> unpinned() {
        return reader;
    }

}
