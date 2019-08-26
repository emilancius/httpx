package org.emgen.extensions

import spock.lang.Specification

class StringExtensionsSpec extends Specification {

    void "Given no 'string' parameter, function startsAsAny() returns false"() {
        expect:
        !StringExtensions.startsAsAny(null)
        !StringExtensions.startsAsAny(null, "sequence A", "sequence B")
    }

    void "Given empty 'string' parameter, function startsAsAny() returns false"() {
        expect:
        !StringExtensions.startsAsAny("")
        !StringExtensions.startsAsAny("", "sequence A", "sequence B")
        !StringExtensions.startsAsAny(" ")
        !StringExtensions.startsAsAny(" ", "sequence A", "sequence B")
    }

    void "Given non - empty 'string' parameter & no 'characterSequences' parameter(s), function startsAsAny() returns false"() {
        expect:
        !StringExtensions.startsAsAny("string to test")
    }

    void "Given non - empty 'string' parameter & non - empty 'characterSequences' parameter(s), so that 'string' does not start as any sequence, function startsAsAny() returns false"() {
        expect:
        !StringExtensions.startsAsAny("string to test", "sequence A", "sequence B")
    }

    void "Given non - empty 'string' parameter & non - empty 'characterSequences' parameter(s), so that 'string' starts as at least one sequence, function startsAsAny() returns true"() {
        expect:
        StringExtensions.startsAsAny("string to test", "sequence A", "sequence B", "string")
    }

    void "Given no 'string' parameter, function isEmpty() returns true"() {
        expect:
        StringExtensions.isEmpty(null)
    }

    void "Given empty 'string' parameter, function isEmpty() returns true"() {
        expect:
        StringExtensions.isEmpty("")
        StringExtensions.isEmpty(" ")
    }

    void "Given non - empty 'string' parameter, function isEmpty() returns false"() {
        expect:
        !StringExtensions.isEmpty("string to test")
    }
}
