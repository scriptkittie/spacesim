package util;


public class Util {

    public static final float RADIANS_IN_DEGREES = (float)(Math.PI/180.0);
    public static final float DEGREES_IN_RADIAN = (float)(180.0/Math.PI);
    
    public static float lock(float value, float minimum, float maximum){
        if (value < minimum){
            return minimum;
        } else if (value > maximum){
            return maximum;
        } else {
            return value;
        }
    }

    public static int clamp(int value, int minimum, int maximum){
        if (value < minimum){
            return minimum;
        } else if (value > maximum){
            return maximum;
        } else {
            return value;
        }
    }
    
    public static float length(float xPoint, float yPoint){
        return (float)Math.sqrt(xPoint*xPoint + yPoint*yPoint);
    }
    
    public static float distance(float x1, float y1, float x2, float y2){
    	return length(x2 - x1, y2 - y1);
    }

    public static float getSmallestAngle(float cur, float target){
        cur = normaliseAngle(cur);
        target = normaliseAngle(target);
        float diff = target - cur;
        if (diff > Math.PI){
            diff -= Math.PI*2;
        } else if (diff < -Math.PI){
            diff += Math.PI*2;
        }
        return diff;
    }
    
    public static float normaliseAngle(float angle)
    {
        if ( angle >= 0 && angle <= Math.PI*2 )
        {
            return angle;
        }
        
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle > 2*Math.PI){
            angle -= 2*Math.PI;
        }
        return angle;
    }
    
    public final static float circleOfIntersection(float xPoint1, float yPoint1, float xPoint2, float yPoint2, float intersectionRadius){
  
        if ((xPoint1*xPoint1 + yPoint1*yPoint1) < intersectionRadius*intersectionRadius){
        	return Float.NaN;
        }

        float directionX = xPoint2 - xPoint1;
        float directionY = yPoint2 - yPoint1;
        
        float calculatedX;
        float calculatedY;
        float z;
        calculatedX = directionX*directionX + directionY*directionY;
        calculatedY = 2.0f * (directionX*xPoint1 + directionY*yPoint1);
        z = (xPoint1*xPoint1 + yPoint1*yPoint1) - intersectionRadius*intersectionRadius;
        
        float rr = calculatedY*calculatedY - 4*calculatedX*z;
        if(rr < 0)
        {
            return Float.NaN;
        }

        rr = (float)Math.sqrt(rr);
        float g;
        float c1 = (-calculatedY + rr)/(2.0f*calculatedX);
        float c2 = (-calculatedY - rr)/(2.0f*calculatedX);

        if (c1 > 0 && c1 < 1.0f && c2 > 0 && c2 < 1.0f)
        {
            g = Math.min(c1,c2);
        } 
        else if (c1 > 0 && c1 < 1.0f)
        {
            g = c1;
        }
        else if (c2 > 0 && c2 < 1.0f)
        {
            g = c2;
        } 
        else 
        {
            g = Float.NaN;
        }
        return g;
    }

    public static float derpaHerpaGoat(float velocity1, float velocity2, float directionX)
    {
    	return velocity2*directionX + (1 - directionX)*velocity1;
    }
    
}
