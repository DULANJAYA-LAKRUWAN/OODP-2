package lk.jiat.taxi;

/**
 * Mediator Interface for the Taxi Service.
 * Defines the contract for communication between customers and drivers.
 */
public interface TaxiMediator {
    void registerDriver(Driver driver);
    void registerCustomer(Customer customer);
    void requestTaxi(Customer customer, String location);
}
