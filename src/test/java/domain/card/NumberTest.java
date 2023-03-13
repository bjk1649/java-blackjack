package domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NumberTest {
    @Nested
    class 점수반환 {
        @ParameterizedTest(name = "ENUM->{0}, 점수->{1}")
        @CsvSource(value = {"ACE:1", "TWO:2", "THREE:3", "FOUR:4", "FIVE:5", "SIX:6", "SEVEN:7", "EIGHT:8", "NINE:9",
                "TEN:10", "KING:10", "QUEEN:10", "JACK:10"}, delimiter = ':')
        void should_가진점수반환_when_score호출(Number number, int expected) {
            //given

            //when
            int actual = number.score();

            //then
            assertThat(actual).isEqualTo(expected);
        }
    }
}
