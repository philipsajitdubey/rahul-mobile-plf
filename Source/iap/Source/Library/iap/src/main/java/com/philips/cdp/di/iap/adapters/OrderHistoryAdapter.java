/**
 * (C) Koninklijke Philips N.V., 2015.
 * All rights reserved.
 */
package com.philips.cdp.di.iap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.philips.cdp.di.iap.R;
import com.philips.cdp.di.iap.eventhelper.EventHelper;
import com.philips.cdp.di.iap.response.orders.Orders;
import com.philips.cdp.di.iap.response.orders.ProductData;
import com.philips.cdp.di.iap.session.NetworkImageLoader;
import com.philips.cdp.di.iap.utils.IAPConstant;
import com.philips.cdp.di.iap.utils.Utility;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = OrderHistoryAdapter.class.getName();
    final private Context mContext;
    final private List<Orders> mOrders;
    final private List<ProductData> mProductDetails;
    private int mSelectedIndex;

    public OrderHistoryAdapter(final Context context, final List<Orders> orders, final List<ProductData> product) {
        mContext = context;
        mOrders = orders;
        mProductDetails = product;
        mSelectedIndex = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.iap_order_history_shopping_item, null);
        return new OrderHistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Orders order = mOrders.get(position);
        OrderHistoryHolder orderHistoryHolder = (OrderHistoryHolder) holder;
        orderHistoryHolder.mTime.setText(Utility.getFormattedDate(order.getPlaced()));
        String orderStatus = order.getStatusDisplay();
        orderHistoryHolder.mOrderState.setText(String.format(mContext.getString(R.string.iap_order_state), orderStatus.substring(0, 1).toUpperCase() + orderStatus.substring(1)));
        orderHistoryHolder.mOrderNumber.setText(String.format(mContext.getResources().getString(R.string.iap_order_number_msg), order.getCode()));

        for (ProductData data : mProductDetails) {
            if (data.getOrderCode() != null && data.getOrderCode().equals(order.getCode())) {
                //Inflate the Dynamic Layout Information View
                View hiddenInfo = View.inflate(mContext, R.layout.iap_order_history_product_details, null);
                orderHistoryHolder.mProductDetailsLayout.addView(hiddenInfo);
                ((TextView) hiddenInfo.findViewById(R.id.tv_productName)).setText(data.getProductTitle());
                String quantity = String.format(mContext.getString(R.string.iap_quantity), String.valueOf(data.getQuantity()));
                ((TextView) hiddenInfo.findViewById(R.id.tv_product_number)).setText(quantity);
                getNetworkImage(((NetworkImageView) hiddenInfo.findViewById(R.id.iv_product_image)),
                        data.getImageURL());
            }
        }
        if(order.getTotal() != null) {
            orderHistoryHolder.mTvtotalPrice.setText(order.getTotal().getFormattedValue());
        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        ((OrderHistoryHolder) holder).mProductDetailsLayout.removeAllViews();
    }

    private void getNetworkImage(final NetworkImageView imageView, final String imageUrl) {
        ImageLoader imageLoader;
        // Instantiate the RequestQueue.
        imageLoader = NetworkImageLoader.getInstance(mContext)
                .getImageLoader();

        imageLoader.get(imageUrl, ImageLoader.getImageListener(imageView,
                R.drawable.no_icon, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(imageUrl, imageLoader);
    }

    @Override
    public int getItemCount() {
        if (mOrders != null) {
            return mOrders.size();
        }
        return 0;
    }

    public int getSelectedPosition() {
        return mSelectedIndex;
    }

    public class OrderHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvtotalPrice;
        private TextView mTime;
        private TextView mOrderNumber;
        private TextView mOrderState;
        private LinearLayout mProductDetailsLayout;

        public OrderHistoryHolder(final View itemView) {
            super(itemView);
            mTvtotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
            mTime = (TextView) itemView.findViewById(R.id.tv_time);
            mOrderNumber = (TextView) itemView.findViewById(R.id.tv_order_number);
            mOrderState = (TextView) itemView.findViewById(R.id.tv_order_state);
            mProductDetailsLayout = (LinearLayout) itemView.findViewById(R.id.product_detail);
            mProductDetailsLayout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mSelectedIndex = getAdapterPosition();
            if (v.getId() == R.id.product_detail) {
                EventHelper.getInstance().notifyEventOccurred(IAPConstant.PURCHASE_HISTORY_DETAIL);
            }
        }
    }
}
