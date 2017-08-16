package com.example.carlosje.lectorcodbarrastpv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {



    private TextView TV_lectura,barcode_text;
    private FloatingActionButton floatingActionButton_save,floatingActionButton_edit,floatingActionButton_edit_return,floatingActionButton_search;
    private EditText tx_ubicacion, tx_user,et_TPV;
    private ImageView im_scanea;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager IManager;
    private ConstraintLayout LayGeneral;
    private String usuarioid;
    private ImageButton imageButton_Salir;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LayGeneral=(ConstraintLayout) findViewById(R.id.LayGeneral);

        usuarioid = getIntent().getStringExtra("usuario");

        TV_lectura = (TextView) findViewById(R.id.TV_lectura);
        barcode_text= (TextView) findViewById(R.id.tx_TPV);
        floatingActionButton_save = (FloatingActionButton) findViewById(R.id.floatingActionButton_save);
        floatingActionButton_edit = (FloatingActionButton) findViewById(R.id.floatingActionButton_edit);
        floatingActionButton_edit_return= (FloatingActionButton) findViewById(R.id.floatingActionButton_edit_return);
        im_scanea=(ImageView) findViewById(R.id.im_scanea);
        imageButton_Salir = (ImageButton) findViewById(R.id.imageButton_Salir);
        et_TPV=(EditText) findViewById((R.id.et_TPV));
        et_TPV = (EditText) findViewById(R.id.et_TPV);
        tx_ubicacion=(EditText) findViewById((R.id.tx_ubicacion));
        tx_user=(EditText) findViewById((R.id.tx_user));

        floatingActionButton_search= (FloatingActionButton) findViewById(R.id.floatingActionButton_search);









        tx_ubicacion.setVisibility(View.GONE);
        tx_user.setVisibility(View.GONE);
        et_TPV.setVisibility(View.GONE);
        floatingActionButton_save.setVisibility(View.GONE);
        floatingActionButton_search.setVisibility(View.GONE);
        floatingActionButton_edit_return.setVisibility(View.GONE);
        floatingActionButton_edit.setVisibility(View.GONE);
        LayGeneral.setBackgroundColor(Color.rgb(250,250,250));



        tx_user.setText(usuarioid);







        final EditText et_TPV =(EditText)this.findViewById(R.id.et_TPV);
        et_TPV.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)

                   {

                        if (recycler != null){
                            recycler.setVisibility(View.GONE);

                        }


                        et_TPV.setVisibility(View.GONE);
                        barcode_text.setVisibility(View.VISIBLE);
                       floatingActionButton_search.setVisibility(View.GONE);


                        if (et_TPV.getText().toString().equals("")){
                            barcode_text.setText("TPV");


                        }else{
                            floatingActionButton_edit.setVisibility(View.VISIBLE);
                            barcode_text.setText(et_TPV.getText().toString());
                            et_TPV.setText("");


                        }


                       InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                       imm.hideSoftInputFromWindow(et_TPV.getWindowToken(), 0);





                        imageButton_Salir.setVisibility(View.VISIBLE);

                       consulta();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                recycler.setVisibility(View.VISIBLE);

                            }
                        }, 500);
                        return true;
                    }
                return false;
            }
        });



        final EditText tx_ubicacion =(EditText)this.findViewById(R.id.tx_ubicacion);
        tx_ubicacion.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)

                {

                    LayGeneral.setBackgroundColor(Color.rgb(250,250,250));


                    floatingActionButton_save.setVisibility(View.GONE);


                    tx_user.setVisibility(View.GONE);
                    tx_ubicacion.setVisibility(View.GONE);
                    floatingActionButton_edit.setVisibility(View.VISIBLE);
                    floatingActionButton_edit_return.setVisibility(View.GONE);
                    im_scanea.setVisibility(View.VISIBLE);







                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            consulta();
                            recycler.setVisibility(View.VISIBLE);

                        }
                    }, 100);





                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_TPV.getWindowToken(), 0);






                    return true;
                }
                return false;
            }
        });






    }











    public void editTPV(View view){
        if (recycler != null){
            recycler.setVisibility(View.GONE);

        }
        String revCod=barcode_text.getText().toString();
        if (revCod.equals("TPV")){
            et_TPV.setText("");


        }else{
            et_TPV.setText(revCod);



        }


        et_TPV.setVisibility(View.VISIBLE);
        barcode_text.setVisibility(View.GONE);
        imageButton_Salir.setVisibility(View.GONE);
        floatingActionButton_search.setVisibility(View.VISIBLE);
        floatingActionButton_edit.setVisibility(View.GONE);

        et_TPV.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_TPV, InputMethodManager.SHOW_IMPLICIT);



    }












    public void salir(View view){


        Intent salida=new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
        finish();


    }





    private void writeNewPost(String codigo, String date, String usuario, String ubicacion) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = database.child(codigo).push().getKey();
        Post post = new Post(date, usuario, ubicacion);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put(codigo + key, postValues);


        childUpdates.put(key, postValues);

        database.child(codigo).updateChildren(childUpdates);
    }


    @IgnoreExtraProperties
    public class Post {

        public String date;
        public String usuario;
        public String ubicacion;


        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String date, String usuario, String ubicacion) {
            this.date = date;
            this.usuario = usuario;
            this.ubicacion = ubicacion;

        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("date", date);
            result.put("Usuario", usuario);
            result.put("Ubicacion", ubicacion);

            return result;
        }

    }








    public void scanNow(View view){

        if (et_TPV != null){
            et_TPV.setVisibility(View.GONE);
        }
        barcode_text.setVisibility(View.VISIBLE);

        tx_ubicacion.setVisibility(View.GONE);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scanea el código de barras");
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
        floatingActionButton_save.setVisibility(View.GONE);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String scanContent="TPV";

        if (scanningResult.getContents() != null) {
            //we have a result


            scanContent = scanningResult.getContents();


            // display it on screen




        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No se recibió Código de Barras", Toast.LENGTH_SHORT);
            toast.show();


        }

        barcode_text.setText(scanContent);

        if (scanContent !="TPV") {
            floatingActionButton_edit.setVisibility(View.VISIBLE);

            consulta();

        }

    }






    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private ValueEventListener eventListener;


    public void edit(View view){
        tx_ubicacion.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tx_ubicacion, InputMethodManager.SHOW_IMPLICIT);


        imageButton_Salir.setVisibility(View.GONE);

        floatingActionButton_save.setVisibility(View.VISIBLE);
        floatingActionButton_edit_return.setVisibility(View.VISIBLE);
        floatingActionButton_edit.setVisibility(View.GONE);
        tx_user.setVisibility(View.GONE);
        tx_ubicacion.setVisibility(View.VISIBLE);
        im_scanea.setVisibility(View.GONE);
        recycler.setVisibility(View.GONE);
        LayGeneral.setBackgroundColor(Color.rgb(143,143,143));
        tx_user.setText(usuarioid);



    }

    public void edit_return(View view){

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        LayGeneral.setBackgroundColor(Color.rgb(250,250,250));


        floatingActionButton_save.setVisibility(View.GONE);


        tx_user.setVisibility(View.GONE);
        tx_ubicacion.setVisibility(View.GONE);
        floatingActionButton_edit.setVisibility(View.VISIBLE);
        floatingActionButton_edit_return.setVisibility(View.GONE);
        im_scanea.setVisibility(View.VISIBLE);







            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    consulta();
                    recycler.setVisibility(View.VISIBLE);

                }
            }, 100);




    }


    public void Search(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_TPV.getWindowToken(), 0);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {


                if (et_TPV.getText().toString().equals("")){
                    barcode_text.setText("TPV");



                }else{
                    floatingActionButton_edit.setVisibility(View.VISIBLE);
                    barcode_text.setText(et_TPV.getText().toString());
                    et_TPV.setText("");


                }

                et_TPV.setVisibility(View.GONE);
                barcode_text.setVisibility(View.VISIBLE);
                floatingActionButton_search.setVisibility(View.GONE);





                imageButton_Salir.setVisibility(View.VISIBLE);


                consulta();

            }
        }, 1000);



    }







    public void Save(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_TPV.getWindowToken(), 0);




        //floatingActionButton_save.setVisibility(View.VISIBLE);

        final String minutos;
        final String horas;
        final String dias;
        final String meses;




        Calendar miCalendario = Calendar.getInstance();


        int mes = miCalendario.get(Calendar.MONTH);
        int año = miCalendario.get(Calendar.YEAR);
        int dia = miCalendario.get(Calendar.DAY_OF_MONTH);
        int hr = miCalendario.get(Calendar.HOUR_OF_DAY);
        int min = miCalendario.get(Calendar.MINUTE);


        if (min<10){
            minutos="0"+min;
        }else{

             minutos=""+min;
        }

        if (hr<10){
            horas="0"+hr;
        }else{
            horas=""+hr;
        }




        if (mes<10){
            meses="0"+mes;
        }else{

            meses=""+mes;
        }

        if (dia<10){
            dias="0"+dia;
        }else{

            dias=""+dia;
        }





        final String date = (meses + "/" + dias + "/" + año + " " + horas + ":" + minutos);

        final String codigo = barcode_text.getText().toString();

        final String usuario = usuarioid;
        final String ubicacion = tx_ubicacion.getText().toString();


        writeNewPost(codigo, date, usuario, ubicacion);


        Toast toast = Toast.makeText(getApplicationContext(), "Datos Guardados", Toast.LENGTH_SHORT);
        toast.show();


        tx_user.setText("");
        tx_ubicacion.setText("");



        floatingActionButton_save.setVisibility(View.GONE);
        LayGeneral.setBackgroundColor(Color.rgb(250,250,250));


        tx_user.setVisibility(View.GONE);
        tx_ubicacion.setVisibility(View.GONE);
        floatingActionButton_edit.setVisibility(View.VISIBLE);
        floatingActionButton_edit_return.setVisibility(View.GONE);
        im_scanea.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                consulta();
                recycler.setVisibility(View.VISIBLE);

            }
        }, 500);





    }







    public String Reci_usuario, Reci_fecha, Reci_ubicacion;


        //public void consulta(View view){

      void consulta(){


        final String codigo = barcode_text.getText().toString();



        final List<Cube> items=new ArrayList<>();
          items.clear();




        recycler=(RecyclerView) findViewById(R.id.reciclador);

          // para alinear horizontalmente
        IManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);


          // para alinear vertical

          // IManager= new LinearLayoutManager(this);




          database.child(codigo).orderByChild("date");


        database.child(codigo).orderByChild("date").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                items.clear();

                for (DataSnapshot tpvSnapshot : snapshot.getChildren()){

                    Reci_usuario=tpvSnapshot.child("Usuario").getValue().toString();
                    Reci_fecha=tpvSnapshot.child("date").getValue().toString();
                    Reci_ubicacion=tpvSnapshot.child("Ubicacion").getValue().toString();

                    //lis_codigos.add(Reci_usuario);
                    items.add(new Cube(Reci_usuario,Reci_fecha,Reci_ubicacion));



                }

                //cuento items

                //String x = items.get(2).getFecha().toString();

                //int itemCount = items.size();

               // Toast.makeText(getApplicationContext(),x , Toast.LENGTH_LONG).show();



                ////////    ORDENA DE MAYOR A MENOR POR FECHA   ///////////

                class ComparadorFechas implements Comparator<Cube> {
                    public int compare(Cube a, Cube b) {
                       return b.getFecha().compareTo(a.getFecha());
                    }
                }

               Collections.sort(items, new ComparadorFechas());


                ///////////



                recycler.setHasFixedSize(true);


                recycler.setLayoutManager(IManager);

                adapter= new CubeAdapter(items);
                recycler.setAdapter(adapter);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        recycler.setVisibility(View.VISIBLE);

                    }
                }, 500);




                //Toast.makeText(MainActivity.this, lis_codigos.toString(), Toast.LENGTH_LONG).show();





            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });















        //Toast toast = Toast.makeText(getApplicationContext(),"conuslta", Toast.LENGTH_SHORT);
        //toast.show();



    }







}