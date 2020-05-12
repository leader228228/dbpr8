package birintsev.sumdu.edu.ua.dbpr8.controller;

import birintsev.sumdu.edu.ua.dbpr8.entity.Scan;
import birintsev.sumdu.edu.ua.dbpr8.service.ScanService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@AllArgsConstructor
public class ConsoleControllerImpl implements ConsoleCommandsController {

    private final ScanService service;

    @Override
    public void migrate(String directory) throws SQLException, IOException {
        Collection<Scan> scanCollection = service.getAllScans(directory);
        try {
            System.out.println("Adding data into database...");
            service.addScans(scanCollection);
            System.out.println("Data successfully added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
