package domain;

import java.util.Objects;

public class Name {
    public static final int UPPER_BOUND_OF_NAME_LENGTH = 5;
    private final String value;

    public Name(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateIsNotBlank(value);
        validateNoBlankEachSide(value);
        validateLengthInRange(value);
    }

    private void validateIsNotBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    private void validateNoBlankEachSide(String value) {
        if (value.trim().length() != value.length()) {
            throw new IllegalArgumentException("이름의 양쪽에 공백이 들어갈 수 없습니다.");
        }
    }

    private void validateLengthInRange(String value) {
        if (value.length() > UPPER_BOUND_OF_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 1 ~ 5자입니다.");
        }
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
