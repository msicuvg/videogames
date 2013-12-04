package org.smarthome;

import org.smarthome.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
	private Button btnCargar;
	private Button btnLed2;
	private Button btnLed3;
	private Button btnLed4;
	private Button btnLed5;
	private Button btnLed6;
	private Button btnLed7;

	private EditText txtNombre;
	private Conexion con;
	private AlertDialog alert;
	private SharedPreferences prefs;           	 	
	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
       //<OPEN>C:\presentacion5.pptx</OPEN>
    
       btnCargar = (Button) findViewById(R.id.btnCargar);
       txtNombre = (EditText) findViewById(R.id.txtNombre);

       btnLed2 = (Button) findViewById(R.id.btnLed2);
       btnLed3 = (Button) findViewById(R.id.btnLed3);
       btnLed4 = (Button) findViewById(R.id.btnLed4);
       btnLed5 = (Button) findViewById(R.id.btnLed5);
       btnLed6 = (Button) findViewById(R.id.btnLed6);
       btnLed7 = (Button) findViewById(R.id.btnLed7);
       
       btnCargar.setOnClickListener(cargarListener);
       btnLed2.setOnClickListener(led2Listener);       
       btnLed3.setOnClickListener(led3Listener);       
       btnLed4.setOnClickListener(led4Listener);       
       btnLed5.setOnClickListener(led5Listener);       
       btnLed6.setOnClickListener(led6Listener);       
       btnLed7.setOnClickListener(led7Listener);       
        
       prefs  = PreferenceManager.getDefaultSharedPreferences(this);
    
       
      /* try{
    	 
    	   txtNombre.setText("Conexión exitosa!!!");
       }catch(Exception e){
    	   txtNombre.setText("Error al iniciar conexion!!!");
       }*/
		
    }
    
    //Evento para ejecutar consulta
    private OnClickListener cargarListener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("1\n\n");
        		txtNombre.setText("Led 1!!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    
    //Evento para ejecutar consulta
    private OnClickListener led2Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("2\n\n");
        		txtNombre.setText("Led 2!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    //Evento para ejecutar consulta
    private OnClickListener led3Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("3\n\n");
        		txtNombre.setText("Led 3!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
  
    //Evento para ejecutar consulta
    private OnClickListener led4Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("4\n\n");
        		txtNombre.setText("Led 4!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    
    //Evento para ejecutar consulta
    private OnClickListener led5Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("5\n\n");
        		txtNombre.setText("Led 5!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    
    //Evento para ejecutar consulta
    private OnClickListener led6Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("6\n\n");
        		txtNombre.setText("Led 6!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    
    //Evento para ejecutar consulta
    private OnClickListener led7Listener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		String value = prefs.getString("server", "192.168.1.177");
        		con =  new  Conexion(value);
        		con.send("7\n\n");
        		txtNombre.setText("Led 7!" );
        		con.close();
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }            
    };
    
    //Evento para ejecutar salir
    private OnClickListener salirListener = new OnClickListener()  
    {            
        public void onClick(View v)  
        {    
        	try{
        		con.send("<EXIT/>\n");
        		txtNombre.setText("Salir!!!!" );
        		
        	}catch(Exception e){
        		txtNombre.setText(e.getMessage());
        	}
        }        
    };
    
	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.slidescontrol, menu);
	        return true;
	    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {	        
		        	
        case R.id.preferences:
        	//System.out.println("collaborative menu!!");
			Intent i = new Intent(this, Preferences.class);
      	 	
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       	 	//String  value = prefs.getString("collaborativeserver", "192.168.1.254");
        	//Toast.makeText(this, "Server ip: " + value, Toast.LENGTH_LONG).show();            	
			startActivity(i);
       	 	//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
       	 	//String  value = prefs.getString("collaborativeserver", "192.168.1.254");
        	//Toast.makeText(this, "Presionaste Preferencias: " + value, Toast.LENGTH_LONG).show();
            return true;
        case R.id.quit:
        {
        	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
  	        
  	        builder.setMessage("¿Está seguro de salir?")
  	               .setCancelable(false)
  	               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
  	                   public void onClick(DialogInterface dialog, int id) {
  	                        Main.this.finish();
  	                   }
  	               })
  	               .setNegativeButton("No", new DialogInterface.OnClickListener() {
  	                   public void onClick(DialogInterface dialog, int id) {
  	                        dialog.cancel();
  	                   }
  	        });
  	        	  	        
  	        alert = builder.create();
  	        alert.setTitle("Slides control");
  	        alert.setIcon(R.drawable.color_exit1);
  	    	alert.show();
  	    	
           //Toast.makeText(this, "Presionaste Salir", Toast.LENGTH_LONG).show();
//           System.exit(0);
        }
            //return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    private String preparatePackage(String str){
    	str = str.replace("<N/>", "\n");
		str = str.replace("<R/>", "\n");
		str = str.replace("<TITLE>", "");
		str = str.replace("<BODY>", "\n");
		str = str.replace("</BODY>", "");
		str = str.replace("<CONTENT>", "");
		str = str.replace("</TITLE>", "\n");
		str = str.replace("</CONTENT>", "");
		return str;
    }
}