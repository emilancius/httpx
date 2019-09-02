package org.emgen.httpx.extensions

import org.emgen.httpx.http.exceptions.ArgumentExistenceException
import spock.lang.Specification

class MapExtensionsSpec extends Specification {

    void "MapExtensions.class cannot be instantiated and throws InstantiationError"() {
        when:
        MapExtensions.newInstance()

        then:
        thrown(InstantiationError)
    }

    void "Given no map, function isEmpty() returns true"() {
        expect:
        MapExtensions.isEmpty(null)
    }

    void "Given empty map, function isEmpty() returns true"() {
        expect:
        MapExtensions.isEmpty(Collections.emptyMap())
    }

    void "Given no map, function removeByPredicate() throws ArgumentExistenceException"() {
        when:
        MapExtensions.removeByPredicate(null) { key -> key == 0 }

        then:
        thrown(ArgumentExistenceException)
    }

    void "Given no predicate, function removeByPredicate() throws ArgumentExistenceException"() {
        when:
        MapExtensions.removeByPredicate(new HashMap<Integer, String>(), null)

        then:
        thrown(ArgumentExistenceException)
    }

    void "Given map and predicate, function removeByPredicate() returns map, that does not have entries, that tested as 'true' on predicate"() {
        given:
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "0")
        map.put(1, "1")

        expect:
        MapExtensions.removeByPredicate(map) { key -> key == 0 }.size() == 1
    }
}
