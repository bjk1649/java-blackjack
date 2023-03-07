package controller;

import domain.BlackjackGame;
import domain.Decision;
import domain.Participant;
import domain.Player;
import domain.RandomShuffleStrategy;
import java.util.List;
import java.util.stream.Collectors;
import view.InputView;
import view.OutputView;

public class BlackjackController {

    private final InputView inputView;
    private final OutputView outputView;

    public BlackjackController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        BlackjackGame blackjackGame = initializeGame();
        handOutCardsToPlayers(blackjackGame);
        handOutCardsToDealer(blackjackGame);
        outputView.printParticipantsScore(toParticipantDtosWithScore(blackjackGame.getParticipants()));
        outputView.printGameOutcomes(blackjackGame.getPlayersOutcome());
    }

    private BlackjackGame initializeGame() {
        BlackjackGame blackjackGame = createGame();
        blackjackGame.handOutInitialCards(new RandomShuffleStrategy());

        outputView.printParticipantsInitialCards(toParticipantDtos(blackjackGame.getParticipants()));
        return blackjackGame;
    }

    private BlackjackGame createGame() {
        try {
            List<String> playerNames = inputView.readNames();
            return BlackjackGame.createWithPlayerNames(playerNames);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return createGame();
        }
    }

    private void handOutCardsToPlayers(BlackjackGame blackjackGame) {
        while (blackjackGame.hasDrawablePlayer()) {
            Player currentDrawablePlayer = blackjackGame.getCurrentDrawablePlayer();
            Decision decision = getDecision(currentDrawablePlayer);
            blackjackGame.hitOrStand(decision);
            outputView.printAllCards(new ParticipantDto(currentDrawablePlayer));
        }
    }

    private Decision getDecision(Player currentDrawablePlayer) {
        try {
            return Decision.from(inputView.readDecision(currentDrawablePlayer.name()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getDecision(currentDrawablePlayer);
        }
    }

    private void handOutCardsToDealer(BlackjackGame blackjackGame) {
        while (blackjackGame.isDealerDrawable()) {
            blackjackGame.handOutCardToDealer();
            outputView.printDealerHandOutInfo();
        }
    }

    private List<ParticipantDto> toParticipantDtos(List<Participant> participants) {
        return participants.stream()
                .map(ParticipantDto::new)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<ParticipantDtoWithScore> toParticipantDtosWithScore(List<Participant> participants) {
        return participants.stream()
                .map(ParticipantDtoWithScore::new)
                .collect(Collectors.toUnmodifiableList());
    }

}

