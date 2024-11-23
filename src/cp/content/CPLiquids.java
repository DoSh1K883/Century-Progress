package cp.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.*;

public class CPLiquids{
    public static Liquid ammonia, argon;

    public static void load(){

        ammonia = new Liquid("ammonia", Color.valueOf("c6e5b1")){{
            heatCapacity = 0.4f;
            effect = StatusEffects.freezing;
            boilPoint = 0.1f;
        }};

        argon = new Liquid("argon", Color.valueOf("ff8e8e")){{
            gas = true;
        }};
    }
}