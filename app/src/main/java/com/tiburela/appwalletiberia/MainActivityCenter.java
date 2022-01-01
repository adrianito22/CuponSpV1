package com.tiburela.appwalletiberia;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;
import java.util.Objects;

public class MainActivityCenter extends AppCompatActivity {
    Handler handler;
    Runnable r;
    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configura_idioma();

        setContentView(R.layout.mainactivityult);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




       // setSupportActionBar ( toolbar );
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ().setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

       // drawer.setScrimColor(Color.parseColor("#5eba7d"));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_home, R.id.nav_gallery, R.id.lista_paises,R.id.favoritos_paises, R.id.conf_acerca, R.id.acerca, R.id.atribuciones,R.id.politicas, R.id.licencias ,R.id.puntuaapp)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation_view);
      //  bottomNavigation.set


        NavigationUI.setupWithNavController(bottomNavigation, navController);

     //  NavigationView bnavigationView=(NavigationView) findViewById(R.id.nav_home);
      //  bnavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));






        handler = new Handler();
        r = new Runnable() {

            @Override
            public void run() {

                Toast.makeText(MainActivityCenter.this, "usuario inactivo ",Toast.LENGTH_SHORT).show();

                Intent intencion= new Intent(MainActivityCenter.this, ActivityLogin.class);
                startActivity(intencion);

                // TODO Auto-generated method stub
            }
        };
        startHandler();

    }
    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
        stopHandler();//stop first and then start
        startHandler();


        Log.d("DDKKFFFF","USSER ITERACTUO ,EMPEZAMOSS NUEVAMENTE");

    }


    public void stopHandler() {
        handler.removeCallbacks(r);


    }


    public void startHandler() {

        //90000
       // handler.postDelayed(r, 2*60*1000); //for 2 minutes
        handler.postDelayed(r, 90000); //for 2 minutes


    }










//activa si queremos el menu...
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                irLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }




    private void irLogin(){

        Intent intencion= new Intent(MainActivityCenter.this,ActivityLogin.class);
        startActivity(intencion);

        onBackPressed();

    }





    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    protected boolean openFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
        return true;
    }














    public void onRestart()
    {
        super.onRestart();
   //     decide_cuando_mostrar_dialogo(getApplicationContext()); //mostramos el dialog

    }



    public void   setLocale     (String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
     //   Intent refresh = new Intent(this, MainActivity.class);
      //  finish();
      //  startActivity(refresh);
    }



    private void  configura_idioma(){

        /*****
         * se puede pulir mas verificando que si ya esta traducido no hacerlo
         *
         * arergal;r bien esto..
         *
         */


        String lenguaje_actual="";

       SharedPreferences mysharedpreferences = this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        String idioma_selecionado_en_configuraciones= mysharedpreferences.getString("idioma_elejido","default");
        String idioma_actual_local = this.getResources().getConfiguration().locale.getDisplayName();

        Log.i("idiomaxs", "el leguaje selecionado actual de configuraciones es "+idioma_selecionado_en_configuraciones);
        Log.i("idiomaxs", "el leguaje actual es "+idioma_actual_local);


        //setLocale();
        if(idioma_selecionado_en_configuraciones.equals("default")){
            Log.i("idiomaxs", "idioma por defecto,idioma no configurado");

        }else if(idioma_selecionado_en_configuraciones.equals("pt")){
            setLocale("pt");
          //  entonces cambiamos el idiomaa portugues

        }else if(idioma_selecionado_en_configuraciones.equals("en")){
            setLocale("en");

        }else if(idioma_selecionado_en_configuraciones.equals("es")){
            setLocale("es");
        }



    }





    private void muestra_fragment(){

        LoginaAffterLinkSendFr mFragment= new LoginaAffterLinkSendFr(); //la remplazamos..

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, mFragment)
                .commit();



    //    FrameLayout fl = (FrameLayout) findViewById(R.id.fragmentId); //eliminamos la vista
     //   fl.removeAllViews();
    //    LoginaAffterLinkSendFr mFragment= new LoginaAffterLinkSendFr(); //la remplazamos..
    //    FragmentManager fragmentManager = getSupportFragmentManager();

     //   Bundle bundle = new Bundle();
      //  bundle.putString("user", email);
     //   bundle.putString("pasword", contrasena_string);


// set Fragmentclass Arguments
      //  mFragment.setArguments(bundle);

     //   fragmentManager.beginTransaction()
         //       .replace(R.id.fragmentId, mFragment).commit();


    }


    @Override
    protected void onPause() {
        super.onPause();

       // stopHandler();

        //PROBAAABLEMENTE POENOSSSM, SSSTAAART HANDER..
        Log.d("DDKKFFFF","ON PAAUSSSSE");

    }



    @Override
    public void onResume() {
        super.onResume();
        stopHandler();//stop first and then start
        startHandler();
        Log.d("DDKKFFFF","ONREUSMEN");

    }


}