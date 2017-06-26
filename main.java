
import java.io.File;
import java.util.Random;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class main extends Application {

	private static final int EARTH = 100;
	private static final int SUN = 200;
	private static final int MOON = 300;
	private float alpha=0;
	private double angel=0;
	private Pane pane;
	private Node sun;
	private Node moon;
	private Node earth;
	private PointLight light;
	private ImageView spaceship;
	private PerspectiveCamera camera ;
	private ImagePattern pattern;
	private Scene scene;	
	private double earthT1=1;
	private double earthT2=1;
	private double sunT1=1;
	private Text distance;
	private Text text;
	private int SpaceShipSpeed =3;
	private Rectangle  boxButton1;
	private Rectangle boxButton2;
	private Rectangle boxButton3;	
	private Rectangle shine;
	private int minDistanceFromSun=2000;
	private float maxDistanceFromSun =3000;
	private float lastDistance;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {	
		
		primaryStage.setTitle("Univers 3D modul");	
		pane = new BorderPane();
		scene = new Scene(pane,2000,1000,true);	
		
		// shine from the sun
		shine = new Rectangle(2000, 2000);
		shine.setFill(Color.rgb(0, 0, 0, 0));
	
		// handle background 
		Image background =new Image("/background.png");
		pattern = new ImagePattern(background);
    	scene.setFill(pattern);
	
    	//create the planets and add its to the scene 
		earth=PlanetViewer.Planet(EARTH);
		moon = PlanetViewer.Planet(MOON);
		sun=PlanetViewer.Planet(SUN);
		pane.getChildren().addAll(earth,sun,moon);
		
		camera = new PerspectiveCamera(); 
	    camera.setTranslateX(sun.getTranslateX()-900);
	    camera.setTranslateY(sun.getTranslateY()-300);
	    camera.setTranslateZ(-2000);
	    
	    
	    light = new PointLight();
	    light.setColor(Color.WHITE);
	    light.setTranslateX(sun.getTranslateX());
	    light.setTranslateY(sun.getTranslateY());
	    light.setTranslateZ(sun.getTranslateZ()-800);
	    pane.getChildren().add(light);

	    
	   boxButton1= new Rectangle (0,0,30,30);
	   boxButton2= new Rectangle(0,0,30,30);
	   boxButton3= new Rectangle(0,0,50,30);
	  
	    
	    scene.setCamera(camera);
	
	    primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
		
		//rotate the planets in Y-Axis
		rotateAroundYAxis(earth,earthT1).play();
		rotateAroundYAxis(moon,1).play();
		rotateAroundYAxis(sun,sunT1).play(); 
		 
		//draw the planets
		transition(earth,sun.getTranslateX(),sun.getTranslateY());
		
		spaceship = new ImageView("/cockpit.png");
		
		 String musicFile = "/Workspace/Java3dSeminar/bin/sound.mp3";   
	     Media sound = new Media(new File(musicFile).toURI().toString());
	     MediaPlayer mp = new MediaPlayer(sound);
	     mp.play();
	    
	     Thread soundThread = new Thread(){
	         public void run() {
	             while(true){
	            	 	try {
							this.sleep(100000);
							MediaPlayer mp = new MediaPlayer(sound);
							if(mp.getStatus().equals(Status.PLAYING))
								mp.stop();
		            		 mp.play();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	             }
	         }
	     };
	     soundThread.start();
	     
	     primaryStage.setOnCloseRequest(event -> {
	    	 soundThread.stop();
	    	});
	       
	       Image imgEarth = new Image("/right.jpg");
	       boxButton1.setFill(new ImagePattern(imgEarth));
	       
	       Image imgSun = new Image("/sun.jpg");
	       boxButton2.setFill(new ImagePattern(imgSun));
	       
	       Image img = new Image("/rotate.jpg");
	       boxButton3.setFill(new ImagePattern(img));
	      
	     boxButton1.setOnMousePressed(new EventHandler<MouseEvent>() {
	         @Override
	         public void handle(MouseEvent event) {
	        	 earthT2=earthT2*(-1);
				 rotateAroundYAxis(earth,earthT2).play();
	         }
	     });
	       
	     boxButton2.setOnMousePressed(new EventHandler<MouseEvent>() {
	         @Override
	         public void handle(MouseEvent event) {
	        		sunT1=sunT1*(-1);
					rotateAroundYAxis(sun,sunT1).play();
	         }
	     });
	     
	     boxButton3.setOnMousePressed(new EventHandler<MouseEvent>() {
	         @Override
	         public void handle(MouseEvent event) {
	        	 earthT1=earthT1*(-1);
	         }
	     });
		
		 scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				
				if(key.getCode().equals(KeyCode.RIGHT)){
					camera.setTranslateX(camera.getTranslateX()+SpaceShipSpeed);
					changeAlpha("RIGHT");
				}
				else if(key.getCode().equals(KeyCode.LEFT)){
					camera.setTranslateX(camera.getTranslateX()-SpaceShipSpeed);
					changeAlpha("LEFT");
				}
				else if(key.getCode().equals(KeyCode.UP)){
					if(sun.getTranslateZ()-spaceship.getTranslateZ()>minDistanceFromSun){
						camera.setTranslateZ(camera.getTranslateZ()+20);
						changeAlpha("UP");
					}
				}
				else if(key.getCode().equals(KeyCode.DOWN)){
					camera.setTranslateZ(camera.getTranslateZ()-20);
					changeAlpha("DOWN");
				}
				else if(key.getCode().equals(KeyCode.SPACE)){
					camera.setTranslateY(camera.getTranslateY()-SpaceShipSpeed);
					changeAlpha("SPACE");
				}
				else if(key.getCode().equals(KeyCode.Z)){
					camera.setTranslateY(camera.getTranslateY()+SpaceShipSpeed);
					changeAlpha("Z");
				}
			}
		});
		
		text=new Text();
		text.setFont(Font.font("Verdana", FontWeight.BOLD,8));
		text.setFill(Color.GREEN);
		
		
		distance=new Text();
		distance.setFont(Font.font("Verdana", FontWeight.BOLD,9));
		distance.setFill(Color.GREEN);
		
		lastDistance=distanceFromSun();
	}
	
	private RotateTransition rotateAroundYAxis(Node node,double rotateVector) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(10), node);
		rotate.setAxis(Rotate.Y_AXIS);
		if(rotateVector==1){
			rotate.setFromAngle(360);
			rotate.setToAngle(0);
		}else {
			rotate.setFromAngle(0);
			rotate.setToAngle(360);
		}
		
		rotate.setInterpolator(Interpolator.LINEAR);
		rotate.setCycleCount(RotateTransition.INDEFINITE);
		return rotate;
    }
	  
	private void transition(Node planet,double x, double y){
        Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(20),e->{
			if(earthT1==1){
				angel+=0.02;	
			}else{
				angel-=0.02;
			}
		    
		    
			earth.setTranslateX((Math.sin(angel)*650)+sun.getTranslateX());
			earth.setTranslateZ((Math.cos(angel)*600));
		    moon.setTranslateX((Math.sin(angel+20)*250)+earth.getTranslateX());
		    moon.setTranslateZ(Math.cos(angel+20)*600);
		    
		       pane.getChildren().clear();
		       pane.getChildren().add(earth);
			   pane.getChildren().add(moon);
			   pane.getChildren().add(sun);
			
			   spaceship.setTranslateZ(camera.getTranslateZ()-1300);
			   spaceship.setTranslateX(camera.getTranslateX()+600);
		       spaceship.setTranslateY(camera.getTranslateY()+300);
			   pane.getChildren().add(spaceship);
			
		       boxButton1.setTranslateX(camera.getTranslateX()+850);
		       boxButton1.setTranslateY(camera.getTranslateY()+650);
		       boxButton1.setTranslateZ(camera.getTranslateZ()-1300);
		       
		       boxButton2.setTranslateX(camera.getTranslateX()+910);
		       boxButton2.setTranslateY(camera.getTranslateY()+650);
		       boxButton2.setTranslateZ(camera.getTranslateZ()-1300);
		       
		       boxButton3.setTranslateX(camera.getTranslateX()+880);
		       boxButton3.setTranslateY(camera.getTranslateY()+610);
		       boxButton3.setTranslateZ(camera.getTranslateZ()-1300);
			
		       distance.setText("Distance from the Sun : "+String.format("%.2f", distanceFromSun()) +"\nDistance from Erath : "+String.format("%.2f", (earth.getTranslateZ()-spaceship.getTranslateZ()))+"\nHeight : "+(camera.getTranslateY()+1000));
		       distance.setTranslateX(camera.getTranslateX()+630);
		       distance.setTranslateY(camera.getTranslateY()+500);
		       distance.setTranslateZ(camera.getTranslateZ()-1300);
		       
		       Random r = new Random();
		       String matrix[]= new String[4];
		       
		       for(int i=0; i<matrix.length ; i++){
		    	   matrix[i]=""+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a')+(char)(r.nextInt(26) + 'a');
		       }
		       
		       text.setText(">../"+matrix[0]+"\n\n\n\n"+matrix[1]+"\nMove with Arrows,Space and Z\n~/"+matrix[2]+"\n//../"+matrix[3]);
		       text.setTranslateX(camera.getTranslateX()+630);
		       text.setTranslateY(camera.getTranslateY()+490);
		       text.setTranslateZ(camera.getTranslateZ()-1300);
		       
		       shine.setTranslateX(camera.getTranslateX());
		       shine.setTranslateY(camera.getTranslateY());
		       shine.setTranslateZ(camera.getTranslateZ());
		       shine.setFill(Color.rgb(255, 255, 0, alpha));
		       
		    pane.getChildren().add(distance); 
		    pane.getChildren().add(text);  
			pane.getChildren().add(boxButton2);
			pane.getChildren().add(boxButton1);
			pane.getChildren().add(boxButton3);
			pane.getChildren().add(shine); 
			pane.getChildren().add(light);
		}));
		
		 
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
    }

	
	public void changeAlpha(String str){
		float currentDistance = distanceFromSun();
		float ratio=0;
		
		if(str.equals("UP")||str.equals("DOWN")){
			ratio=0.007f;
		}
		if(str.equals("SPACE")||str.equals("Z")||str.equals("LEFT")||str.equals("RIGHT")){
			ratio=0.0025f;
		}
		
		if(currentDistance<lastDistance){
			if(alpha+ratio <255){
				alpha=alpha+ratio;
			}
		}else{
			if(alpha-ratio >0){
				alpha=alpha-ratio;
			}
		}
		
		lastDistance= currentDistance;
	}

	public float distanceFromSun(){
		float distance = (float)Math.sqrt((float)Math.pow(sun.getTranslateX()-camera.getTranslateX()-1000, 								2)+(float)Math.pow(sun.getTranslateY()-camera.getTranslateY()-200, 								2)+(float)Math.pow(sun.getTranslateZ()-camera.getTranslateZ(), 2));
		return distance;
	}
}

	

