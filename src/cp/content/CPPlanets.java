package cp.content;

import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import cp.content.CPItems;

public class CPPlanets{

    public static Planet
            caereleum,
            alioth;

    public static void load(){
        caereleum = new Planet("caereleum", Planets.sun, 5f) {{
            bloom = true;
            accessible = false;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("ff7a38"),
                    Color.valueOf("ff9638"),
                    Color.valueOf("ffc64c"),
                    Color.valueOf("ffc64c"),
                    Color.valueOf("ffe371"),
                    Color.valueOf("f4ee8e")
            );
        }};

        alioth = new Planet("alioth", caereleum, 1f, 2) {{
            generator = new ErekirPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            allowWaves = true;
            allowWaveSimulation = true;
            allowSectorInvasion = false;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            //doesn't play well with configs
            prebuildBase = true;
            ruleSetter = r -> {
                r.waveTeam = Team.malis;
                r.placeRangeCheck = false;
                r.showSpawns = true;
            };
            iconColor = Color.valueOf("7d4dff");
            atmosphereColor = Color.valueOf("3c1b8f");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.4f;
            startSector = 0;
            alwaysUnlocked = true;
            landCloudColor = Pal.spore.cpy().a(0.5f);
            hiddenItems.addAll(Items.erekirItems).addAll(Items.serpuloItems).removeAll(CPItems.aliothItems);
            unlockedOnLand.add(CPBlocks.markOne);
            defaultCore = CPBlocks.markOne;
        }};

        //i dont fucking know (thx dusted land source code)
        Planets.serpulo.hiddenItems.addAll(CPItems.aliothItems).removeAll(Items.serpuloItems);
        Planets.erekir.hiddenItems.addAll(CPItems.aliothItems).removeAll(Items.erekirItems);
    }
}