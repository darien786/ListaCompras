package com.gamehub.listacompras;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentoCalculadora#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentoCalculadora extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentoCalculadora() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentoCalculadora.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentoCalculadora newInstance(String param1, String param2) {
        fragmentoCalculadora fragment = new fragmentoCalculadora();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private Button btnLista, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn, btnIgual, btnSuma, btnResta, btnMulti, btnPunto, btnBorrar, btnDividir;
    public TextView editTextResul;
    protected double operando1 = Double.NaN;
    protected double operando2;
    protected  char  operador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().hide();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragmento_calculadora, container, false);

        //Creacion de los componentes botones
        btn = view.findViewById(R.id.ceroBoton);
        btn1 = view.findViewById(R.id.unoBoton);
        btn2 = view.findViewById(R.id.dosBoton);
        btn3 = view.findViewById(R.id.tresBoton);
        btn4 = view.findViewById(R.id.cuatroBoton);
        btn5 = view.findViewById(R.id.cincoBoton);
        btn6 = view.findViewById(R.id.seisBoton);
        btn7 = view.findViewById(R.id.sieteBoton);
        btn8 = view.findViewById(R.id.ochoBoton);
        btn9 = view.findViewById(R.id.nueveBoton);

        btnIgual = view.findViewById(R.id.igualBoton);
        btnResta = view.findViewById(R.id.restaBoton);
        btnMulti = view.findViewById(R.id.multiplicaciónBoton);
        btnSuma = view.findViewById(R.id.sumaBoton);
        btnPunto = view.findViewById(R.id.puntoBoton);
        btnBorrar = view.findViewById(R.id.borrarBoton);
        btnDividir = view.findViewById(R.id.dividirBoton);

        btnLista = view.findViewById(R.id.totalLista);

        editTextResul = view.findViewById(R.id.resultadoTextView);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append("9");
            }
        });

        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextResul.append(".");
            }
        });

        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                operador = '+';
                editTextResul.setText(null);
            }
        });

        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                operador = '-';
                editTextResul.setText(null);
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                operador = '*';
                editTextResul.setText(null);
            }
        });

        btnDividir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                operador = '/';
                editTextResul.setText(null);
            }
        });

        // Establecer listener para el botón Equals
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
                operador = '=';
                editTextResul.setText(String.valueOf(operando1));
                operando1 = Double.NaN;
            }
        });

        // Establecer listener para el botón Clear
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operando1 = Double.NaN;
                operando2 = Double.NaN;
                editTextResul.setText(null);
            }
        });

        return view;
    }

    private void compute() {
        if (!Double.isNaN(operando1)) {
            operando2 = Double.parseDouble(editTextResul.getText().toString());
            switch (operador) {
                case '+':
                    operando1 += operando2;
                    break;
                case '-':
                    operando1 -= operando2;
                    break;
                case '*':
                    operando1 *= operando2;
                    break;
                case '/':
                    operando1 /= operando2;
                    break;
                case '=':
                    break;
            }
        } else {
            operando1 = Double.parseDouble(editTextResul.getText().toString());
        }
    }
}
