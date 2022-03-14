package blackjack.domain.card;

import static blackjack.domain.card.CardNumber.*;
import static blackjack.domain.card.CardSymbol.*;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HoldingCardTest {

    @Test
    @DisplayName("합이 21초과일 경우 버스트다")
    void over21_isBust() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(QUEEN, CLOVER),
                        Card.valueOf(JACK, CLOVER)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(FIVE, CLOVER));
        Assertions.assertThat(holdingCard.isBust()).isTrue();
    }

    @Test
    @DisplayName("합이 21보다 작을 경우 버스트가 아니다.")
    void under21_isNotBust() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(EIGHT, CLOVER),
                        Card.valueOf(SEVEN, CLOVER)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(SIX, CLOVER));
        Assertions.assertThat(holdingCard.isBust()).isFalse();
    }

    @Test
    @DisplayName("Ace를 포함한 합이 21보다 작을 경우 버스트가 아니다.")
    void under21withAce_isNotBust() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(JACK, CLOVER),
                        Card.valueOf(KING, CLOVER)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(ACE, CLOVER));
        Assertions.assertThat(holdingCard.isBust()).isFalse();
    }

    @Test
    @DisplayName("Ace를 포함한 합이 21보다 클 경우 버스트다.")
    void under21withAce_isBust() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(JACK, CLOVER),
                        Card.valueOf(KING, CLOVER)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(ACE, CLOVER));
        holdingCard.add(Card.valueOf(ACE, SPADE));
        Assertions.assertThat(holdingCard.isBust()).isTrue();
    }

    @Test
    @DisplayName("A가 4장 존재할 때 14로 계산할 수 있는가?")
    void fourAce_calculate() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(ACE, CLOVER), Card.valueOf(ACE, SPADE),
                        Card.valueOf(ACE, HEART),
                        Card.valueOf(ACE, DIAMOND)));
        HoldingCard holdingCard = new HoldingCard(cards);
        int result = holdingCard.computeTotalScore();
        Assertions.assertThat(result).isEqualTo(14);
    }

    @Test
    @DisplayName("A가 4개 존재하고, 7이 추가되면 21로 계산할 수 있는가?")
    void fourAce_Add7_calculate() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(ACE, CLOVER), Card.valueOf(ACE, SPADE),
                        Card.valueOf(ACE, HEART),
                        Card.valueOf(ACE, DIAMOND)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(SEVEN, DIAMOND));
        int result = holdingCard.computeTotalScore();
        Assertions.assertThat(result).isEqualTo(21);
    }

    @Test
    @DisplayName("A가 4개 존재하고, 7과 10이 추가되면 21로 계산할 수 있는가?")
    void fourAce_Add17_calculate() {
        List<Card> cards = new ArrayList<>(
                List.of(Card.valueOf(ACE, CLOVER), Card.valueOf(ACE, SPADE),
                        Card.valueOf(ACE, HEART),
                        Card.valueOf(ACE, DIAMOND)));
        HoldingCard holdingCard = new HoldingCard(cards);
        holdingCard.add(Card.valueOf(SEVEN, DIAMOND));
        holdingCard.add(Card.valueOf(TEN, DIAMOND));
        int result = holdingCard.computeTotalScore();
        Assertions.assertThat(result).isEqualTo(21);
    }
}