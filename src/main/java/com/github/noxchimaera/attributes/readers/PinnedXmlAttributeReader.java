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

package com.github.noxchimaera.attributes.readers;

import com.github.noxchimaera.attributes.writers.SimpleXmlAttributeWriter;
import com.github.noxchimaera.attributes.writers.XmlAttributeWriter;
import org.w3c.dom.Element;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Max Balushkin
 */
public class PinnedXmlAttributeReader<TObject, TAttr> {

    private XmlAttributeReader<TAttr> reader;
    private BiConsumer<TObject, TAttr> setter;

    public PinnedXmlAttributeReader(String name, Function<String, TAttr> mapper, BiConsumer<TObject, TAttr> setter) {
        this(new SimpleXmlAttributeReader<>(name, mapper), setter);
    }

    public PinnedXmlAttributeReader(XmlAttributeReader<TAttr> reader, BiConsumer<TObject, TAttr> setter) {
        this.reader = reader;
        this.setter = setter;
    }

    public void read(Element xmlElement, TObject target) {
        TAttr attr = reader.read(xmlElement);
        setter.accept(target, attr);
    }

    public XmlAttributeReader<TAttr> unpinned() {
        return reader;
    }

}
