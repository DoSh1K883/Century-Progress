package cp.content;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;

public class CPStatuses {
    public static StatusEffect target, cleared;

    public static void load(){

        target = new StatusEffect("target"){{
            color = Color.valueOf("c10036");
            healthMultiplier = 0.7f;
        }};

//        cleared = new StatusEffect("cleared"){{
//            color = Color.valueOf("82c2ff"):
//        }};
    }
}