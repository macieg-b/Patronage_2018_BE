package mba.patronage.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String resourceName;

    public NotFoundException(String resourceName) {
        this.resourceName = resourceName;
    }
}
