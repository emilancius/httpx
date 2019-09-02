package org.emgen.httpx.extensions

import spock.lang.Specification

class ListExtensionsSpec extends Specification {

    void "ListExtensions.class cannot be instantiated and throws InstantiationError"() {
        when:
        ListExtensions.newInstance()

        then:
        thrown(InstantiationError)
    }

    void "Given no list, function isEmpty() returns true"() {
        expect:
        ListExtensions.isEmpty(null)
    }

    void "Given empty list, function isEmpty() returns true"() {
        expect:
        ListExtensions.isEmpty(new ArrayList())
    }

    void "Given non empty list, function isEmpty() returns false"() {
        expect:
        !ListExtensions.isEmpty(Collections.singletonList(0))
    }

    void "Given no components, function create() returns empty list"() {
        expect:
        ListExtensions.create().size() == 0
        ListExtensions.create(null).size() == 0
    }

    void "Given components, function create(), returns list, that contains those components"() {
        given:
        List<Integer> list = ListExtensions.create(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        expect:
        list.size() == 10

        for (int n = 0; n < list.size(); n++) {
            list.get(n) == n
        }
    }
}
