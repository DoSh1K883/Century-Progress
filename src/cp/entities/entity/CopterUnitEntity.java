package cp.entities.entity;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import cp.content.CPUnitTypes;
import cp.type.draw.Rotor;
import cp.entities.units.CopterUnitType;
import mindustry.content.Fx;
import mindustry.entities.EntityCollisions;
import mindustry.gen.UnitEntity;
import cp.type.draw.Rotor.RotorMount;
import cp.type.draw.Rotor;
import mindustry.type.UnitType;

public class CopterUnitEntity extends UnitEntity {
    public RotorMount[] rotors;
    public float rotSpeedScl = 1f;

    @Override
    public String toString() {
        return "CopterUnit#" + id;
    }

    /** @author GlennFolker#6881 */
    @Override
    public void setType(UnitType type) {
        super.setType(type);
        if (type instanceof CopterUnitType vint) {
            rotors = new RotorMount[vint.rotors.size];
            for (int i = 0; i < rotors.length; i++) {
                Rotor rotorType = vint.rotors.get(i);
                rotors[i] = new RotorMount(rotorType);
            }
        }
    }

    @Override
    public EntityCollisions.SolidPred solidity(){
        CopterUnitType type = (CopterUnitType) this.type;
        if(type.hover){
            return isFlying() ? null : EntityCollisions::solid;
        }
        else {
            return null;
        }
    }

    @Override
    public void update() {
        super.update();
        CopterUnitType type = (CopterUnitType) this.type;
        float rX = x + Angles.trnsx(rotation - 90, type.fallSmokeX, type.fallSmokeY);
        float rY = y + Angles.trnsy(rotation - 90, type.fallSmokeX, type.fallSmokeY);

        // Slows down rotor when dying
        if (dead || health() <= 0) {
            rotation += Time.delta * (type.spinningFallSpeed * vel().len()) * Mathf.signs[id % 2];
            if (Mathf.chanceDelta(type.fallSmokeChance)) {
                Fx.fallSmoke.at(rX, rY);
                Fx.burning.at(rX, rY);
            }
            rotSpeedScl = Mathf.lerpDelta(rotSpeedScl, 0f, type.rotorDeathSlowdown);
        } else {
            rotSpeedScl = Mathf.lerpDelta(rotSpeedScl, 1f, type.rotorDeathSlowdown);
        }

        for (RotorMount rotor : rotors) {
            rotor.rotorRot += ((rotor.rotor.rotorSpeed * rotSpeedScl) + rotor.rotor.minimumRotorSpeed) * Time.delta;
        }
        type.fallSpeed = 0.006f;
    }
}

