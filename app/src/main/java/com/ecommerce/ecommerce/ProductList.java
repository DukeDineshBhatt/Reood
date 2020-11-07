package com.ecommerce.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nl.dionsegijn.steppertouch.OnStepCallback;
import nl.dionsegijn.steppertouch.StepperTouch;

public class ProductList extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<ItemModel> arrayList;

    LinearLayout layout;
    CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();

        adapter = new CustomAdapter(arrayList);
        recyclerView.setAdapter(adapter);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent(getActivity(),CheckoutActivity.class);
                startActivity(intent);

            }
        });


        return view;


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

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> {

        ArrayList<ItemModel> arrayList;

        public CustomAdapter(ArrayList<ItemModel> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.product_layout, viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(final viewHolder viewHolder, int position) {


            //viewHolder.name.setText(arrayList.get(position).getName());
            //  viewHolder.image.setImageResource(arrayList.get(position).getImage());

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(getActivity(),CustomizeActivity.class);
                    startActivity(intent);

                }
            });


            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewHolder.add.setVisibility(View.GONE);

                    viewHolder.stepperTouch.setVisibility(View.VISIBLE);
                    viewHolder.stepperTouch.setMinValue(0);
                    viewHolder.stepperTouch.setMaxValue(99);
                    viewHolder.stepperTouch.setSideTapEnabled(true);
                    viewHolder.stepperTouch.setCount(1);

                    layout.setVisibility(View.VISIBLE);


                    viewHolder.stepperTouch.addStepCallback(new OnStepCallback() {
                        @Override
                        public void onStep(int value, boolean positive) {

                            if (value == 0) {

                                viewHolder.add.setVisibility(View.VISIBLE);
                                viewHolder.stepperTouch.setVisibility(View.GONE);
                                layout.setVisibility(View.GONE);


                            }

                        }
                    });

                }
            });
        }

        @Override
        public int getItemCount() {
            //return arrayList.size();
            return 5;
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView name, price, des;
            ImageView image;
            StepperTouch stepperTouch;
            Button add;
            LinearLayout layout;

            public viewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                price = (TextView) itemView.findViewById(R.id.price);
                des = (TextView) itemView.findViewById(R.id.des);
                image = (ImageView) itemView.findViewById(R.id.image);
                add = (Button) itemView.findViewById(R.id.add);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);
                stepperTouch = itemView.findViewById(R.id.stepperTouch);

            }

        }
    }
}