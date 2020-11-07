package com.ecommerce.ecommerce.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.ecommerce.Adapters.CartItemAdapter;
import com.ecommerce.ecommerce.R;

public class CartFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView cartItemsRecycler = root.findViewById(R.id.cart_item_recycler);
        CartItemAdapter cartItemAdapter = new CartItemAdapter(getActivity());
        cartItemsRecycler.setAdapter(cartItemAdapter);
        return root;
    }
}