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

import org.w3c.dom.Element;

import java.util.function.BiConsumer;

/**
 * @author Max Balushkin
 */
public interface XmlNodeReader<TNode> {

    String tag();
    TNode read(Element xmlElement, ReadStrategy strategy);

    default <TParent> PinnedXmlNodeReader<TParent, TNode> pinned(BiConsumer<TParent, TNode> setter) {
        return new PinnedXmlNodeReader<>(this, setter);
    }

}
