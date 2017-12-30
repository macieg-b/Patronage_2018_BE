package mba.patronage.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@Builder
class ErrorResponseEntity {

    private final HttpStatus status;
    private final String resourceName;

}
