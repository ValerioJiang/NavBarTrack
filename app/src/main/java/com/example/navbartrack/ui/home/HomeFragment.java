package com.example.navbartrack.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navbartrack.R;
import com.example.navbartrack.service.online.ProductService;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button btn_scan_prod, btnSearchBarcode;
    private EditText editTxtBarcodeSearch;
    ProductService productService;
    private Helper mHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        productService = new ProductService(root.getContext());
        btn_scan_prod = root.findViewById(R.id.btn_scan_prod);
        btnSearchBarcode = root.findViewById(R.id.btnSearchBarcode);
        editTxtBarcodeSearch = root.findViewById(R.id.editTxtBarcodeSearch);
        //Toast.makeText(getContext(), getActivity().getIntent().getExtras().getString("accessToken"), Toast.LENGTH_LONG).show();

        String accessToken = getActivity().getIntent().getExtras().getString("accessToken");

        btnSearchBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    productService.getProductByBarcode(editTxtBarcodeSearch.getText().toString(), accessToken, new ProductService.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String msg) {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        Button btn_test = (Button) view.findViewById(R.id.btn_scan_prod);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




}