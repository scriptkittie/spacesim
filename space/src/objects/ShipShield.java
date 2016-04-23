package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import util.Util;

public class ShipShield {

	private static final Color MAX_SHIELD_COLOR = new Color(0.0f, 0.5f, 1.0f);
	private static final Color MIN_SHIELD_COLOR = new Color(1.0f, 0.0f, 0.0f);
	private static final float SHIELD_DECAY_RATE = 0.5f;
	private static final float SHIELD_STRENGTH = 100.0f;
	private static final float SHIELD_RECHARGE_RATE = 10.0f;
	private static final int SHIELD_SEGMENTS = 32;
	
	private float localStrength;
	private TriangulatorSpace[] tris = new TriangulatorSpace[SHIELD_SEGMENTS];
	private ShieldShape fill;
	private float shieldOpacity[] = new float[SHIELD_SEGMENTS];
	private float lastShieldHit;

	
	public ShipShield(float shieldSize){
		fill = new ShieldShape();
		localStrength = SHIELD_STRENGTH;
		for(int i = 0; i < tris.length; i++){
			tris[i] = new TriangulatorSpace();
			float s1 = i/(float)tris.length;
			float s2 = (i+1)/(float)tris.length;
			shieldOpacity[i] = 1.0f;
			s1 *= Math.PI*2;
			s2 *= Math.PI*2;
			tris[i].setPoints(0,0,shieldSize*(float)Math.cos(s1),shieldSize*(float)Math.sin(s1), shieldSize*(float)Math.cos(s2), shieldSize*(float)Math.sin(s2));
		}
	}
	
	public void reset(){
		localStrength = SHIELD_STRENGTH;
	}
	
	public void draw(Graphics g){
		if (localStrength > 0){
			for(int i = 0; i < tris.length; i++){
				g.fill(tris[i], fill);
			}
		}
	}
	
	public void update(float timestep){
		lastShieldHit -= timestep;
		localStrength = Util.lock(localStrength + timestep*SHIELD_RECHARGE_RATE, -10.0f, SHIELD_STRENGTH);
		
	}
	
	public void takeDamage(float amount){
		localStrength -= amount;
		if (localStrength < 0){
			localStrength = -100.0f;
		}
		lastShieldHit = SHIELD_DECAY_RATE;
	}
	
	public boolean isShieldDown(){
		return localStrength < 0;
	}
	
	private static final Vector2f vector_offset = new Vector2f(0,0);
	
	private class ShieldShape implements ShapeFill {
		
		private Color color;
		
		public ShieldShape(){
			color = new Color(1.0f, 1.0f, 1.0f);
		}
		
		public Color colorAt(Shape shape, float x, float y) {
			float shieldValue = Util.lock(localStrength/SHIELD_STRENGTH, 0.0f, 1.0f);
			if (Math.abs(x) > 0.0f || Math.abs(y) > 0.0f){
				color.a = Util.lock(0.4f * (lastShieldHit/SHIELD_DECAY_RATE), 0.0f, 1.0f);
			} else {
				color.a = 0.0f;
			}
			color.r = shieldValue*MAX_SHIELD_COLOR.r + (1.0f - shieldValue)*MIN_SHIELD_COLOR.r;
			color.g = shieldValue*MAX_SHIELD_COLOR.g + (1.0f - shieldValue)*MIN_SHIELD_COLOR.g;
			color.b = shieldValue*MAX_SHIELD_COLOR.b + (1.0f - shieldValue)*MIN_SHIELD_COLOR.b;
			return color;
		}

		public Vector2f getOffsetAt(Shape shape, float x, float y) {
			return vector_offset;
		}
		
	}
	
}
