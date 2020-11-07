package com.ecommerce.ecommerce.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.ecommerce.Activities.SearchActivity;
import com.ecommerce.ecommerce.Adapters.BannerAdapter;
import com.ecommerce.ecommerce.Adapters.CuisineAdapter;
import com.ecommerce.ecommerce.Adapters.ThumbnailAdapter;
import com.ecommerce.ecommerce.DataStructure.Cuisine;
import com.ecommerce.ecommerce.R;

import java.util.ArrayList;

public class HomePickUpFragment extends Fragment {

    RecyclerView banner, cuisine, thumbnails;
    TextView searchbox;
    Button pickup,delivery;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_pickup_fragment, container, false);
        banner = root.findViewById(R.id.banner_recycler);
        cuisine = root.findViewById(R.id.cuisine_recycler);
        thumbnails = root.findViewById(R.id.thumbnail_recycler);
        searchbox = root.findViewById(R.id.search_box_main);
        pickup = root.findViewById(R.id.pickup);
        delivery = root.findViewById(R.id.delivery);


        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(
                        new Intent(
                                getActivity(),
                                SearchActivity.class
                        )
                );
            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new HomeFragment());
                fragmentTransaction.commit();

            }
        });

        ArrayList<String> bannerList = new ArrayList<>();
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        bannerList.add("https://s3.envato.com/files/273273308/01_preview.jpg");
        BannerAdapter bannerAdapter = new BannerAdapter(getActivity(), bannerList);
        banner.setAdapter(bannerAdapter);

        ArrayList<Cuisine> cuisineList = new ArrayList<>();
        cuisineList.add(new Cuisine("Indian", getResources().getDrawable(R.drawable.ic_indian)));
        cuisineList.add(new Cuisine("Chinese", getResources().getDrawable(R.drawable.ic_chinese)));
        cuisineList.add(new Cuisine("Continental", getResources().getDrawable(R.drawable.ic_continental)));
        cuisineList.add(new Cuisine("European", getResources().getDrawable(R.drawable.ic_european)));
        cuisineList.add(new Cuisine("Italian", getResources().getDrawable(R.drawable.ic_italian)));
        cuisineList.add(new Cuisine("Mexican", getResources().getDrawable(R.drawable.ic_mexican)));
        CuisineAdapter cuisineAdapter = new CuisineAdapter(getActivity(), cuisineList);
        cuisine.setAdapter(cuisineAdapter);

        ArrayList<String> dishList = new ArrayList<>();
        dishList.add("Noodles");
        dishList.add("Veg Soup");
        dishList.add("Grilled Chicken");
        dishList.add("Fried Rice");
        dishList.add("Chicken Keema");
        dishList.add("Chicken Manchurian");
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(getActivity(), dishList);
        thumbnails.setAdapter(thumbnailAdapter);
        return root;
    }
}