package com.lakruwan.servletFilter;

import com.lakruwan.servletFilter.chain.Handler;
import com.lakruwan.servletFilter.chain.JspValidationFilter;
import com.lakruwan.servletFilter.chain.ParameterNameFilter;
import com.lakruwan.servletFilter.chain.ParameterValueFilter;
import com.lakruwan.servletFilter.model.Request;

/**
 * Main application class demonstrating the Servlet Filter Mechanism
 * implemented with the Chain of Responsibility Design Pattern.
 *
 * This fulfills Practical 05 for OODP 2.
 */
public class ServletFilterApp {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("   Practical 05: Chain of Responsibility Design Pattern");
        System.out.println("   Servlet Filter Simulation Demonstration");
        System.out.println("======================================================================");

        // 1. Create Handlers (Filters)
        Handler filter1 = new JspValidationFilter();
        Handler filter2 = new ParameterNameFilter();
        Handler filter3 = new ParameterValueFilter("abc", "123");

        // 2. Link the Chain: Filter 1 -> Filter 2 -> Filter 3
        filter1.setHandler(filter2);
        filter2.setHandler(filter3);

        // 3. Test Scenarios
        System.out.println("\n--------------------------------------------------");
        System.out.println("TEST CASE 1: Valid Request (Matches Practical Diagram)");
        System.out.println("URL: 'www.abc.com/a.jsp' | Params: 'username=abc&password=123'");
        System.out.println("--------------------------------------------------");
        Request req1 = new Request("www.abc.com/a.jsp", "username=abc&password=123");
        filter1.handle(req1);

        System.out.println("\n--------------------------------------------------");
        System.out.println("TEST CASE 2: Invalid Resource Extension (Filter 1 Fails)");
        System.out.println("URL: 'www.abc.com/index.html' | Params: 'username=abc&password=123'");
        System.out.println("--------------------------------------------------");
        Request req2 = new Request("www.abc.com/index.html", "username=abc&password=123");
        filter1.handle(req2);

        System.out.println("\n--------------------------------------------------");
        System.out.println("TEST CASE 3: Missing Required Parameter Name (Filter 2 Fails)");
        System.out.println("URL: 'www.abc.com/a.jsp' | Params: 'username=abc'");
        System.out.println("--------------------------------------------------");
        Request req3 = new Request("www.abc.com/a.jsp", "username=abc");
        filter1.handle(req3);

        System.out.println("\n--------------------------------------------------");
        System.out.println("TEST CASE 4: Invalid Parameter Values (Filter 3 Fails)");
        System.out.println("URL: 'www.abc.com/a.jsp' | Params: 'username=abc&password=wrongpass'");
        System.out.println("--------------------------------------------------");
        Request req4 = new Request("www.abc.com/a.jsp", "username=abc&password=wrongpass");
        filter1.handle(req4);

        System.out.println("\n======================================================================");
        System.out.println("   End of Demonstration");
        System.out.println("======================================================================");
    }
}
