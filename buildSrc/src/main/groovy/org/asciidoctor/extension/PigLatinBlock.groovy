package org.asciidoctor.extension

import org.asciidoctor.extension.BlockProcessor
import org.asciidoctor.ast.AbstractBlock
import org.asciidoctor.extension.Reader

class PigLatinBlock extends BlockProcessor {
    private static final String VOWELS = 'AEIOUaeiou'
    private static final String VOWELSY = 'AEIOUaeiouYy'

    PigLatinBlock(String name, Map<String, Object> config) {
        super(name, [contexts: [':paragraph'], content_model: ':simple'])
    }

    def process(AbstractBlock parent, Reader reader, Map<String, Object> attributes) {
        createBlock(parent, 'paragraph', toPigLatin(reader), attributes, [:])
    }

    def toPigLatin(Reader reader) {
        reader.lines().collect { line ->
            line.split(/\W/).collect { String word -> toPigLatin(word) }.join(' ')
        }.join('')
    }

    def toPigLatin(String word) {
        if (VOWELS.contains(word[0])) return word + 'way'

        String suffix = ''
        String previous = ''
        for (c in word) {
            if (VOWELSY.contains(c)) {
                if (previous.toLowerCase() == 'q' && c.toLowerCase() == 'u') {
                    suffix += c // moves 'qu' at the end
                }
                break
            }
            suffix += c
            previous = c
        }

        word = word[suffix.size()..-1]
        if (!Character.isLowerCase(suffix[0] as char)) word = word.capitalize()
        word + suffix.toLowerCase() + 'ay'
    }
}