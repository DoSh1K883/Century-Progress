package cp.entities.units;

import arc.graphics.Color;
import mindustry.type.UnitType;

public class AliothUnitType extends UnitType {
    public AliothUnitType(String name) {
        super(name);
        outlineColor = Color.valueOf("2e2f34");
    }
}