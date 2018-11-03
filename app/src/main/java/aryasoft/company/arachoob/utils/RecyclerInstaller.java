package aryasoft.company.arachoob.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;

public class RecyclerInstaller {

    private RecyclerView RecyclerInstance;
    private RecyclerView.Adapter AdapterInstance;
    private RecyclerView.LayoutManager LayoutManagerInstance;

    private RecyclerInstaller(){}

    public static RecyclerInstaller build()
    {
        return new RecyclerInstaller();
    }

    public RecyclerInstaller setAdapter(RecyclerView.Adapter adapter)
    {
        AdapterInstance = adapter;
        return this;
    }

    public RecyclerInstaller setLayoutManager(RecyclerView.LayoutManager layoutManager)
    {
        LayoutManagerInstance = layoutManager;
        return this;
    }

    public RecyclerInstaller setRecyclerView(RecyclerView recyclerView)
    {
        RecyclerInstance = recyclerView;
        return this;
    }

    public void setup() {
        RecyclerInstance.setLayoutManager(LayoutManagerInstance);
        RecyclerInstance.setAdapter(AdapterInstance);
    }

}
