package com.lakruwan.servletFilter;

import com.lakruwan.servletFilter.chain.FilterCallback;
import com.lakruwan.servletFilter.chain.JspValidationFilter;
import com.lakruwan.servletFilter.chain.ParameterNameFilter;
import com.lakruwan.servletFilter.chain.ParameterValueFilter;
import com.lakruwan.servletFilter.model.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterChainTest {

    private JspValidationFilter filter1;
    private ParameterNameFilter filter2;
    private ParameterValueFilter filter3;

    private List<String> successList;
    private List<String> failureList;
    private List<String> completedList;

    @BeforeEach
    void setUp() {
        successList = new ArrayList<>();
        failureList = new ArrayList<>();
        completedList = new ArrayList<>();

        FilterCallback testCallback = new FilterCallback() {
            @Override
            public void onFilterSuccess(String filterName, String message) {
                successList.add(filterName);
            }

            @Override
            public void onFilterFailure(String filterName, String errorMessage) {
                failureList.add(filterName);
            }

            @Override
            public void onRequestComplete(String targetUrl, String message) {
                completedList.add(targetUrl);
            }
        };

        filter1 = new JspValidationFilter(testCallback);
        filter2 = new ParameterNameFilter(testCallback);
        filter3 = new ParameterValueFilter("abc", "123", testCallback);

        // Chain linking
        filter1.setHandler(filter2);
        filter2.setHandler(filter3);
    }

    @Test
    @DisplayName("Should successfully pass through all 3 filters when request is valid")
    void testValidRequestPassesAllFilters() {
        Request request = new Request("www.abc.com/a.jsp", "username=abc&password=123");
        filter1.handle(request);

        assertEquals(3, successList.size(), "All 3 filters should have succeeded");
        assertTrue(failureList.isEmpty(), "No filters should have failed");
        assertEquals(1, completedList.size(), "Request should be completed");
        assertEquals("www.abc.com/a.jsp", completedList.get(0));
    }

    @Test
    @DisplayName("Should fail at Filter 1 when URL is not a .jsp resource")
    void testInvalidUrlFailsAtFilter1() {
        Request request = new Request("www.abc.com/index.html", "username=abc&password=123");
        filter1.handle(request);

        assertEquals(1, failureList.size());
        assertTrue(failureList.get(0).contains("Filter 1"));
        assertTrue(successList.isEmpty());
        assertTrue(completedList.isEmpty());
    }

    @Test
    @DisplayName("Should pass Filter 1 but fail at Filter 2 when 'password' parameter is missing")
    void testMissingParameterFailsAtFilter2() {
        Request request = new Request("www.abc.com/a.jsp", "username=abc");
        filter1.handle(request);

        assertEquals(1, successList.size());
        assertTrue(successList.get(0).contains("Filter 1"));

        assertEquals(1, failureList.size());
        assertTrue(failureList.get(0).contains("Filter 2"));
        assertTrue(completedList.isEmpty());
    }

    @Test
    @DisplayName("Should pass Filter 1 and 2 but fail at Filter 3 when credentials do not match")
    void testWrongCredentialsFailsAtFilter3() {
        Request request = new Request("www.abc.com/a.jsp", "username=abc&password=999");
        filter1.handle(request);

        assertEquals(2, successList.size());
        assertTrue(successList.get(0).contains("Filter 1"));
        assertTrue(successList.get(1).contains("Filter 2"));

        assertEquals(1, failureList.size());
        assertTrue(failureList.get(0).contains("Filter 3"));
        assertTrue(completedList.isEmpty());
    }

    @Test
    @DisplayName("Request parameter parsing should handle URL query parameters correctly")
    void testRequestParameterParsing() {
        Request request = new Request("www.abc.com/a.jsp", "username=abc&password=123&role=admin");
        assertTrue(request.hasParameter("username"));
        assertTrue(request.hasParameter("password"));
        assertTrue(request.hasParameter("role"));
        assertEquals("abc", request.getParameter("username"));
        assertEquals("123", request.getParameter("password"));
        assertEquals("admin", request.getParameter("role"));
        assertFalse(request.hasParameter("unknown"));
    }
}
