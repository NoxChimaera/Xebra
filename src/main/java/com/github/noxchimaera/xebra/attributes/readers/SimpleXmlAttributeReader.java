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

import java.util.function.Function;

/**
 * Reads XML node attribute.
 * Default implementation of {@link XmlAttributeReader}.
 *
 * @param <TAttr> attribute Java type
 * @author Max Balushkin
 */
public class SimpleXmlAttributeReader<TAttr> implements XmlAttributeReader<TAttr> {

    private String name;
    private Function<String, TAttr> mapper;

    /**
     * Creates new instance of XML attribute reader.
     *
     * @param name   attribute name
     * @param mapper converts from String to specified type
     */
    public SimpleXmlAttributeReader(String name, Function<String, TAttr> mapper) {
        this.name = name;
        this.mapper = mapper;
    }

    @Override
    public TAttr read(Element xmlElement) {
        return mapper.apply(xmlElement.getAttribute(name));
    }

}
