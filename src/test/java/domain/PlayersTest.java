package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.collection;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayersTest {

    @Nested
    class 생성 {
        @Test
        void should_플레이어를생성한다_when_from호출() {
            //given
            List<String> playerNames = List.of("에밀", "포이");

            //when
            Players players = Players.from(playerNames);

            //then
            List<Player> playerList = players.getPlayers();

            assertThat(playerList).containsSequence(new Player("에밀"), new Player("포이"));
        }

        @Test
        void should_예외를던진다_when_플레이어가0명인경우() {
            //given
            List<String> playerNames = Collections.EMPTY_LIST;

            //when
            ThrowingCallable throwingCallable = () -> Players.from(playerNames);

            //then
            assertThatThrownBy(throwingCallable)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("플레이어는 1명 이상이어야 합니다.");
        }
    }

    @Nested
    class 카드받기 {
        @Test
        void should_플레이어는카드를한장씩받는다_when_receiveCard호출() {
            //given
            List<String> playerNames = List.of("에밀", "포이");
            Players players = Players.from(playerNames);
            Deck deck = Deck.create();

            //when
            players.receiveCard(deck);

            //then
            assertThat(players).extracting("players", collection(Player.class))
                    .filteredOn((player) -> player.hand().size() == 1)
                    .hasSize(2);
        }
    }

    @Nested
    class 카드를뽑을수있는플레이어존재여부판단 {
        @Test
        void should_hasDrawablePlayer가true반환_when_카드를뽑을수있는플레이어존재() {
            //given
            Players players = Players.from(List.of("포이"));
            Deck deck = Deck.create();
            deck.shuffle((cards) -> {
                cards.clear();
                cards.add(new Card(Suit.SPADE, Number.ACE));
                cards.add(new Card(Suit.SPADE, Number.ACE));
            });
            players.receiveCard(deck);

            //when
            boolean existingDrawablePlayer = players.hasDrawablePlayer();

            //then
            assertThat(existingDrawablePlayer).isTrue();
        }

        @Test
        void should_hasDrawablePlayer가false반환_when_카드를뽑을수있는플레이어없을때() {
            //given
            Players players = Players.from(List.of("포이"));
            Deck deck = Deck.create();
            deck.shuffle((cards) -> {
                cards.clear();
                cards.add(new Card(Suit.SPADE, Number.ACE));
                cards.add(new Card(Suit.SPADE, Number.JACK));
            });
            players.receiveCard(deck);
            players.receiveCard(deck);

            //when
            boolean existingDrawablePlayer = players.hasDrawablePlayer();

            //then
            assertThat(existingDrawablePlayer).isFalse();
        }
    }

    @Nested
    class 카드를받을수있는플레이어이름반환 {
        @Test
        void should_카드를받을다음플레이어이름반환_when_카드를받을수있는플레이어가존재할시() {
            //given
            Players players = Players.from(List.of("포이"));
            String expected = "포이";
            Deck deck = Deck.create();
            players.receiveCard(deck);

            //when
            String actual = players.getCurrentDrawablePlayer().name();

            //then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void should_예외를반환한다_when_카드를받을수있는플레이어가없을시() {
            //given
            Players players = Players.from(List.of("포이"));
            Deck deck = Deck.create();
            deck.shuffle((cards) -> {
                cards.clear();
                cards.add(new Card(Suit.SPADE, Number.ACE));
                cards.add(new Card(Suit.SPADE, Number.JACK));
            });
            players.receiveCard(deck);
            players.receiveCard(deck);

            //when
            ThrowingCallable throwingCallable = players::getCurrentDrawablePlayer;

            //then
            assertThatThrownBy(throwingCallable)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("카드를 받을 수 있는 플레이어가 없습니다.");
        }
    }
}
