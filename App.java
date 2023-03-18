

import java.util.*;


public class App 
{

    public enum Colours {
        R, G, B, W
    }


    public static int maxCountOfCards = 40;


    public static List<String> createShuffleDeck (int n, int c, List<String> cards) {
        if (n*c > maxCountOfCards) {
            throw new IllegalArgumentException(String.format("Unable to distribute %d cards to %d people due to our deck size",n,c));
        }
        List<String> cardsNew = new ArrayList<>(cards);
        Collections.shuffle(cardsNew);
        return cardsNew;
    }

    public static void main(String[] args )
    {
        Map<Integer, List<String>> playerAndHisCards = new HashMap<>();
        List<Colours> colours = List.of(Colours.R, Colours.B, Colours.G, Colours.W);
        List<String> cards = new ArrayList<>();
        for (Colours colour : colours)  {
            for (int i = 1; i <= 10; i++) {
                cards.add(colour.name() + i);
            }
        }
        Scanner userInput = new Scanner(System.in);

        System.out.println( "Hello User!" );
        while (true) {
            System.out.println();
            System.out.println("List of commands to enter:" );
            System.out.println("start N C");
            System.out.println("get-cards C");
            System.out.println("exit");
            String input = userInput.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("See you!");
                break;
            } else if (input.contains("start")) {
                playerAndHisCards.clear();
                try {
                    String[] mass = input.split(" ");
                    int countCardsPerPlayer = Integer.parseInt(mass[1]);
                    int countPlayers = Integer.parseInt(mass[2]);
                    List<String> shuffleDeck = App.createShuffleDeck(countCardsPerPlayer, countPlayers, cards);
                    for (int i = 1 ; i <= countPlayers; i++) {
                        Integer numberOfPlayer = i;
                        List<String> cardsForPlayer = new ArrayList<>();
                        for (int j = 1 ; j <= countCardsPerPlayer ; j++) {
                            cardsForPlayer.add(shuffleDeck.remove(0));
                        }
                        playerAndHisCards.put(numberOfPlayer, cardsForPlayer);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.contains("get-cards")) {
                try {
                    if (playerAndHisCards.size() == 0) {
                        throw new IllegalArgumentException(
                                "Cards have not yet been distributed. Deal the cards first with the start command");
                    }
                    String[] mass = input.split(" ");
                    Integer player = Integer.parseInt(mass[1]);
                    if (playerAndHisCards.get(player) == null) {
                        throw new IllegalArgumentException("There is no such player in this game");
                    }
                    List<String> cardsFromSpecificPlayer = playerAndHisCards.get(player);
                    StringBuilder answer = new StringBuilder();
                    answer.append(player);
                    for (String s : cardsFromSpecificPlayer) {
                        answer.append(" ");
                        answer.append(s);
                    }
                    System.out.println(answer);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
