package birintsev.sumdu.edu.ua.dbpr8.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.sql.SQLException;

public interface ConsoleCommandsController {
    /**
     * @param           directory a path to directory containing json files with the data to be migrated
     *
     * @return          an entity containing info about the process (if succeeded)
     *                  or {@code null} if there is any additional information
     *
     * @throws          SQLException if a database issues any error
     * @throws          IOException if content of json files is invalid
     *                              or configurations (like config file path)
     *                              are not completely valid
     * */
    ResponseEntity migrate(@NonNull String directory) throws SQLException, IOException;
}
