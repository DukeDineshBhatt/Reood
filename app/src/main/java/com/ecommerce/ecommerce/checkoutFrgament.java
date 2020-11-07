package com.ecommerce.ecommerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.CartAdapter;

import java.util.ArrayList;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class checkoutFrgament extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<ItemModel> arrayList;
    CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checkout_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        arrayList = new ArrayList<>();


        adapter = new CustomAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> {

        ArrayList<ItemModel> arrayList;

        public CustomAdapter(ArrayList<ItemModel> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public CustomAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cart_list_model, viewGroup, false);
            return new CustomAdapter.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CustomAdapter.viewHolder viewHolder, int position) {

            viewHolder.stepperTouch.setMinValue(0);
            viewHolder.stepperTouch.setMaxValue(99);
            viewHolder.stepperTouch.setSideTapEnabled(false);
            viewHolder.stepperTouch.setCount(1);
        }

        @Override
        public int getItemCount() {
            //return arrayList.size();
            return 3;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView name, price, des;
            ImageView image;
            StepperTouch stepperTouch;
            Button add;

            public viewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                price = (TextView) itemView.findViewById(R.id.price);
                des = (TextView) itemView.findViewById(R.id.des);
                image = (ImageView) itemView.findViewById(R.id.image);
                add = (Button) itemView.findViewById(R.id.add);
                stepperTouch = itemView.findViewById(R.id.stepperTouch);


            }
        }
    }

    public class ItemModel {

        int image;
        String name, price, des;

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String price) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }
}