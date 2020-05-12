package birintsev.sumdu.edu.ua.dbpr8.service;

import birintsev.sumdu.edu.ua.dbpr8.entity.Scan;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @see         Scan
 * */
public interface ScanService {
    /**
     * @return          information about all the stored scans
     * */
    Collection<Scan> getAllScans(String directory) throws RemoteException;
    /**
     * @return          {@code true} if and only if all the scans were successfully saved
     *                  {@code false} otherwise
     * @param           scans scans to be added to the database
     *
     * @throws          IOException if any I/O exception occurred
     *                              (e.g networks parameters are invalid or connection exception)
     * @throws          SQLException if a database issues any error
     * */
    default boolean addScans(Collection<Scan> scans) throws IOException, SQLException {
        for (Scan scan : scans) {
            if (!saveScan(scan)) {
                throw new IOException("Saving " + scan + " scan failed");
            }
        }
        return true;
    }
    /**
     * @return          {@code true} if and only if the scan was successfully saved
     *                  {@code false} otherwise
     * @param           scan scan to be added to the database
     *
     * @throws          IOException if any I/O exception occurred
     *                              (e.g networks parameters are invalid or connection exception)
     * @throws          SQLException if a database issues any error
     * */
    boolean saveScan(Scan scan) throws IOException, SQLException;
}
