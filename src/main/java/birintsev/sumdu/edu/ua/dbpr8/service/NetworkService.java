package birintsev.sumdu.edu.ua.dbpr8.service;

import birintsev.sumdu.edu.ua.dbpr8.entity.Network;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @see         Network
 * */
public interface NetworkService {
    /**
     * @return          information about all the stored networks
     * */
    Collection<Network> getAllNetworks() throws RemoteException;
    /**
     * @return          {@code true} if and only if all the networks were successfully saved
     *                  {@code false} otherwise
     * @param           networks networks to be added to the database
     *
     * @throws          IOException if any I/O exception occurred
     *                              (e.g networks parameters are invalid or connection exception)
     * @throws          SQLException if a database issues any error
     * */
    default boolean addNetworks(Collection<Network> networks) throws IOException, SQLException {
        for (Network network : networks) {
            if (!saveNetwork(network)) {
                throw new IOException("Saving " + network + " network failed");
            }
        }
        return true;
    }
    /**
     * @return          {@code true} if and only if the network was successfully saved
     *                  {@code false} otherwise
     * @param           network network to be added to the database
     *
     * @throws          IOException if any I/O exception occurred
     *                              (e.g networks parameters are invalid or connection exception)
     * @throws          SQLException if a database issues any error
     * */
    boolean saveNetwork(Network network) throws IOException, SQLException;
}
