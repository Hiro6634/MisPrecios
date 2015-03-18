package ar.com.symsys.misprecios;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

import java.util.ArrayList;
import java.util.Arrays;

import ar.com.symsys.misprecios.storage.Product;
import ar.com.symsys.misprecios.storage.StorageManager;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link AddProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment implements View.OnClickListener{

    private LinearLayout linearLayout;
    private EditText etProductId;
    private EditText etDescription;
    private EditText etBrand;
    private Spinner  spCategory;

    Button      btnAccept, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false );

        linearLayout    = (LinearLayout)view.findViewById(R.id.linearLayout);
        etProductId     = (EditText)view.findViewById(R.id.editProductId);
        etBrand         = (EditText)view.findViewById(R.id.editProductBrand);
        etDescription   = (EditText)view.findViewById(R.id.editProductDescription);
        spCategory      = (Spinner)view.findViewById(R.id.spinnerProductCategory);
        btnAccept       = (Button)view.findViewById(R.id.btn_add_product_accept);
        btnCancel       = (Button)view.findViewById(R.id.btn_add_product_cancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        String[] arrayCategories = new String[]{"Almacen", "Limpieza", "Perfumería"};
        ArrayList<String> categories = new ArrayList<String>(Arrays.asList(arrayCategories));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.spinner_list_item,
                categories);
        spCategory.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etProductId.setText(((ToolsActivity)getActivity()).getProductId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_product_accept:
                Toast.makeText(getActivity().getApplicationContext(),"Saving Data Fake",Toast.LENGTH_SHORT).show();
                Product product = new Product();
                product.setProductId(((ToolsActivity)getActivity()).getProductId());
                product.setDescription(etDescription.toString());
                product.setBrand(etBrand.toString());
                product.setCategory(spCategory.getSelectedItem().toString());

                break;

            case R.id.btn_add_product_cancel:
                getActivity().onBackPressed();
                break;
        }
    }
}
