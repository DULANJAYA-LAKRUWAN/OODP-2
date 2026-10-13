package lk.jiat.taxi;

/**
 * Listener interface for observing Taxi App Center events in the UI or logger.
 */
public interface TaxiAppListener {
    void onLog(String message);
    void onDriverStatusChanged(Driver driver);
}
