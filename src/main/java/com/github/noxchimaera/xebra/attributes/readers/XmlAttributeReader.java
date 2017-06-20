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

/**
 * Reads XML node attribute.
 *
 * @param <TAttr> attribute Java type
 * @author Max Balushkin
 */
public interface XmlAttributeReader<TAttr> {

    /**
     * Reads attribute of specified XML node.
     *
     * @param xmlElement XML node
     * @return attribute value
     */
    TAttr read(Element xmlElement);

    /**
     * Creates pinned attribute reader.
     *
     * @param setter  sets attribute of object
     * @param <TNode> object type
     * @return pinned read
     */
    default <TNode> PinnedXmlAttributeReader<TNode, TAttr> pinned(BiConsumer<TNode, TAttr> setter) {
        return new PinnedXmlAttributeReader<>(this, setter);
    }

}
