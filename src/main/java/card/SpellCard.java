package card;


import attributes.Attribute;

import java.util.List;
import java.util.Map;

public class SpellCard extends Card {
    private Map<Attribute, List<Integer>> attributeListMap;

    public SpellCard(String name, int cost, String description, Map<Attribute, List<Integer>> attributeListMap) {
        super(name, cost, description);
        this.attributeListMap = attributeListMap;
    }

    public Map<Attribute, List<Integer>> getAttributeListMap() {
        return attributeListMap;
    }
}
