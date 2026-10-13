package lk.jiat.taxi;

import com.lakruwan.mediator.taxi.TaxiServiceApp;

/**
 * Backward compatibility wrapper for JIAT Practical 08 submission.
 * Delegates to the refactored modular TaxiServiceApp.
 */
public class TaxiServiceMain {
    public static void main(String[] args) {
        TaxiServiceApp.main(args);
    }
}
