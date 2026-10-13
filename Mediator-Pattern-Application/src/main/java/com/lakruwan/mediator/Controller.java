package com.lakruwan.mediator;

import java.util.*;

/**
 * Concrete Mediator representing the Air Traffic Control (ATC) Controller.
 * Encapsulates and coordinates traffic coordination, runway assignments,
 * landing/takeoff queues, and collision prevention.
 */
public class Controller implements ATCTower {

    private final List<Plane> registeredPlanes = new ArrayList<>();
    private final List<ATCListener> listeners = new ArrayList<>();

    // Runways and current occupant (null if runway is available)
    private final Map<String, Plane> runwayStatus = new LinkedHashMap<>();

    // Holding queue for landing planes (airborne)
    private final Queue<Plane> landingQueue = new LinkedList<>();

    // Taxi queue for planes ready to take off (ground)
    private final Queue<Plane> takeoffQueue = new LinkedList<>();

    public Controller() {
        // Initialize standard international airport runways
        runwayStatus.put("Runway 01L", null);
        runwayStatus.put("Runway 02R", null);
    }

    public void addListener(ATCListener listener) {
        listeners.add(listener);
    }

    @Override
    public synchronized void registerPlane(Plane airplane) {
        if (!registeredPlanes.contains(airplane)) {
            registeredPlanes.add(airplane);
            notifyLog("TOWER", airplane.getCallsign() + " registered with Bandaranaike ATC Radar.", false);
            for (ATCListener l : listeners) {
                l.onPlaneRegistered(airplane);
            }
        }
    }

    @Override
    public synchronized void requestLanding(Plane airplane) {
        registerPlane(airplane);
        airplane.notifyATC("Requesting to land");

        String availableRunway = getAvailableRunway();

        if (availableRunway != null) {
            // Assign runway immediately
            runwayStatus.put(availableRunway, airplane);
            String instruction = "CLEARED TO LAND on " + availableRunway + ". Wind 270 at 12 knots.";
            notifyLog("TOWER -> " + airplane.getCallsign(), instruction, false);
            airplane.receiveATCInstruction(instruction);
            updateRunwayUI(availableRunway, "OCCUPIED (Landing)", airplane.getCallsign());
        } else {
            // Runways occupied: place in holding pattern
            if (!landingQueue.contains(airplane)) {
                landingQueue.offer(airplane);
            }
            String holdInstruction = "HOLD PATTERN: All runways occupied. Holding at 5000ft, position #" + landingQueue.size() + " in queue.";
            notifyLog("TOWER -> " + airplane.getCallsign(), holdInstruction, true);
            airplane.receiveATCInstruction(holdInstruction);
        }
    }

    @Override
    public synchronized void requestTakeoff(Plane airplane) {
        registerPlane(airplane);
        airplane.notifyATC("Requesting to take off");

        String availableRunway = getAvailableRunway();

        if (availableRunway != null && landingQueue.isEmpty()) {
            // Assign runway for takeoff
            runwayStatus.put(availableRunway, airplane);
            String instruction = "CLEARED FOR TAKEOFF on " + availableRunway + ". Climb and maintain 8000ft.";
            notifyLog("TOWER -> " + airplane.getCallsign(), instruction, false);
            airplane.receiveATCInstruction(instruction);
            updateRunwayUI(availableRunway, "OCCUPIED (Taking Off)", airplane.getCallsign());
        } else {
            // Runways occupied or landings prioritized
            if (!takeoffQueue.contains(airplane)) {
                takeoffQueue.offer(airplane);
            }
            String waitInstruction = "HOLD SHORT: Runway in use or landing traffic prioritized. Taxi queue #" + takeoffQueue.size() + ".";
            notifyLog("TOWER -> " + airplane.getCallsign(), waitInstruction, true);
            airplane.receiveATCInstruction(waitInstruction);
        }
    }

    /**
     * Called when a plane finishes landing or takeoff, freeing the runway.
     */
    public synchronized void clearRunway(String runwayName) {
        if (!runwayStatus.containsKey(runwayName)) {
            return;
        }

        Plane previousPlane = runwayStatus.get(runwayName);
        runwayStatus.put(runwayName, null);

        if (previousPlane != null) {
            notifyLog("TOWER", runwayName + " is now VACATED and CLEAR (previously " + previousPlane.getCallsign() + ").", false);
        } else {
            notifyLog("TOWER", runwayName + " marked clear.", false);
        }

        updateRunwayUI(runwayName, "AVAILABLE", "None");

        // Process waiting queue (Landings receive priority over takeoffs)
        if (!landingQueue.isEmpty()) {
            Plane nextLanding = landingQueue.poll();
            notifyLog("TOWER", "Dispatching next landing from holding pattern: " + nextLanding.getCallsign(), false);
            requestLanding(nextLanding);
        } else if (!takeoffQueue.isEmpty()) {
            Plane nextTakeoff = takeoffQueue.poll();
            notifyLog("TOWER", "Dispatching next takeoff from taxiway: " + nextTakeoff.getCallsign(), false);
            requestTakeoff(nextTakeoff);
        }
    }

    private String getAvailableRunway() {
        for (Map.Entry<String, Plane> entry : runwayStatus.entrySet()) {
            if (entry.getValue() == null) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void updateRunwayUI(String runway, String status, String callsign) {
        for (ATCListener l : listeners) {
            l.onRunwayStatusChanged(runway, status, callsign);
        }
    }

    private void notifyLog(String source, String message, boolean isAlert) {
        for (ATCListener l : listeners) {
            l.onATCMessage(source, message, isAlert);
        }
    }

    public List<Plane> getRegisteredPlanes() {
        return Collections.unmodifiableList(registeredPlanes);
    }

    public Map<String, Plane> getRunwayStatus() {
        return Collections.unmodifiableMap(runwayStatus);
    }

    public Queue<Plane> getLandingQueue() {
        return landingQueue;
    }

    public Queue<Plane> getTakeoffQueue() {
        return takeoffQueue;
    }
}
