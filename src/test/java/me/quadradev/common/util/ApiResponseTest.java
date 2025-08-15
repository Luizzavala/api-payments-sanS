package me.quadradev.common.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void okShouldBuildSuccessfulResponse() {
        ApiResponse<String> response = ApiResponse.ok("message", "data");
        assertTrue(response.isSuccess());
        assertEquals(200, response.getStatus());
        assertEquals("message", response.getMessage());
        assertEquals("data", response.getData());
        assertNull(response.getError());
    }

    @Test
    void createdShouldBuildCreatedResponse() {
        ApiResponse<String> response = ApiResponse.created("created", "id");
        assertTrue(response.isSuccess());
        assertEquals(201, response.getStatus());
        assertEquals("created", response.getMessage());
        assertEquals("id", response.getData());
        assertNull(response.getError());
    }

    @Test
    void errorShouldBuildErrorResponse() {
        Object details = new Object();
        ApiResponse<String> response = ApiResponse.error(400, "bad", details);
        assertFalse(response.isSuccess());
        assertEquals(400, response.getStatus());
        assertEquals("bad", response.getMessage());
        assertNull(response.getData());
        assertSame(details, response.getError());
    }
}
