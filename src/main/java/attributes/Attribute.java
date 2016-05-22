package attributes;

public enum Attribute {
    NONE,
    DRAW, //Draw cards
    SHOUT, // Effect when entering the gameboard
    CHARGE, // Can move and attack on the turn when summoned
    DOOUBLESTRIKE, // Strikes the minion two times(First time with firstStrike)
    SPIN, // Attack all adjacent enemy minions
    BOUNCE, // Return card to hand from board
    FIRSTSTRIKE, // Enemy first receives hit, then retaliates
    BLOODTHIRSTY, // Trigger when killed an enemy minion
    AIRDROP, //Possible to summon anywhere
    INSPIRE, // Bonus if next to general
    FLYING, // Can fly over minions
    DEATHTOUCH, // If deals damage to minion, kills instantly
    REINFORCMENT, //Summons that many 1/1 units for you
    DIRECTDAMAGE; //Damage to target

    public String toString(){
        switch(this){
            case DRAW:
                return "Draw";
            case SHOUT:
                return "Shout";
            case CHARGE:
                return "Charge";
            case DOOUBLESTRIKE:
                return "Doublestrike";
            case SPIN:
                return "Spin";
            case BOUNCE:
                return "Bounce";
            case FIRSTSTRIKE:
                return "First Strike";
            case BLOODTHIRSTY:
                return "Bloodthirsty";
            case AIRDROP:
                return "Airdrop";
            case INSPIRE:
                return "Inspire";
            case FLYING:
                return "Flying";
            case DEATHTOUCH:
                return "Deathtouch";
            case REINFORCMENT:
                return "Reinforcements";
            case DIRECTDAMAGE:
                return "Direct Damage";
            default:
                return "NONE";
        }
    }

    public Attribute getType(){
        return this;
    }
}
