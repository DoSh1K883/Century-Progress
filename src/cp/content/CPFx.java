package cp.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.UnitAssembler.*;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class CPFx{
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect

    shotgunLine = new Effect(40, e -> {

        if(!(e.data instanceof Vec2 v)) return;

        color(e.color);
        stroke(e.fout() * 0.9f + 0.6f);

        Fx.rand.setSeed(e.id);
        for(int i = 0; i < 7; i++){
            Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
            Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
        }

        e.scaled(14f, b -> {
            stroke(b.fout() * 1.5f);
            color(e.color);
            Lines.line(e.x, e.y, v.x, v.y);
        });
    });
    public static Effect smoothColorCircle(Color out, float rad, float lifetime){
        return new Effect(lifetime, rad * 2, e -> {
            Draw.blend(Blending.additive);
            float radius = e.fin(Interp.pow3Out) * rad;
            Fill.light(e.x, e.y, circleVertices(radius), radius, Color.clear, Tmp.c1.set(out).a(e.fout(Interp.pow5Out)));
            Drawf.light(e.x, e.y, radius * 1.3f, out, 0.7f * e.fout(0.23f));
            Draw.blend();
        }).layer(Layer.effect + 0.15f);
    }
}