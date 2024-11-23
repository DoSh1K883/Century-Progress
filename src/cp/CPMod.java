package cp;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import cp.content.*;
public class CPMod extends Mod{

    public CPMod(){
        Log.info("Loaded CPMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("fireinthehole");
                dialog.cont.add(":)").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("cp-fireinthehole")).pad(20f).row();
                dialog.cont.button("FIRE IN THE HOLE!!!", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent() {
        CPItems.load();
        CPLiquids.load();
        CPStatuses.load();
        CPUnitTypes.load();
        CPBlocks.load();
        CPPlanets.load();
    }
}
