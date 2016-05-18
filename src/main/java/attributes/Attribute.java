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
    REINFORCMENT //Summons that many 1/1 units for you
}
