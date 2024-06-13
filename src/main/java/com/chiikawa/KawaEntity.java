package com.chiikawa;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class KawaEntity extends Monster implements GeoEntity {
    private AnimatableInstanceCache cache= GeckoLibUtil.createInstanceCache(this);
    public KawaEntity(EntityType<? extends Monster> pEntityType, Level plevel){
        super(pEntityType,plevel);
    }
    public static AttributeSupplier setAttributes(){
        return  Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE,1.0F)
                .add(Attributes.MAX_HEALTH,10)
                .add(Attributes.MOVEMENT_SPEED,1.0F)
                .add(Attributes.ATTACK_SPEED,0.5F).build();
    }

    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(1,new FloatGoal(this));
        this.goalSelector.addGoal(2,new WaterAvoidingRandomStrollGoal(this,1.0D));
        this.goalSelector.addGoal(3,new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2,new MeleeAttackGoal(this,1.2D,false));
        
        this.targetSelector.addGoal(4,new NearestAttackableTargetGoal<>(this, IronGolem.class,true));
        this.targetSelector.addGoal(2,new NearestAttackableTargetGoal<>(this, Creeper.class,true));
        this.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(this, Zombie.class,true));
        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, EnderMan.class,true));
    }
    private <E extends KawaEntity> PlayState predicate(final AnimationState<E> event){
        if(event.isMoving()){
            event.setAnimation(RawAnimation.begin().thenLoop("animation.model.walk"));
            return PlayState.CONTINUE;
        }
        event.setAnimation(RawAnimation.begin().thenLoop("animation.model.attack"));
        return PlayState.CONTINUE;
    }
    private <E extends KawaEntity>PlayState attackController(final AnimationState<E> event){
        if(this.swinging){
            event.setAnimation(RawAnimation.begin().then("animation.model.attack", Animation.LoopType.PLAY_ONCE));
            this.swinging=false;
        }
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar)
    {
        controllerRegistrar.add(new AnimationController<>(this,"controller",
                0,this::predicate));
        controllerRegistrar.add(new AnimationController<>(this,"attackController",
                0,this::attackController));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    protected void playStepSound(BlockPos pos, BlockState blockIn){
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_BREAK,0.15F,1.0F);
    }
    protected SoundEvent getAmbientSound(){return chiikawamod.CHII_TAKE.get();}
    protected SoundEvent getHurtSound(DamageSource number){return chiikawamod.CHII_TAKE.get();}
    protected SoundEvent getDeathSound(){return chiikawamod.CHII_DEATH.get();}
    protected float getSoundVolume(){return 0.2F;}

}
