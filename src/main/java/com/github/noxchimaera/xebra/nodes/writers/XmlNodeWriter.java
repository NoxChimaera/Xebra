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
 * Writes object to XML node.
 *
 * @author Max Balushkin
 */
public interface XmlNodeWriter<TNode> {

    /**
     * Returns XML node tag.
     *
     * @return XML node tag
     */
    String tag();

    /**
     * Writes object to XML node.
     *
     * @param doc        XML document
     * @param xmlElement XML node
     * @param node       object
     */
    void write(Document doc, Element xmlElement, TNode node);

    /**
     * Creates pinned node writer.
     *
     * @param getter    gets value from parent object
     * @param <TParent> parent object type
     * @return pinned node writer
     */
    default <TParent> PinnedXmlNodeWriter<TParent, TNode> pinned(Function<TParent, TNode> getter) {
        return new PinnedXmlNodeWriter<>(this, getter);
    }

}
