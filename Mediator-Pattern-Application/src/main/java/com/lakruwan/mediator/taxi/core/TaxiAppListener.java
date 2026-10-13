package com.lakruwan.mediator.taxi.core;

import com.lakruwan.mediator.taxi.colleague.Driver;

/**
 * Observer / Listener interface for dispatch events in the Taxi App Center.
 */
public interface TaxiAppListener {
    void onLog(String message);
    void onDriverStatusChanged(Driver driver);
}
