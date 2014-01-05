package de.tudarmstadt.informatik.secuso.phishedu;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.NoPhishLevelInfo;

public class LevelSelectorActivity extends SwipeActivity implements
		ViewPager.OnPageChangeListener {

	@Override
	int getTitle(){
		return R.string.button_level_overview;
	}
	
	@Override
	protected int getPageCount() {
		return BackendControllerImpl.getInstance().getLevelCount();
	}

	@Override
	protected View getPage(int level, LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		NoPhishLevelInfo level_info = BackendControllerImpl.getInstance().getLevelInfo(level);
		View layoutView = inflater.inflate(R.layout.fragment_level_overview_template, container, false);
		int userlevel = BackendControllerImpl.getInstance().getMaxUnlockedLevel();
		View button = layoutView.findViewById(R.id.levelbutton);
		if (level <= userlevel) {
			button.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.levelicon_active_bg));
			layoutView.findViewById(R.id.levelbutton_padlock).setVisibility(
					View.INVISIBLE);
			if(BackendControllerImpl.getInstance().getLevelCompleted(level)){
				layoutView.findViewById(R.id.levelbutton_tick).setVisibility(View.VISIBLE);
				layoutView.findViewById(R.id.levelbutton_points).setVisibility(View.VISIBLE);
				layoutView.findViewById(R.id.levelbutton_points_text).setVisibility(View.VISIBLE);
			}else{
				layoutView.findViewById(R.id.levelbutton_tick).setVisibility(View.INVISIBLE);
				layoutView.findViewById(R.id.levelbutton_points).setVisibility(View.INVISIBLE);
				layoutView.findViewById(R.id.levelbutton_points_text).setVisibility(View.INVISIBLE);
			}
		} else {
			button.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.levelicon_inactive_bg));
			layoutView.findViewById(R.id.levelbutton_padlock).setVisibility(
					View.VISIBLE);
			layoutView.findViewById(R.id.levelbutton_tick).setVisibility(
					View.INVISIBLE);
			layoutView.findViewById(R.id.levelbutton_points).setVisibility(
					View.INVISIBLE);
			layoutView.findViewById(R.id.levelbutton_points_text).setVisibility(
					View.INVISIBLE);
		}
		TextView levelnumber = (TextView) layoutView.findViewById(R.id.levelbutton_text);
		levelnumber.setText(level_info.levelNumber);
		TextView levelTitle = (TextView) layoutView.findViewById(R.id.level_title);
		levelTitle.setText(level_info.titleId);
		TextView levelDescription = (TextView) layoutView.findViewById(R.id.level_description);
		levelDescription.setText(level_info.subTitleId);
		((TextView) layoutView.findViewById(R.id.levelbutton_points)).setText(Integer.toString(level_info.getlevelPoints()));
		if(level<=1 || level==11){
			layoutView.findViewById(R.id.levelbutton_points).setVisibility(View.INVISIBLE);
			layoutView.findViewById(R.id.levelbutton_points_text).setVisibility(View.INVISIBLE);
		}

		return layoutView;
	}

	@Override
	public void onClickPage(int page) {
		if (page <= BackendControllerImpl.getInstance().getMaxUnlockedLevel()) {
			if (page == 0 && BackendControllerImpl.getInstance().getMaxUnlockedLevel() > 0 && !Constants.ALLOW_REPEAT_AWARENESS) {
				// level 0 cannot be replayed show finished instead.
				Bundle args = new Bundle();
				args.putInt(Constants.ARG_LEVEL, 0);
				args.putBoolean(Constants.ARG_ENABLE_HOME, true);
				switchToFragment(LevelFinishedActivity.class, args);
			} else {
				BackendControllerImpl.getInstance().startLevel(page);
			}
		}
	};
}
