package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.card.Card;
import domain.card.Number;
import domain.card.Suit;
import domain.participant.Name;
import domain.participant.Player;
import domain.participant.Players;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {

    @Nested
    class 결과반환 {
        @Test
        void should_승패를판단하여반환한다_when_getPlayerOutcome호출시() {
            Players players = new Players(List.of(new Player(new Name("포이"), 1000),
                    new Player(new Name("에밀"), 2000)));
            //given
            BlackjackGame blackjackGame = new BlackjackGame(players);
            blackjackGame.handOutInitialCards((cards) -> {
                cards.clear();
                cards.add(new Card(Suit.SPADE, Number.JACK));
                cards.add(new Card(Suit.SPADE, Number.TEN));
                cards.add(new Card(Suit.SPADE, Number.SEVEN));
                cards.add(new Card(Suit.SPADE, Number.NINE));
                cards.add(new Card(Suit.SPADE, Number.EIGHT));
                cards.add(new Card(Suit.SPADE, Number.TEN));
            });

            //when
            Map<String, Integer> outcome = blackjackGame.getRevenues();

            //then
            assertAll(
                    () -> assertThat(outcome.get("포이")).isEqualTo(1000),
                    () -> assertThat(outcome.get("에밀")).isEqualTo(-2000)
            );
        }
    }
}