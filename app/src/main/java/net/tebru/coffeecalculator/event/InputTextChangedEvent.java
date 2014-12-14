package net.tebru.coffeecalculator.event;

/**
 * Class InputTextChangedEvent
 *
 * Event to notify of Input text changes
 */
public class InputTextChangedEvent {

    /**
     * The current text
     */
    private String inputText;

    /**
     * Constructor
     *
     * @param inputText The current text
     */
    public InputTextChangedEvent(String inputText) {
        this.inputText = inputText;
    }

    /**
     * Get the text
     *
     * @return string
     */
    public String getInputText() {
        return inputText;
    }
}
