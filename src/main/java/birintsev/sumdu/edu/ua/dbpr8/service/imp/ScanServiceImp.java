package birintsev.sumdu.edu.ua.dbpr8.service.imp;

import birintsev.sumdu.edu.ua.dbpr8.entity.Network;
import birintsev.sumdu.edu.ua.dbpr8.entity.Scan;
import birintsev.sumdu.edu.ua.dbpr8.service.ScanService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ScanServiceImp implements ScanService {

    private final DataSource dataSource;

    @Override
    public Collection<Scan> getAllScans(String directory) throws RemoteException {
        File dir = new File(directory);
        Collection<File> fileCollection = getAllFiles(dir);
        Collection<Scan> scanCollection = new ArrayList<>();
        for (File file : fileCollection) {
            ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            try {
                Scan scan = objectMapper.readValue(file, Scan.class);
                scanCollection.add(scan);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scanCollection;
    }

    @Override
    public boolean addScans(Collection<Scan> scans) throws IOException, SQLException {
        try (Connection connection = dataSource.getConnection()) {
            for (Scan scan:scans) {
                PreparedStatement insertScan = connection.prepareStatement("insert into dbpr8_scans(UUID, instance, startDate) values (?, ?, ?)");
                insertScan.setString(1, scan.getUuid());
                insertScan.setString(2, scan.getInstance());
                insertScan.setDate(3, scan.getStartDate());
                insertScan.executeUpdate();
                PreparedStatement getGeneratedID = connection.prepareStatement("SELECT LAST_INSERT_ID() lid");
                int res;
                ResultSet resultSet = getGeneratedID.executeQuery();
                if (resultSet.next()) {
                    res = resultSet.getInt("lid");
                } else {
                    throw new SQLException("Can not extract last inserted id");
                }
                for (Network network:scan.getNetworks()) {
                    PreparedStatement insertNetwork = connection.prepareStatement(
                            "insert into dbpr8_networks(SSID, capabilities, status, security, debug, level, BSSID, scan_id) values (?,?,?,?,?,?,?,?)");
                    insertNetwork.setString(1, network.getSSID());
                    insertNetwork.setString(2, network.getCapabilities());
                    insertNetwork.setString(3, network.getStatus());
                    insertNetwork.setString(4, network.getSecurity());
                    insertNetwork.setString(5, network.getDebug());
                    insertNetwork.setInt(6, Integer.parseInt(network.getLevel()));
                    insertNetwork.setString(7, network.getBSSID());
                    insertNetwork.setInt(8, res);
                    insertNetwork.executeUpdate();
                }
            }
            return true;
        }
    }

    @Override
    public boolean saveScan(Scan scan) throws IOException, SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement insertScan = connection.prepareStatement("insert into dbpr8_scans(UUID, instance, startDate) values (?, ?, ?)");
            insertScan.setString(1, scan.getUuid());
            insertScan.setString(2, scan.getInstance());
            insertScan.setDate(3, scan.getStartDate());
            insertScan.executeUpdate();
            PreparedStatement getGeneratedID = connection.prepareStatement("SELECT LAST_INSERT_ID() lid");
            int res;
            ResultSet resultSet = getGeneratedID.executeQuery();
            if (resultSet.next()) {
                res = resultSet.getInt("lid");
            } else {
                throw new SQLException("Can not extract last inserted id");
            }
            for (Network network:scan.getNetworks()) {
                PreparedStatement insertNetwork = connection.prepareStatement(
                        "insert into dbpr8_networks(SSID, capabilities, status, security, debug, level, BSSID, scan_id) values (?,?,?,?,?,?,?,?)");
                insertNetwork.setString(1, network.getSSID());
                insertNetwork.setString(2, network.getCapabilities());
                insertNetwork.setString(3, network.getStatus());
                insertNetwork.setString(4, network.getSecurity());
                insertNetwork.setString(5, network.getDebug());
                insertNetwork.setInt(6, Integer.parseInt(network.getLevel()));
                insertNetwork.setString(7, network.getBSSID());
                insertNetwork.setInt(8, res);
                insertNetwork.executeUpdate();
            }
        }
        return true;
    }

    public int getLastInsertId () throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement("SELECT LAST_INSERT_ID() lid")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int res = resultSet.getInt("lid");
                return res;
            }
            throw new SQLException("Can not extract last inserted id");
        }
    }

    public Collection<File> getAllFiles(File folder) {
        Collection<File> fileCollection = new ArrayList<>();
        File[] folderEntries = folder.listFiles();
        if (folderEntries != null) {
            for (File entry : folderEntries) {
                if (entry.isDirectory()) {
                    getAllFiles(entry);
                    continue;
                }
                fileCollection.add(entry);
            }
        }
        return fileCollection;
    }
}
