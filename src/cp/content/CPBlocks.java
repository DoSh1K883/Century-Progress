package cp.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import cp.content.*;
import cp.entities.units.*;
import cp.type.blocks.distribution.*;
import cp.type.blocks.production.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.type.ItemStack.*;

public class CPBlocks {
    public static Block
    //turret
        crusher, trigger, twice, rupture, confusion, slash,
            //special
            turretRadar,
    //units
        //primary
            supportConstructor,
        //advanced
    //defense
        repairWall, mendWall, healingPillar,
    //storage
        markOne, keeper,
    //transport
        isolatedDuct, isolatedRouter, isolatedJunction, isolatedSorter, isolatedOverflowGate, isolatedUnderflowGate, isolatedBridge, railway, railRouter, railLoader, railUnloader, railwayContainer, containerConstructor, containerDeconstructor,
    //liquid
        copperConduit, copperJunction, copperRouter, copperBridge, copperLiquidTank,
    //production
        highFrequencyDrill, heatDrill, ammoniaCondenser, wallHammer, nitrogenConcentrator,
    //power
        powerTower, reinforcedBattery,
    //crafting
        impactPress, oxidationCell, silinorCrucible,
    //environment
        chromiumStone, chromiumStoneRough, chromiumStoneWall, chromiumBoulderWall, caveStone, caveSand, caveVent, caveStoneWall, caveBoulderWall, crystalicFloor, crystalicWall, ammonia, deepCoral, deepCoralWall, deepCoralDense,
    //props
        moonlightPrime, caveBoulder, caveBoulders, duneCluster, crystalicBoulder, deepCrystals,
    //ores
        oreCalbet, orePelner,
    //wall ores
        coalWall;
    public static void load() {

        //turret

        turretRadar = new Radar("turret-radar"){{
            requirements(Category.turret, BuildVisibility.fogOnly, with(CPItems.calbet, 100, Items.graphite, 150));
            outlineColor = Color.valueOf("2e2f34");
            health = 550;
            fogRadius = 30;
            size = 2;
            consumePower(2f);
        }};
        trigger = new PowerTurret("trigger"){{
            requirements(Category.turret, with(CPItems.calbet, 100, Items.graphite, 70, CPItems.silinor, 100));
            scaledHealth = 230;
            armor = 10f;
            size = 2;
            range = 120f;
            fogRadiusMultiplier = 0.1f;
            reload = 75f;
            shootY = 4f;
            minWarmup = 0.9f;
            shootSound = Sounds.lasershoot;
            outlineColor = Color.valueOf("342937");
            consumePower(6f);

            shoot = new ShootHelix();

            shootType = new RailBulletType(){{
                damage = 15;
                length = 120f;
                hitColor = Color.valueOf("cbdbfc");
                lineEffect = Fx.chainLightning;
                hitEffect = pierceEffect = endEffect = new WaveEffect(){{
                    colorFrom = colorTo = Pal.surge;
                    sizeTo = 3f;
                    strokeFrom = 4f;
                    lifetime = 10f;
                }};
                shootEffect = Fx.shootTitan;
                pierce = false;
                status = CPStatuses.target;
            }};

            cooldownTime = reload / 2f;
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-side"){{
                            progress = PartProgress.recoil;
                            mirror = true;
                            moveRot = 6f;
                            moveX = 1.3f;
                        }}
                );
            }};
        }};
        crusher = new PowerTurret("crusher"){{
            requirements(Category.turret, with(CPItems.pelner, 100, Items.graphite, 70, CPItems.silinor, 100));
            scaledHealth = 230;
            armor = 10f;
            size = 2;
            range = 120f;
            fogRadiusMultiplier = 0.1f;
            reload = 30f;
            minWarmup = 0.9f;
            shootSound = Sounds.lasershoot;
            outlineColor = Color.valueOf("342937");
            heatRequirement = 2f;
            maxHeatEfficiency = 2f;


            shoot = new ShootAlternate(4f){{
                shots = 4;
            }};
            inaccuracy = 20f;

            shootType = new LaserBulletType(5){{
                colors = new Color[]{Color.valueOf("ff0000"), Color.valueOf("ff864f"), Color.white};

                hitEffect = Fx.hitLancer;
                hitColor = Color.valueOf("ff864f");
                hitSize = 4;
                lifetime = 7f;
                drawSize = 400f;
                collidesGround = false;
                length = 150f;
                width = 7f;
                ammoMultiplier = 1f;
                pierceCap = 1;
                pierceArmor = true;
            }};

            cooldownTime = reload / 1.5f;
            drawer = new DrawTurret("reinforced-"){{
                parts.add(new RegionPart("-side"){{
                              progress = PartProgress.recoil;
                              mirror = true;
                              moveRot = -6f;
                              moveX = 1.3f;
                          }}
                );
                for(int i = 2; i > 0; i--){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + i){{
                        progress = PartProgress.recoil;
                        recoilIndex = f - 1;
                        under = true;
                        moveY = -2f;
                    }});
                }
            }};
        }};
        twice = new ItemTurret("twice"){{
            requirements(Category.turret, with(Items.beryllium, 150, Items.silicon, 150, Items.graphite, 250));

            Effect sfe = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);

            ammo(
                    CPItems.calbet, new BasicBulletType(8f, 95){{
                        width = 13f;
                        height = 19f;
                        hitSize = 7f;
                        shootEffect = Fx.randLifeSpark;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 1;
                        reloadMultiplier = 1f;
                        pierceCap = 3;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.tungstenShot;
                        frontColor = Color.white;
                        trailWidth = 2.2f;
                        trailLength = 11;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;
                    }}
            );

            coolantMultiplier = 6f;
            shootSound = Sounds.shootAlt;

            targetUnderBlocks = false;
            shake = 1f;
            ammoPerShot = 2;
            drawer = new DrawTurret("reinforced-"){{
                parts.add(
                        new RegionPart("-back"){{
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("cbdbfc");
                            moveX = 3f;
                            moveY = -3f;
                            mirror = true;
                        }},
                        new RegionPart("-side"){{
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("cbdbfc");
                            moveX = 1f;
                            moveY = -2f;
                            moveRot = -2f;
                            mirror = true;
                        }}
                );
            }};
            shootY = -2;
            outlineColor = Pal.darkOutline;
            size = 3;
            envEnabled |= Env.space;
            reload = 40f;
            recoil = 2f;
            range = 190;
            shootCone = 3f;
            scaledHealth = 180;
            rotateSpeed = 2f;
            researchCostMultiplier = 0.05f;

            coolant = consume(new ConsumeLiquid(Liquids.water, 15f / 60f));
        }};

        //units

        //defense

        //storage

        markOne = new CoreBlock("mark-1"){{
            requirements(Category.effect, with(CPItems.calbet, 800, CPItems.pelner, 1000, Items.graphite, 300));

            alwaysUnlocked = true;

            isFirstTier = true;
            health = 3500;
            itemCapacity = 2000;
            size = 3;
            armor = 5f;

            unitType = CPUnitTypes.thor;
            unitCapModifier = 12;
        }};
        keeper = new StorageBlock("keeper"){{
            requirements(Category.effect, with(CPItems.calbet, 300, CPItems.hafur, 500, Items.graphite, 50));
            health = 3000;
            size = 3;
            itemCapacity = 600;
            coreMerge = false;
        }};

        //transport

        isolatedDuct = new Duct("isolated-duct"){{
            requirements(Category.distribution, with(CPItems.calbet, 1));
            health = 100;
            speed = 9f;
            armored = true;
        }};
        isolatedJunction = new Junction("reinforced-junction"){{
            requirements(Category.distribution, with(CPItems.calbet, 3));
            speed = 26;
            capacity = 6;
            health = 140;
            buildCostMultiplier = 6f;
            squareSprite = false;
        }};
        isolatedRouter = new ImprovedDuctRouter("reinforced-router"){{
            requirements(Category.distribution, with(CPItems.calbet, 3));
            buildCostMultiplier = 4f;
        }};
        isolatedSorter = new Sorter("reinforced-sorter-inv"){{
            requirements(Category.distribution, with(CPItems.calbet, 2, Items.copper, 2));
            buildCostMultiplier = 3f;
            invert = true;
        }};
        isolatedOverflowGate = new OverflowGate("reinforced-overflow-gate"){{
            requirements(Category.distribution, with(CPItems.calbet, 2, Items.copper, 3));
            buildCostMultiplier = 3f;
        }};
        isolatedUnderflowGate = new OverflowGate("reinforced-underflow-gate"){{
            requirements(Category.distribution, with(CPItems.calbet, 2, Items.copper, 3));
            buildCostMultiplier = 3f;
            invert = true;
        }};
        isolatedBridge = new BufferedItemBridge("reinforced-bridge"){{
            requirements(Category.distribution, with(CPItems.calbet, 6, Items.copper, 4));
            fadeIn = moveArrows = false;
            range = 5;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 60;
            itemCapacity = 6;
        }};

        //liquid

        copperConduit = new ArmoredConduit("copper-conduit"){{
            requirements(Category.liquid, with(Items.copper, 3, CPItems.silinor, 1));
            liquidCapacity = 20f;
            liquidPressure = 2f;
            health = 200;
        }};
        copperJunction = new LiquidJunction("copper-junction"){{
            requirements(Category.liquid, with(Items.copper, 5, CPItems.silinor, 3));
            solid = false;
        }};
        copperRouter = new LiquidRouter("copper-liquid-router"){{
            requirements(Category.liquid, with(Items.copper, 5, CPItems.silinor, 3));
            liquidCapacity = 30f;
            underBullets = true;
            solid = false;
        }};
        copperBridge = new LiquidBridge("copper-bridge"){{
            requirements(Category.liquid, with(Items.copper, 7, CPItems.silinor, 4));
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 5;
            hasPower = false;
        }};
        copperLiquidTank = new LiquidRouter("copper-liquid-tank"){{
            requirements(Category.liquid, with(Items.copper, 1, CPItems.silinor, 1));
            liquidCapacity = 1000f;
            size = 2;
            solid = true;
            squareSprite = false;
        }};

        //production

        highFrequencyDrill = new Drill("highfrequency-drill"){{
            requirements(Category.production, with(CPItems.calbet, 100, Items.graphite, 60, CPItems.pelner, 90));
            health = 500;
            tier = 5;
            drillTime = 250;
            size = 3;
            updateEffect = Fx.pulverizeRed;
            updateEffectChance = 0.05f;
            drillEffect = Fx.mineHuge;
            drawRim = true;
            heatColor = Color.valueOf("ffffff");
            warmupSpeed = 0.001f;
            rotateSpeed = 12f;
            itemCapacity = 15;

            consumeLiquid(Liquids.nitrogen, 8f/60f).boost();
        }};

        heatDrill = new Drill("heat-drill"){{
            requirements(Category.production, with(CPItems.calbet, 50, Items.graphite, 60, CPItems.pelner, 90));
            health = 300;
            tier = 5;
            drillTime = 60 * 5;
            liquidBoostIntensity = 1f;
            size = 2;
            drillEffect = Fx.vapor;
            warmupSpeed = 0.01f;
            rotateSpeed = 8f;
            itemCapacity = 15;
        }};

        wallHammer = new BurstWallDrill("wall-hammer"){{
            requirements(Category.production, with(CPItems.calbet, 100, Items.graphite, 60, CPItems.pelner, 90));
            health = 500;
            size = 2;
        }};

        nitrogenConcentrator = new GenericCrafter("atmospheric-concentrator"){{
            requirements(Category.production, with(CPItems.calbet, 15));
            size = 2;
            hasLiquids = true;

            drawer = new DrawMulti(new DrawRegion("-bottom"),
                    new DrawBlurSpin("-rotator", 0.6f * 9f){{
                        blurThresh = 0.01f;
                    }},
                    new DrawDefault(),
                    new DrawParticles(){{
                        color = Color.valueOf("d4f0ff");
                        alpha = 0.6f;
                        particleSize = 4f;
                        particles = 10;
                        particleRad = 12f;
                        particleLife = 140f;
                    }});

            researchCostMultiplier = 1.1f;
            itemCapacity = 0;
            liquidCapacity = 12f;
            consumePower(10f/60f);
            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.06f;

            outputLiquid = new LiquidStack(Liquids.nitrogen, 3f / 60f);
        }};

        //power

        powerTower = new PowerNode("power-tower"){{
            requirements(Category.power, with(Items.titanium, 5, Items.lead, 10, Items.silicon, 3));
            health = 120;
            size = 1;
            maxNodes = 2;
            laserRange = 20f;
        }};

        //crafting

        impactPress = new HeatCrafter("heat-press"){{
            requirements(Category.crafting, with(CPItems.calbet, 150, CPItems.pelner, 40));
            health = 800;
            size = 3;
            hasItems = true;
            ambientSound = Sounds.smelter;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawRegion("-cap"), new DrawArcSmelt(), new DrawDefault(), new DrawHeatInput());
            itemCapacity = 20;

            consumeItems(with(Items.coal, 3));
            heatRequirement = 4f;

            outputItem = new ItemStack(Items.graphite, 1);
            craftTime = 90f;
        }};

        //environment
                        //floor
        chromiumStone = new Floor("chromium-stone"){{
            attributes.set(Attribute.water, -1f);
            variants = 4;
        }};
        chromiumStoneRough = new Floor("chromium-stone-rough"){{
            attributes.set(Attribute.water, -1f);
            variants = 4;
        }};
        caveStone = new Floor("cave-stone"){{
            attributes.set(Attribute.water, -1f);
            variants = 4;
        }};
        caveSand = new Floor("cave-sand"){{
            attributes.set(Attribute.water, -1f);
            variants = 4;
        }};
        caveVent = new SteamVent("cave-vent"){{
            parent = blendGroup = caveStone;
            attributes.set(Attribute.steam, 1f);
        }};
        crystalicFloor = new Floor("crystalic-floor"){{
            attributes.set(Attribute.water, -1f);
            variants = 4;
        }};
        ammonia = new Floor("pooled-ammonia"){{
            drownTime = 230f;
            status = StatusEffects.freezing;
            statusDuration = 400f;
            speedMultiplier = 0.3f;
            variants = 0;
            liquidDrop = CPLiquids.ammonia;
            isLiquid = true;
            cacheLayer = CacheLayer.water;

            emitLight = true;
            lightRadius = 40f;
            lightColor = Color.valueOf("ddfff4");
        }};
        deepCoral = new Floor("deep-coral"){{
            attributes.set(Attribute.water, 1.5f);
            variants = 8;
        }};
        deepCoralDense = new Floor("deep-coral-dense"){{
            attributes.set(Attribute.water, 1.5f);
            variants = 8;
        }};
                        //walls
        chromiumStoneWall = new StaticWall("chromium-stone-wall"){{
            chromiumStone.asFloor().wall = chromiumStoneRough.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.1f);
            variants = 3;
        }};
        chromiumBoulderWall = new StaticWall("chromium-boulder-wall"){{
            chromiumStone.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.1f);
            variants = 3;
        }};
        caveStoneWall = new StaticWall("cave-stone-wall"){{
            caveStone.asFloor().wall = caveSand.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.7f);
            variants = 3;
        }};
        caveBoulderWall = new StaticWall("cave-boulder-wall"){{
            caveStone.asFloor().wall = caveSand.asFloor().wall = this;
            attributes.set(Attribute.sand, 0.7f);
            variants = 3;
        }};
        crystalicWall = new StaticWall("crystalic-wall"){{
            crystalicFloor.asFloor().wall = this;
            variants = 4;
        }};
        deepCoralWall = new StaticWall("deep-coral-wall"){{
            deepCoral.asFloor().wall = this;
            variants = 4;
        }};

        //props

        caveBoulder = new Prop("cave-boulder"){{
            variants = 4;
            caveStone.asFloor().decoration = caveSand.asFloor().decoration = this;
        }};
        caveBoulders = new TallBlock("cave-boulders"){{
            variants = 2;
            clipSize = 96f;
            shadowAlpha = 0.5f;
            shadowOffset = -1.5f;
        }};
        moonlightPrime = new TreeBlock("moonlight-prime-dead");
        duneCluster = new TallBlock("dune-cluster"){{
            variants = 2;
            clipSize = 208f;
            shadowAlpha = 0.7f;
            shadowOffset = -2f;
        }};
        deepCrystals = new TallBlock("deep-crystals"){{
            variants = 2;
            clipSize = 128f;
            shadowAlpha = 0.6f;
            shadowOffset = -2f;
        }};

        //ores

        oreCalbet = new OreBlock("ore-calbet"){{
            itemDrop = CPItems.calbet;
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
        }};
        orePelner = new OreBlock("ore-pelner"){{
            itemDrop = CPItems.pelner;
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 23.47619f;
        }};

        //wall ores

        coalWall = new StaticWall("coal-wall"){{
            itemDrop = Items.coal;
            variants = 3;
        }};
    }
}