package jfx;

import java.io.*;

public class GameFlag1 implements Serializable
{
	private String element;
	int servelost,upward,bat_hit,serve,score1,score2,fk,f,go;
	double centerX,centerY,radiusX,g,h;

	public GameFlag1(String element,double l,double m,int a,int b,int c,int d,int e,int f,int g,int h,int i,double centerX,double centerY,double radiusX) {
		this.element=element;
        this.g=l;
        this.h=m;
		this.servelost=a;
		this.go=b;
        this.upward=c;
        this.bat_hit=d;
        this.serve=e;
        this.score1=f;
        this.score2=g;
        this.fk=h;
        this.f=i;
        this.radiusX=radiusX;
        this.centerX=centerX;
        this.centerY=centerY;
	}

	public String getElement() {
		return this.element;
	}
    public double getL() {
        return this.g;
    }
    public double getM() {
        return this.h;
    }
    public int getServelost() {
        return this.servelost;
    }
    public int getGo() {
        return this.go;
    }
    public int getUpward() {
        return this.upward;
    }
    public int getBat_hit() {
        return this.bat_hit;
    }
    public int getServe() {
        return this.serve;
    }
    public int getScore1() {
        return this.score1;
    }
    public int getScore2() {return this.score2;}
    public int getFk() {return this.fk;}
    public int getF() {return this.f;}
    public double getCenterY(){ return this.centerY;}
    public double getCenterX() { return this.centerX;}
    public double getRadiusX() { return this.radiusX;}
}