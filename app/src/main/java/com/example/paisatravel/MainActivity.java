package com.example.paisatravel;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] tptravel = {"Cartagena","Santa Marta","San Andrés","Medellín"};
    String presSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText nombre = findViewById(R.id.etnombre);
        EditText fecha = findViewById(R.id.etfecha);
        Spinner destino = findViewById(R.id.spdestino);
        EditText numeropersonas = findViewById(R.id.etpersonas);
        RadioButton rb2 = findViewById(R.id.rb2);
        RadioButton rb4 = findViewById(R.id.rb4);
        RadioButton rb6 = findViewById(R.id.rb6);
        Switch tourciudad = findViewById(R.id.swtourciudad);
        Switch discotecas = findViewById(R.id.swdiscoteca);
        TextView cuota = findViewById(R.id.etvtotal);
        Button calcular = findViewById(R.id.btncalc);
        Button limpiar = findViewById(R.id.btnlimpiar);


        //crear el adaptador que contendra el arreglo tprestamos
        ArrayAdapter Adptptravel = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, tptravel);

        //Asignar el adaptador al spinner
        destino.setAdapter(Adptptravel);

        //Chequear el tipo de destino
        destino.setOnItemSelectedListener(this);

        //evento clic del boton calcular
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().isEmpty()) {
                    if (!fecha.getText().toString().isEmpty()) {
                        if (!numeropersonas.getText().toString().isEmpty()) {
                            if (parseDouble(numeropersonas.getText().toString()) >= 1 && parseDouble(numeropersonas.getText().toString()) <= 10) {
                                double xtotalpersona = parseDouble(numeropersonas.getText().toString());
                                double tour = xtotalpersona * 60000;
                                double disco = xtotalpersona * 80000;
                                double totalPagar = 0;
                                double descuentosintour = 0;
                                DecimalFormat valueFormat = new DecimalFormat("###,###,###,###.#");

                                //chequear el tipo de destino seleccionado
                                double valor = 0;
                                int dias = 0;

                                if (rb2.isChecked()) {
                                    dias = 2;
                                }
                                if (rb4.isChecked()) {
                                    dias = 4;
                                }
                                if (rb6.isChecked()) {
                                    dias = 6;
                                }

                                switch (presSel) {
                                    case "Cartagena":
                                        valor = 200000;
                                        break;
                                    case "Santa Marta":
                                        valor = 180000;
                                        break;
                                    case "San Andrés":
                                        valor = 170000;
                                        break;
                                    case "Medellín":
                                        valor = 190000;
                                }


                                totalPagar = (valor * xtotalpersona * dias );
                                descuentosintour = (valor * xtotalpersona * dias) * 10 / 100;

                                if (xtotalpersona > 5) {
                                    totalPagar = totalPagar - descuentosintour;
                                }


                                if (tourciudad.isChecked()) {
                                    totalPagar = totalPagar + tour;
                                }
                                if (discotecas.isChecked()) {
                                    totalPagar = totalPagar + disco;
                                }

                                cuota.setText(valueFormat.format(totalPagar));

                            } else {
                                Toast.makeText(getApplicationContext(), "Numero de personas debe de ser entre 1 y 10", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Numero de personas obligatorio", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Fecha obligatorio", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre obligatorio", Toast.LENGTH_SHORT).show();
                }
            }

        });
        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.setText("");
                fecha.setText("");
                numeropersonas.setText("");
                tourciudad.setChecked(false);
                discotecas.setChecked(false);
                cuota.setText("");
            }
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presSel = tptravel[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}