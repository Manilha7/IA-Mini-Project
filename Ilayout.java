import java.util.List;

interface Ilayout {
	/**
     * @return the children of the receiver.
     */
    List<Ilayout> children();

    boolean endofGame();

    int getPlayer();
    double getResult();
    }