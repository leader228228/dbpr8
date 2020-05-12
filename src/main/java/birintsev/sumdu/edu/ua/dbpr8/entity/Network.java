package birintsev.sumdu.edu.ua.dbpr8.entity;

import lombok.*;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Network {
    private String SSID;
    private String capabilities;
    private String status;
    private String security;
    private String debug;
    private String level;
    private String BSSID;
}
