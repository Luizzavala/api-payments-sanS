package me.quadradev.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResponseTest {

    @Test
    void ofShouldMapPageFieldsCorrectly() {
        Page<String> page = new PageImpl<>(List.of("a", "b"), PageRequest.of(0, 2), 5);
        PageResponse<String> response = PageResponse.of(page);
        assertEquals(page.getContent(), response.content());
        assertEquals(0, response.page());
        assertEquals(2, response.size());
        assertEquals(5, response.totalElements());
        assertEquals(3, response.totalPages());
        assertFalse(response.last());
    }
}
