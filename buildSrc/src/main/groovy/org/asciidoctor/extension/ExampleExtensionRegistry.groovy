package org.asciidoctor.extension

import org.asciidoctor.Asciidoctor
import org.asciidoctor.extension.spi.ExtensionRegistry

class ExampleExtensionRegistry implements ExtensionRegistry {
    void register(Asciidoctor asciidoctor) {
        asciidoctor.javaExtensionRegistry().block 'pig-latin', PigLatinBlock
    }
}