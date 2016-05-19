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
        parameterMap.put(Attribute.DIRECTDAMAGE, 1);
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
            case "DIRECTDAMAGE":
                attribute = Attribute.DIRECTDAMAGE;
                break;
            default:
                attribute = Attribute.NONE;
        }
        return attribute;
    }

}


