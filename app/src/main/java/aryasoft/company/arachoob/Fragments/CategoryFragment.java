package aryasoft.company.arachoob.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aryasoft.company.arachoob.Adapters.CategoryAdapter;
import aryasoft.company.arachoob.Models.Category;
import aryasoft.company.arachoob.R;

public class CategoryFragment extends Fragment {

    private RecyclerView RecyclerCategory;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setupCategoriesRecycler();
    }

    private void initializeViews(View view)
    {
        RecyclerCategory = view.findViewById(R.id.recyclerCategories);
    }

    private void setupCategoriesRecycler()
    {
        CategoryAdapter categoryAdapter  = new CategoryAdapter(getContext(), createFakeData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerCategory.setLayoutManager(linearLayoutManager);
        RecyclerCategory.setAdapter(categoryAdapter);
    }

    private ArrayList<Category> createFakeData() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.c1, "دسته اول"));
        categories.add(new Category(R.drawable.c2, "دسته دوم"));
        categories.add(new Category(R.drawable.c3, "دسته سوم"));
        categories.add(new Category(R.drawable.c4, "دسته چهارم"));
        categories.add(new Category(R.drawable.c5, "دسته پنجم"));
        categories.add(new Category(R.drawable.c6, "دسته ششم"));
        categories.add(new Category(R.drawable.c7, "دسته هفتم"));

        return categories;

    }

}
