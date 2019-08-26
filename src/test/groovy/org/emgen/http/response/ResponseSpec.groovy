package org.emgen.http.response

import spock.lang.Specification

class ResponseSpec extends Specification {

    void "Given response, that has no body, function bodyBytesCount() returns 0"() {
        expect:
        new Response(200, "OK", null, Collections.emptyMap(), 0).bodyBytesCount() == 0
    }

    void "Given response, that has empty body, function bodyBytesCount() returns 0"() {
        expect:
        new Response(200, "OK", "", Collections.emptyMap(), 0).bodyBytesCount() == 0
    }

    void "Given response, that has non - empty body, function bodyByteCount() returns body length in bytes, using UTF-8 character encoding"() {
        new Response(200, "OK", "\"id\":\"0\"", Collections.emptyMap(), 0).bodyBytesCount() > 0
    }

    void "Given response, that has no headers, function headersBytesCount() returns 0"() {
        expect:
        new Response(200, "OK", null, null, 0).headersBytesCount() == 0
    }

    void "Given response, that has empty headers map, function headersBytesCount() returns 0"() {
        expect:
        new Response(200, "OK", null, Collections.emptyMap(), 0).headersBytesCount() == 0
    }

    void "Given response, that has non - empty headers map, function headersBytesCount() returns headers length in bytes, using UTF-8 character encoding"() {
        setup:
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", Collections.singletonList("application/json"));

        expect:
        new Response(200, "OK", null, headers, 0).headersBytesCount() > 0
    }
}
