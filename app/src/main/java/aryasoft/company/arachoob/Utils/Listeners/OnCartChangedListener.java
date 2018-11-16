package aryasoft.company.arachoob.Utils.Listeners;

import java.util.ArrayList;

import aryasoft.company.arachoob.Models.OrderBasketModel;

public interface OnCartChangedListener
{
   void OnCartChanged(ArrayList<OrderBasketModel> orderBasketList);
}
