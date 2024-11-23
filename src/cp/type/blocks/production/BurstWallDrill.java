package cp.type.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class BurstWallDrill extends Block{
    public int drillOffset = 1;
    public int tier = 5;
    public int areaSize = 2;

    public BurstWallDrill(String name){
        super(name);

        hasItems = true;
        rotate = true;
        update = true;
        solid = true;
        drawArrow = false;
        regionRotated1 = 1;
        ambientSoundVolume = 0.05f;
        ambientSound = Sounds.minebeam;

        envEnabled |= Env.space;
        flags = EnumSet.of(BlockFlag.drill);
    }

    public Rect getRect(Rect rect, float x, float y, int rotation){
        rect.setCentered(x, y, areaSize * tilesize);
        float len = tilesize * (areaSize + size)/2f;

        rect.x += Geometry.d4x(rotation) * len;
        rect.y += Geometry.d4y(rotation) * len;

        return rect;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Rect rect = getRect(Tmp.r1, x, y, rotation);

        Drawf.dashRect(valid ? Pal.accent : Pal.remove, rect);
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    @Override
    public boolean rotatedOutput(int x, int y){
        return false;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for(int i = 0; i < size; i++){
            nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
            for(int j = 0; j < drillOffset + 1; j++){
                Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation)*j, Tmp.p1.y + Geometry.d4y(rotation)*j);
                if(other != null && other.solid()){
                    Item drop = other.wallDrop();
                    if(drop != null && drop.hardness <= tier){
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }
}