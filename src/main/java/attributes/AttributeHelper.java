package attributes;

import java.util.HashMap;
import java.util.Map;

public class AttributeHelper {
    public static Map<Attribute, Integer> parameterMap = new HashMap<>();

    public static Map<Attribute, Integer> getParameterMap() {
        return parameterMap;
    }

    public static void initMap() {
        parameterMap.put(Attribute.DRAW, 1);
        parameterMap.put(Attribute.SHOUT, 0);
        parameterMap.put(Attribute.CHARGE, 0);
        parameterMap.put(Attribute.DOOUBLESTRIKE, 0);
        parameterMap.put(Attribute.SPIN, 0);
        parameterMap.put(Attribute.BOUNCE, 1);
        parameterMap.put(Attribute.FIRSTSTRIKE, 0);
        parameterMap.put(Attribute.BLOODTHIRSTY, 0);
        parameterMap.put(Attribute.REINFORCMENT, 1);
        parameterMap.put(Attribute.DEATHTOUCH, 1);
        parameterMap.put(Attribute.AIRDROP, 0);
        parameterMap.put(Attribute.INSPIRE, 0);
        parameterMap.put(Attribute.FLYING, 0);

    }

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
            case REINFORCMENT:
                attributeType = AttributeType.OTHER;
                break;
            default:
                attributeType = AttributeType.OTHER;
        }
        return attributeType;
    }

    public static Attribute stringToAttribute(String attributeString) {
        Attribute attribute;
        switch (attributeString) {
            case "DRAW":
                attribute = Attribute.DRAW;
                break;
            case "SHOUT":
                attribute = Attribute.SHOUT;
                break;
            case "CHARGE":
                attribute = Attribute.CHARGE;
                break;
            case "DOOUBLESTRIKE":
                attribute = Attribute.DOOUBLESTRIKE;
                break;
            case "SPIN":
                attribute = Attribute.SPIN;
                break;
            case "BOUNCE":
                attribute = Attribute.BOUNCE;
                break;
            case "FIRSTSTRIKE":
                attribute = Attribute.FIRSTSTRIKE;
                break;
            case "BLOODTHIRSTY":
                attribute = Attribute.BLOODTHIRSTY;
                break;
            case "AIRDROP":
                attribute = Attribute.AIRDROP;
                break;
            case "INSPIRE":
                attribute = Attribute.INSPIRE;
                break;
            case "FLYING":
                attribute = Attribute.FLYING;
                break;
            case "DEATHTOUCH":
                attribute = Attribute.DEATHTOUCH;
                break;
            case "REINFORCMENT":
                attribute = Attribute.REINFORCMENT;
                break;
            default:
                attribute = Attribute.NONE;
        }
        return attribute;
    }

}


