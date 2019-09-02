package org.emgen.httpx.extensions

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class URLEncodingExtensionsSpec extends Specification {

    void "URLEncodingExtensionsSpec.class cannot be instantiated and throws InstantiationError"() {
        when:
        URLEncodingExtensions.newInstance()

        then:
        thrown(InstantiationError)
    }

    void "Given no string, function encode() returns null"() {
        expect:
        URLEncodingExtensions.encode(null) == null
        URLEncodingExtensions.encode(null, StandardCharsets.US_ASCII.name()) == null
    }

    void "Given unsupported charset, function encode() does not encode string"() {
        expect:
        URLEncodingExtensions.encode("1 7", "unsupported-charset") == "1 7"
    }

    void "Function encode() encodes string as in URL"() {
        expect:
        URLEncodingExtensions.encode("1 7") == "1+7"
    }

    void "Given no string, function decode() returns null"() {
        expect:
        URLEncodingExtensions.decode(null) == null
        URLEncodingExtensions.decode(null, StandardCharsets.US_ASCII.name()) == null
    }

    void "Given unsupported charset, function decode() does not decode string"() {
        expect:
        URLEncodingExtensions.decode("1+7", "unsupported-charset") == "1+7"
    }

    void "Function decode() decodes URL string"() {
        expect:
        URLEncodingExtensions.decode("1+7") == "1 7"
    }
}
