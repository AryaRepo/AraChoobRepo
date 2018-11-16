package aryasoft.company.arachoob.ApiConnection.ApiInterfaceListeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.ApiConnection.ApiModels.CollectionDataModel;

public interface OnFetchCollectionListener
{
    void OnFetchCollection(ArrayList<CollectionDataModel> totalCollectionDataModels);
}
