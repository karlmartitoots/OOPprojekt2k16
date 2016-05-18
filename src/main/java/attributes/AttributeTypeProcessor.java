package attributes;

public class AttributeTypeProcessor {
    public static AttributeType processAttributeType(Attribute attribute) {
        AttributeType attributeType;
        switch (attribute) {
            case DRAW:
                attributeType = AttributeType.OTHER;
                break;
            case SHOUT:
                attributeType = AttributeType.OTHER;
                break;
            case CHARGE:
                attributeType = AttributeType.BUFF;
                break;
            case DOOUBLESTRIKE:
                attributeType = AttributeType.BUFF;
                break;
            case SPIN:
                attributeType = AttributeType.AFFECTENEMY;
                break;
            case BOUNCE:
                attributeType = AttributeType.AFFECTENEMY;
                break;
            case FIRSTSTRIKE:
                attributeType = AttributeType.BUFF;
                break;
            case BLOODTHIRSTY:
                attributeType = AttributeType.BUFF;
                break;
            case AIRDROP:
                attributeType = AttributeType.OTHER;
                break;
            case INSPIRE:
                attributeType = AttributeType.BUFF;
                break;
            case FLYING:
                attributeType = AttributeType.BUFF;
                break;
            case DEATHTOUCH:
                attributeType = AttributeType.BUFF;
                break;
            default:
                attributeType = AttributeType.OTHER;
        }
        return attributeType;
    }
}
