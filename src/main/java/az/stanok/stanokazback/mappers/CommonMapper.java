package az.stanok.stanokazback.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public abstract class CommonMapper {
    @Autowired
    protected ObjectMapper objectMapper;

    public Long convertDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return 0L;
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDateTime convertDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Long convertDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public LocalDate convertDate(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @SneakyThrows
    public String convertJson(JsonNode object) {
        if (object == null) {
            return null;
        }

        return object.toPrettyString();
    }

    @SneakyThrows
    public JsonNode convertJson(String object) {
        if (!StringUtils.hasLength(object)) {
            return null;
        }

        return objectMapper.readTree(object);
    }

}
