package birintsev.sumdu.edu.ua.dbpr8.entity;

import lombok.*;

import java.sql.Date;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Scan {
    private Date startDate;
    private String uuid;
    private String instance;
    private Collection<Network> networks;
}
