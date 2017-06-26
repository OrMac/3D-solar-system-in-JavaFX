import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.scene.shape.Sphere;


public class PlanetViewer {

  public static final int EARTH = 100;
  public static final int SUN = 200;
  public static final int MOON = 300;

  
  
  public static final double EARTH_RADIUS = 100;
  public static final double SUN_RADIUS = 300;
  public static final double MOON_RADIUS = 50;

	
  private static final double VIEWPORT_SIZE = 800;
  private static final double ROTATE_SECS   = 30;

  private static  double MAP_WIDTH  ;
  private static  double MAP_HEIGHT ;

  private static String DIFFUSE_MAP;
    
  
  
  
  public static Node Planet(int numOfPlanet) {
	
	  PhongMaterial planetMaterial = new PhongMaterial();  
	  Sphere planet = null;
	  
	  if(numOfPlanet == EARTH){
		  planet = new Sphere(EARTH_RADIUS);
		    planet.setTranslateX(VIEWPORT_SIZE /2d);
		    planet.setTranslateY(VIEWPORT_SIZE /2d );
		    planet.setTranslateZ(-10);
		    
		    DIFFUSE_MAP=  "/earth_gebco8_texture_8192x4096.jpg";

		    MAP_WIDTH = 8192 / 2d;
		    		
		    MAP_HEIGHT = 4092 / 2d	;
		    
		    planetMaterial.setDiffuseMap(
		    	      new Image(
		    	    		  DIFFUSE_MAP,
		    	      	        MAP_WIDTH,
		    	      	        MAP_HEIGHT,
		    	      	        true,
		    	      	        true
		    	    		  )
		    			);
	  }
    
	  else if(numOfPlanet == SUN){
		  planet = new Sphere(SUN_RADIUS);
		    planet.setTranslateX(VIEWPORT_SIZE /2d);
		    planet.setTranslateY(VIEWPORT_SIZE /2d );
		    planet.setTranslateZ(-10);
		    
		    DIFFUSE_MAP= "/suncyl1.jpg";
		    
		    MAP_WIDTH = 2000 / 2d;
		    		
		    MAP_HEIGHT = 1000 / 2d;
		    planetMaterial.setDiffuseMap(
		    	      new Image(
		    	    		  DIFFUSE_MAP,
		    	      	        MAP_WIDTH,
		    	      	        MAP_HEIGHT,
		    	      	        true,
		    	      	        true
		    	    		  )
		    			);
	  } 
	  else if(numOfPlanet == MOON){
		  planet = new Sphere(MOON_RADIUS);
		    planet.setTranslateX(VIEWPORT_SIZE /2d);
		    planet.setTranslateY(VIEWPORT_SIZE /2d );
		    planet.setTranslateZ(-10);
		    
		    DIFFUSE_MAP=  "/moon.png";
		    
		    MAP_WIDTH = 8192 / 2d;
		    		
		    MAP_HEIGHT = 4092 / 2d;
		    planetMaterial.setDiffuseMap(
		    	      new Image(
		    	    		  DIFFUSE_MAP,
		    	      	        MAP_WIDTH,
		    	      	        MAP_HEIGHT,
		    	      	        true,
		    	      	        true
		    	    		  )
		    			);
	  } 
	  
    
    planet.setMaterial(planetMaterial);

    return planet;
  }
  
 
  

}