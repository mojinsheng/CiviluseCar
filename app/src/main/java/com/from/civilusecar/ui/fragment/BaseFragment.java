package com.from.civilusecar.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


/**
 * 基类碎片
 * @author Jesse
 *
 */
public abstract class BaseFragment extends Fragment implements OnClickListener{
	public Context mContext;
//	protected SdkManager mManager;
//	protected AdsEventListener mEvent;
	protected View mView;
	protected abstract View getContentView(LayoutInflater inflater, ViewGroup container,
										   Bundle savedInstanceState);
	/**
	 * 初始化控件
	 */
	protected abstract void initViews();
    /**
     * 初始化数据
     */
	protected abstract void initDatas();
	/**
	 * 初始化监听
	 */
	protected abstract void initListeners();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = getActivity();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = getContentView(inflater,container,savedInstanceState);
		return mView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initDatas();
		initListeners();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 销毁页面
	 */
	public void finishActivity(){
		this.getActivity().finish();
	}
	/**
	 * 获取上下文对象
	 * @return
	 */
	public Context getContext(){
		return mContext;
	}

	

	
//	/**
//	 * 返回
//	 */
//	public void finishFragment(){
//		((MainActivity)getActivity()).getCurrentStackFragment().goBack();
//	}
//	/**
//	 * 启动 fragment
//	 * @param fragment
//	 * @param tag
//	 */
//	public void startFragment(Fragment fragment,String tag){
//		((MainActivity)getActivity()).addFragment(fragment, tag);
//	}
//	/**
//	 * 启动 fragment
//	 * @param fragment
//	 * @param tag
//	 * @param values
//	 */
//	public void startFragment(Fragment fragment,String tag,String values){
//		((MainActivity)getActivity()).addFragment(fragment, tag,values);
//	}
}
