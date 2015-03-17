package ar.com.symsys.misprecios;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link AddProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductFragment extends Fragment implements View.OnClickListener{

    LinearLayout linearLayout;
    EditText etProductId;

    Button      btnAccept, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_product, container, false );

        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);
        etProductId = (EditText)view.findViewById(R.id.editProductId);
        btnAccept = (Button)view.findViewById(R.id.btn_add_product_accept);
        btnCancel = (Button)view.findViewById(R.id.btn_add_product_cancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

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
                break;

            case R.id.btn_add_product_cancel:
                getActivity().onBackPressed();
                break;
        }
    }
}
