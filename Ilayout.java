import java.util.List;

interface Ilayout {
	/**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    boolean endofGame();

    void setPlayer(char player);
    char getPlayer();
    char getResult();
    }