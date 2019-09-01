package org.emgen.httpx.http.request

import org.emgen.httpx.http.exceptions.RequestCreationException
import spock.lang.Specification

class RequestSpec extends Specification {

    void "Given no 'target' parameter for request creation, function create() throws RequestCreationException"() {
        setup:
        Request.Creator creator = new Request.Creator().action(RequestAction.GET)

        when:
        creator.create()

        then:
        thrown(RequestCreationException)
    }

    void "Given empty 'target' parameter for request creation, function create() throws RequestCreationException"() {
        setup:
        Request.Creator creator = new Request.Creator().target("").action(RequestAction.GET)

        when:
        creator.create()

        then:
        thrown(RequestCreationException)
    }

    void "Given 'target' parameter, that does not start as neither http:// nor https:// protocol for request creation, function create() throws RequestCreationException"() {
        setup:
        Request.Creator creator = new Request.Creator().target("test.org").action(RequestAction.GET)

        when:
        creator.create()

        then:
        thrown(RequestCreationException)
    }

    void "Given no 'action' parameter for request creation, function create() throws RequestCreationException"() {
        setup:
        Request.Creator creator = new Request.Creator().target("https://test.org")

        when:
        creator.create()

        then:
        thrown(RequestCreationException)
    }

    void "Given non - empty 'target' parameter, that starts as http:// or https:// protocol & 'action' parameter for request creation, function create() returns created request"() {
        setup:
        Request.Creator creatorA = new Request.Creator()
                .target("https://target.org")
                .action(RequestAction.GET)
        Request.Creator creatorB = new Request.Creator()
                .target("http://target.org")
                .action(RequestAction.GET)

        when:
        Request requestA = creatorA.create()
        Request requestB = creatorB.create()

        then:
        notThrown(RequestCreationException)
        requestA != null
        requestB != null
    }

    void "Given request, that has no body, function sendsBody() returns false"() {
        expect:
        !new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.POST)
                .create()
                .sendsBody()
    }

    void "Given request, that has empty body, function sendsBody() returns false"() {
        expect:
        !new Request.Creator()
                .target("https://test.org")
                .body("")
                .action(RequestAction.POST)
                .create()
                .sendsBody()
        !new Request.Creator()
                .target("https://test.org")
                .body(" ")
                .action(RequestAction.POST)
                .create()
                .sendsBody()
    }

    void "Given request, that contains body & has 'action' parameter, that does not support request body, function sendsBody() returns false"() {
        expect:
        !new Request.Creator()
                .target("https://test.org")
                .body("\"id\":\"0\"")
                .action(RequestAction.GET)
                .create()
                .sendsBody()
    }

    void "Given request, that contains body & that has 'action' parameter, that supports request body, function sendsBody() returns true"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .body("\"id\":\"0\"")
                .action(RequestAction.POST)
                .create()
                .sendsBody()
    }

    void "Given no 'name' parameter, function parameter() ignores query parameter & is not inserted to parameter map"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameter(null, "0")
                .create()
                .parameters()
                .size() == 0
    }

    void "Given empty 'name' parameter, function parameter() ignores query parameter & is not inserted to parameter map"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameter("", "0")
                .create()
                .parameters()
                .size() == 0
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameter(" ", "0")
                .create()
                .parameters()
                .size() == 0
    }

    void "Given 'name' & 'value' parameters, function parameter() inserts query parameter into parameter map"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameter("id", "0")
                .create()
                .parameters()
                .get("id")
                .size() == 1
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameter("types", "A")
                .parameter("types", "B")
                .create()
                .parameters()
                .get("types")
                .size() == 2
    }

    void "In case parameter's map is not instantiated, function parameter() instantiates it"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .parameters(null)
                .parameter("id", "0")
                .create()
                .parameters() != null
    }

    void "Given any 'name' and 'value' parameters, function header() inserts header into header map"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .header("Content-Type", "application/json")
                .create()
                .headers()
                .get("Content-Type")
                .size() == 1
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .header("Accept-Language", "en-US")
                .header("Accept-Language", "q=0.9")
                .create()
                .headers()
                .get("Accept-Language")
                .size() == 2
    }

    void "In case header's map is not instantiated, function header() instantiates it"() {
        expect:
        new Request.Creator()
                .target("https://test.org")
                .action(RequestAction.GET)
                .headers(null)
                .header("Content-Type", "application/json")
                .create()
                .headers() != null
    }
}
