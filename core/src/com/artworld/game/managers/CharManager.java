package com.artworld.game.managers;

import com.artworld.game.entities.Entity;

import static com.artworld.game.utils.Constants.*;


/**
 * Created by Roma on 06.10.2017.
 */

public class CharManager {
    private Entity entity;
    private float defHp, defMp, defDmg, defArmor;

    private float factorHp = 1.25f, factorMp= 1.18f, factorDmg= 1.19f, factorArm= 1.17f,  bonusRegenHp = 0.03f, bonusRegenMp = 0.01f;

    private float countTimeRegenHp, countTimeRegenMp;

    public CharManager(Entity entity, float defHp, float defMp, float defDmg, float defArmor) {

        this.entity = entity;
        this.defHp = defHp;
        this.defMp = defMp;
        this.defDmg = defDmg;
        this.defArmor = defArmor;

    }


    private void checkLvlUp(){
        while(entity.getExp()>=getExpByLvl(entity.getLvl() + 1))
                entity.lvlUp();

    }
    private void checkLvlDown(){
        while(entity.getExp()<getExpByLvl(entity.getLvl()))
            entity.lvlDown();
    }
    private float regenBonusHp(float bonus){
        return  (getMaxHp() * (bonus / 100f));
    }
    private float regenBonusMp(float bonus){
        return  (getMaxMp() * (bonus / 100f));
    }

    public void addExp(float exp){
        if(entity.getExp() + exp>getExpByLvl(MAXLVL)){
            entity.setExp(getExpByLvl(MAXLVL));
        }else{
            entity.setExp(entity.getExp()+exp);
        }
        checkLvlUp();
    }
    public void takeExp(float exp){
        if(entity.getExp() - exp < 0){
            entity.setExp(0);
        }else{
            entity.setExp(entity.getExp()- exp);
        }
        checkLvlDown();
    }

    public void addHp(float hp){
        if(entity.getHp() + hp > getMaxHp()){
            entity.setHp(getMaxHp());
        }else{
            entity.setHp(entity.getHp()+hp);
        }
    }
    public void takeHp(float hp){
        if(entity.getHp()-hp < 0){
            entity.setHp(0);
        }else{
            entity.setHp(entity.getHp()-hp);
        }
    }
    public void setHp(float hp){
        if(hp > getMaxHp()){
            entity.setHp(getMaxHp());
        }else{
            entity.setHp(hp);
        }
    }

    public void addMp(float mp){
        if(entity.getMp() + mp > getMaxMp()){
            entity.setMp(getMaxMp());
        }else{
            entity.setMp(entity.getMp()+mp);
        }
    }
    public void takeMp(float mp){
        if(entity.getMp()-mp < 0){
            entity.setMp(0);
        }else{
            entity.setMp(entity.getMp()-mp);
        }
    }
    public void setMp(float mp){
        if(mp > getMaxMp()){
            entity.setMp(getMaxMp());
        }else{
            entity.setMp(mp);
        }
    }

    public void regenHp(boolean isRegenHp, float delta){
        countTimeRegenHp += delta;
        if(countTimeRegenHp >= 1) {
            if (isRegenHp)
                addHp(1 + regenBonusHp(bonusRegenHp * getLvl()));
            countTimeRegenHp = 0;
        }
    }
    public void regenMp(boolean isRegenMp, float delta) {
        countTimeRegenMp += delta;
        if (countTimeRegenHp >= 1) {
            if (isRegenMp)
                addMp(1 + regenBonusMp(bonusRegenMp *  getLvl()));
            countTimeRegenMp = 0;
        }
    }

    public float getExpByLvl(float lvl){
        return (int) (BASICEXP * Math.pow((double)lvl, FACTOREXP)-BASICEXP);
    }
    public float getLvlByExp(float exp){
        if(exp<0)
            return 1;
        if(exp>getExpByLvl(MAXLVL))
            return MAXLVL;
        for(int i = 1; i <= MAXLVL; i++) {
            if (exp >= getExpByLvl(i) && exp < getExpByLvl(i + 1)){
                return i;
            }
        }
        return MAXLVL;
    }
    public float getMaxHp() {
        return (float) (defHp * Math.pow(factorHp, getLvl()));
    }
    public float getMaxMp() {
        return (float) (defMp * Math.pow(factorMp, (double) entity.getLvl()));
    }
    public float getDmg() {
        return (float) (defDmg * Math.pow(factorDmg, (double) entity.getLvl()));
    }
    public float getArm() {
        return (float) (defArmor * Math.pow(factorArm, (double) entity.getLvl()));
    }
    private float getExp(){
        return entity.getExp();
    }

    public double getBonusRegenHp() {
        return bonusRegenHp;
    }
    public void setBonusRegenHp(float bonusRegenHp) {
        this.bonusRegenHp = bonusRegenHp;
    }
    public double getBonusRegenMp() {
        return bonusRegenMp;
    }
    public void setBonusRegenMp(float bonusRegenMp) {
        this.bonusRegenMp = bonusRegenMp;
    }
    /**
     *
     * @param factorHp Колличество % за уровень.
     */
    public void setFactorHp(float factorHp) {
        this.factorHp = factorHp/100+1;
    }
    /**
     *
     * @param factorMp Колличество % за уровень.
     */
    public void setFactorMp(float factorMp) {
        this.factorMp = factorMp/100+1;
    }
    /**
     *
     * @param factorDmg Колличество % за уровень.
     */
    public void setFactorDmg(float factorDmg) {
        this.factorDmg = factorDmg/100+1;
    }
    /**
     *
     * @param factorArm Колличество % за уровень.
     */
    public void setFactorArm(float factorArm) {
        this.factorArm = factorArm/100+1;
    }

    public float getGivenexp(float lvl){
        return (( getMaxHp()+ getMaxMp()) * (( getDmg() + getArm()) /  BASICEXP)) *  (lvl/MAXLVL);
    }
    private float getLvl(){
        return entity.getLvl();
    }
    public void setLvl(float lvl){
        entity.setExp(getExpByLvl(lvl));
        entity.setLvl(lvl);
    }

}
