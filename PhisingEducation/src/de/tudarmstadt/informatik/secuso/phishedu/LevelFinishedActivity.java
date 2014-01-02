package de.tudarmstadt.informatik.secuso.phishedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;

public class LevelFinishedActivity extends SwipeActivity {
	protected void onStartClick() {
		BackendControllerImpl.getInstance().startLevel(getLevel() + 1);
	}

	@Override
	protected String startButtonText() {
		return "Weiter zu " + getResources().getString(BackendControllerImpl.getInstance().getLevelInfo(this.getLevel()+1).titleId);
	}
	
	@Override
	int getIcon() {
		return R.drawable.desktop;
	}

	@Override
	int getTitle(){
		return BackendControllerImpl.getInstance().getLevelInfo().titleId;
	};
	
	@Override
	int getSubTitle() {
		return R.string.finished;
	}
	
	@Override
	protected int getPageCount() {
		return BackendControllerImpl.getInstance().getLevelInfo(getLevel()).finishedLayouts.length;
	}

	@Override
	protected View getPage(int page, LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(BackendControllerImpl.getInstance().getLevelInfo(getLevel()).finishedLayouts[page],
				container, false);
		setScoreText(view);
		return view;
	}

	private void setScoreText(View view) {
		if (getLevel() > 1) {
			((TextView) view.findViewById(R.id.level_score)).setText(Integer.toString(BackendControllerImpl.getInstance().getLevelPoints()));
			((TextView) view.findViewById(R.id.total_score)).setText(Integer.toString(BackendControllerImpl.getInstance().getTotalPoints()));
		}
	}
	
	/**
	 * In Level0 back is not allowed to force the user to read the description.
	 */
	@Override
	boolean enableHomeButton() {
		return getLevel() != 0;
	}
}
