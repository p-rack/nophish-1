package de.tudarmstadt.informatik.secuso.phishedu.backend.attacks;

import de.tudarmstadt.informatik.secuso.phishedu.backend.BackendController;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishAttackType;
import de.tudarmstadt.informatik.secuso.phishedu.backend.PhishURLInterface;

/**
 * This Attack replaces the whole URL by a replace from phishtank
 * @author Clemens Bergmann <cbergmann@schuhklassert.de>
 *
 */
public class PhishTankURLAttack extends AbstractAttack {
	
	/**
	 * This constructor is required because of the implementation in {@link BackendController#getNextUrl()}
	 * @param object This Parmeter is discarded. It is replaced by a PhishTank URL
	 */
	public PhishTankURLAttack(PhishURLInterface object) {
		super(object);
		this.object=BackendController.getInstance().getPhishURL(PhishAttackType.AnyPhish);
	}

	@Override
	public PhishAttackType getAttackType() {
		return PhishAttackType.AnyPhish;
	}	
	
	/**
	 * As we don't know the structure of the url from phishTank. All parts might be phishy.
	 */
	@Override
	public boolean partClicked(int part) {
		return true;
	}
	
	
}
