package az.stanok.stanokazback.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.Objects;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<R> {
    private R result;
    private boolean ok;

    private String error; // short error desc
    private String message; // human readable error desc
    private Map<String, String> errors;

    public static <R> ApiResponse<R> failure(
            String error, String humanReadableMessage, @Nullable Map<String, String> errors
    ) {
        error = error == null ? "No message" : error;
        Objects.requireNonNull(humanReadableMessage);
        return new ApiResponseBuilder<R>().error(error).message(humanReadableMessage).errors(errors).build();
    }

    public static <R> ApiResponse<R> failure(String error, String humanReadableMessage) {
        return failure(error, humanReadableMessage, null);
    }

    public static <R> ApiResponse<R> success(R result) {
        return new ApiResponseBuilder<R>().result(result).ok(true).build();
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.success(null);
    }

}
