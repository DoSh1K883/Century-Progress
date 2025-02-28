package cp.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import cp.entities.units.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import cp.content.*;
import cp.entities.entity.*;
import cp.type.draw.Rotor;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class CPUnitTypes {
    public static UnitType
    //mechs
            thor,
    //treads
            gust, juggernaut,

    //legs
            solifugae, focus,

    //air
            perseus, resurgense, siege, mineDrone;

    public static void load() {

        //mechs

        mineDrone = new AliothUnitType("thor-drone"){{
            controller = u -> new MinerAI();
            constructor = UnitEntity::create;

            defaultCommand = UnitCommand.mineCommand;

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            range = 50f;
            isEnemy = false;

            engineSize = 0;
            setEnginesMirror(
                    new UnitEngine(2f, -2f, 2f, 315f)
            );

            mineTier = 4;
            mineSpeed = 1.2f;
        }};

        thor = new AliothUnitType("thor"){{
            constructor = MechUnit::create;
            health = 740;
            armor = 10f;
            hitSize = 16f;
            speed = 0.85f;
            rotateSpeed = 2.5f;
            mechLegColor = Color.valueOf("2e2f34");

            canBoost = true;
            boostMultiplier = 1.5f;
            mineSpeed = 4.5f;
            mineTier = 3;
            buildSpeed = 0.5f;

            weapons.add(new RepairBeamWeapon("cp-colossus-healer"){{
                x = 0; y = -2.5f;
                targetBuildings = true;
                repairSpeed = 3f;
                recentDamageMultiplier = 0.2f;
                autoTarget = false;
                controllable = true;
                mirror = false;

                laserColor = Color.valueOf("cbdbfc");
                healColor = Color.valueOf("cbdbfc");

                bullet = new BulletType(){{
                    maxRange = 55f;
                }};
            }},
            new Weapon(){{
                x = 0; y = -2.5f;
                recoil = 0f;
                reload = 5f;
                mirror = false;
                rotate = true;

                bullet = new ArtilleryBulletType(55f/4f, 5, "shell"){{
                    lifetime = 4f;
                    width = height = 0;
                    collides = true;
                    collidesTiles = true;
                }};
            }});
        }};

        //legs

        solifugae = new AliothUnitType("solifugae"){{
            constructor = LegsUnit::create;
            health = 4300;
            armor = 6f;
            hitSize = 19f;
            speed = 0.7f;
            rotateSpeed = 2f;

            legCount = 8;
            legLength = 18f;
            legGroupSize = 4;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -3f;
            legBaseOffset = 7f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.9f;
            legMoveSpace = 1f;
            hovering = true;

            shadowElevation = 0.2f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = true;
            researchCostMultiplier = 0f;

            abilities.add(new RepairFieldAbility(3f, 300f, 45f));

            weapons.add(new Weapon("cp-solifugae-weapon"){{
                x = 7f; y = -10f;
                shootSound = Sounds.largeCannon;
                recoil = 4f;
                reload = 90f;
                mirror = true;
                alternate = false;
                rotate = true;
                rotateSpeed = 0.4f;
                rotationLimit = 25f;
                shootY = 10f;

                bullet = new BasicBulletType(){{
                    damage = 500;
                    speed = 15f;
                    lifetime = 30f;
                    shootEffect = hitEffect = Fx.colorSpark;
                    hitColor = trailColor = backColor = Color.valueOf("cbdbfc");
                    frontColor = Color.white;
                    height = width = 8f;

                    trailEffect = new MultiEffect(Fx.railHit, Fx.artilleryTrailSmoke);
                    trailChance = 0.5f;
                    trailLength = 12;
                    trailWidth = 2.4f;
                    trailRotation = true;
                }};
            }});
        }};

        //treads
        gust = new AliothUnitType("gust"){{
            constructor = TankUnit::create;
            health = 520;
            armor = 3f;
            hitSize = 12f;
            speed = 1.35f;
            rotateSpeed = 6f;
            omniMovement = false;
            treadRects = new Rect[]{new Rect(4f - 64f/2f, 6f - 64f/2f, 12f, 50f)};

            weapons.add(new Weapon("cp-gust-weapon"){{
                x = 0f; y = -1.5f;
                shootSound = Sounds.spark;
                recoil = 0f;
                reload = 60f;
                mirror = false;
                rotate = true;
                rotateSpeed = 7f;

                shootY = 1.5f;
                shoot = new ShootPattern(){{
                    shotDelay = 5f;
                    shots = 6;
                }};
                inaccuracy = 2f;

                bullet = new BasicBulletType(){{
                    sprite = "cp-razor";
                    damage = 10;
                    lifetime = 30f;
                    speed = 4f;
                    height = 18f;
                    width = 13f;

                    shootEffect = Fx.pulverizeSmall.wrap(Color.valueOf("f7e97e"), 3f);
                    frontColor = Color.white;
                    backColor = hitColor = Color.valueOf("d1efff");
                    trailColor = Color.valueOf("d1efff7e");
                    trailLength = 20;
                    trailWidth = 2f;
                }};
            }});
        }};
        juggernaut = new AliothUnitType("juggernaut-shields"){{
            constructor = TankUnit::create;
            health = 4000;
            armor = 20f;
            hitSize = 28f;
            speed = 0.63f;
            rotateSpeed = 1.5f;
            omniMovement = false;
            treadRects = new Rect[]{new Rect(33f - 160f/2f, 24f - 160f/2f, 21f, 112f)};

            weapons.add(new Weapon("cp-juggernaut-weapon-shields"){{
                x = 0f; y = 0f;
                shootSound = Sounds.largeCannon;
                recoil = 3f;
                reload = 80f;
                mirror = false;
                rotate = true;
                rotateSpeed = 3f;

                shootY = 72f/4f;

                bullet = new BasicBulletType(){{
                    sprite = "cp-adv-missile";
                    lifetime = 18f;
                    drag = 0.01f;
                    speed = 15f;
                    height = 18f;
                    width = 11f;

                    shootEffect = Fx.pulverizeSmall.wrap(Color.valueOf("f7e97e"), 3f);
                    frontColor = Color.white;
                    backColor = hitColor = Color.valueOf("cbdbfc");
                    trailColor = Color.valueOf("cbdbfc");
                    hitEffect = despawnEffect = Fx.blastExplosion;
                    trailEffect = Fx.hitSquaresColor;
                    trailRotation = true;
                    trailInterval = 1.5f;
                    trailLength = 20;
                    trailWidth = 2.5f;

                    fragBullets = 9;
                    fragBullet = new BasicBulletType(5f, 30){{
                        sprite = "missile-large";
                        width = 5f;
                        height = 8f;
                        lifetime = 15f;
                        hitSize = 4f;
                        pierceCap = 1;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Color.valueOf("cbdbfc");
                        frontColor = Color.white;
                        trailWidth = 1.7f;
                        trailLength = 3;
                        drag = 0.01f;
                        despawnEffect = hitEffect = Fx.hitBulletColor;
                    }};
                }};
            }});
        }};

        //air
        perseus = new AliothUnitType("perseus"){{
            aiController = BuilderAI::new;
            isEnemy = false;

            mineSpeed = 4.5f;
            mineTier = 3;
            buildSpeed = 0.5f;

            constructor = ElevationMoveUnit::create;
            hovering = true;
            shadowElevation = 0.1f;

            health = 440;
            armor = 5f;
            hitSize = 12f;
            speed = 2.5f;
            drag = 0.08f;
            rotateSpeed = 5f;
            engineSize = 0;

            for(float f : new float[]{-3f, 3f}){
                parts.add(new HoverPart(){{
                    x = 3.9f;
                    y = f;
                    mirror = true;
                    radius = 6f;
                    phase = 90f;
                    stroke = 2f;
                    layerOffset = -0.001f;
                    color = Color.valueOf("cbdbfc");
                }});
            }

            weapons.add(new Weapon("cp-perseus-weapon"){{
                x = 0f; y = 0f;
                shootSound = Sounds.blaster;
                recoil = 1f;
                reload = 90f;
                mirror = false;
                rotate = false;
                top = false;
                layerOffset = -0.0000000000001f;
                shootY = 2f;

                bullet = new BulletType(){{
                    shootEffect = new MultiEffect(Fx.shootBigColor, new Effect(9, e -> {
                        color(Color.white, e.color, e.fin());
                        stroke(0.7f + e.fout());
                        Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                        Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                    }), new WaveEffect(){{
                        colorFrom = colorTo = Color.valueOf("cbdbfc");
                        sizeTo = 15f;
                        lifetime = 12f;
                        strokeFrom = 3f;
                    }});

                    smokeEffect = Fx.shootBigSmoke2;
                    shake = 2f;
                    speed = 0f;
                    keepVelocity = false;
                    inaccuracy = 2f;

                    spawnUnit = new MissileUnitType("perseus-missile"){{
                        trailColor = engineColor = Color.valueOf("cbdbfc");
                        engineSize = 1.75f;
                        engineLayer = Layer.effect;
                        speed = 3f;
                        maxRange = 6f;
                        lifetime = 60f * 0.6f;
                        outlineColor = Pal.darkOutline;
                        health = 55;
                        lowAltitude = true;

                        parts.add(new FlarePart(){{
                            progress = PartProgress.life.slope().curve(Interp.pow2In);
                            radius = 0f;
                            radiusTo = 35f;
                            stroke = 3f;
                            rotation = 45f;
                            y = -5f;
                            followRotation = true;
                        }});

                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(50f, 8f){{
                                shootEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Pal.techBlue, 24f), new WaveEffect(){{
                                    colorFrom = colorTo = Color.valueOf("cbdbfc");
                                    sizeTo = 40f;
                                    lifetime = 12f;
                                    strokeFrom = 4f;
                                }});
                            }};
                        }});
                    }};
                }};
            }});
        }};
        resurgense = new AliothUnitType("colossus"){{
            aiController = FlyingFollowAI::new;

            constructor = UnitEntity::create;
            health = 12000;
            armor = 15f;
            hitSize = 46f;
            speed = 0.8f;
            drag = 0.07f;
            rotateSpeed = 2.1f;
            accel = 0.1f;
            shadowElevation = 2f;
            engineSize = 0;
            flying = true;

            setEnginesMirror(
                    new UnitEngine(84 / 4f, -79 / 4f, 6f, 330f),
                    new UnitEngine(51 / 4f, -97 / 4f, 5f, 315f)
            );

            abilities.add(new SuppressionFieldAbility(){{
                orbRadius = 5f;
                particleSize = 3f;
                y = 10f;
                particles = 10;
                active = false;

                color = Color.valueOf("cbdbfc");
                particleColor = Color.valueOf("cbdbfc");
            }},
            new ShieldArcAbility(){{
                radius = 130f/4f;
                regen = 2f;
                max = 5000f;
                cooldown = 5f * 60f;
                angle = 180f;
            }});

            for(int i : Mathf.signs){
                abilities.add(new SuppressionFieldAbility(){{
                    orbRadius = 5f;
                    particleSize = 3f;
                    y = -32f / 4f;
                    x = 43f * i / 4f;
                    particles = 10;
                    active = false;

                    color = Color.valueOf("cbdbfc");
                    particleColor = Color.valueOf("cbdbfc");
                }});
            }

            weapons.add(new Weapon("cp-colossus-weapon"){{
                x = 73f/4f; y = 13f/4f;
                shootSound = Sounds.blaster;
                reload = 20f;
                mirror = true;
                layerOffset = -20f;
                rotate = true;
                rotateSpeed = 0.4f;
                rotationLimit = 40f;
                shootY = 1f;

                bullet = new BasicBulletType(){{
                    sprite = "large-orb";
                    lifetime = 30f;
                    speed = 8f;
                    shootEffect = Fx.colorSpark;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = trailColor = backColor = Color.valueOf("cbdbfc");
                    frontColor = Color.white;
                    height = 17f;
                    width = 15f;
                    trailLength = 12;
                    trailWidth = 2.2f;
                    despawnEffect = hitEffect = Fx.shootSmokeTitan;

                    pierce = true;
                    pierceBuilding = true;
                    status = StatusEffects.freezing;

                    fragOnHit = false;
                    fragBullets = 3;
                    fragRandomSpread = 0f;
                    fragSpread = 360f;
                    fragAngle = 180f;
                    fragBullet = new RailBulletType(){{
                        damage = 50;
                        length = 220f;
                        hitColor = Color.valueOf("cbdbfc");
                        lineEffect = Fx.chainLightning;
                        hitEffect = new WaveEffect(){{
                            colorFrom = colorTo = Color.valueOf("cbdbfc");
                            sizeTo = 4f;
                            strokeFrom = 4f;
                            lifetime = 10f;
                        }};
                        shootEffect = Fx.shootTitan;
                        pierceCap = 2;
                    }};
                }};
            }},
            new RepairBeamWeapon("cp-colossus-healer"){{
                x = 47f/4f; y = 22f/4f;
                targetBuildings = true;
                repairSpeed = 1.4f;
                recentDamageMultiplier = 1.3f;
                bullet = new BulletType(){{
                    maxRange = 80f;
                }};
            }}
            );
        }};
        siege = new CopterUnitType("siege"){{
            constructor = CopterUnitEntity::new;
            health = 1150;
            armor = 15f;
            hitSize = 15f;
            speed = 1.5f;
            rotateSpeed = 3f;
            accel = 0.07f;
            drag = 0.04f;

            outlineColor = Color.valueOf("2e2f34");
            rotors.add(
                    new Rotor(name + "-rotor"){{
                        rotorLayer = 0.5f;
                        rotorSpeed = -10f;
                        x = 0f;
                        y = 0f;
                        rotorNotRadial = false;
                        rotorRadial = true;
                        drawRotorTop = true;
                        drawGlow = true;
                        bladeCount = 5;
                    }}
            );
        }};
        focus = new AliothUnitType("focus"){{
            aiController = FlyingFollowAI::new;

            constructor = UnitEntity::create;
            health = 12000;
            armor = 15f;
            hitSize = 20f;
            speed = 1.5f;
            drag = 0.07f;
            rotateSpeed = 2.1f;
            accel = 0.1f;
            shadowElevation = 2f;
            engineSize = 0;
            engineOffset = 3;
            flying = true;

            lowAltitude = true;

            weapons.add(new Weapon("cp-focus-weapon"){{
                x = -24f/4f; y = -2f/4f;
                shootSound = Sounds.blaster;
                reload = 45f;
                mirror = true;
                rotate = true;
                rotationLimit = 40f;
                shootY = 1f;

                bullet = new BasicBulletType(){{
                    lifetime = 60f;
                    speed = 4f;
                    shootEffect = Fx.colorSpark;
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = trailColor = backColor = Color.valueOf("cbdbfc");
                    frontColor = Color.white;
                    height = 11f;
                    width = 8f;
                    trailLength = 12;
                    trailWidth = 0.8f;

                    /*
                    fragOnHit = false;
                    fragBullets = 3;
                    fragRandomSpread = 0f;
                    fragSpread = 360f;
                    fragAngle = 180f;
                    fragBullet = new RailBulletType(){{
                        damage = 50;
                        length = 220f;
                        hitColor = Color.valueOf("cbdbfc");
                        lineEffect = Fx.chainLightning;
                        hitEffect = new WaveEffect(){{
                            colorFrom = colorTo = Color.valueOf("cbdbfc");
                            sizeTo = 4f;
                            strokeFrom = 4f;
                            lifetime = 10f;
                        }};
                        shootEffect = Fx.shootTitan;
                        pierceCap = 2;
                    }};
                     */
                }};
            }});
        }};
    }
}