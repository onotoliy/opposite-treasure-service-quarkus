package com.github.onotoliy.opposite.treasure;

import com.github.onotoliy.opposite.treasure.dto.data.Event;
import com.github.onotoliy.opposite.treasure.dto.data.core.Option;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class EventResourceTest {
    @Test
    public void test() {

            given()
                .when()
                .get("/event/list")
                .then()
                .statusCode(200)
                .body(is("[]"));

//        given()
//            .body(new Event(
//                UUID.randomUUID(),
//                "String name",
//                BigDecimal.ONE,
//                BigDecimal.ONE,
//                Instant.now(),
//                Instant.now(),
//                null,
//                null
//            ))
//            .when()
//            .post("/event")
//            .then()
//            .statusCode(200)
//            .body(is("{}"));
        }

}
