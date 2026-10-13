package com.lakruwan.mediator;

/**
 * Mediator Interface for Air Traffic Control Tower.
 * Directs flight traffic and coordinates runway usage.
 */
public interface ATCTower {
    void registerPlane(Plane airplane);
    void requestTakeoff(Plane airplane);
    void requestLanding(Plane airplane);
}
