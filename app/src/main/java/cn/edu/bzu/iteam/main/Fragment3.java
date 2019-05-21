package cn.edu.bzu.iteam.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import cn.edu.bzu.iteam.idingfang.R;

public class Fragment3 extends Fragment 
{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.guide_fragment3, null);
		ImageButton btn = (ImageButton) v.findViewById(R.id.ibtngo);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MainActivity.class);
				getActivity().startActivity(intent);
			}
		});
		return v;
	}
	
	

}
