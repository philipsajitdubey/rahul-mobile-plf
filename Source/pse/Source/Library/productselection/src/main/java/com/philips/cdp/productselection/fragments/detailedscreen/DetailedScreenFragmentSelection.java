package com.philips.cdp.productselection.fragments.detailedscreen;import android.app.ProgressDialog;import android.content.res.Configuration;import android.graphics.Typeface;import android.os.Bundle;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentTransaction;import android.support.v4.view.ViewPager;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.FrameLayout;import android.widget.LinearLayout;import android.widget.TextView;import com.philips.cdp.productselection.ProductModelSelectionHelper;import com.philips.cdp.productselection.R;import com.philips.cdp.productselection.fragments.detailedscreen.adapter.ProductAdapter;import com.philips.cdp.productselection.fragments.homefragment.ProductSelectionBaseFragment;import com.philips.cdp.productselection.fragments.savedscreen.SavedScreenFragmentSelection;import com.philips.cdp.productselection.prx.PrxAssetDataListener;import com.philips.cdp.productselection.prx.PrxWrapper;import com.philips.cdp.productselection.utils.Constants;import com.philips.cdp.productselection.utils.ProductSelectionLogger;import com.philips.cdp.prxclient.datamodels.assets.Asset;import com.philips.cdp.prxclient.datamodels.assets.AssetModel;import com.philips.cdp.prxclient.datamodels.assets.Assets;import com.philips.platform.uid.view.widget.DotNavigationIndicator;import java.util.ArrayList;import java.util.List;/** * This Fragments takes responsibility to show the complete detailed description of the * specific product with multiple images. * <p> * The Data it shows is from the Philips IT System. * * @author naveen@philips.com * @Date 28/01/2016 */public class DetailedScreenFragmentSelection extends ProductSelectionBaseFragment implements View.OnClickListener {    private static final String TAG = DetailedScreenFragmentSelection.class.getSimpleName();    private static ViewPager mViewpager = null;    private static DotNavigationIndicator mIndicater = null;    private static TextView mProductName = null;    private static TextView mProductCtn = null;    private static Button mSelectButton = null;    private String[] mDetailedScreenimage = null;    private ProgressDialog mAssetDialog = null;    private List<String> mSelectedProductImageList = null;    //private LinearLayout mDetailedScreenParentContainer, mDetailedScreenImageContainer = null;    //private LinearLayout.LayoutParams mDetailedScreenParams, mDetailedImageScreenParams = null;    private LinearLayout mParentDetailsView = null;    private FrameLayout.LayoutParams mParentDetailsViewParams = null;    private int mPortraitTablet = 0;    public DetailedScreenFragmentSelection() {    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        View view = inflater.inflate(R.layout.fragment_detailed_screen, container, false);        return view;    }    @Override    public void onActivityCreated(Bundle savedInstanceState) {        super.onActivityCreated(savedInstanceState);        ProductSelectionLogger.i(TAG, "Displaying product details Screen");        if(getActivity()==null ) {return;}        //mDetailedScreenParentContainer = (LinearLayout) getActivity().findViewById(R.id.detailed_screen_parent_one);        //mDetailedScreenParams = (LinearLayout.LayoutParams) mDetailedScreenParentContainer.getLayoutParams();        //mDetailedScreenImageContainer = (LinearLayout) getActivity().findViewById(R.id.detailed_screen_parent_image);        //mDetailedImageScreenParams = (LinearLayout.LayoutParams) mDetailedScreenImageContainer.getLayoutParams();        mViewpager = (ViewPager) getView().findViewById(R.id.detailedscreen_pager);        mIndicater = (DotNavigationIndicator) getView().findViewById(R.id.detailedscreen_indicator);        mProductName = (TextView) getView().findViewById(R.id.detailed_screen_productname);        mSelectButton = (Button) getView().findViewById(R.id.detailedscreen_select_button);        mProductCtn = (TextView) getView().findViewById(R.id.detailed_screen_productctn);        mParentDetailsView = (LinearLayout) getView().findViewById(R.id.detailed_screen_parent);        mParentDetailsViewParams = (FrameLayout.LayoutParams) mParentDetailsView.getLayoutParams();        mPortraitTablet = (int) getResources()                .getDimension(R.dimen.activity_margin_tablet_portrait);        if (/*isConnectionAvailable() && */(mUserSelectedProduct != null)) {            getProductImagesFromPRX();            String productTitle, ctn;            productTitle = mUserSelectedProduct.getData().getProductTitle();            ctn = mUserSelectedProduct.getData().getCtn();            mProductName.setText(productTitle);            mProductCtn.setText(ctn);            ProductSelectionLogger.i(TAG, "Selected Product Title is : " + productTitle);            ProductSelectionLogger.i(TAG, "Selected Product CTN Number is : " + ctn);        } else            ProductSelectionLogger.e(TAG, "Summary Model is not available to get the information");        mSelectedProductImageList = new ArrayList<String>();        mProductName.setTypeface(Typeface.DEFAULT_BOLD);        mSelectButton.setOnClickListener(this);        Configuration configuration = getResources().getConfiguration();        setViewParams(configuration);        tagPage();    }    private void initializeUi() {        mProductName.setText(mUserSelectedProduct.getData().getProductTitle());        mProductCtn.setText(mUserSelectedProduct.getData().getCtn());        getProductImagesFromPRX();    }    @Override    public void onConfigurationChanged(Configuration config) {        super.onConfigurationChanged(config);        setViewParams(config);    }    @Override    public void setViewParams(Configuration config) {        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {            mParentDetailsViewParams.leftMargin = mParentDetailsViewParams.rightMargin = mLeftRightMarginPort;        } else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {            mParentDetailsViewParams.leftMargin = mParentDetailsViewParams.rightMargin = mLeftRightMarginLand;        }        mParentDetailsView.setLayoutParams(mParentDetailsViewParams);    }    @Override    public String getActionbarTitle() {        if ((mUserSelectedProduct != null)) {            return mUserSelectedProduct.getData().getProductTitle();        } else {            return getResources().getString(R.string.pse_Find_Your_Product_Title);        }    }    @Override    public void onDestroyView() {        if (mAssetDialog != null) {            if (!(getActivity().isFinishing()) && mAssetDialog.isShowing()) {                mAssetDialog.dismiss();                mAssetDialog.cancel();            }        }        super.onDestroyView();    }    private void getProductImagesFromPRX() {        if (!(getActivity() != null && isAdded())) {            return;        }        if (mSelectedProductImageList != null) {            mSelectedProductImageList.clear();        }        if (mAssetDialog == null)            mAssetDialog = new ProgressDialog(getActivity(), R.style.loaderTheme);        mAssetDialog.setProgressStyle(android.R.style.Widget_Material_ProgressBar_Large);        mAssetDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.loader));        mAssetDialog.setCancelable(true);        if (!(getActivity().isFinishing()))            mAssetDialog.show();        PrxWrapper prxWrapperCode = new PrxWrapper(getActivity().getApplicationContext(), ProductModelSelectionHelper.getInstance().getAPPInfraInstance(), mUserSelectedProduct.getData().getCtn(),                ProductModelSelectionHelper.getInstance().getProductModelSelectionType().getSector(),                ProductModelSelectionHelper.getInstance().getProductModelSelectionType().getCatalog());        prxWrapperCode.requestPrxAssetData(new PrxAssetDataListener() {                                               @Override                                               public void onSuccess(AssetModel assetModel) {                                                   if (getActivity() != null)                                                       if (!(getActivity().isFinishing()) && mAssetDialog.isShowing()) {                                                           mAssetDialog.dismiss();                                                           mAssetDialog.cancel();                                                       }                                                   com.philips.cdp.prxclient.datamodels.assets.Data data = assetModel.getData();                                                   if (data != null) {                                                       Assets assets = data.getAssets();                                                       List<Asset> asset = assets.getAsset();                                                       for (Asset assetObject : asset) {                                                           String assetDescription = assetObject.getDescription();                                                           String assetResource = assetObject.getAsset();                                                           String assetExtension = assetObject.getType();                                                           if ((assetExtension.equalsIgnoreCase(Constants.DETAILEDSCREEN_PRIDUCTIMAGES_APP)) || (assetExtension.equalsIgnoreCase(Constants.DETAILEDSCREEN_PRIDUCTIMAGES_DPP)) || (assetExtension.equalsIgnoreCase(Constants.DETAILEDSCREEN_PRIDUCTIMAGES_MI1) || (assetExtension.equalsIgnoreCase(Constants.DETAILEDSCREEN_PRIDUCTIMAGES_PID)) || (assetExtension.equalsIgnoreCase(Constants.DETAILEDSCREEN_PRIDUCTIMAGES_RTP)))) {                                                               if (assetResource != null) {                                                                   //  mSelectedProductImageList.add(assetResource.replace("/content/", "/image/") + "?wid=" + (int) (getResources().getDimension(R.dimen.productdetails_screen_image) / getResources().getDisplayMetrics().density) + "&amp;");                                                                   mSelectedProductImageList.add(assetResource.replace("/content/", "/image/"));                                                               }                                                           }                                                       }                                                   }                                                   ProductSelectionLogger.i(TAG, "Found " + mSelectedProductImageList.size() + " images for products\n : ");                                                   mDetailedScreenimage = new String[mSelectedProductImageList.size()];                                                   for (int i = 0; i < mSelectedProductImageList.size(); i++) {                                                       if (i < 5)                                                           mDetailedScreenimage[i] = mSelectedProductImageList.get(i);                                                   }                                                   try {                                                       mViewpager.setAdapter(new ProductAdapter(getChildFragmentManager(), mDetailedScreenimage));                                                       mIndicater.setViewPager(mViewpager);                                                   } catch (IllegalStateException e) {                                                       ProductSelectionLogger.e(TAG, "Unable to set adapater. java.lang.IllegalStateException: Activity has been destroyed");                                                   } catch (Exception e) {                                                       ProductSelectionLogger.e(TAG, "Crash controller + " + e);                                                   }                                               }                                               @Override                                               public void onFail(String errorMessage) {                                                   if (getActivity() != null)                                                       if (!(getActivity().isFinishing()) && mAssetDialog.isShowing()) {                                                           mAssetDialog.dismiss();                                                           mAssetDialog.cancel();                                                       }                                                   ProductSelectionLogger.d(TAG, " Images downloading failed for the Product " + mUserSelectedProduct.getData().getProductTitle());                                               }                                           }                , TAG);    }    /*private void removeDetailsScreen() {        FragmentManager fragManager = getActivity().getSupportFragmentManager();        List<Fragment> listFragment = fragManager.getFragments();        for (int i = listFragment.size() - 1; i >= 0; i--) {            Fragment fragment = listFragment.get(i);            try {                if (fragment != null && fragment instanceof SavedScreenFragmentSelection) {                    fragManager.popBackStack();                }            } catch (IllegalStateException e) {            }        }    }*/    @Override    public void onClick(View v) {        if (v.getId() == R.id.detailedscreen_select_button && isConnectionAvailable()) {            if (mUserSelectedProduct != null) {                /*Tagging.trackAction(Constants.ACTION_KEY_SEND_DATA, Constants.                        ACTION_NAME_PRODUCT_SELECTED,                        mUserSelectedProduct.getData().getProductTitle());*/                ProductModelSelectionHelper.getInstance().getTaggingInterface().trackActionWithInfo                        (Constants.ACTION_KEY_SEND_DATA, Constants.                                        ACTION_NAME_PRODUCT_SELECTED,                                mUserSelectedProduct.getData().getProductTitle());            }            SavedScreenFragmentSelection savedScreenFragmentSelection = new SavedScreenFragmentSelection();            savedScreenFragmentSelection.setUserSelectedProduct(mUserSelectedProduct);            showFragment(savedScreenFragmentSelection);        }    }    private void replaceFragmentForTablet(Fragment fragment) {        List<Fragment> listFragment = getActivity().getSupportFragmentManager().getFragments();        for (int i = listFragment.size() - 1; i >= 0; i--) {            Fragment fragment1 = listFragment.get(i);         /*   ProductSelectionLogger.i("testing", "Detils Screen : " + fragment1);*/        }        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();        Fragment fragmentDetailsTablet = getActivity().getSupportFragmentManager().findFragmentByTag("DetailedScreenFragmentSelection");      /*  ProductSelectionLogger.i("testing", "Details screen found  : " + fragmentDetailsTablet);*/        try {            if (fragmentDetailsTablet != null) {                fragmentTransaction.remove(fragmentDetailsTablet);                fragmentTransaction.commitAllowingStateLoss();                List<Fragment> listFragment1 = getActivity().getSupportFragmentManager().getFragments();                for (int i = listFragment1.size() - 1; i >= 0; i--) {                    Fragment fragment1 = listFragment1.get(i);                   /* ProductSelectionLogger.i("testing", "Details Screen : " + fragment1);*/                }            }        } catch (IllegalStateException e) {        }        try {            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();            ft.add(R.id.fragmentTabletProductDetailsParent, fragment, "SavedScreenFragmentSelection");            ft.commit();        } catch (IllegalStateException e) {        }    }    private void tagPage() {        if (getPreviousName() == null) {            trackFirstPage(Constants.PAGE_DETAILS_SCREEN);        } else {            //  Tagging.trackPage(Constants.PAGE_DETAILS_SCREEN, getPreviousName());            ProductModelSelectionHelper.getInstance().getTaggingInterface().                    trackPageWithInfo(Constants.PAGE_DETAILS_SCREEN, getPreviousName(),                            getPreviousName());        }        setPreviousPageName(Constants.PAGE_DETAILS_SCREEN);    }    public void trackFirstPage(String currPage) {        // When welcome screen is not shown then this screen will be the first page to tag.       /* if (null != Tagging.getLaunchingPageName()) {            Tagging.trackPage(currPage, Tagging.getLaunchingPageName());        } else {            Tagging.trackPage(currPage, getPreviousName());        }*/        if (currPage != null) {            ProductModelSelectionHelper.getInstance().getTaggingInterface().trackPageWithInfo                    (currPage, getPreviousName(), getPreviousName());        }    }}