package cp.type.blocks.distribution;

import arc.Core;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class ImprovedDuctRouter extends Block{
    public float speed = 5f;

    public TextureRegion topRegion;

    public ImprovedDuctRouter(String name){
        super(name);

        group = BlockGroup.transportation;
        update = true;
        solid = false;
        hasItems = true;
        unloadable = false;
        itemCapacity = 1;
        noUpdateDisabled = true;
        saveConfig = true;
        rotate = true;
        clearOnDoubleTap = true;
        underBullets = true;
        priority = TargetPriority.transport;
        envEnabled = Env.space | Env.terrestrial | Env.underwater;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.itemsMoved, 60f / speed * itemCapacity, StatUnit.itemsSecond);
    }

    @Override
    public void load() {
        super.load();
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(topRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public boolean rotatedOutput(int x, int y){
        return false;
    }

    public class ImprovedDuctRouterBuild extends Building{

        public float progress;
        public @Nullable Item current;

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(topRegion, x, y, rotdeg());
        }

        @Override
        public void updateTile(){
            progress += edelta() / speed * 2f;

            if(current != null){
                if(progress >= (1f - 1f/speed)){
                    var target = target();
                    if(target != null){
                        target.handleItem(this, current);
                        items.remove(current, 1);
                        current = null;
                        progress %= (1f - 1f/speed);
                    }
                }
            }else{
                progress = 0;
            }

            if(current == null && items.total() > 0){
                current = items.first();
            }
        }

        @Nullable
        public Building target(){
            if(current == null) return null;

            int dump = cdump;

            for(int i = 0; i < proximity.size; i++){
                Building other = proximity.get((i + dump) % proximity.size);
                int rel = relativeTo(other);

                if(!(rel == (rotation + 2) % 4) && other.team == team && other.acceptItem(this, current)){
                    incrementDump(proximity.size);
                    return other;
                }

                incrementDump(proximity.size);
            }

            return null;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return current == null && items.total() == 0 &&
                (Edges.getFacingEdge(source.tile(), tile).relativeTo(tile) == rotation);
        }

        @Override
        public int removeStack(Item item, int amount){
            int removed = super.removeStack(item, amount);
            if(item == current) current = null;
            return removed;
        }

        @Override
        public void handleStack(Item item, int amount, Teamc source){
            super.handleStack(item, amount, source);
            current = item;
        }

        @Override
        public void handleItem(Building source, Item item){
            current = item;
            progress = -1f;
            items.add(item, 1);
            noSleep();
        }

        @Override
        public byte version(){
            return 1;
        }
        }
}
