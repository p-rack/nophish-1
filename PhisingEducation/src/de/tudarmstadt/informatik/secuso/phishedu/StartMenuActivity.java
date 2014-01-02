package de.tudarmstadt.informatik.secuso.phishedu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendControllerImpl;
import de.tudarmstadt.informatik.secuso.phishedu.backend.MainActivity;

/**
 * 
 * @author Gamze Canova this activity respresents the main menu of the app
 */
public class StartMenuActivity extends PhishBaseActivity {
	
	@Override
	public View getLayout(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.start_menu, container,false);
		
		if (BackendControllerImpl.getInstance().getMaxUnlockedLevel() > 0) {
			TextView startbutton = (TextView) v.findViewById(R.id.menu_button_play);
			startbutton.setText(R.string.button_play_on);
		}
		
		TextView version = (TextView) v.findViewById(R.id.version);
		PackageInfo pInfo;
		try {
			pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			version.setText(pInfo.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	@Override
	public int[] getClickables() {
		return new int[]{
			R.id.menu_button_about,
			R.id.menu_button_level_overview,
			R.id.menu_button_more_info,
			R.id.menu_button_play,
			R.id.menu_button_social,
		};
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.menu_button_about:
			((MainActivity)getActivity()).switchToFragment(AboutActivity.class);
			break;
		case R.id.menu_button_level_overview:
			((MainActivity)getActivity()).switchToFragment(LevelSelectorActivity.class);
			break;
		case R.id.menu_button_more_info:
			((MainActivity)getActivity()).switchToFragment(MoreInfoActivity.class);
			break;
		case R.id.menu_button_play:
			int userlevel = BackendControllerImpl.getInstance().getMaxUnlockedLevel();
			if(userlevel == BackendControllerImpl.getInstance().getLevelCount() - 1){
				((MainActivity)getActivity()).switchToFragment(AppEndActivity.class);
			}else{
				BackendControllerImpl.getInstance().startLevel(userlevel);
			}
			break;
		case R.id.menu_button_social:
			((MainActivity)getActivity()).switchToFragment(GooglePlusActivity.class);
			break;
			
		}
	}
	
	@Override
	public void onBackPressed() {
		showExitPopup();
	}

	private void showExitPopup() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

		// Setting Dialog Title
		alertDialog.setTitle(getString(R.string.end_app));

		// Setting Dialog Message
		alertDialog.setMessage(getString(R.string.end_app_text));

		alertDialog.setPositiveButton(R.string.end_app_yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				});

		alertDialog.setNegativeButton(R.string.end_app_no,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();

	}
	
	@Override
	public boolean enableHomeButton() {
		return false;
	}
}
