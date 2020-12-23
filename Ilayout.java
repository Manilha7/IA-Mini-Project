import java.util.List;

interface Ilayout {
	/**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    boolean endofGame();

    void setPlayer(int player);
    int getPlayer();
    double getResult();
    }