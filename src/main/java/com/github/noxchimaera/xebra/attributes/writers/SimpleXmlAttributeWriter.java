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
 * Writes XML node attribute.
 * Default implementation of {@link XmlAttributeWriter}.
 *
 * @param <TAttr> attribute Java type
 * @author Max Balushkin
 */
public class SimpleXmlAttributeWriter<TAttr> implements XmlAttributeWriter<TAttr> {

    private String name;
    private Function<TAttr, String> mapper;

    /**
     * Creates new instance of XML attribute writer.
     *
     * @param name   attribute name
     * @param mapper converts attribute value to String
     */
    public SimpleXmlAttributeWriter(String name, Function<TAttr, String> mapper) {
        this.name = name;
        this.mapper = mapper;
    }

    @Override
    public void write(Element xmlElement, TAttr value) {
        xmlElement.setAttribute(name, mapper.apply(value));
    }

}
