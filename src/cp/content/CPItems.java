package cp.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

import static mindustry.content.Items.*;

public class CPItems {

    public static Item

            calbet, pelner, hafur, silinor, lurtum, nutredium, baros, densatum;

    public static final Seq<Item> aliothItems = new Seq<>();

    public static final Seq<Item> CPItems = new Seq<>();

    public static void load(){
        calbet = new Item("calbet", Color.valueOf("8b8088")){{
            cost = 1.2f;
            hardness = 1;
        }};

        pelner = new Item("pelner", Color.valueOf("7a7c65")){{
            cost = 1f;
            hardness = 1;
            flammability = 0.3f;
        }};

        hafur = new Item("hafur", Color.valueOf("56425d")){{
            cost = 1.8f;
        }};

        silinor = new Item("silinor", Color.valueOf("92d8a7")){{
            cost = 0.9f;
            charge = 0.75f;
        }};

        lurtum = new Item("lurtum", Color.valueOf("b6c9c4")){{
            cost = 1.4f;
            explosiveness = 0.5f;
        }};

        nutredium = new Item("nutredium", Color.valueOf("7c515c")){{
            cost = 2f;
            flammability = 0.1f;
        }};

        baros = new Item("baros", Color.valueOf("ffd69d")){{
            cost = 1f;
            charge = 0.75f;
        }};

        densatum = new Item("densatum", Color.valueOf("66616e")){{
            cost = 1.6f;
            healthScaling = 1.2f;
        }};

        aliothItems.addAll (
                calbet, pelner, copper, coal, sand, graphite, hafur, silinor, lurtum, nutredium, baros, densatum
        );
    }
}