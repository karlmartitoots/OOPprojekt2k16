package userFeatures;

import card.GeneralCard;

//basically a "struct" to pass along to the game's GUI
public class Settings {

    public GeneralCard getWhiteGeneral() {
        return whiteGeneral;
    }

    public GeneralCard getBlackGeneral() {
        return blackGeneral;
    }

    GeneralCard whiteGeneral, blackGeneral;

    /**
     * An object generated for holding the game settings/passing them from Setup to Game
     * @param whiteGeneral Holds the white sides GeneralCard
     * @param blackGeneral Holds the black sides GeneralCard
     */
    public Settings(GeneralCard whiteGeneral, GeneralCard blackGeneral) {
        this.whiteGeneral = whiteGeneral;
        this.blackGeneral = blackGeneral;
    }
}
