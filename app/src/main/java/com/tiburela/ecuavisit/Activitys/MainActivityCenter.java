package com.tiburela.ecuavisit.Activitys;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;
import static android.content.ContentValues.TAG;

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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiburela.ecuavisit.LoginAndRegistro.ActivityLogin;
import com.tiburela.ecuavisit.LoginAndRegistro.RegistroCuentaGoogle;
import com.tiburela.ecuavisit.R;
import com.tiburela.ecuavisit.models.UsuarioCliente;
import com.tiburela.ecuavisit.variablesGlobales.Variables;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivityCenter extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    String   userIDCurrentUser;
    DatabaseReference ref;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivityult);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configura_idioma();





        // setSupportActionBar ( toolbar );
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ().setDisplayShowHomeEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
      //  NavigationView navigationView = findViewById(R.id.nav_view);
     //   navigationView.setItemIconTintList(null);


        toolbar.setVisibility(View.GONE);



        // drawer.setScrimColor(Color.parseColor("#5eba7d"));


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(

                R.id.nav_home, R.id.nav_gallery, R.id.lista_paises,R.id.favoritos_paises, R.id.conf_acerca, R.id.acerca, R.id.atribuciones,R.id.politicas, R.id.licencias ,R.id.puntuaapp)

                .setOpenableLayout(drawer)        // WITH THIS LINE

               // .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      //  NavigationUI.setupWithNavController(navigationView, navController);


        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation_view);
        //  bottomNavigation.set


        NavigationUI.setupWithNavController(bottomNavigation, navController);

        //  NavigationView bnavigationView=(NavigationView) findViewById(R.id.nav_home);
        //  bnavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));





    }




    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            account.getDisplayName();
            account.getFamilyName();
            Log.i("eluserest","aqui hay data el user es "+account.getEmail());

            //verificamos que el perfil este completo..

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                userIDCurrentUser=user.getUid();
                // String nameCurretnUSER=ref.getST
                ref= FirebaseDatabase.getInstance().getReference().child("Clientes").child(userIDCurrentUser);


                //vamos a datachange

               addPostEventListener(ref);


            }




        }




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
        Log.d("DDKKFFFF","ONREUSMEN");

    }





        private void addPostEventListener(DatabaseReference mPostReference) {
            // [START post_value_event_listener]
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI

                    Variables.globalUsuarioClienteObj = dataSnapshot.getValue(UsuarioCliente.class);

                    Log.i("solodataaqui","midata eel nombre es  "+Variables.globalUsuarioClienteObj.getNombre());

                  //  Toast.makeText(MainActivityCenter.this, "EL NOMBRE ES "+usuarioClienteObjecCurrent.getNombre(), Toast.LENGTH_SHORT).show();

                    if(Variables.globalUsuarioClienteObj.getNombre().length()==0){//noa a completado el nombre

                        Toast.makeText(MainActivityCenter.this, "Completa el registro", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(MainActivityCenter.this, RegistroCuentaGoogle.class));

                    }else {

                        Variables.user2=Variables.globalUsuarioClienteObj;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", error.toException());
                }


            };
            mPostReference.addValueEventListener(postListener);
            // [END post_value_event_listener]
        }










    public void SaelUseryPasword(){

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivityCenter.this, LoginActivity.class));


    }






}