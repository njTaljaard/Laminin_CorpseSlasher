package CorpseSlasherServer;

/**
 * @author Laminin
 * @param Derivco
 * @param University of Pretoria
 * @param COS301
 *
 * Input will receive all the server calls from the client and send it to the
 * correct functions in DatabaseUdate.
 */
public class Input {

    public Input() {
    }

    /**
     * getInput will call the correct functions from DatabaseUpdate.
     *
     * @param value - The string received from the client.
     */
    public void getInput(String value) {
        System.out.println("Input: " + value);
    }
}
