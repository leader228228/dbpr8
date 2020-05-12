package birintsev.sumdu.edu.ua.dbpr8.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import lombok.*;
import org.springframework.format.datetime.DateFormatter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Scan {
    @JsonSerialize(converter = Serializer.class)
    @JsonDeserialize(converter = Deserializer.class)
    private Date startDate;
    private String uuid;
    private String instance;
    private Collection<Network> networks;

    private static final String datePattern = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final DateFormatter formatter = new DateFormatter(datePattern);

    public static class Serializer extends StdConverter<Date, String> {
        @Override
        public String convert(Date value) {
            return formatter.print(value, Locale.getDefault());
        }
    }

    public static class Deserializer extends StdConverter<String, Date> {
        @Override
        public Date convert(String value) {
            try {
                return new Date(formatter.parse(value, Locale.getDefault()).getTime());
            } catch (ParseException e) {
                throw new UncheckedIOException(new IOException(e));
            }
        }
    }
}
